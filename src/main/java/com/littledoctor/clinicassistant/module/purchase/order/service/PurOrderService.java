package com.littledoctor.clinicassistant.module.purchase.order.service;

import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
public interface PurOrderService {

    /**
     * 分页查询订单
     * @param page
     * @param purItemName
     * @param purchaseOrderDate
     * @param supplierId
     * @return
     */
    Page<PurOrder> queryPage(Pageable page, String purItemName, String purchaseOrderDate, String supplierId) throws Exception;

    /**
     * 保存采购单
     * @param purOrder
     * @return
     */
    PurOrder save(PurOrder purOrder);

    /**
     * 根据采购单id查询采购单
     * @param purchaseOrderId
     * @return
     */
    PurOrder queryById(String purchaseOrderId) throws Exception;

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
