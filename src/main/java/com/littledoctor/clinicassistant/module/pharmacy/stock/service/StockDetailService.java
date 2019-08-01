package com.littledoctor.clinicassistant.module.pharmacy.stock.service;

import com.github.pagehelper.PageInfo;
import com.littledoctor.clinicassistant.module.pharmacy.stock.entity.StockDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:27
 * @Description: 入库单
 */
public interface StockDetailService {

    /**
     * 保存入库单
     * @param stockDetails
     * @return
     */
    List<StockDetail> save(List<StockDetail> stockDetails) throws Exception;

    /**
     * 分页查询
     * @param page
     * @param keywords
     * @param pharmacyItemType
     * @return
     */
    PageInfo<StockDetail> queryPage(Pageable page, String keywords, String pharmacyItemType) throws Exception;
}
