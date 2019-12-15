package com.littledoctor.clinicassistant.module.purchase.order.dao;

import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-06 13:53
 * @Description: 采购单 进货单
 */
public interface PurOrderRepository extends JpaRepository<PurOrder, Long>, JpaSpecificationExecutor<PurOrder> {

    /**
     * 根据ID将采购单的状态改为已入库
     * @param purchaseOrderIds
     */
    @Modifying
    @Query(value = "update PURCHASE_ORDER set IS_ENTRY = 1 where PURCHASE_ORDER_ID in (?1)", nativeQuery = true)
    int updateEntry(String purchaseOrderIds);
}
