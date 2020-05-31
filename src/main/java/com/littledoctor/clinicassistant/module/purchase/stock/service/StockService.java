package com.littledoctor.clinicassistant.module.purchase.stock.service;

import com.littledoctor.clinicassistant.module.purchase.order.service.OrderService;
import com.littledoctor.clinicassistant.module.purchase.stock.dao.StockDao;
import com.littledoctor.clinicassistant.module.purchase.stock.entity.StockEntity;
import com.littledoctor.clinicassistant.module.purchase.stock.mapper.StockMapper;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:28
 * @Description: 入库单
 */
@Service
@Transactional
public class StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private OrderService orderService;

    @Autowired(required = false)
    private StockMapper stockMapper;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 保存入库单
     * @param purStockEntities
     * @return
     */
    public List<StockEntity> save(List<StockEntity> purStockEntities) throws Exception {
        if (purStockEntities != null && purStockEntities.size() > 0) {
            HashSet<String> purOrderIds = new HashSet<>();
            for (int i = 0, len = purStockEntities.size(); i < len; i++) {
                if (purStockEntities.get(i).getPurOrderId() != null) purOrderIds.add(purStockEntities.get(i).getPurOrderId().toString());
                purStockEntities.get(i).setCreateTiem(new Date());
                purStockEntities.get(i).setUpdateTime(new Date());
            }
            if (!purOrderIds.isEmpty()) {
                // 新增入库单的时候需要将与其对应的采购单的状态改为已入库
                orderService.updateEntry(purOrderIds);
            }
            return stockDao.saveAll(purStockEntities);
        }
        return new ArrayList<>();
    }

    /**
     * 分页查询 库存
     * @param page
     * @param keywords
     * @param expireDate
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> queryPage(Pageable page, String keywords, boolean expireDate) throws Exception {
        Long offset = page.getOffset();
        int pageSize = page.getPageSize();
        int count = stockMapper.count(keywords, expireDate);
        List<Map<String, Object>> stockDetails = stockMapper.findAll(keywords,offset,pageSize, expireDate);
        return new PageImpl<>(stockDetails, page, count);
    }

    /**
     * 根据ID查询库存信息
     * @param purStockId
     * @return
     */
    public StockEntity queryById(Long purStockId) throws Exception {
        if (purStockId != null) {
            return stockDao.findById(purStockId).get();
        }
        return new StockEntity();
    }

    /**
     * 更新 售价 库存数量
     * @param stockEntity
     * @return
     */
    public StockEntity update(StockEntity stockEntity) throws Exception {
        if (stockEntity != null && stockEntity.getPurStockId() != null) {
            StockEntity old = this.queryById(stockEntity.getPurStockId());
            if (old != null) {
                old.setSellingPrice(stockEntity.getSellingPrice());
                old.setStockCount(stockEntity.getStockCount());
                old.setUpdateTime(new Date());
                return stockDao.saveAndFlush(old);
            }
        }
        return null;
    }

    /**
     * 下架
     * @param purStockId
     * @return
     * @throws Exception
     */
    public Boolean unshelve(Long purStockId) throws Exception {
        if (purStockId != null) {
            StockEntity old = this.queryById(purStockId);
            if (old != null) {
                old.setStockState(4);// 下架
                old.setUpdateTime(new Date());
                stockDao.saveAndFlush(old);
                return true;
            }
        }
        return false;
    }

    /**
     * 查询库存中药
     * @param keywords
     * @return
     */
    public List<Map<String, Object>> getCombogridForHerbalMedicine(String keywords) throws Exception {
        return stockMapper.getCombogridForHerbalMedicine(keywords);
    }

    /**
     * 根据药材名称查询药材信息
     * @param medicalName
     * @return
     * @throws Exception
     */
    public Map<String, Object> findByName(String medicalName) throws Exception {
        if (StringUtils.isNotBlank(medicalName)) {
            return stockMapper.findByName(medicalName);
        }
        return null;
    }

    /**
     * 查看库存品目的采购信息
     * @param purStockId
     * @return
     * @throws Exception
     */
    public Map<String, Object> findByIdForOrder(Long purStockId) throws Exception {
        if (!ObjectUtils.isEmpty(purStockId)) {
            return stockMapper.findByIdForOrder(purStockId);
        }
        return new HashMap<>();
    }

    /**
     * 查询预警库存
     * @param page
     * @param keywords
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> queryPageForWarn(Pageable page, String keywords) throws Exception {
        Long offset = page.getOffset();
        int pageSize = page.getPageSize();
        int count = stockMapper.countWarn(keywords);
        List<Map<String, Object>> result = stockMapper.findWarnAll(keywords,offset,pageSize);
        return new PageImpl<>(result, page, count);
    }

    /**
     * 查询已过期
     * @param page
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> queryPageForExpire(Pageable page) throws Exception {
        Long offset = page.getOffset();
        int pageSize = page.getPageSize();
        stockDao.updateStateForExpire();// 更新过期状态
        int count = stockMapper.countExpire();
        List<Map<String, Object>> result = stockMapper.findExpireAll(offset,pageSize);
        return new PageImpl<>(result, page, count);
    }

    /**
     * 查询库存中成药
     * @param keywords
     * @return
     */
    public List<Map<String, Object>> getCombogridForPatentMedicine(String keywords) throws Exception{
        return stockMapper.getCombogridForPatentMedicine(keywords);
    }
}
