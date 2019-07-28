package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-11 18:23
 * @Description: 入库单
 */
@Entity
@Table(name = "WAREHOUSING_ENTRY")
public class WarehousingEntry {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WAREHOUSING_ENTRY_ID", nullable = false)
    private Integer warehousingEntryId;

    /** 采购单ID */
    @Column(name = "PURCHASE_BILL_ID")
    private Integer purchaseBillId;

    /** 采购单号 */
    @Transient
    private String purchaseBillCode;

    /** 入库单单号 取当前时间yyyymmddhhmmsssss */
    @Column(name = "WAREHOUSING_ENTRY_CODE")
    private String warehousingEntryCode;

    /** 入库单日期 */
    @Column(name = "WAREHOUSING_ENTRY_DATE")
    private String warehousingEntryDate;

    /** 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 入库单明细 */
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "WAREHOUSING_ENTRY_ID")
    private List<WarehousingEntryItem> warehousingEntryItems = new ArrayList<>();

    public Integer getWarehousingEntryId() {
        return warehousingEntryId;
    }

    public void setWarehousingEntryId(Integer warehousingEntryId) {
        this.warehousingEntryId = warehousingEntryId;
    }

    public String getWarehousingEntryCode() {
        return warehousingEntryCode;
    }

    public void setWarehousingEntryCode(String warehousingEntryCode) {
        this.warehousingEntryCode = warehousingEntryCode;
    }

    public String getWarehousingEntryDate() {
        return warehousingEntryDate;
    }

    public void setWarehousingEntryDate(String warehousingEntryDate) {
        this.warehousingEntryDate = warehousingEntryDate;
    }

    public Integer getPurchaseBillId() {
        return purchaseBillId;
    }

    public void setPurchaseBillId(Integer purchaseBillId) {
        this.purchaseBillId = purchaseBillId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<WarehousingEntryItem> getWarehousingEntryItems() {
        return warehousingEntryItems;
    }

    public void setWarehousingEntryItems(List<WarehousingEntryItem> warehousingEntryItems) {
        this.warehousingEntryItems = warehousingEntryItems;
    }

    public String getPurchaseBillCode() {
        return purchaseBillCode;
    }

    public void setPurchaseBillCode(String purchaseBillCode) {
        this.purchaseBillCode = purchaseBillCode;
    }

    /** 复制一个新的对象，不包含入库单明细 */
    public WarehousingEntry copy(){
        WarehousingEntry we = new WarehousingEntry();
        we.setCreateTime(this.getCreateTime());
        we.setPurchaseBillId(this.getPurchaseBillId());
        we.setUpdateTime(this.getUpdateTime());
        we.setWarehousingEntryCode(this.getWarehousingEntryCode());
        we.setWarehousingEntryDate(this.getWarehousingEntryDate());
        we.setWarehousingEntryId(this.getWarehousingEntryId());
        return we;
    }
}
