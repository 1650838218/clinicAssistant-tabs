package com.littledoctor.clinicassistant.module.purchase.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-06 13:50
 * @Description: 采购单 进货单
 */
@Entity
@Table(name = "PUR_ORDER")
public class PurOrder {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUR_ORDER_ID", nullable = false)
    private Long purOrderId;

    /** 采购单号，取当前时间yyyymmddhhmmsssss*/
    @Column(name = "PUR_ORDER_CODE")
    private String purOrderCode;

    /** 采购单日期 */
    @Column(name = "PUR_ORDER_DATE")
    private String purOrderDate;

    /** 供货商ID */
    @Column(name = "SUPPLIER_ID")
    private Long supplierId;

    /** 采购单总价 */
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    /** 采购单照片附件 */
    @Column(name = "PUR_ORDER_PICTURE")
    private Blob purOrderPicture;

    /** 是否已入库 SF 1：已入库；0：未入库*/
    @Column(name = "IS_ENTRY")
    private Boolean isEntry;

    /** 采购单创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTiem;

    /** 采购单更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "PUR_ORDER_ID")
    private List<PurOrderDetail> purOrderDetails = new ArrayList<>();

    /** 供货商联系人 */
    @Transient
    private String supplierName;

    /** 采购品目名称，本采购单所采购的品目的名称，用逗号分隔 */
    @Transient
    private String purItemNames;

    public Long getPurOrderId() {
        return purOrderId;
    }

    public void setPurOrderId(Long purOrderId) {
        this.purOrderId = purOrderId;
    }

    public String getPurOrderCode() {
        return purOrderCode;
    }

    public void setPurOrderCode(String purOrderCode) {
        this.purOrderCode = purOrderCode;
    }

    public String getPurOrderDate() {
        return purOrderDate;
    }

    public void setPurOrderDate(String purOrderDate) {
        this.purOrderDate = purOrderDate;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Blob getPurOrderPicture() {
        return purOrderPicture;
    }

    public void setPurOrderPicture(Blob purOrderPicture) {
        this.purOrderPicture = purOrderPicture;
    }

    public Boolean getEntry() {
        return isEntry;
    }

    public void setEntry(Boolean entry) {
        isEntry = entry;
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

    public List<PurOrderDetail> getPurOrderDetails() {
        return purOrderDetails;
    }

    public void setPurOrderDetails(List<PurOrderDetail> purOrderDetails) {
        this.purOrderDetails = purOrderDetails;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurItemNames() {
        return purItemNames;
    }

    public void setPurItemNames(String purItemNames) {
        this.purItemNames = purItemNames;
    }
}
