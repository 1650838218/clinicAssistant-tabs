package com.littledoctor.clinicassistant.module.item.consumable.controller;

import com.littledoctor.clinicassistant.module.item.consumable.entity.ItemConsumableEntity;
import com.littledoctor.clinicassistant.module.item.consumable.service.ItemConsumableService;
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
 * @Description: 其他耗材 品目
 */
@Controller
@RequestMapping(value = "/itemConsumable")
public class ItemConsumableController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemConsumableService itemConsumableService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemConsumableEntity save(@RequestBody ItemConsumableEntity entity) {
        try {
            return itemConsumableService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemConsumableEntity();
    }
}
