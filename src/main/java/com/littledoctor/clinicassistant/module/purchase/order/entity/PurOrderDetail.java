package com.littledoctor.clinicassistant.module.purchase.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:18
 * @Description: 采购单明细
 */
@Entity
@Table(name = "PUR_ORDER_DETAIL")
public class PurOrderDetail {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUR_ORDER_DETAIL_ID", nullable = false)
    private Long PurOrderDetailId;

    /** 采购品目，为了关联采购的是哪个品目 */
    @Column(name = "PUR_ITEM_ID")
    private Long purItemId;

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
    @Column(name = "PUR_COUNT")
    private BigDecimal PurCount;

    /** 单价 */
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 总价 */
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    /** 品目名称 */
    @Transient
    private String purItemName;

    /** 采购单位名称 */
    @Transient
    private String purUnitName;

    /** 库存单位名称 */
    @Transient
    private String stockUnitName;

    /** 单位换算 */
    @Transient
    private Double unitConvert;

    /** 库存数量 */
    @Transient
    private Double stockCount;

    public Long getPurOrderDetailId() {
        return PurOrderDetailId;
    }

    public void setPurOrderDetailId(Long purOrderDetailId) {
        PurOrderDetailId = purOrderDetailId;
    }

    public Long getPurItemId() {
        return purItemId;
    }

    public void setPurItemId(Long purItemId) {
        this.purItemId = purItemId;
    }

    public BigDecimal getPurCount() {
        return PurCount;
    }

    public void setPurCount(BigDecimal purCount) {
        PurCount = purCount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getPurItemName() {
        return purItemName;
    }

    public void setPurItemName(String purItemName) {
        this.purItemName = purItemName;
    }

    public String getPurUnitName() {
        return purUnitName;
    }

    public void setPurUnitName(String purUnitName) {
        this.purUnitName = purUnitName;
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

    public Double getUnitConvert() {
        return unitConvert;
    }

    public void setUnitConvert(Double unitConvert) {
        this.unitConvert = unitConvert;
    }
}
