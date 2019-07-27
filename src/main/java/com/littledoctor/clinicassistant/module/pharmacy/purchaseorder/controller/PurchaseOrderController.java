package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.plugin.layui.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po.PurchaseOrderPo;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po.PurchaseOrderDetailPo;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service.PurchaseOrderService;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.vo.PurchaseOrderVo;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.service.SupplierService;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryItem;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryType;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<PurchaseOrderVo> queryPage(Pageable page, String purchaseOrderCode, String purchaseOrderDate, String supplierId) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            Page<PurchaseOrderPo> pos = purchaseOrderService.queryPage(page, purchaseOrderCode, purchaseOrderDate, supplierId);

            return new LayuiTableEntity<PurchaseOrderVo>();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存采购单
     * @param purchaseOrderPo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public PurchaseOrderPo save(@RequestBody PurchaseOrderPo purchaseOrderPo) {
        try {
            Assert.notNull(purchaseOrderPo, Message.PARAMETER_IS_NULL);
            return purchaseOrderService.save(purchaseOrderPo);
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
    public PurchaseOrderPo queryById(@RequestParam String purchaseOrderId, String type) {
        try {
            PurchaseOrderPo purchaseOrderPo = purchaseOrderService.queryById(purchaseOrderId);
            if ("EAGER".equals(type)) {
                purchaseOrderPo.getSupplier();// 查询供应商
                // 查询字典显示值
                if (purchaseOrderPo.getPurchaseOrderDetailPos() != null && purchaseOrderPo.getPurchaseOrderDetailPos().size() > 0) {
                    DictionaryType dt = dictionaryService.getByKey("SLDW");
                    if (dt != null && dt.getDictItem() != null && dt.getDictItem().size() > 0) {
                        for (int i = 0; i < purchaseOrderPo.getPurchaseOrderDetailPos().size(); i++) {
                            PurchaseOrderDetailPo pbi = purchaseOrderPo.getPurchaseOrderDetailPos().get(i);
                            if (pbi.getPurchaseUnit() != null) {
                                for (int j = 0; j < dt.getDictItem().size(); j++) {
                                    DictionaryItem di = dt.getDictItem().get(j);
                                    if (pbi.getPurchaseUnit().equals(Integer.valueOf(di.getDictItemValue()))) {
                                        pbi.setPurchaseUnitName(di.getDictItemName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return purchaseOrderPo;
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
