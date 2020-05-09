package com.littledoctor.clinicassistant.module.item.patentmedicine.dao;

import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.PatentMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
public interface PatentMedicineDao extends JpaRepository<PatentMedicineEntity, Long>, JpaSpecificationExecutor<PatentMedicineEntity> {
}
