package com.littledoctor.clinicassistant.module.rxdaily.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 病历(RX_DAILY_MAIN)
 * @author 周俊林
 * @version 1.0.0 2019-09-21
 */
@Entity
@Table(name = "RX_DAILY_MAIN")
public class RxDailyMain implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4640065184072222294L;

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RX_DAILY_ID", unique = true, nullable = false, length = 20)
    private Long rxDailyId;

    /** 就诊号 */
    @Column(name = "REGISTER_NUMBER")
    private String registerNumber;

    /** 就诊时间 */
    @Column(name = "ARRIVE_TIME", length = 20)
    private Date arriveTime;

    /** 患者姓名 */
    @Column(name = "PATIENT_NAME", length = 100)
    private String patientName;

    /** 患者性别 */
    @Column(name = "PATIENT_SEX")
    private Integer patientSex;

    /** 年龄 */
    @Column(name = "PATIENT_AGE", length = 5)
    private Integer patientAge;

    /** 电话 */
    @Column(name = "PATIENT_PHONE", length = 15)
    private String patientPhone;

    /** 职业 */
    @Column(name = "PATIENT_JOB", length = 100)
    private String patientJob;

    /** 住址 */
    @Column(name = "PATIENT_ADDRESS", length = 500)
    private String patientAddress;

    /** 主诉 */
    @Column(name = "SELF_SPEAK", length = 500)
    private String selfSpeak;

    /** 病史 */
    @Column(name = "MEDICAL_HISTORY", length = 500)
    private String medicalHistory;

    /** 望闻切诊 */
    @Column(name = "LOOK_SMELL_ASK_PRESS", length = 500)
    private String lookSmellAskPress;

    /** 辩证分析 */
    @Column(name = "DISCRIMINATE_SYNDROME", length = 500)
    private String discriminateSyndrome;

    /** 诊断 */
    @Column(name = "DIAGNOSIS", length = 100)
    private String diagnosis;

    /** 治法 */
    @Column(name = "THERAPEUTIC_METHOD", length = 100)
    private String therapeuticMethod;

    /** 处方类型（1：中药方；2：中成药方；3：医技项目；），可多选，用逗号分隔 */
    @Column(name = "RX_TYPE", length = 10)
    private String rxType;

    /** 修改时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** 会员卡号 */
    @Column(name = "VIP_CARD_NUMBER", length = 100)
    private String vipCardNumber;

    /** 应收 */
    @Column(name = "RECEIVABLE")
    private BigDecimal receivable;

    /** 折扣 */
    @Column(name = "DISCOUNT")
    private BigDecimal discount;

    /** 实收 */
    @Column(name = "ACTUAL_RECEIVABLE")
    private BigDecimal actualReceivable;

    /** 找零 */
    @Column(name = "GIVE_CHANGE")
    private BigDecimal giveChange;

    /** 支付状态 1：未支付 2：已支付*/
    @Column(name = "PAYMENT_STATE")
    private Integer paymentState;

    /** 支付时间 */
    @Column(name = "PAYMENT_TIME")
    private Date paymentTime;

    public Long getRxDailyId() {
        return rxDailyId;
    }

    public void setRxDailyId(Long rxDailyId) {
        this.rxDailyId = rxDailyId;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(Integer patientSex) {
        this.patientSex = patientSex;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientJob() {
        return patientJob;
    }

    public void setPatientJob(String patientJob) {
        this.patientJob = patientJob;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getSelfSpeak() {
        return selfSpeak;
    }

    public void setSelfSpeak(String selfSpeak) {
        this.selfSpeak = selfSpeak;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getLookSmellAskPress() {
        return lookSmellAskPress;
    }

    public void setLookSmellAskPress(String lookSmellAskPress) {
        this.lookSmellAskPress = lookSmellAskPress;
    }

    public String getDiscriminateSyndrome() {
        return discriminateSyndrome;
    }

    public void setDiscriminateSyndrome(String discriminateSyndrome) {
        this.discriminateSyndrome = discriminateSyndrome;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTherapeuticMethod() {
        return therapeuticMethod;
    }

    public void setTherapeuticMethod(String therapeuticMethod) {
        this.therapeuticMethod = therapeuticMethod;
    }

    public String getRxType() {
        return rxType;
    }

    public void setRxType(String rxType) {
        this.rxType = rxType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVipCardNumber() {
        return vipCardNumber;
    }

    public void setVipCardNumber(String vipCardNumber) {
        this.vipCardNumber = vipCardNumber;
    }

    public BigDecimal getReceivable() {
        return receivable;
    }

    public void setReceivable(BigDecimal receivable) {
        this.receivable = receivable;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getActualReceivable() {
        return actualReceivable;
    }

    public void setActualReceivable(BigDecimal actualReceivable) {
        this.actualReceivable = actualReceivable;
    }

    public BigDecimal getGiveChange() {
        return giveChange;
    }

    public void setGiveChange(BigDecimal giveChange) {
        this.giveChange = giveChange;
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
}