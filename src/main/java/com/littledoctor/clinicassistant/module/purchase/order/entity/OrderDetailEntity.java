package com.littledoctor.clinicassistant.module.purchase.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:18
 * @Description: 采购单明细
 */
@Entity
@Table(name = "PUR_ORDER_DETAIL")
public class OrderDetailEntity {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUR_ORDER_DETAIL_ID", nullable = false)
    private Long purOrderDetailId;

    /** 采购品目，为了关联采购的是哪个品目 */
    @Column(name = "ITEM_ID")
    private Long itemId;

    /** 品目名称 */
    @Column(name = "item_name")
    private String itemName;

    /** 品目分类 */
    @Column(name = "item_type")
    private String itemType;

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
    private BigDecimal purCount;

    /** 采购单位名称 */
    @Column(name = "PUR_UNIT_NAME")
    private String purUnitName;

    /** 单价 */
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 总价 */
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    /** 库存单位名称 */
    @Transient
    private String stockUnitName;

    /** 单位换算 */
    @Transient
    private Long unitConvert;

    /** 库存数量 */
    @Transient
    private BigDecimal stockCount;

    public Long getPurOrderDetailId() {
        return purOrderDetailId;
    }

    public void setPurOrderDetailId(Long purOrderDetailId) {
        this.purOrderDetailId = purOrderDetailId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getPurCount() {
        return purCount;
    }

    public void setPurCount(BigDecimal purCount) {
        this.purCount = purCount;
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

    public BigDecimal getStockCount() {
        return stockCount;
    }

    public void setStockCount(BigDecimal stockCount) {
        this.stockCount = stockCount;
    }

    public Long getUnitConvert() {
        return unitConvert;
    }

    public void setUnitConvert(Long unitConvert) {
        this.unitConvert = unitConvert;
    }
}
