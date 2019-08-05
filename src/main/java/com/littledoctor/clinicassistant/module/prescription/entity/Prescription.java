package com.littledoctor.clinicassistant.module.prescription.entity;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:26
 * @Description: 处方
 */
@Entity
@Table(name = "PRESCRIPTION")
public class Prescription {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRESCRIPTION_ID")
    private Integer prescriptionId;

    /** 处方名称 */
    @Column(name = "PRESCRIPTION_NAME")
    private String prescriptionName;

    /** 处方简称 首字母 */
    @Column(name = "ABBREVIATION")
    private String abbreviation;

    /** 目录ID */
    @Column(name = "CATALOGUE_ID")
    private Integer catalogueId;

    /** 方剂类型（1：经方，2：验方，3：时方，4：单方，5：偏方，6：秘方） */
    @Column(name = "PRESCRIPTION_TYPE")
    private String prescriptionType;

    /** 处方来源 */
    @Column(name = "PRESCRIPTION_SOURCE")
    private String prescriptionSource;

    /** 方剂组成（JSON格式的字符串） */
    @Column(name = "PRESCRIPTION_COMPONENT")
    private String prescriptionComponent;

    /** 服用方法 */
    @Column(name = "TAKING_METHOD")
    private String takingMethod;

    /** 方剂功用 */
    @Column(name = "PRESCRIPTION_FUNCTION")
    private String prescriptionFunction;

    /** 方剂主治 */
    @Column(name = "PRESCRIPTION_ATTENDING")
    private String prescriptionAttending;

    /** 方解 */
    @Column(name = "PRESCRIPTION_EXPLAIN")
    private String prescriptionExplain;

    /** 禁忌 */
    @Column(name = "PRESCRIPTION_TABOO")
    private String prescriptionTaboo;

    /** 方歌 */
    @Column(name = "PRESCRIPTION_SONG")
    private String prescriptionSong;

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Integer catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public String getPrescriptionSource() {
        return prescriptionSource;
    }

    public void setPrescriptionSource(String prescriptionSource) {
        this.prescriptionSource = prescriptionSource;
    }

    public String getPrescriptionComponent() {
        return prescriptionComponent;
    }

    public void setPrescriptionComponent(String prescriptionComponent) {
        this.prescriptionComponent = prescriptionComponent;
    }

    public String getTakingMethod() {
        return takingMethod;
    }

    public void setTakingMethod(String takingMethod) {
        this.takingMethod = takingMethod;
    }

    public String getPrescriptionFunction() {
        return prescriptionFunction;
    }

    public void setPrescriptionFunction(String prescriptionFunction) {
        this.prescriptionFunction = prescriptionFunction;
    }

    public String getPrescriptionAttending() {
        return prescriptionAttending;
    }

    public void setPrescriptionAttending(String prescriptionAttending) {
        this.prescriptionAttending = prescriptionAttending;
    }

    public String getPrescriptionExplain() {
        return prescriptionExplain;
    }

    public void setPrescriptionExplain(String prescriptionExplain) {
        this.prescriptionExplain = prescriptionExplain;
    }

    public String getPrescriptionTaboo() {
        return prescriptionTaboo;
    }

    public void setPrescriptionTaboo(String prescriptionTaboo) {
        this.prescriptionTaboo = prescriptionTaboo;
    }

    public String getPrescriptionSong() {
        return prescriptionSong;
    }

    public void setPrescriptionSong(String prescriptionSong) {
        this.prescriptionSong = prescriptionSong;
    }
}