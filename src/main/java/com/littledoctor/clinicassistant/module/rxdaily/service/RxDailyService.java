package com.littledoctor.clinicassistant.module.rxdaily.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.module.rxdaily.entity.MedicalRecordVo;
import com.littledoctor.clinicassistant.module.rxdaily.entity.RxDailyMain;
import com.littledoctor.clinicassistant.module.rxdaily.entity.SettleAccount;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:25
 * @Description:
 */
public interface RxDailyService {

    /**
     * 保存 病历
     * @param medicalRecordVo
     * @return
     */
    ReturnResult save(MedicalRecordVo medicalRecordVo) throws Exception;

    /**
     * 根据ID查询 病历
     * @param recordId
     * @return
     */
    MedicalRecordVo findById(String recordId) throws Exception;

    /**
     * 保存结算信息
     * @param settleAccount
     * @return
     */
    ReturnResult settleAccount(SettleAccount settleAccount) throws Exception;

    List<SettleAccount> findSettleAccountByRecordId(Long recordId) throws Exception;

    /**
     * 挂号，创建处方笺
     * @param rxDailyMain
     * @return
     */
    RxDailyMain register(RxDailyMain rxDailyMain) throws Exception;

    /**
     * 获取下一个号码
     * @return
     */
    int getRegisterNumber() throws Exception;

    /**
     * 查询当天没有结算的挂号单 处方笺
     * @return
     */
    List<RxDailyMain> getTodayRxDailyMainForNotPayment() throws Exception;
}
