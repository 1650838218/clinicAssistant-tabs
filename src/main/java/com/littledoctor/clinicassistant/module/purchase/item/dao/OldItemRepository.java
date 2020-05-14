package com.littledoctor.clinicassistant.module.purchase.item.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 20:34
 * @Description: 采购品目
 */
public interface OldItemRepository extends JpaRepository<ItemEntity,Long>, JpaSpecificationExecutor<ItemEntity> {

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.itemId, t.itemName) from ItemEntity t ")
    List<SelectOption> getSelectOption();

    /**
     * 获取selecOption
     * @return
     * @param keywords
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.itemId, t.itemName) " +
            "from ItemEntity t where t.itemName like concat('%',?1,'%') or t.fullPinyin like concat('%',?1,'%') " +
            "or t.abbrPinyin like concat('%',?1,'%')")
    List<SelectOption> getSelectOption(String keywords);

}
