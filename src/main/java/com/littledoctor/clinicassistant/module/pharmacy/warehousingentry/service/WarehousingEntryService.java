package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.service;

import com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.entity.WarehousingEntry;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:27
 * @Description: 入库单
 */
public interface WarehousingEntryService {

    /**
     * 保存入库单
     * @param we
     * @return
     */
    WarehousingEntry save(WarehousingEntry we) throws Exception;
}
