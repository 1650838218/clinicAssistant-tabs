package com.littledoctor.clinicassistant.module.rxdaily.dao;

import com.littledoctor.clinicassistant.module.rxdaily.entity.RxDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:27
 * @Description:
 */
public interface RxDailyRepository extends JpaRepository<RxDaily, Long>, JpaSpecificationExecutor<RxDaily> {

    /**
     * 获取下一个号码
     * @return
     */
    @Query(value = "select count(1) + 1 from RX_DAILY where DATE_FORMAT(ARRIVE_TIME,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')", nativeQuery = true)
    int getRegisterNumber();
}
