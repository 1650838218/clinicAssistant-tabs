package com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/18 22:00
 * @Description: 药房品目
 */
@Entity
@Table(name = "PHARMACY_ITEM")
public class PharmacyItem {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PHARMACY_ITEM_ID", nullable = false)
    private Integer pharmacyItemId;

    /** 品目名称 */
    @Column(name = "PHARMACY_ITEM_NAME")
    private String pharmacyItemName;

    /** 药品分类，YPFL：1：中草药；2：中成药；3：中药饮片；99：其他 */
    @Column(name = "PHARMACY_ITEM_TYPE")
    private Integer pharmacyItemType;

    /** 是否有毒 */
    @Column(name = "POISONOUS")
    private boolean poisonous;

    /** 条形码 */
    @Column(name = "BARCODE")
    private String barcode;

    /** 简拼 首字母 */
    @Column(name = "ABBREVIATION")
    private String abbreviation;

    /** 全拼 */
    @Column(name = "FULL_PINYIN")
    private String fullPinyin;

    /** 成分 */
    @Column(name = "COMPONENT")
    private String component;

    /** 功能主治 */
    @Column(name = "FUNCTION_ATTENDING")
    private String functionAttending;

    /** 规格 */
    @Column(name = "SPECIFICATIONS")
    private String specifications;

    /** 用法用量 */
    @Column(name = "USAGE_AND_DOSAGE")
    private String usageAndDosage;

    /** 禁忌 */
    @Column(name = "TABOO")
    private String taboo;

    /** 厂家，制造商 */
    @Column(name = "MANUFACTURER")
    private String manufacturer;

    /** 库存预警 */
    @Column(name = "STOCK_WARN")
    private Double stockWarn;

    /** 库存单位 */
    @Column(name = "STOCK_UNIT")
    private Integer stockUnit;

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

    public Integer getPharmacyItemType() {
        return pharmacyItemType;
    }

    public void setPharmacyItemType(Integer pharmacyItemType) {
        this.pharmacyItemType = pharmacyItemType;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFullPinyin() {
        return fullPinyin;
    }

    public void setFullPinyin(String fullPinyin) {
        this.fullPinyin = fullPinyin;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getFunctionAttending() {
        return functionAttending;
    }

    public void setFunctionAttending(String functionAttending) {
        this.functionAttending = functionAttending;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getUsageAndDosage() {
        return usageAndDosage;
    }

    public void setUsageAndDosage(String usageAndDosage) {
        this.usageAndDosage = usageAndDosage;
    }

    public String getTaboo() {
        return taboo;
    }

    public void setTaboo(String taboo) {
        this.taboo = taboo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getStockWarn() {
        return stockWarn;
    }

    public void setStockWarn(Double stockWarn) {
        this.stockWarn = stockWarn;
    }

    public Integer getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(Integer stockUnit) {
        this.stockUnit = stockUnit;
    }
}
