package com.littledoctor.clinicassistant.module.record.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 周俊林
 * @Date: 2019-10-27 16:33
 * @Description:
 */
@Mapper
public interface MedicalRecordMapper {

    /**
     * 删除某病历下的所有处方
     * @param recordId
     */
    void deleteMedicalRecordRx(@Param("recordId") Long recordId);
}
