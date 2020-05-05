package com.littledoctor.clinicassistant.module.item.meridian.controller;

import com.littledoctor.clinicassistant.module.item.medicalsupply.entity.ItemMedicalSupplyEntity;
import com.littledoctor.clinicassistant.module.item.medicalsupply.service.ItemMedicalSupplyService;
import com.littledoctor.clinicassistant.module.item.meridian.entity.ItemMeridianEntity;
import com.littledoctor.clinicassistant.module.item.meridian.service.ItemMeridianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 经络 品目
 */
@Controller
@RequestMapping(value = "/itemMeridian")
public class ItemMeridianController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemMeridianService itemMeridianService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemMeridianEntity save(@RequestBody ItemMeridianEntity entity) {
        try {
            return itemMeridianService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemMeridianEntity();
    }
}
