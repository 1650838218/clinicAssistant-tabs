package com.littledoctor.clinicassistant.module.purchase.stock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @业务信息: 库存
 * @Filename: StockMapper.java
 * @Description:
 * 2019-08-01   周俊林
 */
@Mapper
public interface StockMapper {

    /**
     * 查询库存
     * @param keywords 品目名称
     * @param expireDate
     * @return
     */
    int count(@Param("keywords") String keywords, @Param("expireDate") boolean expireDate);

    /**
     * 根据条件查询库存信息
     * @param keywords 品目名称
     * @param offset 分页 偏移
     * @param pageSize 分页 每页大小
     * @param expireDate
     * @return
     */
    List<Map<String, Object>> findAll(@Param("keywords") String keywords, @Param("offset") Long offset, @Param("pageSize") int pageSize, @Param("expireDate") boolean expireDate);

    /**
     * 查询库存中药
     * @param keywords
     * @return
     */
    List<Map<String, Object>> getCombogridForHerbalMedicine(@Param("keywords") String keywords);

    /**
     * 根据药材名称查询药材信息
     * @param medicalName
     * @return
     */
    Map<String, Object> findByName(@Param("medicalName") String medicalName);

    /**
     * 查看库存品目的采购信息
     * @param purStockId
     * @return
     */
    Map<String, Object> findByIdForOrder(@Param("purStockId") Long purStockId);

    /**
     * 查询库存预警
     * @param keywords
     * @param offset
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> findWarnAll(@Param("keywords") String keywords, @Param("offset") Long offset, @Param("pageSize") int pageSize);

    /**
     * 查询库存预警
     * @param keywords
     * @return
     */
    int countWarn(@Param("keywords") String keywords);

    /**
     * 查询已过期
     * @return
     */
    int countExpire();

    /**
     * 查询已过期
     * @param offset
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> findExpireAll(@Param("offset") Long offset, @Param("pageSize") int pageSize);

    /**
     * 查询库存中成药
     * @param keywords
     * @return
     */
    List<Map<String, Object>> getCombogridForPatentMedicine(@Param("keywords") String keywords);
}
