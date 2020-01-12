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
    @Column(name = "RX_ID")
    private Integer rxId;

    /** 处方名称 */
    @Column(name = "RX_NAME")
    private String rxName;

    /** 处方简称 首字母 */
    @Column(name = "RX_ABBR")
    private String rxAbbr;

    /** 目录ID */
    @Column(name = "CATALOGUE_ID")
    private Integer catalogueId;

    /** 处方来源 */
    @Column(name = "RX_SOURCE")
    private String rxSource;

    /** 方剂组成（JSON格式的字符串） */
    @Column(name = "RX_COMPONENT")
    private String rxComponent;

    /** 服用方法 */
    @Column(name = "TAKING_METHOD")
    private String takingMethod;

    /** 方剂功用 */
    @Column(name = "RX_FUNCTION")
    private String rxFunction;

    /** 禁忌 */
    @Column(name = "RX_TABOO")
    private String rxTaboo;

    /** 方歌 */
    @Column(name = "RX_SONG")
    private String rxSong;

    public Integer getRxId() {
        return rxId;
    }

    public void setRxId(Integer rxId) {
        this.rxId = rxId;
    }

    public String getRxName() {
        return rxName;
    }

    public void setRxName(String rxName) {
        this.rxName = rxName;
    }

    public String getRxAbbr() {
        return rxAbbr;
    }

    public void setRxAbbr(String rxAbbr) {
        this.rxAbbr = rxAbbr;
    }

    public Integer getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Integer catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getRxSource() {
        return rxSource;
    }

    public void setRxSource(String rxSource) {
        this.rxSource = rxSource;
    }

    public String getRxComponent() {
        return rxComponent;
    }

    public void setRxComponent(String rxComponent) {
        this.rxComponent = rxComponent;
    }

    public String getTakingMethod() {
        return takingMethod;
    }

    public void setTakingMethod(String takingMethod) {
        this.takingMethod = takingMethod;
    }

    public String getRxFunction() {
        return rxFunction;
    }

    public void setRxFunction(String rxFunction) {
        this.rxFunction = rxFunction;
    }

    public String getRxTaboo() {
        return rxTaboo;
    }

    public void setRxTaboo(String rxTaboo) {
        this.rxTaboo = rxTaboo;
    }

    public String getRxSong() {
        return rxSong;
    }

    public void setRxSong(String rxSong) {
        this.rxSong = rxSong;
    }
}