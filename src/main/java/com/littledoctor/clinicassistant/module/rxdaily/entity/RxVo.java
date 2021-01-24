package com.littledoctor.clinicassistant.module.rxdaily.entity;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-10-13 16:24
 * @Description:
 */
public class RxVo {
    private MedicalRecordRx medicalRecordRx;

    private List<MedicalRecordRxDetail> detailList;

    public MedicalRecordRx getMedicalRecordRx() {
        return medicalRecordRx;
    }

    public void setMedicalRecordRx(MedicalRecordRx medicalRecordRx) {
        this.medicalRecordRx = medicalRecordRx;
    }

    public List<MedicalRecordRxDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<MedicalRecordRxDetail> detailList) {
        this.detailList = detailList;
    }
}
