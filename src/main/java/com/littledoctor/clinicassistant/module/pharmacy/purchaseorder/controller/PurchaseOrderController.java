package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.plugin.layui.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity.PharmacyItem;
import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.service.PharmacyItemService;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrder;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrderDetail;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity.PurchaseOrderSingle;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service.PurchaseOrderService;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.entity.Supplier;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.service.SupplierService;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryItem;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryType;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:11
 * @Description: 采购单
 */
@RestController
@RequestMapping(value = "/pharmacy/purchaseorder")
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
            PurchaseOrder purchaseOrder = purchaseOrderService.queryById(purchaseOrderId);
            return purchaseOrder;
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
