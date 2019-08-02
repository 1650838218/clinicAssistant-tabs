package com.littledoctor.clinicassistant.module.pharmacy.stock.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littledoctor.clinicassistant.module.pharmacy.purchaseorder.service.PurchaseOrderService;
import com.littledoctor.clinicassistant.module.pharmacy.stock.dao.StockDetailRepository;
import com.littledoctor.clinicassistant.module.pharmacy.stock.entity.StockDetail;
import com.littledoctor.clinicassistant.module.pharmacy.stock.mapper.StockDetailMapper;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

    @Autowired(required = false)
    private StockDetailMapper stockDetailMapper;

    @Autowired
    private DictionaryService dictionaryService;

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

    /**
     * 分页查询 库存
     * @param page
     * @param keywords
     * @param pharmacyItemType
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<Map<String, String>> queryPage(Pageable page, String keywords, String pharmacyItemType) throws Exception {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<Map<String, String>> stockDetails = stockDetailMapper.findAll(keywords, pharmacyItemType);
        PageInfo<Map<String, String>> result = new PageInfo<>(stockDetails);
        // 设置库存单位名称,药品分类名称
        if (result.getTotal() > 0) {
            Map<String, String> kcdw = dictionaryService.getItemMapByKey("KCDW");// 库存单位
            Map<String, String> ypfl = dictionaryService.getItemMapByKey("YPFL");// 药品分类
            for (int i = 0, len = result.getList().size(); i < len; i++) {
                Map<String, String> map = result.getList().get(i);
                if (map.containsKey("stockUnit") && kcdw.containsKey(map.get("stockUnit"))) {
                    map.put("stockUnitName", kcdw.get(map.get("stockUnit")));
                }
                if (map.containsKey("pharmacyItemType") && ypfl.containsKey(map.get("pharmacyItemType"))) {
                    map.put("pharmacyItemTypeName", ypfl.get(map.get("pharmacyItemType")));
                }
            }
        }
        return result;
    }

    /**
     * 根据ID查询库存信息
     * @param stockDetailId
     * @return
     */
    @Override
    public StockDetail queryById(Integer stockDetailId) throws Exception {
        if (stockDetailId != null) {
            return stockDetailRepository.findById(stockDetailId).get();
        }
        return null;
    }

    /**
     * 更新 售价 库存数量
     * @param stockDetail
     * @return
     */
    @Override
    public StockDetail update(StockDetail stockDetail) throws Exception {
        if (stockDetail != null && stockDetail.getStockDetailId() != null) {
            StockDetail old = this.queryById(stockDetail.getStockDetailId());
            if (old != null) {
                old.setSellingPrice(stockDetail.getSellingPrice());
                old.setStockCount(stockDetail.getStockCount());
                old.setUpdateTime(new Date());
                return stockDetailRepository.saveAndFlush(old);
            }
        }
        return null;
    }

    /**
     * 下架
     * @param stockDetail
     * @return
     * @throws Exception
     */
    @Override
    public Boolean unshelve(StockDetail stockDetail) throws Exception {
        if (stockDetail != null && stockDetail.getStockDetailId() != null) {
            StockDetail old = this.queryById(stockDetail.getStockDetailId());
            if (old != null) {
                old.setStockState(4);// 下架
                old.setUpdateTime(new Date());
                stockDetailRepository.saveAndFlush(old);
                return true;
            }
        }
        return false;
    }
}
