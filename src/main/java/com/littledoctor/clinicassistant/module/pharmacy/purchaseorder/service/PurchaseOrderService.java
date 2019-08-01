package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrder;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrderSingle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
public interface PurchaseOrderService {

    /**
     * 分页查询订单
     * @param page
     * @param purchaseOrderCode
     * @param purchaseOrderDate
     * @param supplierId
     * @return
     */
    Page<PurchaseOrderSingle> queryPage(Pageable page, String purchaseOrderCode, String purchaseOrderDate, String supplierId) throws Exception;

    /**
     * 保存采购单
     * @param purchaseOrder
     * @return
     */
    PurchaseOrder save(PurchaseOrder purchaseOrder);

    /**
     * 根据采购单id查询采购单
     * @param purchaseOrderId
     * @return
     */
    PurchaseOrder queryById(String purchaseOrderId) throws Exception;

    /**
     * 删除采购单
     * @param purchaseOrderId
     * @return
     */
    boolean delete(String purchaseOrderId) throws Exception;

    /**
     * 根据ID更新入库状态，将入库状态改为true
     * @param purchaseOrderIds
     */
    boolean updateEntry(HashSet<String> purchaseOrderIds) throws Exception;
}
