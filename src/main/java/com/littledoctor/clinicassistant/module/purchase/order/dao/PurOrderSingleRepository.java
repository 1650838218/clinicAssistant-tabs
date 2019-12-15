package com.littledoctor.clinicassistant.module.purchase.order.dao;

import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrderSingle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-27 23:31
 * @Description:
 */
public interface PurOrderSingleRepository extends JpaRepository<PurOrderSingle, Long>, JpaSpecificationExecutor<PurOrderSingle> {
}
