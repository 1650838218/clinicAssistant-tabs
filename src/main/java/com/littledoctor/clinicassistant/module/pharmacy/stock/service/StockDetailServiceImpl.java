package com.littledoctor.clinicassistant.module.pharmacy.stock.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service.PurchaseOrderService;
import com.littledoctor.clinicassistant.module.pharmacy.stock.dao.StockDetailRepository;
import com.littledoctor.clinicassistant.module.pharmacy.stock.entity.StockDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:28
 * @Description: 入库单
 */
@Service
@Transactional
public class StockDetailServiceImpl implements StockDetailService {

    @Autowired
    private StockDetailRepository stockDetailRepository;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 保存入库单
     * @param stockDetails
     * @return
     */
    @Override
    public List<StockDetail> save(List<StockDetail> stockDetails) throws Exception {
        if (stockDetails != null && stockDetails.size() > 0) {
            HashSet<String> purchaseOrderIds = new HashSet<>();
            for (int i = 0, len = stockDetails.size(); i < len; i++) {
                if (stockDetails.get(i).getPurchaseOrderId() != null) purchaseOrderIds.add(stockDetails.get(i).getPurchaseOrderId().toString());
//                stockDetails.get(i).setStockState(1);// 初始化库存状态为1
                stockDetails.get(i).setCreateTiem(new Date());
                stockDetails.get(i).setUpdateTime(new Date());
            }
            if (!purchaseOrderIds.isEmpty()) {
                // 新增入库单的时候需要将与其对应的采购单的状态改为已入库
                purchaseOrderService.updateEntry(purchaseOrderIds);
            }
            return stockDetailRepository.saveAll(stockDetails);
        }
        return null;
    }
}