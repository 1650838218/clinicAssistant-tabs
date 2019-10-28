package com.littledoctor.clinicassistant.module.record.entity;
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
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * 病历处方明细(MEDICAL_RECORD_RX_DETAIL)
 *
 * @author bianj
 * @version 1.0.0 2019-09-21
 */
@Entity
@Table(name = "MEDICAL_RECORD_RX_DETAIL", schema = "CLINICASSISTANT")
public class MedicalRecordRxDetail implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1374057787208065742L;

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RX_DETAIL_ID", unique = true, nullable = false, length = 20)
    private Long rxDetailId;

    /** 病历ID */
    @Column(name = "RECORD_ID", nullable = true, length = 19)
    private Long recordId;

    /** 处方类型 */
    @Column(name = "RX_TYPE")
    private Integer rxType;

    /** 医技项目ID */
    @Column(name = "SKILL_ID")
    private Long skillId;

    /** 药房品目ID */
    @Column(name = "PHARMACY_ITEM_ID")
    private Long pharmacyItemId;

    /** 剂量/数量/诊疗次数 */
    @Column(name = "DOSE")
    private BigDecimal dose;

    /** 单位 */
    @Column(name = "UNIT_NAME")
    private String unitName;

    /** 用法用量 */
    @Column(name = "USAGE_DOSE")
    private String usageDose;

    /** 单价 */
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 金额 */
    @Column(name = "TOTAL_MONEY")
    private BigDecimal totalMoney;

    /**
     * 获取主键ID
     *
     * @return 主键ID
     */
    public Long getRxDetailId() {
        return this.rxDetailId;
    }

    /**
     * 设置主键ID
     *
     * @param rxDetailId
     *          主键ID
     */
    public void setRxDetailId(Long rxDetailId) {
        this.rxDetailId = rxDetailId;
    }

    /**
     * 获取病历ID
     *
     * @return 病历ID
     */
    public Long getRecordId() {
        return this.recordId;
    }

    /**
     * 设置病历ID
     *
     * @param recordId
     *          病历ID
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取处方类型
     *
     * @return 处方类型
     */
    public Integer getRxType() {
        return this.rxType;
    }

    /**
     * 设置处方类型
     *
     * @param rxType
     *          处方类型
     */
    public void setRxType(Integer rxType) {
        this.rxType = rxType;
    }

    /**
     * 获取医技项目ID
     *
     * @return 医技项目ID
     */
    public Long getSkillId() {
        return this.skillId;
    }

    /**
     * 设置医技项目ID
     *
     * @param skillId
     *          医技项目ID
     */
    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Long getPharmacyItemId() {
        return pharmacyItemId;
    }

    public void setPharmacyItemId(Long pharmacyItemId) {
        this.pharmacyItemId = pharmacyItemId;
    }

    /**
     * 获取剂量/数量/诊疗次数
     *
     * @return 剂量/数量/诊疗次数
     */
    public BigDecimal getDose() {
        return this.dose;
    }

    /**
     * 设置剂量/数量/诊疗次数
     *
     * @param dose
     *          剂量/数量/诊疗次数
     */
    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    /**
     * 获取单位
     *
     * @return 单位
     */
    public String getUnitName() {
        return this.unitName;
    }

    /**
     * 设置单位
     *
     * @param unitName
     *          单位
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * 获取用法用量
     *
     * @return 用法用量
     */
    public String getUsageDose() {
        return this.usageDose;
    }

    /**
     * 设置用法用量
     *
     * @param usageDose
     *          用法用量
     */
    public void setUsageDose(String usageDose) {
        this.usageDose = usageDose;
    }

    /**
     * 获取单价
     *
     * @return 单价
     */
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * 设置单价
     *
     * @param unitPrice
     *          单价
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取金额
     *
     * @return 金额
     */
    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    /**
     * 设置金额
     *
     * @param totalMoney
     *          金额
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}