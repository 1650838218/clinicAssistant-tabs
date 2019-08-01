package com.littledoctor.clinicassistant.module.pharmacy.stock.mapper;

import com.littledoctor.clinicassistant.module.pharmacy.stock.entity.StockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @业务信息:
 * @Filename: StockDetailMapper.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-01   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-01   周俊林
 */
@Mapper
public interface StockDetailMapper {

    /**
     * 根据条件查询库存信息
     * @param keywords
     * @param pharmacyItemType
     * @return
     */
    List<StockDetail> findAll(@Param("keywords") String keywords, @Param("pharmacyItemType") String pharmacyItemType);
}
