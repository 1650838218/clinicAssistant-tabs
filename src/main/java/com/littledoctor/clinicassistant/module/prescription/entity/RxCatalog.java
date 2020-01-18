package com.littledoctor.clinicassistant.module.prescription.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:26
 * @Description: 方剂目录
 */
@Entity
@Table(name = "RX_CATALOG")
public class RxCatalog implements Serializable {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATALOG_ID")
    private Long catalogId;

    /** 父节点ID */
    @Column(name = "PARENT_CATALOG_ID")
    private Long parentCatalogId;

    /** 目录类型 1：目录；2：处方 */
    @Column(name = "CATALOG_TYPE")
    private Integer catalogType;

    /** 目录名称 */
    @Column(name = "CATALOG_NAME")
    private String catalogName;

    /** 目录顺序号 */
    @Column(name = "CATALOG_ORDER")
    private Integer catalogOrder;

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getParentCatalogId() {
        return parentCatalogId;
    }

    public void setParentCatalogId(Long parentCatalogId) {
        this.parentCatalogId = parentCatalogId;
    }

    public Integer getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(Integer catalogType) {
        this.catalogType = catalogType;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getCatalogOrder() {
        return catalogOrder;
    }

    public void setCatalogOrder(Integer catalogOrder) {
        this.catalogOrder = catalogOrder;
    }
}
