package com.littledoctor.clinicassistant.module.item.medicalsupply.controller;

import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.service.ItemHerbalMedicineService;
import com.littledoctor.clinicassistant.module.item.medicalsupply.entity.ItemMedicalSupplyEntity;
import com.littledoctor.clinicassistant.module.item.medicalsupply.service.ItemMedicalSupplyService;
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
 * @Description: 医疗用品 品目
 */
@Controller
@RequestMapping(value = "/itemMedicalSupply")
public class ItemMedicalSupplyController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemMedicalSupplyService itemMedicalSupplyService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemMedicalSupplyEntity save(@RequestBody ItemMedicalSupplyEntity entity) {
        try {
            return itemMedicalSupplyService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemMedicalSupplyEntity();
    }
}
