package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.dao;

import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-06 13:53
 * @Description:
 */
public interface PurchaseBillRepository extends JpaRepository<PurchaseBill, Integer>, JpaSpecificationExecutor<PurchaseBill> {
}
