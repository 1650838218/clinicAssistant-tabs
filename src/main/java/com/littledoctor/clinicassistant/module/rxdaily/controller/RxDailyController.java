package com.littledoctor.clinicassistant.module.rxdaily.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.rxdaily.entity.MedicalRecordVo;
import com.littledoctor.clinicassistant.module.rxdaily.entity.RxDaily;
import com.littledoctor.clinicassistant.module.rxdaily.entity.SettleAccount;
import com.littledoctor.clinicassistant.module.rxdaily.service.RxDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
     * 挂号，保存处方笺
     * @param rxDaily
     * @return
     */
    @PostMapping("/saveRegister")
    public ReturnResult register(@RequestBody RxDaily rxDaily) {
        try {
            Assert.notNull(rxDaily, Message.PARAMETER_IS_NULL);
            if (rxDaily.getRxDailyId() != null) {
                // 修改
                RxDaily resultEntity = rxDailyService.updateRegister(rxDaily);
                ReturnResult result = new ReturnResult(true, Message.UPDATE_SUCCESS);
                result.setObject(rxDaily);
                return result;
            } else {
                // 新建
                RxDaily resultEntity = rxDailyService.createRegister(rxDaily);
                ReturnResult result = new ReturnResult(true, Message.CREATE_SUCCESS);
                result.setObject(rxDaily);
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ReturnResult(false, Message.SAVE_FAILED);
    }

    /**
     * 查询当天没有结算的挂号单 处方笺
     */
    @GetMapping("/getTodayRxDailyMainForNotPayment")
    public LayuiTableEntity<RxDaily> getTodayRxDailyMainForNotPayment() {
        try {
            return new LayuiTableEntity<RxDaily>(rxDailyService.getTodayRxDailyMainForNotPayment());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<RxDaily>();
    }

    /**
     * 删除挂号单 处方笺
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ReturnResult deleteRegister(@PathVariable("id") Long rxDailyId) {
        ReturnResult returnResult = new ReturnResult();
        try {
            Assert.notNull(rxDailyId, Message.PARAMETER_IS_NULL);
            rxDailyService.deleteRegister(rxDailyId);
            returnResult.setSuccess(true);
            returnResult.setMsg(Message.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnResult.setSuccess(false);
            returnResult.setMsg(Message.DELETE_FAILED);
        }
        return returnResult;
    }

    /**
     * 根据Id查询挂号单
     * @param rxDailyId
     * @return
     */
    @GetMapping("/getRegisterById")
    public ReturnResult getRegisterById(Long rxDailyId) {
        ReturnResult returnResult = new ReturnResult();
        try {
            Assert.notNull(rxDailyId, Message.PARAMETER_IS_NULL);
            RxDaily rxDaily = rxDailyService.getRegisterById(rxDailyId);
            returnResult.setSuccess(true);
            returnResult.setMsg(Message.QUERY_SUCCESS);
            returnResult.setObject(rxDaily);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnResult.setSuccess(false);
            returnResult.setMsg(Message.QUERY_FAILED);
        }
        return returnResult;
    }

    /**
     * 保存处方笺  收费 结算 保存
     * @param rxDaily
     * @return
     */
    @PostMapping("/saveRxDaily")
    public ReturnResult saveRxDaily(@RequestBody RxDaily rxDaily) {
        try {
            Assert.notNull(rxDaily, Message.PARAMETER_IS_NULL);
            if (rxDaily.getRxDailyId() != null) {
                // 修改
                RxDaily resultEntity = rxDailyService.updateRxDaily(rxDaily);
                ReturnResult result = new ReturnResult(true, Message.UPDATE_SUCCESS);
                result.setObject(rxDaily);
                return result;
            } else {
                // 新建
                RxDaily resultEntity = rxDailyService.createRxDaily(rxDaily);
                ReturnResult result = new ReturnResult(true, Message.CREATE_SUCCESS);
                result.setObject(rxDaily);
                return result;
            }
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
