package com.littledoctor.clinicassistant.module.item.meridian.dao;

import com.littledoctor.clinicassistant.module.item.meridian.entity.MeridianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 经络 品目
 */
public interface MeridianDao extends JpaRepository<MeridianEntity, Long>, JpaSpecificationExecutor<MeridianEntity> {
}
