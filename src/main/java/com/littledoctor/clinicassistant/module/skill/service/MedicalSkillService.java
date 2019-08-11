package com.littledoctor.clinicassistant.module.skill.service;

import com.littledoctor.clinicassistant.common.plugin.select.SelectOption;
import com.littledoctor.clinicassistant.module.skill.entity.MedicalSkill;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-10 12:43
 * @Description: 医技项目
 */
public interface MedicalSkillService {
    /**
     * 查询所有医技项目
     * @return
     */
    List<MedicalSkill> findAll() throws Exception;

    /**
     * 保存医技项目
     * @param medicalSkill
     * @return
     */
    MedicalSkill save(MedicalSkill medicalSkill) throws Exception;

    /**
     * 删除医技项目
     * @param medicalSkillId
     * @return
     */
    boolean delete(String medicalSkillId) throws Exception;

    /**
     * 获取select option list
     * @return
     */
    List<SelectOption> getSelectOption() throws Exception;
}
