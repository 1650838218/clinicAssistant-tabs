package com.littledoctor.clinicassistant.module.purchase.purchaseorder.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-27 13:32
 * @Description:
 */
@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrderSingle {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURCHASE_ORDER_ID", nullable = false)
    private Integer purchaseOrderId;

    /** 采购单号，取当前时间yyyymmddhhmmsssss*/
    @Column(name = "PURCHASE_ORDER_CODE")
    private String purchaseOrderCode;

    /** 采购单日期 */
    @Column(name = "PURCHASE_ORDER_DATE")
    private String purchaseOrderDate;

    /** 采购单类型 */
    @Column(name = "PURCHASE_ORDER_TYPE")
    private String purchaseOrderType;

    /** 供货商ID */
    @Column(name = "SUPPLIER_ID")
    private Integer supplierId;

    /** 采购单总价 */
    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    /** 采购单照片附件 */
    @Column(name = "PURCHASE_ORDER_PICTURE")
    private Blob purchaseOrderPicture;

    /** 是否已入库 SF 1：已入库；0：未入库*/
    @Column(name = "IS_ENTRY")
    private Boolean isEntry;

    /** 采购单创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTiem;

    /** 采购单更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 供货商联系人 */
    @Transient
    private String supplierName;

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public String getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(String purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getPurchaseOrderType() {
        return purchaseOrderType;
    }

    public void setPurchaseOrderType(String purchaseOrderType) {
        this.purchaseOrderType = purchaseOrderType;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Blob getPurchaseOrderPicture() {
        return purchaseOrderPicture;
    }

    public void setPurchaseOrderPicture(Blob purchaseOrderPicture) {
        this.purchaseOrderPicture = purchaseOrderPicture;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
