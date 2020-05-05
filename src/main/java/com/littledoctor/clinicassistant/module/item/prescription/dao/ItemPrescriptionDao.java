package com.littledoctor.clinicassistant.module.item.prescription.dao;

import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.ItemPatentMedicineEntity;
import com.littledoctor.clinicassistant.module.item.prescription.entity.ItemPrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
public interface ItemPrescriptionDao extends JpaRepository<ItemPrescriptionEntity, Long>, JpaSpecificationExecutor<ItemPrescriptionEntity> {
}
