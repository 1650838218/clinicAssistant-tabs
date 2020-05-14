package com.littledoctor.clinicassistant.module.item.herbalmedicine.entity;/*
 * Welcome to use the TableGo Tools.
 * 
 * http://www.tablego.cn
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author: bianj
 * Email: tablego@qq.com
 * Version: 6.6.6
 */
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 中药表(item_herbal_medicine)
 * 
 * @author 周俊林
 * @version 1.0.0 2020-05-04
 */
@Entity
@Table(name = "item_herbal_medicine")
public class HerbalMedicineEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8197057909099689566L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 品目ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", unique = true, nullable = false, length = 20)
    private Long itemId;

    /** 中药名称 */
    @Column(name = "item_name", nullable = false, length = 50)
    private String itemName;

    /** 简拼 */
    @Column(name = "abbr_pinyin", nullable = true, length = 50)
    private String abbrPinyin;

    /** 全拼 */
    @Column(name = "full_pinyin", nullable = true, length = 500)
    private String fullPinyin;

    /** 中药分类 */
    @Column(name = "item_type", nullable = true, length = 50)
    private String itemType;

    /** 性味归经 */
    @Column(name = "flavor_meridian_tropism", nullable = true, length = 100)
    private String flavorMeridianTropism;

    /** 功效 */
    @Column(name = "efficacy", nullable = true, length = 50)
    private String efficacy;

    /** 应用 */
    @Column(name = "application", nullable = true, length = 1000)
    private String application;

    /** 用法用量 */
    @Column(name = "usage_dosage", nullable = true, length = 500)
    private String usageDosage;

    /** 禁忌 */
    @Column(name = "taboo", nullable = true, length = 500)
    private String taboo;

    /** 产地 */
    @Column(name = "producer", nullable = true, length = 100)
    private String producer;

    /** 是否有毒（1：是；0：否） */
    @Column(name = "is_poison", nullable = true)
    private Integer isPoison;

    /** 进货包装 */
    @Column(name = "pur_unit", nullable = true, length = 10)
    private String purUnit;

    /** 零售单位 */
    @Column(name = "stock_unit", nullable = true, length = 10)
    private String stockUnit;

    /** 单位换算 */
    @Column(name = "unit_convert", nullable = true, length = 10)
    private Integer unitConvert;

    /** 库存预警值，保留6位小数可以精确到微克 */
    @Column(name = "stock_warn", nullable = true, length = 16)
    private BigDecimal stockWarn;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取品目ID
     * 
     * @return 品目ID
     */
    public Long getItemId() {
        return this.itemId;
    }

    /**
     * 设置品目ID
     * 
     * @param itemId
     *          品目ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取中药名称
     * 
     * @return 中药名称
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 设置中药名称
     * 
     * @param itemName
     *          中药名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
     * 获取中药分类
     * 
     * @return 中药分类
     */
    public String getItemType() {
        return this.itemType;
    }

    /**
     * 设置中药分类
     * 
     * @param itemType
     *          中药分类
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
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
     * 获取功效
     * 
     * @return 功效
     */
    public String getEfficacy() {
        return this.efficacy;
    }

    /**
     * 设置功效
     * 
     * @param efficacy
     *          功效
     */
    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    /**
     * 获取应用
     * 
     * @return 应用
     */
    public String getApplication() {
        return this.application;
    }

    /**
     * 设置应用
     * 
     * @param application
     *          应用
     */
    public void setApplication(String application) {
        this.application = application;
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
     * 获取产地
     * 
     * @return 产地
     */
    public String getProducer() {
        return this.producer;
    }

    /**
     * 设置产地
     * 
     * @param producer
     *          产地
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
     * 获取进货包装
     * 
     * @return 进货包装
     */
    public String getPurUnit() {
        return this.purUnit;
    }

    /**
     * 设置进货包装
     * 
     * @param purUnit
     *          进货包装
     */
    public void setPurUnit(String purUnit) {
        this.purUnit = purUnit;
    }

    /**
     * 获取零售单位
     * 
     * @return 零售单位
     */
    public String getStockUnit() {
        return this.stockUnit;
    }

    /**
     * 设置零售单位
     * 
     * @param stockUnit
     *          零售单位
     */
    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    /**
     * 获取单位换算
     * 
     * @return 单位换算
     */
    public Integer getUnitConvert() {
        return this.unitConvert;
    }

    /**
     * 设置单位换算
     * 
     * @param unitConvert
     *          单位换算
     */
    public void setUnitConvert(Integer unitConvert) {
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

    /* This code was generated by TableGo tools, mark 2 end. */
}