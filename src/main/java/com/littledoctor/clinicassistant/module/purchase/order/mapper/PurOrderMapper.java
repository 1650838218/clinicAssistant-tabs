package com.littledoctor.clinicassistant.module.purchase.order.mapper;

import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @业务信息: 采购单
 * @Filename: PurOrderMapper.java
 * 2019-12-26   周俊林
 */
@Mapper
public interface PurOrderMapper {

    /**
     * 分页查询采购单
     * @param purItemName 采购品目名称，简拼，全拼
     * @param supplierId 供应商ID
     * @param startDate 采购日期
     * @param endDate 采购日期
     * @param offSet 分页偏移量
     * @param pageSize 一页的条目数
     * @return
     */
    List<PurOrder> findAll(@Param("purItemName") String purItemName, @Param("supplierId") String supplierId,
                           @Param("startDate") String startDate, @Param("endDate") String endDate,
                           @Param("offSet") Long offSet, @Param("pageSize") int pageSize);
}
