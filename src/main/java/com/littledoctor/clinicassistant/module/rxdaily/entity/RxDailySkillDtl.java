package com.littledoctor.clinicassistant.module.rxdaily.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 医技处方明细(RX_DAILY_SKILL_DTL)
 * 
 * @author bianj
 * @version 1.0.0 2021-03-03
 */
@Entity
@Table(name = "RX_DAILY_SKILL_DTL")
public class RxDailySkillDtl implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3423234713905745088L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 虚拟主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RX_DAILY_SKILL_DTL_ID", unique = true, nullable = false, length = 20)
    private Long rxDailySkillDtlId;

    /** 处方笺ID */
    @Column(name = "RX_DAILY_ID", nullable = false, length = 20)
    private Long rxDailyId;

    /** 医技品目ID */
    @Column(name = "ITEM_ID", nullable = true, length = 20)
    private Long itemId;

    /** 次数 */
    @Column(name = "DOSE", nullable = true, length = 12)
    private String dose;

    /** 单价 */
    @Column(name = "UNIT_PRICE", nullable = true, length = 12)
    private String unitPrice;

    /** 金额 */
    @Column(name = "TOTAL_MONEY", nullable = true, length = 12)
    private String totalMoney;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取虚拟主键
     * 
     * @return 虚拟主键
     */
    public Long getRxDailySkillDtlId() {
        return this.rxDailySkillDtlId;
    }

    /**
     * 设置虚拟主键
     * 
     * @param rxDailySkillDtlId
     *          虚拟主键
     */
    public void setRxDailySkillDtlId(Long rxDailySkillDtlId) {
        this.rxDailySkillDtlId = rxDailySkillDtlId;
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
     * 获取次数
     * 
     * @return 次数
     */
    public String getDose() {
        return this.dose;
    }

    /**
     * 设置次数
     * 
     * @param dose
     *          次数
     */
    public void setDose(String dose) {
        this.dose = dose;
    }

    /**
     * 获取单价
     * 
     * @return 单价
     */
    public String getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * 设置单价
     * 
     * @param unitPrice
     *          单价
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取金额
     * 
     * @return 金额
     */
    public String getTotalMoney() {
        return this.totalMoney;
    }

    /**
     * 设置金额
     * 
     * @param totalMoney
     *          金额
     */
    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}