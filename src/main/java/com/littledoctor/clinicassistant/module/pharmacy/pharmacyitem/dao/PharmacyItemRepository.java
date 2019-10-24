package com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.dao;

import com.littledoctor.clinicassistant.common.plugin.SelectOption;
import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity.PharmacyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 20:34
 * @Description: 药房品目
 */
public interface PharmacyItemRepository extends JpaRepository<PharmacyItem,Integer>, JpaSpecificationExecutor<PharmacyItem> {

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.plugin.select.SelectOption(t.pharmacyItemId, t.pharmacyItemName) from PharmacyItem t ")
    List<SelectOption> getSelectOption();

    /**
     * 获取selecOption
     * @return
     * @param keywords
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.plugin.select.SelectOption(t.pharmacyItemId, t.pharmacyItemName) " +
            "from PharmacyItem t where t.pharmacyItemName like concat('%',?1,'%') or t.fullPinyin like concat('%',?1,'%') " +
            "or t.abbreviation like concat('%',?1,'%')")
    List<SelectOption> getSelectOption(String keywords);
}
