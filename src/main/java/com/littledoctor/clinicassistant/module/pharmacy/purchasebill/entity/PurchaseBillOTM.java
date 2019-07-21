package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:23
 * @Description: 采购单
 */
@Entity
@Table(name = "PURCHASE_BILL")
public class PurchaseBillOTM {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASE_BILL_ID", nullable = false)
    private Integer purchaseBillId;

    /** 采购单号，取当前时间yyyymmddhhmmsssss*/
    @Column(name = "PURCHASE_BILL_CODE")
    private String purchaseBillCode;

    /** 采购单日期 */
    @Column(name = "PURCHASE_BILL_DATE")
    private String purchaseBillDate;

    /** 供货商ID */
    @Column(name = "SUPPLIER_ID")
    private Integer supplierId;

    /** 供货商名称 */
    @Transient
    private String supplierName;

    /** 供货商联系方式 */
    @Column(name = "SUPPLIER_PHONE")
    private String supplierPhone;

/*    *//** 采购单类型, CGDLX: 1:药品；2：医疗器械；3：日常用品*//*
    @Column(name = "PURCHASE_BILL_TYPE")
    private Integer purchaseBillType;*/

    /** 采购单总价 */
    @Column(name = "TOTAL_PRICE", columnDefinition = "double(10,2) default '0.00'")
    private Double totalPrice;

    /** 采购单照片附件 */
    @Column(name = "PURCHASE_BILL_PICTURE")
    private Blob purchaseBillPicture;

    /** 采购单创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTiem;

    /** 采购单更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 是否已入库 SF 1：已入库；0：未入库*/
    @Column(name = "WAREHOUSING_ENTRY")
    private Boolean warehousingEntry;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "PURCHASE_BILL_ID")
    private List<PurchaseBillItem> purchaseBillItems = new ArrayList<>();

    public Integer getPurchaseBillId() {
        return purchaseBillId;
    }

    public void setPurchaseBillId(Integer purchaseBillId) {
        this.purchaseBillId = purchaseBillId;
    }

    public String getPurchaseBillCode() {
        return purchaseBillCode;
    }

    public void setPurchaseBillCode(String purchaseBillCode) {
        this.purchaseBillCode = purchaseBillCode;
    }

    public String getPurchaseBillDate() {
        return purchaseBillDate;
    }

    public void setPurchaseBillDate(String purchaseBillDate) {
        this.purchaseBillDate = purchaseBillDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Blob getPurchaseBillPicture() {
        return purchaseBillPicture;
    }

    public void setPurchaseBillPicture(Blob purchaseBillPicture) {
        this.purchaseBillPicture = purchaseBillPicture;
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

    public Boolean getWarehousingEntry() {
        return warehousingEntry;
    }

    public void setWarehousingEntry(Boolean warehousingEntry) {
        this.warehousingEntry = warehousingEntry;
    }

    public List<PurchaseBillItem> getPurchaseBillItems() {
        return purchaseBillItems;
    }

    public void setPurchaseBillItems(List<PurchaseBillItem> purchaseBillItems) {
        this.purchaseBillItems = purchaseBillItems;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
