package com.littledoctor.clinicassistant.module.item.consumable.dao;

import com.littledoctor.clinicassistant.module.item.acupoint.entity.ItemAcupointEntity;
import com.littledoctor.clinicassistant.module.item.consumable.entity.ItemConsumableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 腧穴 品目
 */
public interface ItemConsumableDao extends JpaRepository<ItemConsumableEntity, Long>, JpaSpecificationExecutor<ItemConsumableEntity> {
}
