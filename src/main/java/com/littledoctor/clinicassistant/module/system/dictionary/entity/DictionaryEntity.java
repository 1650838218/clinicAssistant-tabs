package com.littledoctor.clinicassistant.module.system.dictionary.entity;

import java.math.BigDecimal;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * SYS_DICTIONARY
 * 
 * @author bianj
 * @version 1.0.0 2019-11-17
 */
@Entity
@Table(name = "SYS_DICTIONARY")
public class DictionaryEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4537003247986740957L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DICT_ID", unique = true, nullable = false, length = 20)
    private Long dictId;

    /** 字典键 */
    @Column(name = "DICT_KEY", nullable = false, length = 50)
    private String dictKey;

    /** 字典名称或字典项名称 */
    @Column(name = "DICT_NAME", nullable = false, length = 50)
    private String dictName;

    /** 字典项的值 */
    @Column(name = "DICT_VALUE", nullable = true, length = 50)
    private String dictValue;

    /** 顺序号 */
    @Column(name = "DICT_ORDER", nullable = true, length = 10)
    private BigDecimal dictOrder;

    /** 是否可用 */
    @Column(name = "IS_USE", nullable = false)
    private Integer isUse;

    /** 字典类型；1：字典类型；2：字典项 */
    @Column(name = "DICT_TYPE", nullable = false)
    private Integer dictType;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取主键ID
     * 
     * @return 主键ID
     */
    public Long getDictId() {
        return this.dictId;
    }

    /**
     * 设置主键ID
     * 
     * @param dictId
     *          主键ID
     */
    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    /**
     * 获取字典键
     * 
     * @return 字典键
     */
    public String getDictKey() {
        return this.dictKey;
    }

    /**
     * 设置字典键
     * 
     * @param dictKey
     *          字典键
     */
    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    /**
     * 获取字典名称或字典项名称
     * 
     * @return 字典名称或字典项名称
     */
    public String getDictName() {
        return this.dictName;
    }

    /**
     * 设置字典名称或字典项名称
     * 
     * @param dictName
     *          字典名称或字典项名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取字典项的值
     * 
     * @return 字典项的值
     */
    public String getDictValue() {
        return this.dictValue;
    }

    /**
     * 设置字典项的值
     * 
     * @param dictValue
     *          字典项的值
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    /**
     * 获取顺序号
     * 
     * @return 顺序号
     */
    public BigDecimal getDictOrder() {
        return this.dictOrder;
    }

    /**
     * 设置顺序号
     * 
     * @param dictOrder
     *          顺序号
     */
    public void setDictOrder(BigDecimal dictOrder) {
        this.dictOrder = dictOrder;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取字典类型；1：字典类型；2：字典项
     *
     * @return 字典类型；1
     */
    public Integer getDictType() {
        return this.dictType;
    }

    /**
     * 设置字典类型；1：字典类型；2：字典项
     *
     * @param dictType
     *          字典类型；1
     */
    public void setDictType(Integer dictType) {
        this.dictType = dictType;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}