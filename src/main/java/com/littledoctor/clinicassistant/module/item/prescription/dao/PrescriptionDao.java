package com.littledoctor.clinicassistant.module.item.prescription.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.item.prescription.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
public interface PrescriptionDao extends JpaRepository<PrescriptionEntity, Long>, JpaSpecificationExecutor<PrescriptionEntity> {
    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.itemId, t.itemName) from PrescriptionEntity t")
    List<SelectOption> getSelectOption();
}
