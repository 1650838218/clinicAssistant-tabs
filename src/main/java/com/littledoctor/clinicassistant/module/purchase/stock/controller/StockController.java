package com.littledoctor.clinicassistant.module.purchase.stock.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.purchase.stock.entity.StockEntity;
import com.littledoctor.clinicassistant.module.purchase.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:25
 * @Description: 库存
 */
@RestController
@RequestMapping("/purchase/stock")
public class StockController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;

    /**
     * 保存，适用于新增库存，修改库存
     * @param purStockEntities
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public List<StockEntity> save(@RequestBody List<StockEntity> purStockEntities) {
        try {
            return stockService.save(purStockEntities);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<Map<String, Object>> queryPage(Pageable page, String keywords, boolean expireDate) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<Map<String, Object>>(stockService.queryPage(page, keywords, expireDate));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 查询预警库存
     * @param page
     * @param keywords
     * @return
     */
    @RequestMapping(value = "/queryPageForWarn", method = RequestMethod.GET)
    public LayuiTableEntity<Map<String, Object>> queryPageForWarn(Pageable page, String keywords) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<Map<String, Object>>(stockService.queryPageForWarn(page, keywords));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 查询已过期
     * @param page
     * @return
     */
    @RequestMapping(value = "/queryPageForExpire", method = RequestMethod.GET)
    public LayuiTableEntity<Map<String, Object>> queryPageForExpire(Pageable page) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<Map<String, Object>>(stockService.queryPageForExpire(page));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 更新 售价 库存数量
     * @param stockEntity
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public StockEntity update(@RequestBody StockEntity stockEntity) {
        try {
            return stockService.update(stockEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 下架
     * @param purStockId
     * @return
     */
    @RequestMapping(value = "/unshelve", method = RequestMethod.GET)
    public Boolean unshelve(@RequestParam Long purStockId) {
        try {
            return stockService.unshelve(purStockId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 查看库存品目的采购信息
     * @param purStockId 库存id
     * @return
     */
    @RequestMapping(value = "/findByIdForOrder", method = RequestMethod.GET)
    public Map<String,Object> findByIdForOrder(@RequestParam Long purStockId) {
        try {
            return stockService.findByIdForOrder(purStockId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new HashMap<>();
    }

    /**
     * 查询库存中药
     * @return
     */
    @RequestMapping(value = "/getCombogridForHerbalMedicine", method = RequestMethod.GET)
    public List<Map<String, Object>> getCombogridForHerbalMedicine(
            @RequestParam(value = "q", required = false) String keywords) {
        try {
            return stockService.getCombogridForHerbalMedicine(keywords);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
}
