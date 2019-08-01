package com.littledoctor.clinicassistant.module.pharmacy.stock.controller;

import com.littledoctor.clinicassistant.module.pharmacy.stock.entity.StockDetail;
import com.littledoctor.clinicassistant.module.pharmacy.stock.service.StockDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
