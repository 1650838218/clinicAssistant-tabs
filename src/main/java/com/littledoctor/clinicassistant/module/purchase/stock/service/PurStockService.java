package com.littledoctor.clinicassistant.module.purchase.stock.service;

import com.github.pagehelper.PageInfo;
import com.littledoctor.clinicassistant.module.purchase.stock.entity.PurStock;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:27
 * @Description: 入库单
 */
public interface PurStockService {

    /**
     * 保存入库单
     * @param purStocks
     * @return
     */
    List<PurStock> save(List<PurStock> purStocks) throws Exception;

    /**
     * 分页查询
     * @param page
     * @param keywords
     * @param pharmacyItemType
     * @return
     */
    PageInfo<Map<String, String>> queryPage(Pageable page, String keywords, String pharmacyItemType) throws Exception;

    PurStock queryById(Long purStockId) throws Exception;

    /**
     * 更新 售价 库存数量
     * @param purStock
     * @return
     */
    PurStock update(PurStock purStock) throws Exception;

    /**
     * 下架
     * @param purStock
     * @return
     * @throws Exception
     */
    Boolean unshelve(PurStock purStock) throws Exception;

    /**
     * 获取下拉表格的list
     * @param keywords
     * @param pharmacyItemType
     * @return
     */
    List<Map<String, Object>> getCombogrid(String keywords, String pharmacyItemType) throws Exception;

    /**
     * 根据药材名称查询药材信息
     * @param medicalName
     * @return
     */
    Map<String, Object> findByName(String medicalName) throws Exception;
}
