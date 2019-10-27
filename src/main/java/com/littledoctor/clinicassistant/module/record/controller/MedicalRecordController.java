package com.littledoctor.clinicassistant.module.record.controller;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.record.entity.MedicalRecordVo;
import com.littledoctor.clinicassistant.module.record.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:25
 * @Description: 病历
 */
@RestController
@RequestMapping(value = "/record")
public class MedicalRecordController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedicalRecordService medicalRecordService;

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
            return medicalRecordService.save(medicalRecordVo);
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
            return medicalRecordService.findById(recordId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new MedicalRecordVo();
    }
}
