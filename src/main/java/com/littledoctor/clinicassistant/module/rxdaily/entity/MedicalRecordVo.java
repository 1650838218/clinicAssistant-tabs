package com.littledoctor.clinicassistant.module.rxdaily.entity;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-09-21 17:47
 * @Description: 病历实体，用于接收前台保存参数
 */
public class MedicalRecordVo {

    // 患者信息
    private RxDaily rxDaily;

    // 处方
    private List<RxVo> rxVoList;

    // 结算
    private SettleAccount settleAccount;

    public RxDaily getRxDaily() {
        return rxDaily;
    }

    public void setRxDaily(RxDaily rxDaily) {
        this.rxDaily = rxDaily;
    }

    public List<RxVo> getRxVoList() {
        return rxVoList;
    }

    public void setRxVoList(List<RxVo> rxVoList) {
        this.rxVoList = rxVoList;
    }

    public SettleAccount getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(SettleAccount settleAccount) {
        this.settleAccount = settleAccount;
    }
}
