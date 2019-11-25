package com.littledoctor.clinicassistant.module.purchase.purchaseorder.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.purchase.purchaseorder.entity.PurchaseOrder;
import com.littledoctor.clinicassistant.module.purchase.purchaseorder.entity.PurchaseOrderSingle;
import com.littledoctor.clinicassistant.module.purchase.purchaseorder.service.PurchaseOrderService;
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
@RequestMapping(value = "/purchase/purchaseorder")
public class PurchaseOrderController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<PurchaseOrderSingle> queryPage(Pageable page, String purchaseOrderCode, String purchaseOrderDate, String supplierId) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<PurchaseOrderSingle>(purchaseOrderService.queryPage(page, purchaseOrderCode, purchaseOrderDate, supplierId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存采购单
     * @param purchaseOrder
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public PurchaseOrder save(@RequestBody PurchaseOrder purchaseOrder) {
        try {
            Assert.notNull(purchaseOrder, Message.PARAMETER_IS_NULL);
            return purchaseOrderService.save(purchaseOrder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据采购单ID查询采购单
     * @param purchaseOrderId
     * @return
     */
    @RequestMapping(value = "queryById", method = RequestMethod.GET)
    public PurchaseOrder queryById(@RequestParam String purchaseOrderId) {
        try {
            return purchaseOrderService.queryById(purchaseOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除采购单
     * @param purchaseOrderId
     * @return
     */
    @RequestMapping(value = "/delete/{purchaseOrderId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("purchaseOrderId") String purchaseOrderId) {
        try {
            Assert.notNull(purchaseOrderId, Message.PARAMETER_IS_NULL);
            return purchaseOrderService.delete(purchaseOrderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}