package com.littledoctor.clinicassistant.module.item.skill.dao;

import com.littledoctor.clinicassistant.module.item.prescription.entity.ItemPrescriptionEntity;
import com.littledoctor.clinicassistant.module.item.skill.entity.ItemSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗项目 品目
 */
public interface ItemSkillDao extends JpaRepository<ItemSkillEntity, Long>, JpaSpecificationExecutor<ItemSkillEntity> {
}
