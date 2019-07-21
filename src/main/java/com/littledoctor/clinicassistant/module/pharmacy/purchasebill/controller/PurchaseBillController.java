package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.plugin.layui.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBill;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBillItem;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBillOTM;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.service.PurchaseBillService;
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

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<PurchaseBill> queryPage(Pageable page, String purchaseBillCode, String purchaseBillDate, String supplierId) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            return new LayuiTableEntity<PurchaseBill>(purchaseBillService.queryPage(page, purchaseBillCode, purchaseBillDate, supplierId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存采购单
     * @param purchaseBillOTM
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public PurchaseBillOTM save(@RequestBody PurchaseBillOTM purchaseBillOTM) {
        try {
            Assert.notNull(purchaseBillOTM, Message.PARAMETER_IS_NULL);
            return purchaseBillService.save(purchaseBillOTM);
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
    public PurchaseBillOTM queryById(@RequestParam String purchaseBillId, String type) {
        try {
            PurchaseBillOTM purchaseBill = purchaseBillService.queryById(purchaseBillId);
            if ("EAGER".equals(type)) {
                if (purchaseBill.getSupplierId() != null) {
                    purchaseBill.setSupplierName(supplierService.findById(purchaseBill.getSupplierId().toString()).getSupplierName());
                }
                if (purchaseBill.getPurchaseBillItems() != null && purchaseBill.getPurchaseBillItems().size() > 0) {
                    DictionaryType dt = dictionaryService.getByKey("SLDW");
                    if (dt != null && dt.getDictItem() != null && dt.getDictItem().size() > 0) {
                        for (int i = 0; i < purchaseBill.getPurchaseBillItems().size(); i++) {
                            PurchaseBillItem pbi = purchaseBill.getPurchaseBillItems().get(i);
                            if (pbi.getCountUnit() != null) {
                                for (int j = 0; j < dt.getDictItem().size(); j++) {
                                    DictionaryItem di = dt.getDictItem().get(j);
                                    if (pbi.getCountUnit().equals(Integer.valueOf(di.getDictItemValue()))) {
                                        pbi.setCountUnitName(di.getDictItemName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return purchaseBill;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除采购单
     * @param purchaseBillId
     * @return
     */
    @RequestMapping(value = "/delete/{purchaseBillId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("purchaseBillId") String purchaseBillId) {
        try {
            Assert.notNull(purchaseBillId, Message.PARAMETER_IS_NULL);
            return purchaseBillService.delete(purchaseBillId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
