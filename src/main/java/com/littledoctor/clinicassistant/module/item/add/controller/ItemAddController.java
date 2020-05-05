package com.littledoctor.clinicassistant.module.item.add.controller;

import com.littledoctor.clinicassistant.module.item.add.service.ItemAddService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/18 22:31
 * @Description: 品目管理  新增品目
 */
@RestController
@RequestMapping(value = "/itemAdd")
public class ItemAddController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemAddService itemAddService;

    /**
     * 判断品目名称是否不重复，是否不存在
     * @param itemId
     * @param itemName
     * @return true 不存在  false 已存在，默认false
     */
    @RequestMapping(value = "/notRepeatName", method = RequestMethod.GET)
    public boolean notRepeatName(String itemId, @RequestParam String itemName, @RequestParam String itemType) {
        try {
            return itemAddService.notRepeatName(itemId, itemName, itemType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
