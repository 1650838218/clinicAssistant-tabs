package com.littledoctor.clinicassistant.module.pharmacy.unitconversion.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @业务信息: 单位换算
 * @Filename: UnitConversionServiceImpl.java
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
@Service
public class UnitConversionServiceImpl implements UnitConversionService {

    @Resource
    private EntityManager entityManager;

    /**
     * 根据源单位和目标单位查询换算值
     * @param sourceUnit
     * @param targetUnit
     * @return
     * @throws Exception
     */
    @Override
    public Double queryConversionValue(String sourceUnit, String targetUnit) throws Exception {
        String sql = "select conversion_value from unit_conversion where source_unit = ? and target_unit = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, sourceUnit);
        query.setParameter(2, targetUnit);
        Double result = Double.valueOf(query.getSingleResult().toString());
        return result;
    }
}
