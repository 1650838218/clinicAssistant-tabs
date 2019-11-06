package com.littledoctor.clinicassistant.module.record.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.module.record.entity.MedicalRecordVo;
import com.littledoctor.clinicassistant.module.record.entity.SettleAccount;

import java.util.List;

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
}
