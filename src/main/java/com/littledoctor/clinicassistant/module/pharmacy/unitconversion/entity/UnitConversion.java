package com.littledoctor.clinicassistant.module.pharmacy.unitconversion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @业务信息: 单位换算
 * @Filename: UnitConversion.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-07-30   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-07-30   周俊林
 */
public class UnitConversion {

    /** 源单位 字典键：SLDW */
    private String sourceUnit;

    /** 目标单位 字典键：SLDW */
    private String targetUnit;

    /** 换算值 */
    private Double conversionValue;

    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    public Double getConversionValue() {
        return conversionValue;
    }

    public void setConversionValue(Double conversionValue) {
        this.conversionValue = conversionValue;
    }
}
