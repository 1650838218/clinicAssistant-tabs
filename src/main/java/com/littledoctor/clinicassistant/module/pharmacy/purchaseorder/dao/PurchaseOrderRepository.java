package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.dao;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-06 13:53
 * @Description:
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>, JpaSpecificationExecutor<PurchaseOrder> {
}
