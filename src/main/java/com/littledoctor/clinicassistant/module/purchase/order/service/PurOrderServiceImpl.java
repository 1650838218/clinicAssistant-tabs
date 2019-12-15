package com.littledoctor.clinicassistant.module.purchase.order.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.module.purchase.item.entity.PurItemEntity;
import com.littledoctor.clinicassistant.module.purchase.item.service.PurItemService;
import com.littledoctor.clinicassistant.module.purchase.order.dao.PurOrderRepository;
import com.littledoctor.clinicassistant.module.purchase.order.dao.PurOrderSingleRepository;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrder;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrderDetail;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrderSingle;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import com.littledoctor.clinicassistant.module.purchase.supplier.service.SupplierService;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
@Service
public class PurOrderServiceImpl implements PurOrderService {

    @Autowired
    private PurOrderRepository purOrderRepository;

    @Autowired
    private PurOrderSingleRepository purOrderSingleRepository;

    @Autowired
    private PurItemService purItemService;

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
    public Page<PurOrderSingle> queryPage(Pageable page, String purchaseOrderCode, String purchaseOrderDate, String supplierId) throws Exception {
        Page<PurOrderSingle> purchaseOrderPage = purOrderSingleRepository.findAll(new Specification<PurOrderSingle>() {
            @Override
            public Predicate toPredicate(Root<PurOrderSingle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
        List<PurOrderSingle> pol = purchaseOrderPage.getContent();
        if (pol != null && pol.size() > 0) {
            for (int i = 0, len = pol.size(); i < len; i++) {
                PurOrderSingle pos = pol.get(i);
                if (pos.getSupplierId() != null) {
                    SupplierEntity s = supplierService.findById(String.valueOf(pos.getSupplierId()));
                    if (s != null) pos.setSupplierName(s.getSupplierName());
                }
            }
        }
        return purchaseOrderPage;
    }

    /**
     * 保存采购单
     * @param purOrder
     * @return
     */
    @Override
    public PurOrder save(PurOrder purOrder) {
        purOrder.setCreateTiem(new Date());
        purOrder.setEntry(false);
        purOrder.setUpdateTime(new Date());
        return purOrderRepository.saveAndFlush(purOrder);
    }

    /**
     * 根据采购单ID查询采购单
     * @param purOrderId
     * @return
     */
    @Override
    public PurOrder queryById(String purOrderId) throws Exception {
        PurOrder purOrder = purOrderRepository.findById(Long.parseLong(purOrderId)).get();
        if (purOrder != null) {
            // 设置供应商名称
            SupplierEntity supplierEntity = supplierService.findById(String.valueOf(purOrder.getSupplierId()));
            purOrder.setSupplierName(supplierEntity.getSupplierName());
            List<PurOrderDetail> pods = purOrder.getPurOrderDetails();
            if (pods != null && pods.size() > 0) {
                // 查询字典显示值
                Map<String, String> sldw = dictionaryService.getItemMapByKey(DictionaryKey.PUR_ITEM_JHBZ);
                Map<String, String> kcdw = dictionaryService.getItemMapByKey(DictionaryKey.PUR_ITEM_LSDW);
                for (int i = 0, len = pods.size(); i < len; i++) {
                    PurOrderDetail pbi = pods.get(i);
                    // 查询药品信息
                    if (pbi.getPurItemId() != null) {
                        PurItemEntity pi = purItemService.getById(String.valueOf(pbi.getPurItemId()));
                        if (pi != null) {
                            pbi.setPurItemName(pi.getPurItemName());
                            pbi.setUnitConvert(pi.getUnitConvert());
                            // 计算库存量
                            if (pbi.getPurCount() != null && pbi.getUnitConvert() != null) {
                                pbi.setStockCount(pbi.getPurCount().multiply(new BigDecimal(pbi.getUnitConvert())));
                            }
                            // 设置数量单位名称
                            if (sldw != null) pbi.setPurUnitName(sldw.get(pi.getPurUnit()));
                            if (kcdw != null) pbi.setStockUnitName(kcdw.get(pi.getStockUnit()));
                        }
                    }
                }
            }
        }
        return purOrder;
    }

    /**
     * 删除采购单
     * @param purOrderId
     * @return
     */
    @Override
    public boolean delete(String purOrderId) throws Exception {
        if (StringUtils.isNotBlank(purOrderId)) {
            purOrderRepository.deleteById(Long.parseLong(purOrderId));
            return true;
        }
        return false;
    }

    /**
     * 更新入库状态为已入库
     * @param purchaseOrderIds
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateEntry(HashSet<String> purchaseOrderIds) throws Exception {
        if (purchaseOrderIds != null && !purchaseOrderIds.isEmpty()) {
            String ids = "";
            Iterator iterator = purchaseOrderIds.iterator();
            while (iterator.hasNext()) {
                ids += iterator.next().toString();
                if (iterator.hasNext()) ids += ",";
            }
            purOrderRepository.updateEntry(ids);
            return true;
        }
        return false;
    }
}
