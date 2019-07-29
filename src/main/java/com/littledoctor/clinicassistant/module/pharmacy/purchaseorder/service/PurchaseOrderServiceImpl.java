package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service;

import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity.PharmacyItem;
import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.service.PharmacyItemService;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.dao.PurchaseOrderRepository;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.dao.PurchaseOrderSingleRepository;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrder;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrderDetail;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrderSingle;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.entity.Supplier;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.service.SupplierService;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryItem;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryType;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderSingleRepository purchaseOrderSingleRepository;

    @Autowired
    private PharmacyItemService pharmacyItemService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private SupplierService supplierService;

    /**
     * 分页查询订单
     * @param page
     * @param purchaseOrderCode
     * @param purchaseOrderDate
     * @param supplierId
     * @return
     * @throws Exception
     */
    @Override
    public Page<PurchaseOrderSingle> queryPage(Pageable page, String purchaseOrderCode, String purchaseOrderDate, String supplierId) throws Exception {
        Page<PurchaseOrderSingle> purchaseOrderPage = purchaseOrderSingleRepository.findAll(new Specification<PurchaseOrderSingle>() {
            @Override
            public Predicate toPredicate(Root<PurchaseOrderSingle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (StringUtils.isNotBlank(purchaseOrderCode)) {
                    predicateList.add(criteriaBuilder.equal(root.get("purchaseOrderCode"), purchaseOrderCode));
                }
                if (StringUtils.isNotBlank(purchaseOrderDate)) {
                    String[] dates = purchaseOrderDate.split(" - ");
                    if (dates != null && dates.length == 2) {
                        predicateList.add(criteriaBuilder.between(root.get("purchaseOrderDate"), dates[0], dates[1]));
                    }
                }
                if (StringUtils.isNotBlank(supplierId)) {
                    predicateList.add(criteriaBuilder.equal(root.get("supplierId"), supplierId));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }, page);
        // 设置供应商名称
        List<PurchaseOrderSingle> pol = purchaseOrderPage.getContent();
        if (pol != null && pol.size() > 0) {
            for (int i = 0, len = pol.size(); i < len; i++) {
                PurchaseOrderSingle pos = pol.get(i);
                if (pos.getSupplierId() != null) {
                    Supplier s = supplierService.findById(String.valueOf(pos.getSupplierId()));
                    if (s != null) pos.setSupplierName(s.getSupplierName());
                }
            }
        }
        return purchaseOrderPage;
    }

    /**
     * 保存采购单
     * @param purchaseOrder
     * @return
     */
    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        purchaseOrder.setCreateTiem(new Date());
        purchaseOrder.setEntry(false);
        purchaseOrder.setUpdateTime(new Date());
        return purchaseOrderRepository.saveAndFlush(purchaseOrder);
    }

    /**
     * 根据采购单ID查询采购单
     * @param purchaseOrderId
     * @return
     */
    @Override
    public PurchaseOrder queryById(String purchaseOrderId) throws Exception {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(Integer.parseInt(purchaseOrderId)).get();
        if (purchaseOrder != null) {
            // 设置供应商名称
            Supplier supplier = supplierService.findById(String.valueOf(purchaseOrder.getSupplierId()));
            purchaseOrder.setSupplierName(supplier.getSupplierName());
            List<PurchaseOrderDetail> pods = purchaseOrder.getPurchaseOrderDetails();
            if (pods != null && pods.size() > 0) {
                // 查询字典显示值
                DictionaryType dt = dictionaryService.getByKey("SLDW");
                for (int i = 0, len = pods.size(); i < len; i++) {
                    PurchaseOrderDetail pbi = pods.get(i);
                    // 查询药品信息
                    if (pbi.getPharmacyItemId() != null) {
                        PharmacyItem pi = pharmacyItemService.getById(String.valueOf(pbi.getPharmacyItemId()));
                        if (pi != null) {
                            pbi.setPharmacyItemName(pi.getPharmacyItemName());
                            pbi.setManufacturer(pi.getManufacturer());
                            pbi.setSpecifications(pi.getSpecifications());
                        }
                    }
                    // 设置数量单位名称
                    if (dt != null && dt.getDictItem() != null && dt.getDictItem().size() > 0) {
                        if (pbi.getPurchaseUnit() != null) {
                            for (int j = 0; j < dt.getDictItem().size(); j++) {
                                DictionaryItem di = dt.getDictItem().get(j);
                                if (pbi.getPurchaseUnit().equals(Integer.valueOf(di.getDictItemValue()))) {
                                    pbi.setPurchaseUnitName(di.getDictItemName());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return purchaseOrder;
    }

    /**
     * 删除采购单
     * @param purchaseOrderId
     * @return
     */
    @Override
    public boolean delete(String purchaseOrderId) throws Exception {
        if (StringUtils.isNotBlank(purchaseOrderId)) {
            purchaseOrderRepository.deleteById(Integer.parseInt(purchaseOrderId));
            return true;
        }
        return false;
    }

    /**
     * 根据id查询采购单，用于入库单
     * @param purchaseOrderId
     * @return
     */
    @Override
    public PurchaseOrder queryByIdForEntry(String purchaseOrderId) throws Exception {
        if (StringUtils.isNotBlank(purchaseOrderId)) {
            PurchaseOrder po = this.queryById(purchaseOrderId);
            if (po != null) {
                // 查询库存单位，换算单位，计算库存量，计算售价
                List<PurchaseOrderDetail> details = po.getPurchaseOrderDetails();
                // 查询字典显示值
                DictionaryType dt = dictionaryService.getByKey("SLDW");
                if (details != null && details.size() > 0 && dt != null && dt.getDictItem() != null && dt.getDictItem().size() > 0) {
                    for (int i = 0, len = details.size(); i < len; i++) {
                        PurchaseOrderDetail pod = details.get(i);
                        if (pod != null) {
                            PharmacyItem pi = pharmacyItemService.getById(String.valueOf(pod.getPharmacyItemId()));
                            if (pi != null) {
                                // 设置数量单位名称
                                if (pi.getStockUnit() != null) {
                                    for (int j = 0; j < dt.getDictItem().size(); j++) {
                                        DictionaryItem di = dt.getDictItem().get(j);
                                        if (pi.getStockUnit().equals(Integer.valueOf(di.getDictItemValue()))) {
                                            pod.setStockUnitName(di.getDictItemName());// 库存单位
                                            // TODO 查询换算单位，计算库存量
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return po;
        }
        return null;
    }
}
