package com.littledoctor.clinicassistant.module.rxdaily.entity;/*
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
import java.math.BigDecimal;

/**
 * 病历处方(MEDICAL_RECORD_RX)
 * @author 周俊林
 * @version 1.0.0 2019-09-21
 */
@Entity
@Table(name = "MEDICAL_RECORD_RX")
public class MedicalRecordRx implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3277829398542288913L;

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_RX_ID", unique = true, nullable = false, length = 20)
    private Long recordRxId;

    /** 病历ID */
    @Column(name = "RECORD_ID", nullable = false, length = 20)
    private Long recordId;

    /** 处方类型（1：中药方；2：中成药方；3：医技项目） */
    @Column(name = "RX_TYPE", nullable = false)
    private Integer rxType;

    /** 剂数 */
    @Column(name = "DOSE_COUNT", nullable = true, length = 10)
    private Long doseCount;

    /** 单剂金额 */
    @Column(name = "SINGLE_MONEY", nullable = true, length = 12)
    private BigDecimal singleMoney;

    /** 用法用量 */
    @Column(name = "USAGE_DOSE", nullable = true, length = 100)
    private String usageDose;

    /** 总金额 */
    @Column(name = "TOTAL_MONEY", nullable = true, length = 12)
    private BigDecimal totalMoney;

    /** 医嘱 */
    @Column(name = "ADVICE", nullable = true, length = 100)
    private String advice;

    /**
     * 获取主键ID
     * 
     * @return 主键ID
     */
    public Long getRecordRxId() {
        return this.recordRxId;
    }

    /**
     * 设置主键ID
     * 
     * @param recordRxId
     *          主键ID
     */
    public void setRecordRxId(Long recordRxId) {
        this.recordRxId = recordRxId;
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
     * 获取处方类型（1：中药方；2：中成药方；3：医技项目）
     * 
     * @return 处方类型（1
     */
    public Integer getRxType() {
        return this.rxType;
    }

    /**
     * 设置处方类型（1：中药方；2：中成药方；3：医技项目）
     * 
     * @param rxType
     *          处方类型（1
     */
    public void setRxType(Integer rxType) {
        this.rxType = rxType;
    }

    /**
     * 获取剂数
     * 
     * @return 剂数
     */
    public Long getDoseCount() {
        return this.doseCount;
    }

    /**
     * 设置剂数
     * 
     * @param doseCount
     *          剂数
     */
    public void setDoseCount(Long doseCount) {
        this.doseCount = doseCount;
    }

    /**
     * 获取单剂金额
     * 
     * @return 单剂金额
     */
    public BigDecimal getSingleMoney() {
        return this.singleMoney;
    }

    /**
     * 设置单剂金额
     * 
     * @param singleMoney
     *          单剂金额
     */
    public void setSingleMoney(BigDecimal singleMoney) {
        this.singleMoney = singleMoney;
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
     * 获取总金额
     * 
     * @return 总金额
     */
    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    /**
     * 设置总金额
     * 
     * @param totalMoney
     *          总金额
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * 获取医嘱
     * 
     * @return 医嘱
     */
    public String getAdvice() {
        return this.advice;
    }

    /**
     * 设置医嘱
     * 
     * @param advice
     *          医嘱
     */
    public void setAdvice(String advice) {
        this.advice = advice;
    }

    /*  */
}