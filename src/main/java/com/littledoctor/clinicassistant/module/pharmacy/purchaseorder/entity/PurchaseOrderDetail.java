package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:18
 * @Description: 采购单明细
 */
@Entity
@Table(name = "PURCHASE_ORDER_DETAIL")
public class PurchaseOrderDetail {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURCHASE_ORDER_DETAIL_ID", nullable = false)
    private Integer purchaseOrderDetailId;

    /** 药房品目，为了关联采购的是哪个药品 */
    @Column(name = "PHARMACY_ITEM_ID")
    private Integer pharmacyItemId;

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

    /** 单价 */
    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    /** 总价 */
    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    /** 药品名称 */
    @Transient
    private String pharmacyItemName;

    /** 规格 */
    @Transient
    private String specifications;

    /** 制造商 */
    @Transient
    private String manufacturer;

    /** 进货单位名称 */
    @Transient
    private String purchaseUnitName;

    /** 库存单位名称 */
    @Transient
    private String stockUnitName;

    /** 库存数量 */
    @Transient
    private Double stockCount;

    public Integer getPurchaseOrderDetailId() {
        return purchaseOrderDetailId;
    }

    public void setPurchaseOrderDetailId(Integer purchaseOrderDetailId) {
        this.purchaseOrderDetailId = purchaseOrderDetailId;
    }

    public Integer getPharmacyItemId() {
        return pharmacyItemId;
    }

    public void setPharmacyItemId(Integer pharmacyItemId) {
        this.pharmacyItemId = pharmacyItemId;
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

    public String getPharmacyItemName() {
        return pharmacyItemName;
    }

    public void setPharmacyItemName(String pharmacyItemName) {
        this.pharmacyItemName = pharmacyItemName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPurchaseUnitName() {
        return purchaseUnitName;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
    }

    public String getStockUnitName() {
        return stockUnitName;
    }

    public void setStockUnitName(String stockUnitName) {
        this.stockUnitName = stockUnitName;
    }

    public Double getStockCount() {
        return stockCount;
    }

    public void setStockCount(Double stockCount) {
        this.stockCount = stockCount;
    }
}
