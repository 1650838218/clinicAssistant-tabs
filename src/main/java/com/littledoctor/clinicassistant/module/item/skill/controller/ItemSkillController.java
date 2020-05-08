package com.littledoctor.clinicassistant.module.item.skill.controller;

import com.littledoctor.clinicassistant.module.item.skill.entity.ItemSkillEntity;
import com.littledoctor.clinicassistant.module.item.skill.service.ItemSkillService;
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
 * @Description: 医疗项目 品目
 */
@Controller
@RequestMapping(value = "/itemSkill")
public class ItemSkillController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemSkillService itemSkillService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemSkillEntity save(@RequestBody ItemSkillEntity entity) {
        try {
            return itemSkillService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemSkillEntity();
    }
}
