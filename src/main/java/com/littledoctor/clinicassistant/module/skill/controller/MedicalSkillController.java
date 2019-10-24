package com.littledoctor.clinicassistant.module.skill.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.plugin.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.plugin.SelectOption;
import com.littledoctor.clinicassistant.module.skill.entity.MedicalSkill;
import com.littledoctor.clinicassistant.module.skill.service.MedicalSkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-10 12:37
 * @Description: 医技项目
 */
@RestController
@RequestMapping("/skill")
public class MedicalSkillController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedicalSkillService medicalSkillService;

    /**
     * 查询所有的医技项目
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public LayuiTableEntity<MedicalSkill> findAll() {
        try {
            return new LayuiTableEntity<MedicalSkill>(medicalSkillService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存医技项目
     * @param medicalSkill
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MedicalSkill save(@RequestBody MedicalSkill medicalSkill) {
        try {
            Assert.notNull(medicalSkill, Message.PARAMETER_IS_NULL);
            return medicalSkillService.save(medicalSkill);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除医技项目
     * @param skillId
     * @return
     */
    @RequestMapping(value = "/delete/{skillId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("skillId") String skillId) {
        try {
            Assert.notNull(skillId, Message.PARAMETER_IS_NULL);
            return medicalSkillService.delete(skillId);
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
            return medicalSkillService.getSelectOption();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
