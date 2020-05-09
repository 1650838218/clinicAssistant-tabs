package com.littledoctor.clinicassistant.module.item.acupoint.controller;

import com.littledoctor.clinicassistant.module.item.acupoint.entity.ItemAcupointEntity;
import com.littledoctor.clinicassistant.module.item.acupoint.service.ItemAcupointService;
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
 * @Description: 腧穴 品目
 */
@Controller
@RequestMapping(value = "/itemAcupoint")
public class ItemAcupointController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemAcupointService itemAcupointService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemAcupointEntity save(@RequestBody ItemAcupointEntity entity) {
        try {
            return itemAcupointService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemAcupointEntity();
    }
}
