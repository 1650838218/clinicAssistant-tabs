package com.littledoctor.clinicassistant.module.rxdaily.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: 周俊林
 * @Date: 2019-10-27 16:33
 * @Description:
 */
@Mapper
public interface RxDailyMapper {

    /**
     * 删除某病历下的所有处方
     * @param recordId
     */
    void deleteMedicalRecordRx(@Param("recordId") Long recordId);
}
