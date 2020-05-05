package com.littledoctor.clinicassistant.module.item.medicalsupply.dao;

import com.littledoctor.clinicassistant.module.item.medicalsupply.entity.ItemMedicalSupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗用品 品目
 */
public interface ItemMedicalSupplyDao extends JpaRepository<ItemMedicalSupplyEntity, Long>, JpaSpecificationExecutor<ItemMedicalSupplyEntity> {
}
