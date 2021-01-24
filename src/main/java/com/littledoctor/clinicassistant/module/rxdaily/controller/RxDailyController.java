package com.littledoctor.clinicassistant.module.rxdaily.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.rxdaily.entity.MedicalRecordVo;
import com.littledoctor.clinicassistant.module.rxdaily.entity.RxDailyMain;
import com.littledoctor.clinicassistant.module.rxdaily.entity.SettleAccount;
import com.littledoctor.clinicassistant.module.rxdaily.service.RxDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:25
 * @Description: 日常处方笺
 */
@RestController
@RequestMapping(value = "/rxdaily")
public class RxDailyController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RxDailyService rxDailyService;

    /**
     * 获取下一个号码
     */
    @GetMapping("/getRegisterNumber")
    public int getRegisterNumber() {
        try {
            return rxDailyService.getRegisterNumber();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 挂号，新建处方笺
     * @param rxDailyMain
     * @return
     */
    @PostMapping("/register")
    public ReturnResult register(@RequestBody RxDailyMain rxDailyMain) {
        try {
            Assert.notNull(rxDailyMain, Message.PARAMETER_IS_NULL);
            RxDailyMain resultEntity = rxDailyService.register(rxDailyMain);
            ReturnResult result = new ReturnResult(true, Message.CREATE_SUCCESS);
            result.setObject(rxDailyMain);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ReturnResult(false, Message.CREATE_FAILED);
    }

    /**
     * 查询当天没有结算的挂号单 处方笺
     */
    @GetMapping("/getTodayRxDailyMainForNotPayment")
    public LayuiTableEntity<RxDailyMain> getTodayRxDailyMainForNotPayment() {
        try {
            return new LayuiTableEntity<RxDailyMain>(rxDailyService.getTodayRxDailyMainForNotPayment());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<RxDailyMain>();
    }

    /**
     * 保存 病历
     * @param medicalRecordVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnResult save(@RequestBody MedicalRecordVo medicalRecordVo) {
        try {
            Assert.notNull(medicalRecordVo, Message.PARAMETER_IS_NULL);
            return rxDailyService.save(medicalRecordVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ReturnResult(false, Message.SAVE_FAILED);
    }

    /**
     * 根据ID查询 病历
     * @param recordId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public MedicalRecordVo findById(String recordId) {
        try {
            Assert.hasLength(recordId, Message.PARAMETER_IS_NULL);
            return rxDailyService.findById(recordId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new MedicalRecordVo();
    }

    /**
     * 保存结算信息
     * @param settleAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/settleAccount", method = RequestMethod.POST)
    public ReturnResult settleAccount(@RequestBody SettleAccount settleAccount) {
        try {
            Assert.notNull(settleAccount, Message.PARAMETER_IS_NULL);
            return rxDailyService.settleAccount(settleAccount);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ReturnResult(false, Message.SAVE_FAILED);
    }
}
