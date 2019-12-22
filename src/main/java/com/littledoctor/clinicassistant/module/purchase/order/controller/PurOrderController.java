package com.littledoctor.clinicassistant.module.purchase.order.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrder;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrderSingle;
import com.littledoctor.clinicassistant.module.purchase.order.service.PurOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:11
 * @Description: 采购单
 */
@RestController
@RequestMapping(value = "/purchase/order")
public class PurOrderController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PurOrderService purOrderService;

    /**
     * 分页查询
     * @param page
     * @param purOrderCode
     * @param purOrderDate
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<PurOrder> queryPage(Pageable page, String purOrderCode, String purOrderDate, String supplierId) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<PurOrder>(purOrderService.queryPage(page, purOrderCode, purOrderDate, supplierId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 保存采购单
     * @param purOrder
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public PurOrder save(@RequestBody PurOrder purOrder) {
        try {
            Assert.notNull(purOrder, Message.PARAMETER_IS_NULL);
            return purOrderService.save(purOrder);
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
    public PurOrder queryById(@RequestParam String purOrderId) {
        try {
            return purOrderService.queryById(purOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new PurOrder();
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
            return purOrderService.delete(purOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
