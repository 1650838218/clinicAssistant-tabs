package com.littledoctor.clinicassistant.module.rxdaily.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 中草药处方(RX_DAILY_HERBAL)
 * 
 * @author bianj
 * @version 1.0.0 2021-03-03
 */
@Entity
@Table(name = "RX_DAILY_HERBAL")
public class RxDailyHerbal implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6605863270516614898L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 虚拟主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RX_DAILY_HERBAL_ID", unique = true, nullable = false, length = 20)
    private Long rxDailyHerbalId;

    /** 处方笺ID */
    @Column(name = "RX_DAILY_ID", nullable = false, length = 20)
    private Long rxDailyId;

    /** 剂数 */
    @Column(name = "DOSE_COUNT", nullable = true, length = 10)
    private Long doseCount;

    /** 单剂金额 */
    @Column(name = "SINGLE_MONEY", nullable = true, length = 12)
    private String singleMoney;

    /** 用法用量 */
    @Column(name = "USAGE_DOSE", nullable = true, length = 100)
    private String usageDose;

    /** 总金额 */
    @Column(name = "TOTAL_MONEY", nullable = true, length = 12)
    private String totalMoney;

    /** 医嘱 */
    @Column(name = "ADVICE", nullable = true, length = 100)
    private String advice;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取虚拟主键
     * 
     * @return 虚拟主键
     */
    public Long getRxDailyHerbalId() {
        return this.rxDailyHerbalId;
    }

    /**
     * 设置虚拟主键
     * 
     * @param rxDailyHerbalId
     *          虚拟主键
     */
    public void setRxDailyHerbalId(Long rxDailyHerbalId) {
        this.rxDailyHerbalId = rxDailyHerbalId;
    }

    /**
     * 获取处方笺ID
     * 
     * @return 处方笺ID
     */
    public Long getRxDailyId() {
        return this.rxDailyId;
    }

    /**
     * 设置处方笺ID
     * 
     * @param rxDailyId
     *          处方笺ID
     */
    public void setRxDailyId(Long rxDailyId) {
        this.rxDailyId = rxDailyId;
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
    public String getSingleMoney() {
        return this.singleMoney;
    }

    /**
     * 设置单剂金额
     * 
     * @param singleMoney
     *          单剂金额
     */
    public void setSingleMoney(String singleMoney) {
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
    public String getTotalMoney() {
        return this.totalMoney;
    }

    /**
     * 设置总金额
     * 
     * @param totalMoney
     *          总金额
     */
    public void setTotalMoney(String totalMoney) {
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

    /* This code was generated by TableGo tools, mark 2 end. */
}