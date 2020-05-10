package com.littledoctor.clinicassistant.module.item.consumable.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.item.consumable.entity.ConsumableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 腧穴 品目
 */
public interface ConsumableDao extends JpaRepository<ConsumableEntity, Long>, JpaSpecificationExecutor<ConsumableEntity> {
    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.itemId, t.itemName) from ConsumableEntity t")
    List<SelectOption> getSelectOption();
}
