package com.littledoctor.clinicassistant.module.purchase.item.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.entity.PurItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 20:34
 * @Description: 采购品目
 */
public interface PurItemRepository extends JpaRepository<PurItemEntity,Integer>, JpaSpecificationExecutor<PurItemEntity> {

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.pharmacyItemId, t.pharmacyItemName) from PurItemEntity t ")
    List<SelectOption> getSelectOption();

    /**
     * 获取selecOption
     * @return
     * @param keywords
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.pharmacyItemId, t.pharmacyItemName) " +
            "from PurItemEntity t where t.pharmacyItemName like concat('%',?1,'%') or t.fullPinyin like concat('%',?1,'%') " +
            "or t.abbreviation like concat('%',?1,'%')")
    List<SelectOption> getSelectOption(String keywords);
}
