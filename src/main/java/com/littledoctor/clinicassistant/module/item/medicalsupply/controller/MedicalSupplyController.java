package com.littledoctor.clinicassistant.module.item.medicalsupply.controller;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.item.medicalsupply.entity.MedicalSupplyEntity;
import com.littledoctor.clinicassistant.module.item.medicalsupply.service.MedicalSupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗用品 品目
 */
@Controller
@RequestMapping(value = "/item/medicalSupply")
public class MedicalSupplyController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedicalSupplyService medicalSupplyService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public MedicalSupplyEntity save(@RequestBody MedicalSupplyEntity entity) {
        try {
            return medicalSupplyService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new MedicalSupplyEntity();
    }

    /**
     * 判断名称是否不重复，是否不存在
     * @param itemId
     * @param itemName
     * @return true 不存在  false 已存在，默认false
     */
    @RequestMapping(value = "/notRepeatName", method = RequestMethod.GET)
    @ResponseBody
    public boolean notRepeatName(String itemId, @RequestParam String itemName) {
        try {
            return medicalSupplyService.notRepeatName(itemId, itemName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public MedicalSupplyEntity findById(@RequestParam Long id) {
        try {
            return medicalSupplyService.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new MedicalSupplyEntity();
    }

    /**
     * 查询目录
     * @return
     */
    @RequestMapping(value = "/queryCatalog", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeEntity> queryCatalog(String keyword) {
        try {
            return medicalSupplyService.queryCatalog(keyword);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable(value = "id") Long id) {
        try {
            Assert.isNull(id, Message.PARAMETER_IS_NULL);
            return medicalSupplyService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
