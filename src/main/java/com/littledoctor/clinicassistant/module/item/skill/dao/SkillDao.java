package com.littledoctor.clinicassistant.module.item.skill.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.item.skill.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗项目 品目
 */
public interface SkillDao extends JpaRepository<SkillEntity, Long>, JpaSpecificationExecutor<SkillEntity> {
    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.itemId, t.itemName) from SkillEntity t")
    List<SelectOption> getSelectOption();
}
