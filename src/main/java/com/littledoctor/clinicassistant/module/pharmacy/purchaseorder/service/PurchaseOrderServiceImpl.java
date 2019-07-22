package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.dao.PurchaseOrderRepository;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrder;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.entity.Supplier;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.service.SupplierService;
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
    public Page<PurchaseOrder> queryPage(Pageable page, String purchaseOrderCode, String purchaseOrderDate, String supplierId) throws Exception {
        Page<PurchaseOrder> purchaseOrderPage = purchaseOrderRepository.findAll(new Specification<PurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<PurchaseOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
        /*if (purchaseOrderPage.getContent() != null && purchaseOrderPage.getContent().size() > 0) {
            List<Supplier> supplierList = supplierService.findAll();
            if (supplierList != null && supplierList.size() > 0) {
                for (int i = 0; i < purchaseOrderPage.getContent().size(); i++) {
                    PurchaseOrder pb = purchaseOrderPage.getContent().get(i);
                    for (int j = 0; j < supplierList.size(); j++) {
                        Supplier supplier = supplierList.get(j);
                        if (pb.getSupplierId().equals(supplier.getSupplierId())) {
                            pb.setSupplierName(supplier.getSupplierName());
                        }
                    }
                }
            }
        }*/
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
    public PurchaseOrder queryById(String purchaseOrderId) {
        return purchaseOrderRepository.findById(Integer.parseInt(purchaseOrderId)).get();
    }

    /**
     * 删除采购单
     * @param purchaseOrderId
     * @return
     */
    @Override
    public boolean delete(String purchaseOrderId) {
        if (StringUtils.isNotBlank(purchaseOrderId)) {
            purchaseOrderRepository.deleteById(Integer.parseInt(purchaseOrderId));
            return true;
        }
        return false;
    }
}
