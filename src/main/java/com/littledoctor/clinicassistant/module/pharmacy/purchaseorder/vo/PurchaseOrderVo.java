package com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.vo;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po.PurchaseOrderDetailPo;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.po.PurchaseOrderPo;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.entity.Supplier;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
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

    /** 采购单总价 */
    private Double totalPrice;

    /** 采购单照片附件 */
    private Blob purchaseOrderPicture;

    /** 是否已入库 SF 1：已入库；0：未入库*/
    private Boolean isEntry;

    private List<PurchaseOrderDetailVo> purchaseOrderDetailVos = new ArrayList<>();

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

    public List<PurchaseOrderDetailVo> getPurchaseOrderDetailVos() {
        return purchaseOrderDetailVos;
    }

    public void setPurchaseOrderDetailVos(List<PurchaseOrderDetailVo> purchaseOrderDetailVos) {
        this.purchaseOrderDetailVos = purchaseOrderDetailVos;
    }

    // 将vo转成po
    public PurchaseOrderPo transformPo(PurchaseOrderPo po) {
        if (this.getEntry() != null) po.setEntry(this.getEntry());
        if (this.getPurchaseOrderCode() != null) po.setPurchaseOrderCode(this.getPurchaseOrderCode());
        if (this.getPurchaseOrderDate() != null) po.setPurchaseOrderDate(this.getPurchaseOrderDate());
        if (this.getPurchaseOrderDetailVos() != null && this.getPurchaseOrderDetailVos().size() > 0) {
            List<PurchaseOrderDetailPo> pos = new ArrayList<>();
            for (int i = 0, len = this.getPurchaseOrderDetailVos().size(); i < len; i++) {
                pos.add(this.getPurchaseOrderDetailVos().get(i).transformPo());
            }
            po.setPurchaseOrderDetailPos(pos);
        }
//        if (this.getPurchaseOrderId() != null) po.setPurchaseOrderId(this.getPurchaseOrderId());
        if (this.getPurchaseOrderPicture() != null) po.setPurchaseOrderPicture(this.getPurchaseOrderPicture());
        if (this.getSupplierId() != null) po.setSupplierId(this.getSupplierId());
        if (this.getTotalPrice() != null) po.setTotalPrice(this.getTotalPrice());
        po.setUpdateTime(new Date());
        if (po.getCreateTiem() == null) po.setCreateTiem(new Date());
        return po;
    }
}
