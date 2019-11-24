package com.littledoctor.clinicassistant.module.purchase.supplier.entity;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 12:57
 * @Description: 供应商
 */
@Entity
@Table(name = "PUR_SUPPLIER")
public class SupplierEntity {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLIER_ID", nullable = false)
    private Integer supplierId;

    /** 供应商名称 */
    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    /** 联系人姓名 */
    @Column(name = "LINK_MAN_1")
    private String linkMan1;

    /** 联系人电话 */
    @Column(name = "PHONE_1")
    private String phone1;

    /** 联系人姓名 */
    @Column(name = "LINK_MAN_2")
    private String linkMan2;

    /** 联系人电话 */
    @Column(name = "PHONE_2")
    private String phone2;

    /** 主营 */
    @Column(name = "MAIN_PRODUCTS")
    private String mainProducts;

    /** 地址 */
    @Column(name = "ADDRESS")
    private String address;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public String getLinkMan1() {
        return linkMan1;
    }

    public void setLinkMan1(String linkMan1) {
        this.linkMan1 = linkMan1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getLinkMan2() {
        return linkMan2;
    }

    public void setLinkMan2(String linkMan2) {
        this.linkMan2 = linkMan2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
