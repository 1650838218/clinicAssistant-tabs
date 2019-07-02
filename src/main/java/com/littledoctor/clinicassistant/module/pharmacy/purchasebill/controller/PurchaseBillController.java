package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.plugin.layui.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBill;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.service.PurchaseBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:11
 * @Description: 采购单
 */
@RestController
@RequestMapping(value = "/pharmacy/purchasebill")
public class PurchaseBillController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PurchaseBillService purchaseBillService;

    /**
     * 分页查询
     * @return
     */
    public LayuiTableEntity<PurchaseBill> queryPage(Pageable page, String dateRange, String supplierId, String orderType) {
        try {
            return new LayuiTableEntity<PurchaseBill>(purchaseBillService.queryPage(page, dateRange, supplierId, orderType));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存采购单
     * @param purchaseBill
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public PurchaseBill save(@RequestBody PurchaseBill purchaseBill) {
        try {
            Assert.notNull(purchaseBill, Message.PARAMETER_IS_NULL);
            return purchaseBillService.save(purchaseBill);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据采购单ID查询采购单
     * @param purchaseBillId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryById", method = RequestMethod.GET)
    public PurchaseBill queryById(@RequestParam String purchaseBillId) {
        try {
            return purchaseBillService.queryById(purchaseBillId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
