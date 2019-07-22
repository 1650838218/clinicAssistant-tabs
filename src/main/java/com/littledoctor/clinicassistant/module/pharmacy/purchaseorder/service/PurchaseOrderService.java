package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
public interface PurchaseOrderService {

    /**
     * 分页查询订单
     * @param page
     * @param purchaseBillCode
     * @param purchaseBillDate
     * @param supplierId
     * @return
     */
    Page<PurchaseOrder> queryPage(Pageable page, String purchaseBillCode, String purchaseBillDate, String supplierId) throws Exception;

    /**
     * 保存采购单
     * @param purchaseOrder
     * @return
     */
    PurchaseOrder save(PurchaseOrder purchaseOrder);

    /**
     * 根据采购单id查询采购单
     * @param purchaseBillId
     * @return
     */
    PurchaseOrder queryById(String purchaseBillId);

    /**
     * 删除采购单
     * @param purchaseBillId
     * @return
     */
    boolean delete(String purchaseBillId);
}
