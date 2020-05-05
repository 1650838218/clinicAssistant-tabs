package com.littledoctor.clinicassistant.module.item.patentmedicine.controller;

import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.service.ItemHerbalMedicineService;
import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.ItemPatentMedicineEntity;
import com.littledoctor.clinicassistant.module.item.patentmedicine.service.ItemPatentMedicineService;
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
 * @Description: 成药 品目
 */
@Controller
@RequestMapping(value = "/itemPatentMedicine")
public class ItemPatentMedicineController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemPatentMedicineService itemPatentMedicineService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemPatentMedicineEntity save(@RequestBody ItemPatentMedicineEntity entity) {
        try {
            return itemPatentMedicineService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemPatentMedicineEntity();
    }
}
