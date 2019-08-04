package com.littledoctor.clinicassistant.module.prescription.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:26
 * @Description: 方剂目录
 */
@Entity
@Table(name = "RX_CATALOGUE")
public class RxCatalogue implements Serializable {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATALOGUE_ID")
    private Integer catalogueId;

    /** 父节点ID */
    @Column(name = "PARENT_CATALOGUE_ID")
    private Integer parentCatalogueId;

    /** 目录类型 1：目录；2：处方 */
    @Column(name = "CATALOGUE_TYPE")
    private Integer catalogueType;

    /** 目录名称 */
    @Column(name = "CATALOGUE_NAME")
    private String catalogueName;

    /** 目录顺序号 */
    @Column(name = "CATALOGUE_ORDER")
    private Integer catalogueOrder;

    public Integer getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Integer catalogueId) {
        this.catalogueId = catalogueId;
    }

    public Integer getParentCatalogueId() {
        return parentCatalogueId;
    }

    public void setParentCatalogueId(Integer parentCatalogueId) {
        this.parentCatalogueId = parentCatalogueId;
    }

    public Integer getCatalogueType() {
        return catalogueType;
    }

    public void setCatalogueType(Integer catalogueType) {
        this.catalogueType = catalogueType;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public void setCatalogueName(String catalogueName) {
        this.catalogueName = catalogueName;
    }

    public Integer getCatalogueOrder() {
        return catalogueOrder;
    }

    public void setCatalogueOrder(Integer catalogueOrder) {
        this.catalogueOrder = catalogueOrder;
    }
}
