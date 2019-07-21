package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.controller;

import com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.service.WarehousingEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:25
 * @Description: 入库单
 */
@RestController
@RequestMapping("/pharmacy/warehousingentry")
public class WarehousingEntryController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WarehousingEntryService warehousingEntryService;
}
