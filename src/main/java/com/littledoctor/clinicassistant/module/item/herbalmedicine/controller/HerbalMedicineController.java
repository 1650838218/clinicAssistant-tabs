package com.littledoctor.clinicassistant.module.item.herbalmedicine.controller;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.HerbalMedicineEntity;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.service.HerbalMedicineService;
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
 * @Description: 中药 品目
 */
@Controller
@RequestMapping(value = "/item/herbalMedicine")
public class HerbalMedicineController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HerbalMedicineService herbalMedicineService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public HerbalMedicineEntity save(@RequestBody HerbalMedicineEntity entity) {
        try {
            return herbalMedicineService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new HerbalMedicineEntity();
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
            return herbalMedicineService.notRepeatName(itemId, itemName);
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
    public HerbalMedicineEntity findById(@RequestParam Long id) {
        try {
            return herbalMedicineService.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new HerbalMedicineEntity();
    }

    /**
     * 查询目录
     * @return
     */
    @RequestMapping(value = "/queryCatalog", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeEntity> queryCatalog(String keyword) {
        try {
            return herbalMedicineService.queryCatalog(keyword);
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
            Assert.notNull(id, Message.PARAMETER_IS_NULL);
            return herbalMedicineService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
