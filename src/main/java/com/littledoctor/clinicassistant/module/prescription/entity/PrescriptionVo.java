package com.littledoctor.clinicassistant.module.prescription.entity;

/**
 * @业务信息: 处方vo对象，只用于mvc接收数据
 * @Filename: PrescriptionVo.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-06   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-06   周俊林
 */
public class PrescriptionVo {

    private Prescription prescription;

    private RxCatalogue rxCatalogue;

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public RxCatalogue getRxCatalogue() {
        return rxCatalogue;
    }

    public void setRxCatalogue(RxCatalogue rxCatalogue) {
        this.rxCatalogue = rxCatalogue;
    }
}
