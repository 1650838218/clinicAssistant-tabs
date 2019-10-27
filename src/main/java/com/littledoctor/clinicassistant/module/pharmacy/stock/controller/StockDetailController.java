package com.littledoctor.clinicassistant.module.pharmacy.stock.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.pharmacy.stock.entity.StockDetail;
import com.littledoctor.clinicassistant.module.pharmacy.stock.service.StockDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:25
 * @Description: 库存
 */
@RestController
@RequestMapping("/pharmacy/stock")
public class StockDetailController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockDetailService stockDetailService;

    /**
     * 保存，适用于新增库存，修改库存
     * @param stockDetails
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public List<StockDetail> save(@RequestBody List<StockDetail> stockDetails) {
        try {
            return stockDetailService.save(stockDetails);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<Map<String, String>> queryPage(Pageable page, String keywords, String pharmacyItemType) {
        try {
            return new LayuiTableEntity<Map<String, String>>(stockDetailService.queryPage(page, keywords, pharmacyItemType));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 更新 售价 库存数量
     * @param stockDetail
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public StockDetail update(@RequestBody StockDetail stockDetail) {
        try {
            return stockDetailService.update(stockDetail);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 下架
     * @param stockDetail
     * @return
     */
    @RequestMapping(value = "/unshelve", method = RequestMethod.POST)
    public Boolean unshelve(@RequestBody StockDetail stockDetail) {
        try {
            return stockDetailService.unshelve(stockDetail);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取下拉表格的list
     * @return
     */
    @RequestMapping(value = "/getCombogrid", method = RequestMethod.GET)
    public List<Map<String, Object>> getCombogrid(
            @RequestParam(value = "q", required = false) String keywords,
            @RequestParam(value = "type", required = false) String pharmacyItemType) {
        try {
            return stockDetailService.getCombogrid(keywords, pharmacyItemType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
}
