package com.littledoctor.clinicassistant.module.skill.dao;

import com.littledoctor.clinicassistant.common.plugin.select.SelectOption;
import com.littledoctor.clinicassistant.module.skill.entity.MedicalSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-10 12:42
 * @Description: 医技项目
 */
public interface MedicalSkillRepository extends JpaRepository<MedicalSkill, Integer>, JpaSpecificationExecutor<MedicalSkill> {

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.plugin.select.SelectOption(t.skillId, t.skillName) from MedicalSkill t")
    List<SelectOption> getSelectOption();
}

