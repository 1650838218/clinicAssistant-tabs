package com.littledoctor.clinicassistant.module.item.herbalmedicine.dao;

import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
public interface ItemHerbalMedicineDao extends JpaRepository<ItemHerbalMedicineEntity, Long>, JpaSpecificationExecutor<ItemHerbalMedicineEntity> {
}
