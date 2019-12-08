package com.littledoctor.clinicassistant.module.purchase.item.entity;
/*
 * Welcome to use the TableGo Tools.
 * 
 * http://www.tablego.cn
 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author: bianj
 * Email: tablego@qq.com
 * Version: 6.0.0
 */
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

/**
 * 采购品目(PUR_ITEM)
 * 
 * @author bianj
 * @version 1.0.0 2019-11-30
 */
@Entity
@Table(name = "PUR_ITEM")
public class PurItemEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1783651102759294002L;

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUR_ITEM_ID", unique = true, nullable = false, length = 20)
    private Long purItemId;

    /** 品目名称 */
    @Column(name = "PUR_ITEM_NAME", nullable = false, length = 50)
    private String purItemName;

    /** 品目分类（1：中草药；2：中成药；3：中药饮片；4：医疗耗材；5：其他耗材；字典键：CGPMFL） */
    @Column(name = "PUR_ITEM_TYPE", nullable = false, length = 10)
    private String purItemType;

    /** 条形码 */
    @Column(name = "BARCODE", nullable = true, length = 50)
    private String barcode;

    /** 简拼 */
    @Column(name = "ABBR_PINYIN", nullable = true, length = 50)
    private String abbrPinyin;

    /** 全拼 */
    @Column(name = "FULL_PINYIN", nullable = true, length = 500)
    private String fullPinyin;

    /** 性味归经 */
    @Column(name = "FLAVOR_MERIDIAN_TROPISM", nullable = true, length = 100)
    private String flavorMeridianTropism;

    /** 功效，功能主治 */
    @Column(name = "EFFICACY", nullable = true, length = 500)
    private String efficacy;

    /** 成分 */
    @Column(name = "COMPONENT", nullable = true, length = 500)
    private String component;

    /** 规格 */
    @Column(name = "SPECIFICATIONS", nullable = true, length = 100)
    private String specifications;

    /** 用法用量 */
    @Column(name = "USAGE_DOSAGE", nullable = true, length = 500)
    private String usageDosage;

    /** 禁忌 */
    @Column(name = "TABOO", nullable = true, length = 500)
    private String taboo;

    /** 厂家制造商，产地 */
    @Column(name = "PRODUCER", nullable = true, length = 100)
    private String producer;

    /** 是否有毒（1：是；0：否） */
    @Column(name = "IS_POISON", nullable = true)
    private Integer isPoison;

    /** 采购单位 */
    @Column(name = "PUR_UNIT", nullable = true, length = 10)
    private String purUnit;

    /** 库存单位 */
    @Column(name = "STOCK_UNIT", nullable = true, length = 10)
    private String stockUnit;

    /** 单位换算 */
    @Column(name = "UNIT_CONVERT", nullable = true, length = 10)
    private Long unitConvert;

    /** 库存预警值，保留6位小数可以精确到微克 */
    @Column(name = "STOCK_WARN", nullable = true, length = 16)
    private BigDecimal stockWarn;

    /*  */

    /*  */

    /**
     * 获取主键ID
     * 
     * @return 主键ID
     */
    public Long getPurItemId() {
        return this.purItemId;
    }

    /**
     * 设置主键ID
     * 
     * @param purItemId
     *          主键ID
     */
    public void setPurItemId(Long purItemId) {
        this.purItemId = purItemId;
    }

    /**
     * 获取品目名称
     * 
     * @return 品目名称
     */
    public String getPurItemName() {
        return this.purItemName;
    }

    /**
     * 设置品目名称
     * 
     * @param purItemName
     *          品目名称
     */
    public void setPurItemName(String purItemName) {
        this.purItemName = purItemName;
    }

    /**
     * 获取品目分类（1：中草药；2：中成药；3：中药饮片；4：医疗耗材；5：其他耗材；字典键：CGPMFL）
     * 
     * @return 品目分类（1
     */
    public String getPurItemType() {
        return this.purItemType;
    }

    /**
     * 设置品目分类（1：中草药；2：中成药；3：中药饮片；4：医疗耗材；5：其他耗材；字典键：CGPMFL）
     * 
     * @param purItemType
     *          品目分类（1
     */
    public void setPurItemType(String purItemType) {
        this.purItemType = purItemType;
    }

    /**
     * 获取条形码
     * 
     * @return 条形码
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 设置条形码
     * 
     * @param barcode
     *          条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 获取简拼
     * 
     * @return 简拼
     */
    public String getAbbrPinyin() {
        return this.abbrPinyin;
    }

    /**
     * 设置简拼
     * 
     * @param abbrPinyin
     *          简拼
     */
    public void setAbbrPinyin(String abbrPinyin) {
        this.abbrPinyin = abbrPinyin;
    }

    /**
     * 获取全拼
     * 
     * @return 全拼
     */
    public String getFullPinyin() {
        return this.fullPinyin;
    }

    /**
     * 设置全拼
     * 
     * @param fullPinyin
     *          全拼
     */
    public void setFullPinyin(String fullPinyin) {
        this.fullPinyin = fullPinyin;
    }

    /**
     * 获取性味归经
     * 
     * @return 性味归经
     */
    public String getFlavorMeridianTropism() {
        return this.flavorMeridianTropism;
    }

    /**
     * 设置性味归经
     * 
     * @param flavorMeridianTropism
     *          性味归经
     */
    public void setFlavorMeridianTropism(String flavorMeridianTropism) {
        this.flavorMeridianTropism = flavorMeridianTropism;
    }

    /**
     * 获取功效，功能主治
     * 
     * @return 功效
     */
    public String getEfficacy() {
        return this.efficacy;
    }

    /**
     * 设置功效，功能主治
     * 
     * @param efficacy
     *          功效
     */
    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    /**
     * 获取成分
     * 
     * @return 成分
     */
    public String getComponent() {
        return this.component;
    }

    /**
     * 设置成分
     * 
     * @param component
     *          成分
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 获取规格
     * 
     * @return 规格
     */
    public String getSpecifications() {
        return this.specifications;
    }

    /**
     * 设置规格
     * 
     * @param specifications
     *          规格
     */
    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    /**
     * 获取用法用量
     * 
     * @return 用法用量
     */
    public String getUsageDosage() {
        return this.usageDosage;
    }

    /**
     * 设置用法用量
     * 
     * @param usageDosage
     *          用法用量
     */
    public void setUsageDosage(String usageDosage) {
        this.usageDosage = usageDosage;
    }

    /**
     * 获取禁忌
     * 
     * @return 禁忌
     */
    public String getTaboo() {
        return this.taboo;
    }

    /**
     * 设置禁忌
     * 
     * @param taboo
     *          禁忌
     */
    public void setTaboo(String taboo) {
        this.taboo = taboo;
    }

    /**
     * 获取厂家制造商，产地
     * 
     * @return 厂家制造商
     */
    public String getProducer() {
        return this.producer;
    }

    /**
     * 设置厂家制造商，产地
     * 
     * @param producer
     *          厂家制造商
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * 获取是否有毒（1：是；0：否）
     * 
     * @return 是否有毒（1
     */
    public Integer getIsPoison() {
        return this.isPoison;
    }

    /**
     * 设置是否有毒（1：是；0：否）
     * 
     * @param isPoison
     *          是否有毒（1
     */
    public void setIsPoison(Integer isPoison) {
        this.isPoison = isPoison;
    }

    /**
     * 获取采购单位
     * 
     * @return 采购单位
     */
    public String getPurUnit() {
        return this.purUnit;
    }

    /**
     * 设置采购单位
     * 
     * @param purUnit
     *          采购单位
     */
    public void setPurUnit(String purUnit) {
        this.purUnit = purUnit;
    }

    /**
     * 获取库存单位
     * 
     * @return 库存单位
     */
    public String getStockUnit() {
        return this.stockUnit;
    }

    /**
     * 设置库存单位
     * 
     * @param stockUnit
     *          库存单位
     */
    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    /**
     * 获取单位换算
     * 
     * @return 单位换算
     */
    public Long getUnitConvert() {
        return this.unitConvert;
    }

    /**
     * 设置单位换算
     * 
     * @param unitConvert
     *          单位换算
     */
    public void setUnitConvert(Long unitConvert) {
        this.unitConvert = unitConvert;
    }

    /**
     * 获取库存预警值，保留6位小数可以精确到微克
     * 
     * @return 库存预警值
     */
    public BigDecimal getStockWarn() {
        return this.stockWarn;
    }

    /**
     * 设置库存预警值，保留6位小数可以精确到微克
     * 
     * @param stockWarn
     *          库存预警值
     */
    public void setStockWarn(BigDecimal stockWarn) {
        this.stockWarn = stockWarn;
    }

    /*  */
}