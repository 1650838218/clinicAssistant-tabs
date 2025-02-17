package com.littledoctor.clinicassistant.module.purchase.order.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.purchase.order.entity.OrderEntity;
import com.littledoctor.clinicassistant.module.purchase.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:11
 * @Description: 采购单
 */
@RestController
@RequestMapping(value = "/purchase/order")
public class OrderController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询
     * @param page
     * @param itemName
     * @param purOrderDate
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<Map<String,Object>> queryPage(Pageable page, String itemName, String purOrderDate, String supplierId) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<Map<String,Object>>(orderService.queryPage(page, itemName, purOrderDate, supplierId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 保存采购单
     * @param orderEntity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public OrderEntity save(@RequestBody OrderEntity orderEntity) {
        try {
            Assert.notNull(orderEntity, Message.PARAMETER_IS_NULL);
            return orderService.save(orderEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据采购单ID查询采购单
     * @param purOrderId
     * @return
     */
    @RequestMapping(value = "queryById", method = RequestMethod.GET)
    public OrderEntity queryById(@RequestParam String purOrderId) {
        try {
            return orderService.queryById(purOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new OrderEntity();
    }

    /**
     * 入库，根据采购单ID查询采购单
     * @param purOrderId
     * @return
     */
    @RequestMapping(value = "queryByIdForStock", method = RequestMethod.GET)
    public Map<String, Object> queryByIdForStock(@RequestParam String purOrderId) {
        try {
            return orderService.queryByIdForStock(purOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new HashMap<>();
    }

    /**
     * 删除采购单
     * @param purOrderId
     * @return
     */
    @RequestMapping(value = "/delete/{purOrderId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("purOrderId") String purOrderId) {
        try {
            Assert.notNull(purOrderId, Message.PARAMETER_IS_NULL);
            return orderService.delete(purOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 查询采购品目
     * @param keywords
     * @return
     */
    @RequestMapping(value = "/getPurchaseItem", method = RequestMethod.GET)
    public List<Map<String, Object>> getPurchaseItem(@RequestParam(value = "q", required = false) String keywords) {
        try {
            return orderService.getPurchaseItem(keywords);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
}
