package com.littledoctor.clinicassistant.module.purchase.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @业务信息: 采购单
 * @Filename: OrderMapper.java
 * 2019-12-26   周俊林
 */
@Mapper
public interface OrderMapper {

    /**
     * 分页查询采购单
     * @param itemName 采购品目名称，简拼，全拼
     * @param supplierId 供应商ID
     * @param startDate 采购日期
     * @param endDate 采购日期
     * @param offSet 分页偏移量
     * @param pageSize 一页的条目数
     * @return
     */
    List<Map<String,Object>> findAll(@Param("itemName") String itemName, @Param("supplierId") String supplierId,
                      @Param("startDate") String startDate, @Param("endDate") String endDate,
                      @Param("offSet") Long offSet, @Param("pageSize") int pageSize);

    /**
     * 查询条数
     * @param itemName 采购品目名称，简拼，全拼
     * @param supplierId 供应商ID
     * @param startDate 采购日期
     * @param endDate 采购日期
     * @return
     */
    int count(@Param("itemName") String itemName, @Param("supplierId") String supplierId,
              @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 入库，查询采购单
     * @param purOrderId
     * @return
     */
    Map<String, Object> findByIdForStock(@Param("purOrderId") Long purOrderId);

    /**
     * 入库，查询订单详情
     * @param purOrderId
     * @return
     */
    List<Map<String, Object>> findOrderDetailForStock(@Param("purOrderId") long purOrderId);

    /**
     * 查询采购品目
     * @param keywords
     * @return
     */
    List<Map<String, Object>> getPurchaseItem(@Param("keywords") String keywords);
}
