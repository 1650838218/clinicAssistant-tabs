package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.entity;

import com.littledoctor.clinicassistant.module.pharmacy.supplier.entity.Supplier;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-06 13:50
 * @Description:
 */
@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASE_ORDER_ID", nullable = false)
    private Integer purchaseOrderId;

    /** 采购单号，取当前时间yyyymmddhhmmsssss*/
    @Column(name = "PURCHASE_ORDER_CODE")
    private String purchaseOrderCode;

    /** 采购单日期 */
    @Column(name = "PURCHASE_ORDER_DATE")
    private String purchaseOrderDate;

    /** 供货商ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID")
    private Supplier supplier;

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

    /** 采购单更新时间 */    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "PURCHASE_ORDER_ID")
    private List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public List<PurchaseOrderDetail> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(List<PurchaseOrderDetail> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }
}