package com.littledoctor.clinicassistant.module.item.prescription.dao;

import com.littledoctor.clinicassistant.module.item.prescription.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
public interface PrescriptionDao extends JpaRepository<PrescriptionEntity, Long>, JpaSpecificationExecutor<PrescriptionEntity> {
}
