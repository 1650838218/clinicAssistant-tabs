package com.littledoctor.clinicassistant.module.purchase.stock.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.module.purchase.order.service.OrderService;
import com.littledoctor.clinicassistant.module.purchase.stock.dao.PurStockRepository;
import com.littledoctor.clinicassistant.module.purchase.stock.entity.PurStockEntity;
import com.littledoctor.clinicassistant.module.purchase.stock.mapper.PurStockMapper;
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
public class PurStockServiceImpl implements PurStockService {

    @Autowired
    private PurStockRepository purStockRepository;

    @Autowired
    private OrderService orderService;

    @Autowired(required = false)
    private PurStockMapper purStockMapper;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 保存入库单
     * @param purStockEntities
     * @return
     */
    @Override
    public List<PurStockEntity> save(List<PurStockEntity> purStockEntities) throws Exception {
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
            return purStockRepository.saveAll(purStockEntities);
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
    @Override
    public Page<Map<String, Object>> queryPage(Pageable page, String keywords, boolean expireDate) throws Exception {
        Long offset = page.getOffset();
        int pageSize = page.getPageSize();
        int count = purStockMapper.count(keywords, expireDate);
        List<Map<String, Object>> stockDetails = purStockMapper.findAll(keywords,offset,pageSize, expireDate);
        return new PageImpl<>(stockDetails, page, count);
    }

    /**
     * 根据ID查询库存信息
     * @param purStockId
     * @return
     */
    @Override
    public PurStockEntity queryById(Long purStockId) throws Exception {
        if (purStockId != null) {
            return purStockRepository.findById(purStockId).get();
        }
        return new PurStockEntity();
    }

    /**
     * 更新 售价 库存数量
     * @param purStockEntity
     * @return
     */
    @Override
    public PurStockEntity update(PurStockEntity purStockEntity) throws Exception {
        if (purStockEntity != null && purStockEntity.getPurStockId() != null) {
            PurStockEntity old = this.queryById(purStockEntity.getPurStockId());
            if (old != null) {
                old.setSellingPrice(purStockEntity.getSellingPrice());
                old.setStockCount(purStockEntity.getStockCount());
                old.setUpdateTime(new Date());
                return purStockRepository.saveAndFlush(old);
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
    @Override
    public Boolean unshelve(Long purStockId) throws Exception {
        if (purStockId != null) {
            PurStockEntity old = this.queryById(purStockId);
            if (old != null) {
                old.setStockState(4);// 下架
                old.setUpdateTime(new Date());
                purStockRepository.saveAndFlush(old);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取下拉表格的list
     * @param keywords
     * @param purItemType
     * @return
     */
    @Override
    public List<Map<String, Object>> getCombogrid(String keywords, String purItemType) throws Exception {
        return purStockMapper.getCombogridForDecoction(keywords, purItemType);
    }

    /**
     * 根据药材名称查询药材信息
     * @param medicalName
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findByName(String medicalName) throws Exception {
        if (StringUtils.isNotBlank(medicalName)) {
            return purStockMapper.findByName(medicalName);
        }
        return null;
    }

    /**
     * 查看库存品目的采购信息
     * @param purStockId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findByIdForOrder(Long purStockId) throws Exception {
        if (!ObjectUtils.isEmpty(purStockId)) {
            return purStockMapper.findByIdForOrder(purStockId);
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
    @Override
    public Page<Map<String, Object>> queryPageForWarn(Pageable page, String keywords) throws Exception {
        Long offset = page.getOffset();
        int pageSize = page.getPageSize();
        int count = purStockMapper.countWarn(keywords);
        List<Map<String, Object>> result = purStockMapper.findWarnAll(keywords,offset,pageSize);
        return new PageImpl<>(result, page, count);
    }

    /**
     * 查询已过期
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryPageForExpire(Pageable page) throws Exception {
        Long offset = page.getOffset();
        int pageSize = page.getPageSize();
        purStockRepository.updateStateForExpire();// 更新过期状态
        int count = purStockMapper.countExpire();
        List<Map<String, Object>> result = purStockMapper.findExpireAll(offset,pageSize);
        return new PageImpl<>(result, page, count);
    }
}
