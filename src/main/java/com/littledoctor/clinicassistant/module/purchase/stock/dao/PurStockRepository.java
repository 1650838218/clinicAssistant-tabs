package com.littledoctor.clinicassistant.module.purchase.stock.dao;

import com.littledoctor.clinicassistant.module.purchase.stock.entity.PurStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:29
 * @Description: 入库单
 */
@Repository
public interface PurStockRepository extends JpaRepository<PurStock, Long>, JpaSpecificationExecutor<PurStock> {
}
