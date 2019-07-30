package com.littledoctor.clinicassistant.module.pharmacy.unitconversion.service;

import org.springframework.stereotype.Service;

/**
 * @业务信息: 单位换算
 * @Filename: UnitConversionService.java
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
public interface UnitConversionService {

    /**
     * 根据源单位和目标单位查询换算值
     * @param sourceUnit
     * @param targetUnit
     * @return
     * @throws Exception
     */
    Double queryConversionValue(String sourceUnit, String targetUnit) throws Exception;
}
