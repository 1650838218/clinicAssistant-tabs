package com.littledoctor.clinicassistant.module.pharmacy.stock.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-11 18:24
 * @Description: 库存明细
 */
@Entity
@Table(name = "STOCK_DETAIL")
public class StockDetail {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STOCK_DETAIL_ID", nullable = false)
    private Integer stockDetailId;

    /** 采购单ID */
    @Column(name = "PURCHASE_ORDER_ID")
    private Integer purchaseOrderId;

    /** 药房品目，为了关联采购的是哪个药品 */
    @Column(name = "PHARMACY_ITEM_ID")
    private Integer pharmacyItemId;

    /** 批号 */
    @Column(name = "BATCH_NUMBER")
    private String batchNumber;

    /** 生产日期 */
    @Column(name = "MANUFACTURE_DATE")
    private String manufactureDate;

    /** 有效期至 */
    @Column(name = "EXPIRE_DATE")
    private String expireDate;

    /** 数量，购进的数量 */
    @Column(name = "STOCK_COUNT")
    private Double stockCount;

    /** 零售价 */
    @Column(name = "SELLING_PRICE")
    private Double sellingPrice;

    /** 库存状态(1：正常；2：已退货；3：已过期) */
    @Column(name = "STOCK_STATE")
    private Integer stockState;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTiem;

    /** 更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public Integer getStockDetailId() {
        return stockDetailId;
    }

    public void setStockDetailId(Integer stockDetailId) {
        this.stockDetailId = stockDetailId;
    }

    public Double getStockCount() {
        return stockCount;
    }

    public void setStockCount(Double stockCount) {
        this.stockCount = stockCount;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getPharmacyItemId() {
        return pharmacyItemId;
    }

    public void setPharmacyItemId(Integer pharmacyItemId) {
        this.pharmacyItemId = pharmacyItemId;
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

    public Integer getStockState() {
        return stockState;
    }

    public void setStockState(Integer stockState) {
        this.stockState = stockState;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Date getCreateTiem() {
        return createTiem;
    }

    public void setCreateTiem(Date createTiem) {
        this.createTiem = createTiem;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
