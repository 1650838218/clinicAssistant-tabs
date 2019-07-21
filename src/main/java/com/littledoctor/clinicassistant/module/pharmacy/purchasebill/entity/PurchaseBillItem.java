package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:18
 * @Description: 采购单明细
 */
@Entity
@Table(name = "PURCHASE_BILL_ITEM")
public class PurchaseBillItem {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASE_BILL_ITEM_ID", nullable = false)
    private Integer purchaseBillItemId;

    /** 药品清单ID，为了关联采购的是哪个药品 */
    @Column(name = "MEDICINE_LIST_ID")
    private Integer medicineListId;

    /** 商品名称，当采购的物品不是药品时，需要填写此字段 */
    @Column(name = "GOODS_NAME")
    private String goodsName;

    /** 数量 */
    @Column(name = "COUNT", columnDefinition = "double(10,2)")
    private Double count;

    /** 数量单位 SLDW 1：公斤(千克)；2：箱 */
    @Column(name = "COUNT_UNIT")
    private Integer countUnit;

    /** 数量单位 字典名 */
    @Transient
    private String countUnitName;

    /** 单价 */
    @Column(name = "UNIT_PRICE", columnDefinition = "double(10,2)")
    private Double unitPrice;

    /** 总价 */
    @Column(name = "TOTAL_PRICE", columnDefinition = "double(10,2)")
    private Double totalPrice;

    public Integer getPurchaseBillItemId() {
        return purchaseBillItemId;
    }

    public void setPurchaseBillItemId(Integer purchaseBillItemId) {
        this.purchaseBillItemId = purchaseBillItemId;
    }
/*
    public Integer getPurchaseBillId() {
        return purchaseBillId;
    }

    public void setPurchaseBillId(Integer purchaseBillId) {
        this.purchaseBillId = purchaseBillId;
    }*/

    public Integer getMedicineListId() {
        return medicineListId;
    }

    public void setMedicineListId(Integer medicineListId) {
        this.medicineListId = medicineListId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getCount() {
        return Double.valueOf(new BigDecimal(this.count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Integer getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(Integer countUnit) {
        this.countUnit = countUnit;
    }

    public Double getUnitPrice() {
        return Double.valueOf(new BigDecimal(this.unitPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return Double.valueOf(new BigDecimal(this.totalPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCountUnitName() {
        return countUnitName;
    }

    public void setCountUnitName(String countUnitName) {
        this.countUnitName = countUnitName;
    }
}
