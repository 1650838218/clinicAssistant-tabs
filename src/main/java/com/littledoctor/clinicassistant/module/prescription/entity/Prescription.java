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
    @Column(name = "CATALOGUE_ID")
    private Integer classicsId;

    /** 处方名称 */
    @Column(name = "CLASSICS_NAME")
    private String classicsName;

    /** 处方简称 首字母 */
    @Column(name = "ABBREVIATION")
    private String abbreviation;

    /** 目录ID */
    @Column(name = "CATALOGUE_ID")
    private Integer catalogueId;

    /** 方剂类型（1：经方，2：验方，3：时方，4：单方，5：偏方，6：秘方） */
    @Column(name = "CLASSICS_TYPE")
    private String classicsType;

    /** 处方来源 */
    @Column(name = "CLASSICS_SOURCE")
    private String classicsSource;

    /** 方剂组成（JSON格式的字符串） */
    @Column(name = "CLASSICS_COMPONENT")
    private String classicsComponent;

    /** 服用方法 */
    @Column(name = "TAKING_METHOD")
    private String takingMethod;

    /** 方剂功用 */
    @Column(name = "CLASSICS_FUNCTION")
    private String classicsFunction;

    /** 方剂主治 */
    @Column(name = "CLASSICS_ATTENDING")
    private String classicsAttending;

    /** 方解 */
    @Column(name = "CLASSICS_EXPLAIN")
    private String classicsExplain;

    /** 禁忌 */
    @Column(name = "CLASSICS_TABOO")
    private String classicsTaboo;

    /** 方歌 */
    @Column(name = "CLASSICS_SONG")
    private String classicsSong;

    public Integer getClassicsId() {
        return classicsId;
    }

    public void setClassicsId(Integer classicsId) {
        this.classicsId = classicsId;
    }

    public String getClassicsName() {
        return classicsName;
    }

    public void setClassicsName(String classicsName) {
        this.classicsName = classicsName;
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

    public String getClassicsType() {
        return classicsType;
    }

    public void setClassicsType(String classicsType) {
        this.classicsType = classicsType;
    }

    public String getClassicsSource() {
        return classicsSource;
    }

    public void setClassicsSource(String classicsSource) {
        this.classicsSource = classicsSource;
    }

    public String getClassicsComponent() {
        return classicsComponent;
    }

    public void setClassicsComponent(String classicsComponent) {
        this.classicsComponent = classicsComponent;
    }

    public String getTakingMethod() {
        return takingMethod;
    }

    public void setTakingMethod(String takingMethod) {
        this.takingMethod = takingMethod;
    }

    public String getClassicsFunction() {
        return classicsFunction;
    }

    public void setClassicsFunction(String classicsFunction) {
        this.classicsFunction = classicsFunction;
    }

    public String getClassicsAttending() {
        return classicsAttending;
    }

    public void setClassicsAttending(String classicsAttending) {
        this.classicsAttending = classicsAttending;
    }

    public String getClassicsExplain() {
        return classicsExplain;
    }

    public void setClassicsExplain(String classicsExplain) {
        this.classicsExplain = classicsExplain;
    }

    public String getClassicsTaboo() {
        return classicsTaboo;
    }

    public void setClassicsTaboo(String classicsTaboo) {
        this.classicsTaboo = classicsTaboo;
    }

    public String getClassicsSong() {
        return classicsSong;
    }

    public void setClassicsSong(String classicsSong) {
        this.classicsSong = classicsSong;
    }
}