package com.littledoctor.clinicassistant.module.pharmacy.supplier.entity;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 12:57
 * @Description: 供应商
 */
@Entity
@Table(name = "SUPPLIER")
public class Supplier {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUPPLIER_ID", nullable = false)
    private Integer supplierId;

    /** 联系人姓名 */
    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    /** 联系人电话 */
    @Column(name = "SUPPLIER_PHONE")
    private String supplierPhone;

    /** 主营 */
    @Column(name = "MAIN_PRODUCTS")
    private String mainProducts;

    /** 地址 */
    @Column(name = "SUPPLIER_ADDRESS")
    private String supplierAddress;

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

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }
}
