package com.littledoctor.clinicassistant.module.purchase.stock.dao;

import com.littledoctor.clinicassistant.module.purchase.stock.entity.PurStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:29
 * @Description: 入库单
 */
@Repository
public interface PurStockRepository extends JpaRepository<PurStock, Long>, JpaSpecificationExecutor<PurStock> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE pur_stock a SET a.stock_state = 3 WHERE a.expire_date > date_format(NOW(),'%Y-%m-%d') AND a.expire_date IS NOT NULL AND LENGTH(a.expire_date) != 0 AND a.stock_state = 1")
    int updateStateForExpire();
}
