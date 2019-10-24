package com.littledoctor.clinicassistant.module.record.entity;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-09-21 17:47
 * @Description: 病历实体，用于接收前台保存参数
 */
public class MedicalRecordVo {

    // 患者信息
    private MedicalRecord medicalRecord;

    // 处方
    private List<RxVo> rxVoList;

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public List<RxVo> getRxVoList() {
        return rxVoList;
    }

    public void setRxVoList(List<RxVo> rxVoList) {
        this.rxVoList = rxVoList;
    }
}
