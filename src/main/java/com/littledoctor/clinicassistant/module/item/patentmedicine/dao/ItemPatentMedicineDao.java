package com.littledoctor.clinicassistant.module.item.patentmedicine.dao;

import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.ItemPatentMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
public interface ItemPatentMedicineDao extends JpaRepository<ItemPatentMedicineEntity, Long>, JpaSpecificationExecutor<ItemPatentMedicineEntity> {
}
