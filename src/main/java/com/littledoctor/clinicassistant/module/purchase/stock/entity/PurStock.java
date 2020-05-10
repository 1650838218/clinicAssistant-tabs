package com.littledoctor.clinicassistant.module.purchase.stock.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-11 18:24
 * @Description: 库存明细
 */
@Entity
@Table(name = "PUR_STOCK")
public class PurStock {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUR_STOCK_ID", nullable = false)
    private Long purStockId;

    /** 采购单ID */
    @Column(name = "PUR_ORDER_ID")
    private Long purOrderId;

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

    /** 数量，购进的数量 */
    @Column(name = "STOCK_COUNT")
    private Double stockCount;

    /** 零售价 */
    @Column(name = "SELLING_PRICE")
    private Double sellingPrice;

    /** 库存状态(1：正常；2：已退货；3：已过期；4：下架;5: 已售完) */
    @Column(name = "STOCK_STATE")
    private Integer stockState;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTiem;

    /** 更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public Long getPurStockId() {
        return purStockId;
    }

    public void setPurStockId(Long purStockId) {
        this.purStockId = purStockId;
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

    public Long getPurOrderId() {
        return purOrderId;
    }

    public void setPurOrderId(Long purOrderId) {
        this.purOrderId = purOrderId;
    }

    public Long getPurItemId() {
        return purItemId;
    }

    public void setPurItemId(Long purItemId) {
        this.purItemId = purItemId;
    }
}
