package com.littledoctor.clinicassistant.module.item.all.mapper;

import com.littledoctor.clinicassistant.module.item.all.entity.ItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FileName: ItemMapper.java
 *
 * @author : zjl
 * @version : V1.0
 * Createdate: 2020-05-12
 * @Description: All rights Reserved, Designed By SunyardFX
 * Copyright: Copyright(C) 2020-2022
 * Company  : SunyardFX.hangzhou
 */
@Mapper
public interface ItemMapper {

    /**
     * 查询采购品目
     * @param keywords
     * @return
     */
    List<ItemEntity> getPurchaseItem(@Param("keywords") String keywords);
}
