package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.dao;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrderSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-27 23:31
 * @Description:
 */
public interface PurchaseOrderSingleRepository extends JpaRepository<PurchaseOrderSingle, Integer>, JpaSpecificationExecutor<PurchaseOrderSingle> {
}
