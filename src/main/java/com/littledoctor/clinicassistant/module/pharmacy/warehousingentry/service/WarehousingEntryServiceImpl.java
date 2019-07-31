package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service.PurchaseOrderService;
import com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.dao.WarehousingEntryRepository;
import com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.entity.WarehousingEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:28
 * @Description: 入库单
 */
@Service
@Transactional
public class WarehousingEntryServiceImpl implements WarehousingEntryService {

    @Autowired
    private WarehousingEntryRepository warehousingEntryRepository;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 保存入库单
     * @param we
     * @return
     */
    @Override
    public WarehousingEntry save(WarehousingEntry we) throws Exception {
        if (we != null && we.getPurchaseOrderId() != null) {
            we.setCreateTime(new Date());
            // 新增入库单的时候需要将与其对应的采购单的状态改为已入库
            purchaseOrderService.updateEntry(we.getPurchaseOrderId());
            return warehousingEntryRepository.saveAndFlush(we);
        }
        return null;
    }
}
