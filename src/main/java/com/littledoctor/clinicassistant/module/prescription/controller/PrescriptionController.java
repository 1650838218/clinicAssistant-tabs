package com.littledoctor.clinicassistant.module.prescription.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.plugin.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.PrescriptionVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import com.littledoctor.clinicassistant.module.prescription.service.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据目录ID查询处方
     * @param catalogueId
     * @return
     */
    @RequestMapping(value = "/findPrescriptionByCatalogueId", method = RequestMethod.GET)
    public Prescription findPrescriptionByCatalogueId(@RequestParam String catalogueId) {
        try {
            return prescriptionService.findPrescriptionByCatalogueId(catalogueId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据ID查询处方
     * @param prescriptionId
     * @return
     */
    @RequestMapping(value = "/findPrescriptionById", method = RequestMethod.GET)
    public Prescription findPrescriptionById(@RequestParam String prescriptionId) {
        try {
            return prescriptionService.findPrescriptionById(prescriptionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存处方
     * @param prescriptionVo
     * @return
     */
    @RequestMapping(value = "/prescription/save", method = RequestMethod.POST)
    public Prescription savePrescription(@RequestBody PrescriptionVo prescriptionVo) {
        try {
            return prescriptionService.savePrescription(prescriptionVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据ID查询处方分类
     * @param catalogueId
     * @return
     */
    @RequestMapping(value = "/findCatalogueById", method = RequestMethod.GET)
    public RxCatalogue findCatalogueById(Integer catalogueId) {
        try {
            return prescriptionService.findCatalogueById(catalogueId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取下拉框的option list
     * @param keywords 处方名称或简称
     * @return
     */
    @RequestMapping(value = "/getSelectOption", method = RequestMethod.GET)
    public List<SelectOption> getSelectOption(@RequestParam(value = "q", required = false) String keywords) {
        try {
            return prescriptionService.getSelectOption(keywords);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据处方ID查询处方组成，并将处方组成转换成药材信息
     * 包括：药材名称，品目ID，单价，库存单位，剂量等
     * @param prescriptionId
     * @return
     */
    @RequestMapping(value = "/getMedicalByPrescriptionId", method = RequestMethod.GET)
    public Map<String, Object> getMedicalByPrescriptionId(@RequestParam String prescriptionId) {
        try {
            return prescriptionService.getMedicalByPrescriptionId(prescriptionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
