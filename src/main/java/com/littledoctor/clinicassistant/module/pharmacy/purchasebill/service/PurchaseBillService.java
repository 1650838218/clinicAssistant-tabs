package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBill;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBillOTM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
public interface PurchaseBillService {

    /**
     * 分页查询订单
     * @param page
     * @param purchaseBillCode
     * @param purchaseBillDate
     * @param supplierId
     * @return
     */
    Page<PurchaseBill> queryPage(Pageable page, String purchaseBillCode, String purchaseBillDate, String supplierId) throws Exception;

    /**
     * 保存采购单
     * @param purchaseBillOTM
     * @return
     */
    PurchaseBillOTM save(PurchaseBillOTM purchaseBillOTM);

    /**
     * 根据采购单id查询采购单
     * @param purchaseBillId
     * @return
     */
    PurchaseBillOTM queryById(String purchaseBillId);

    /**
     * 删除采购单
     * @param purchaseBillId
     * @return
     */
    boolean delete(String purchaseBillId);
}
