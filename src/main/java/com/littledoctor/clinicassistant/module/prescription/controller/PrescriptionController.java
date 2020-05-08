package com.littledoctor.clinicassistant.module.prescription.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetail;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetailVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalog;
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
    @RequestMapping(value = "/queryCatalog", method = RequestMethod.GET)
    public List<RxCatalog> queryCatalog() {
        try {
            return prescriptionService.queryCatalog();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存处方目录
     * @param rxCatalog
     * @return
     */
    @RequestMapping(value = "/catalog/save", method = RequestMethod.POST)
    public RxCatalog saveCatalog(@RequestBody RxCatalog rxCatalog) {
        try {
            return prescriptionService.saveCatalog(rxCatalog);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据ID删除处方目录
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "/catalog/delete/{catalogId}", method = RequestMethod.DELETE)
    public boolean deleteCatalog(@PathVariable(value = "catalogId") String catalogId) {
        try {
            Assert.hasLength(catalogId, Message.PARAMETER_IS_NULL);
            return prescriptionService.deleteCatalog(catalogId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据目录ID查询处方
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "/findPrescriptionByCatalogId", method = RequestMethod.GET)
    public RxDetail findPrescriptionByCatalogId(@RequestParam String catalogId) {
        try {
            return prescriptionService.findPrescriptionByCatalogId(catalogId);
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
    public RxDetail findPrescriptionById(@RequestParam String prescriptionId) {
        try {
            return prescriptionService.findPrescriptionById(prescriptionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存处方
     * @param rxDetailVo
     * @return
     */
    @RequestMapping(value = "/prescription/save", method = RequestMethod.POST)
    public RxDetail savePrescription(@RequestBody RxDetailVo rxDetailVo) {
        try {
            return prescriptionService.savePrescription(rxDetailVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据ID查询处方分类
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "/findCatalogById", method = RequestMethod.GET)
    public RxCatalog findCatalogById(Long catalogId) {
        try {
            return prescriptionService.findCatalogById(catalogId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new RxCatalog();
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
    @RequestMapping(value = "/getMedicalByRxId", method = RequestMethod.GET)
    public Map<String, Object> getMedicalByRxId(@RequestParam String prescriptionId) {
        try {
            return prescriptionService.getMedicalByRxId(prescriptionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
