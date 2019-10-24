package com.littledoctor.clinicassistant.module.record.service;

import com.littledoctor.clinicassistant.module.record.entity.MedicalRecordVo;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:25
 * @Description:
 */
public interface MedicalRecordService {

    /**
     * 保存 病历
     * @param medicalRecordVo
     * @return
     */
    MedicalRecordVo save(MedicalRecordVo medicalRecordVo) throws Exception;
}
