package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.vo;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po.PurchaseOrderDetailPo;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * @业务信息: 采购单 vo 类
 * @Filename: PurchaseOrderVo.java
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
public class PurchaseOrderVo {
    /** 主键ID */
    private Integer purchaseOrderId;

    /** 采购单号，取当前时间yyyymmddhhmmsssss*/
    private String purchaseOrderCode;

    /** 采购单日期 */
    private String purchaseOrderDate;

    /** 供货商ID */
    private Integer supplierId;

    /** 供货商电话 */
    private String supplierPhone;

    /** 采购单总价 */
    private Double totalPrice;

    /** 采购单照片附件 */
    private Blob purchaseOrderPicture;

    /** 是否已入库 SF 1：已入库；0：未入库*/
    private Boolean isEntry;

    private List<PurchaseOrderDetailPo> purchaseOrderDetailVos = new ArrayList<>();

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

    public List<PurchaseOrderDetailPo> getPurchaseOrderDetailVos() {
        return purchaseOrderDetailVos;
    }

    public void setPurchaseOrderDetailVos(List<PurchaseOrderDetailPo> purchaseOrderDetailVos) {
        this.purchaseOrderDetailVos = purchaseOrderDetailVos;
    }
}
