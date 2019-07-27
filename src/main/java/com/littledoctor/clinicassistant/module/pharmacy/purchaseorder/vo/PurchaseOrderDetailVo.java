package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.vo;

import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity.PharmacyItem;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po.PurchaseOrderDetailPo;

import javax.persistence.*;

/**
 * @业务信息: 采购单明细 vo类
 * @Filename: PurchaseOrderDetailVo.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-07-25   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-07-25   周俊林
 */
public class PurchaseOrderDetailVo {

    /** 主键ID */
    private Integer purchaseOrderDetailId;

    /** 药房品目，为了关联采购的是哪个药品 */
    private Integer pharmacyItemId;

    /** 药房品目，为了关联采购的是哪个药品 */
    private String pharmacyItemName;

    /** 商品名称，当采购的物品不是药品时，需要填写此字段 */
    private String goodsName;

    /** 批号 */
    private String batchNumber;

    /** 生产日期 */
    private String manufactureDate;

    /** 有效期至 */
    private String expireDate;

    /** 采购数量 */
    private Double purchaseCount;

    /** 数量单位 SLDW 1：公斤(千克)；2：箱 */
    private Integer purchaseUnit;

    /** 数量单位 字典名 */
    private String purchaseUnitName;

    /** 单价 */
    private Double unitPrice;

    /** 总价 */
    private Double totalPrice;

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

    public String getPharmacyItemName() {
        return pharmacyItemName;
    }

    public void setPharmacyItemName(String pharmacyItemName) {
        this.pharmacyItemName = pharmacyItemName;
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

    public String getPurchaseUnitName() {
        return purchaseUnitName;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
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

    // 将vo转成po
    public PurchaseOrderDetailPo transformPo() {
        PurchaseOrderDetailPo po = new PurchaseOrderDetailPo();
        po.setBatchNumber(this.getBatchNumber());
        po.setExpireDate(this.getExpireDate());
        po.setGoodsName(this.getGoodsName());
        po.setManufactureDate(this.getManufactureDate());
        po.setPharmacyItemId(this.getPharmacyItemId());
        po.setPurchaseCount(this.getPurchaseCount());
        po.setPurchaseOrderDetailId(this.getPurchaseOrderDetailId());
        po.setPurchaseUnit(this.getPurchaseUnit());
        po.setTotalPrice(this.getTotalPrice());
        po.setUnitPrice(this.getUnitPrice());
        return po;
    }
}
