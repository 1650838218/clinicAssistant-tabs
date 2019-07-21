package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.service;

import com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.dao.WarehousingEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:28
 * @Description: 入库单
 */
@Service
public class WarehousingEntryServiceImpl implements WarehousingEntryService {

    @Autowired
    private WarehousingEntryRepository warehousingEntryRepository;
}
