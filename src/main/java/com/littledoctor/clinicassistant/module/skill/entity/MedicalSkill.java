package com.littledoctor.clinicassistant.module.skill.entity;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-10 12:38
 * @Description: 医技项目
 */
@Entity
@Table(name = "MEDICAL_SKILL")
public class MedicalSkill {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SKILL_ID")
    private Integer skillId;

    /** 项目名称 */
    @Column(name = "SKILL_NAME")
    private String skillName;

    /** 单价 */
    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
