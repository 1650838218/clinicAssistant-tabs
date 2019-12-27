package com.littledoctor.clinicassistant.module.purchase.stock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @业务信息:
 * @Filename: PurStockMapper.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-01   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-01   周俊林
 */
@Mapper
public interface PurStockMapper {

    /**
     * 根据条件查询库存信息
     * @param keywords
     * @param purItemType
     * @return
     */
    List<Map<String, String>> findAll(@Param("keywords") String keywords, @Param("purItemType") String purItemType);

    /**
     * 用于病历开中药方时根据药品名称查询药品信息
     * @param keywords
     * @param purItemType
     * @return
     */
    List<Map<String, Object>> getCombogridForDecoction(@Param("keywords") String keywords, @Param("purItemType") String purItemType);

    /**
     * 根据药材名称查询药材信息
     * @param medicalName
     * @return
     */
    Map<String, Object> findByName(@Param("medicalName") String medicalName);
}
