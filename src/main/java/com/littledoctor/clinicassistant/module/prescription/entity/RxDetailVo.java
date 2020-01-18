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
public class RxDetailVo {

    private RxDetail rxDetail;

    private RxCatalog rxCatalog;

    public RxDetail getRxDetail() {
        return rxDetail;
    }

    public void setRxDetail(RxDetail rxDetail) {
        this.rxDetail = rxDetail;
    }

    public RxCatalog getRxCatalog() {
        return rxCatalog;
    }

    public void setRxCatalog(RxCatalog rxCatalog) {
        this.rxCatalog = rxCatalog;
    }
}
