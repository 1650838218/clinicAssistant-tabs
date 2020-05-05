package com.littledoctor.clinicassistant.module.item.add.dao;

import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 品目管理  新增品目
 */
public interface ItemAddDao extends JpaRepository<ItemAllEntity, Long>, JpaSpecificationExecutor<ItemAllEntity> {
}
