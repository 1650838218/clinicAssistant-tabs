package com.littledoctor.clinicassistant.module.prescription.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import com.littledoctor.clinicassistant.module.prescription.service.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:07
 * @Description: 处方管理
 */
@RestController
@RequestMapping(value = "/prescription")
public class PrescriptionController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * 查询处方目录
     * @return
     */
    @RequestMapping(value = "/queryCatalogue", method = RequestMethod.GET)
    public List<RxCatalogue> queryCatalogue() {
        try {
            return prescriptionService.queryCatalogue();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存处方目录
     * @param rxCatalogue
     * @return
     */
    @RequestMapping(value = "/catalogue/save", method = RequestMethod.POST)
    public RxCatalogue saveCatalogue(@RequestBody RxCatalogue rxCatalogue) {
        try {
            return prescriptionService.saveCatalogue(rxCatalogue);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据ID删除处方目录
     * @param catalogueId
     * @return
     */
    @RequestMapping(value = "/catalogue/delete/{catalogueId}", method = RequestMethod.DELETE)
    public boolean deleteCatalogue(@PathVariable(value = "catalogueId") String catalogueId) {
        try {
            Assert.hasLength(catalogueId, Message.PARAMETER_IS_NULL);
            return prescriptionService.deleteCatalogue(catalogueId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
