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

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 病历(MEDICAL_RECORD)
 * @author 周俊林
 * @version 1.0.0 2019-09-21
 */
@Entity
@Table(name = "MEDICAL_RECORD")
public class MedicalRecord implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4640065184072222294L;

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID", unique = true, nullable = false, length = 20)
    private Long recordId;

    /** 就诊时间 */
    @Column(name = "ARRIVE_TIME", nullable = true, length = 19)
    private String arriveTime;

    /** 患者姓名 */
    @Column(name = "PATIENT_NAME", nullable = true, length = 10)
    private String patientName;

    /** 患者性别 */
    @Column(name = "PATIENT_SEX", nullable = true)
    private Integer patientSex;

    /** 年龄 */
    @Column(name = "PATIENT_AGE", nullable = true, length = 5)
    private String patientAge;

    /** 电话 */
    @Column(name = "PATIENT_PHONE", nullable = true, length = 15)
    private String patientPhone;

    /** 职业 */
    @Column(name = "PATIENT_JOB", nullable = true, length = 20)
    private String patientJob;

    /** 住址 */
    @Column(name = "PATIENT_ADDRESS", nullable = true, length = 50)
    private String patientAddress;

    /** 主诉 */
    @Column(name = "SELF_SPEAK", nullable = true, length = 500)
    private String selfSpeak;

    /** 病史 */
    @Column(name = "MEDICAL_HISTORY", nullable = true, length = 500)
    private String medicalHistory;

    /** 望闻切诊 */
    @Column(name = "LOOK_SMELL_ASK_PRESS", nullable = true, length = 500)
    private String lookSmellAskPress;

    /** 辩证分析 */
    @Column(name = "ANALYSIS", nullable = true, length = 500)
    private String analysis;

    /** 诊断 */
    @Column(name = "DIAGNOSIS", nullable = true, length = 100)
    private String diagnosis;

    /** 治法 */
    @Column(name = "THERAPEUTIC_METHOD", nullable = true, length = 100)
    private String therapeuticMethod;

    /** 处方类型（1：中药方；2：中成药方；3：医技项目；），可多选，用逗号分隔 */
    @Column(name = "PRESCRIPTION_TYPE", nullable = true, length = 10)
    private String prescriptionType;

    /** 总金额 */
    @Column(name = "TOTAL_MONEY", nullable = true, length = 12)
    private BigDecimal totalMoney;

    /** 结算状态（1：未结算；2：已结算） */
    @Column(name = "PAYMENT_STATE", nullable = true)
    private Integer paymentState;

    /** 结算时间 */
    @Column(name = "PAYMENT_TIME", nullable = true, length = 19)
    private Date paymentTime;

    /** 创建时间 */
    @Column(name = "CREATE_TIME", nullable = true, length = 19)
    private Date createTime;

    /**
     * 获取主键ID
     *
     * @return 主键ID
     */
    public Long getRecordId() {
        return this.recordId;
    }

    /**
     * 设置主键ID
     *
     * @param recordId
     *          主键ID
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取就诊时间
     *
     * @return 就诊时间
     */
    public String getArriveTime() {
        return this.arriveTime;
    }

    /**
     * 设置就诊时间
     *
     * @param arriveTime
     *          就诊时间
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * 获取患者姓名
     *
     * @return 患者姓名
     */
    public String getPatientName() {
        return this.patientName;
    }

    /**
     * 设置患者姓名
     *
     * @param patientName
     *          患者姓名
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * 获取患者性别
     *
     * @return 患者性别
     */
    public Integer getPatientSex() {
        return this.patientSex;
    }

    /**
     * 设置患者性别
     *
     * @param patientSex
     *          患者性别
     */
    public void setPatientSex(Integer patientSex) {
        this.patientSex = patientSex;
    }

    /**
     * 获取年龄
     *
     * @return 年龄
     */
    public String getPatientAge() {
        return this.patientAge;
    }

    /**
     * 设置年龄
     *
     * @param patientAge
     *          年龄
     */
    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    /**
     * 获取电话
     *
     * @return 电话
     */
    public String getPatientPhone() {
        return this.patientPhone;
    }

    /**
     * 设置电话
     *
     * @param patientPhone
     *          电话
     */
    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    /**
     * 获取职业
     *
     * @return 职业
     */
    public String getPatientJob() {
        return this.patientJob;
    }

    /**
     * 设置职业
     *
     * @param patientJob
     *          职业
     */
    public void setPatientJob(String patientJob) {
        this.patientJob = patientJob;
    }

    /**
     * 获取住址
     *
     * @return 住址
     */
    public String getPatientAddress() {
        return this.patientAddress;
    }

    /**
     * 设置住址
     *
     * @param patientAddress
     *          住址
     */
    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    /**
     * 获取主诉
     *
     * @return 主诉
     */
    public String getSelfSpeak() {
        return this.selfSpeak;
    }

    /**
     * 设置主诉
     *
     * @param selfSpeak
     *          主诉
     */
    public void setSelfSpeak(String selfSpeak) {
        this.selfSpeak = selfSpeak;
    }

    /**
     * 获取病史
     *
     * @return 病史
     */
    public String getMedicalHistory() {
        return this.medicalHistory;
    }

    /**
     * 设置病史
     *
     * @param medicalHistory
     *          病史
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * 获取望闻切诊
     *
     * @return 望闻切诊
     */
    public String getLookSmellAskPress() {
        return this.lookSmellAskPress;
    }

    /**
     * 设置望闻切诊
     *
     * @param lookSmellAskPress
     *          望闻切诊
     */
    public void setLookSmellAskPress(String lookSmellAskPress) {
        this.lookSmellAskPress = lookSmellAskPress;
    }

    /**
     * 获取辩证分析
     *
     * @return 辩证分析
     */
    public String getAnalysis() {
        return this.analysis;
    }

    /**
     * 设置辩证分析
     *
     * @param analysis
     *          辩证分析
     */
    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    /**
     * 获取诊断
     *
     * @return 诊断
     */
    public String getDiagnosis() {
        return this.diagnosis;
    }

    /**
     * 设置诊断
     *
     * @param diagnosis
     *          诊断
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * 获取治法
     *
     * @return 治法
     */
    public String getTherapeuticMethod() {
        return this.therapeuticMethod;
    }

    /**
     * 设置治法
     *
     * @param therapeuticMethod
     *          治法
     */
    public void setTherapeuticMethod(String therapeuticMethod) {
        this.therapeuticMethod = therapeuticMethod;
    }

    /**
     * 获取处方类型（1：中药方；2：中成药方；3：医技项目；），可多选，用逗号分隔
     *
     * @return 处方类型（1：中药方；2：中成药方；3：医技项目；）
     */
    public String getPrescriptionType() {
        return this.prescriptionType;
    }

    /**
     * 设置处方类型（1：中药方；2：中成药方；3：医技项目；），可多选，用逗号分隔
     *
     * @param prescriptionType
     *          处方类型（1：中药方；2：中成药方；3：医技项目；）
     */
    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
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
     * 获取结算状态（1：未结算；2：已结算）
     * 
     * @return 结算状态（1
     */
    public Integer getPaymentState() {
        return this.paymentState;
    }

    /**
     * 设置结算状态（1：未结算；2：已结算）
     * 
     * @param paymentState
     *          结算状态（1
     */
    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    /**
     * 获取结算时间
     * 
     * @return 结算时间
     */
    public Date getPaymentTime() {
        return this.paymentTime;
    }

    /**
     * 设置结算时间
     * 
     * @param paymentTime
     *          结算时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /*  */
}