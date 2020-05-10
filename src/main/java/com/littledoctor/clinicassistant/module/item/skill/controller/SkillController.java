package com.littledoctor.clinicassistant.module.item.skill.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.item.skill.entity.SkillEntity;
import com.littledoctor.clinicassistant.module.item.skill.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗项目 品目
 */
@RestController
@RequestMapping(value = "/item/skill")
public class SkillController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SkillService skillService;

    /**
     * 查询所有的医技项目
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public LayuiTableEntity<SkillEntity> findAll() {
        try {
            return new LayuiTableEntity<SkillEntity>(skillService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存医技项目
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SkillEntity save(@RequestBody SkillEntity entity) {
        try {
            Assert.notNull(entity, Message.PARAMETER_IS_NULL);
            return skillService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new SkillEntity();
    }

    /**
     * 删除医技项目
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Long id) {
        try {
            Assert.notNull(id, Message.PARAMETER_IS_NULL);
            return skillService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取下拉框的option list
     * @return
     */
    @RequestMapping(value = "/getSelectOption", method = RequestMethod.GET)
    public List<SelectOption> getSelectOption() {
        try {
            return skillService.getSelectOption();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
}
