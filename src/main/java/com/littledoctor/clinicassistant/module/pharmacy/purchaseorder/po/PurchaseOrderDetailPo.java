package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po;

import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity.PharmacyItem;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:18
 * @Description: 采购单明细
 */
@Entity
@Table(name = "PURCHASE_ORDER_DETAIL")
public class PurchaseOrderDetailPo implements Serializable {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASE_ORDER_DETAIL_ID", nullable = false)
    private Integer purchaseOrderDetailId;

    /** 药房品目，为了关联采购的是哪个药品 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHARMACY_ITEM_ID")
    private PharmacyItem pharmacyItem;

    /** 商品名称，当采购的物品不是药品时，需要填写此字段 */
    @Column(name = "GOODS_NAME")
    private String goodsName;

    /** 批号 */
    @Column(name = "BATCH_NUMBER")
    private String batchNumber;

    /** 生产日期 */
    @Column(name = "MANUFACTURE_DATE")
    private String manufactureDate;

    /** 有效期至 */
    @Column(name = "EXPIRE_DATE")
    private String expireDate;

    /** 采购数量 */
    @Column(name = "PURCHASE_COUNT")
    private Double purchaseCount;

    /** 数量单位 SLDW 1：公斤(千克)；2：箱 */
    @Column(name = "PURCHASE_UNIT")
    private Integer purchaseUnit;

    /** 数量单位 字典名 */
    @Transient
    private String purchaseUnitName;

    /** 单价 */
    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    /** 总价 */
    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    public Integer getPurchaseOrderDetailId() {
        return purchaseOrderDetailId;
    }

    public void setPurchaseOrderDetailId(Integer purchaseOrderDetailId) {
        this.purchaseOrderDetailId = purchaseOrderDetailId;
    }

    public PharmacyItem getPharmacyItem() {
        return pharmacyItem;
    }

    public void setPharmacyItem(PharmacyItem pharmacyItem) {
        this.pharmacyItem = pharmacyItem;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Double getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Double purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Integer getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(Integer purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public String getPurchaseUnitName() {
        return purchaseUnitName;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
