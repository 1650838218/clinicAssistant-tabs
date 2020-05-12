package com.littledoctor.clinicassistant.module.item.all.controller;

import com.littledoctor.clinicassistant.module.item.all.entity.ItemEntity;
import com.littledoctor.clinicassistant.module.item.all.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/18 22:31
 * @Description: 品目管理
 */
@RestController
@RequestMapping(value = "/item")
public class ItemController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/getPurchaseItem", method = RequestMethod.GET)
    public List<ItemEntity> getPurchaseItem(@RequestParam(value = "q", required = false) String keywords) {
        try {
            return itemService.getPurchaseItem(keywords);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

}
