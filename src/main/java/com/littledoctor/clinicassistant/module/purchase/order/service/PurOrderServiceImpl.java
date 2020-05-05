package com.littledoctor.clinicassistant.module.purchase.order.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import com.littledoctor.clinicassistant.module.purchase.item.service.ItemService;
import com.littledoctor.clinicassistant.module.purchase.order.dao.PurOrderRepository;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrder;
import com.littledoctor.clinicassistant.module.purchase.order.entity.PurOrderDetail;
import com.littledoctor.clinicassistant.module.purchase.order.mapper.PurOrderMapper;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import com.littledoctor.clinicassistant.module.purchase.supplier.service.SupplierService;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
@Service
public class PurOrderServiceImpl implements PurOrderService {

    @Autowired
    private PurOrderRepository purOrderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired(required = false)
    private PurOrderMapper purOrderMapper;

    /**
     * 分页查询订单
     * @param page
     * @param purItemName
     * @param purOrderDate
     * @param supplierId
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryPage(Pageable page, String purItemName, String purOrderDate, String supplierId) throws Exception {
        String startDate = "";// 开始日期  查询某日期范围内的订单
        String endDate = "";// 结束日期
        Long offSet = (long)0;// 偏移量
        int pageSize = 10;// 每页的数据条数
        if (StringUtils.isNotBlank(purOrderDate)) {
            String[] dates = purOrderDate.split(" - ");
            if (dates != null && dates.length == 2) {
                startDate = dates[0];
                endDate = dates[1];
            }
        }
        int count = purOrderMapper.count(purItemName, supplierId, startDate, endDate);
        List<Map<String, Object>> data = purOrderMapper.findAll(purItemName,supplierId,startDate,endDate,offSet,pageSize);
        return new PageImpl<>(data, page, count);
    }

    /**
     * 保存采购单
     * @param purOrder
     * @return
     */
    @Override
    public PurOrder save(PurOrder purOrder) {
        purOrder.setCreateTiem(new Date());
        purOrder.setEntry(false);
        purOrder.setUpdateTime(new Date());
        return purOrderRepository.saveAndFlush(purOrder);
    }

    /**
     * 根据采购单ID查询采购单
     * @param purOrderId
     * @return
     */
    @Override
    public PurOrder queryById(String purOrderId) throws Exception {
        PurOrder purOrder = purOrderRepository.findById(Long.parseLong(purOrderId)).get();
        if (purOrder != null) {
            // 设置供应商名称
            SupplierEntity supplierEntity = supplierService.findById(String.valueOf(purOrder.getSupplierId()));
            purOrder.setSupplierName(supplierEntity.getSupplierName());
            List<PurOrderDetail> pods = purOrder.getPurOrderDetails();
            if (pods != null && pods.size() > 0) {
                // 查询字典显示值
                Map<String, String> sldw = dictionaryService.getItemMapByKey(DictionaryKey.PUR_ITEM_JHBZ);
                Map<String, String> kcdw = dictionaryService.getItemMapByKey(DictionaryKey.PUR_ITEM_LSDW);
                for (int i = 0, len = pods.size(); i < len; i++) {
                    PurOrderDetail pbi = pods.get(i);
                    // 查询药品信息
                    if (pbi.getPurItemId() != null) {
                        ItemEntity pi = itemService.getById(String.valueOf(pbi.getPurItemId()));
                        if (pi != null) {
                            pbi.setPurItemName(pi.getItemName());
                            pbi.setUnitConvert(pi.getUnitConvert());
                            // 计算库存量
                            if (pbi.getPurCount() != null && pbi.getUnitConvert() != null) {
                                pbi.setStockCount(pbi.getPurCount().multiply(new BigDecimal(pbi.getUnitConvert())));
                            }
                            // 设置数量单位名称
                            if (sldw != null) pbi.setPurUnitName(sldw.get(pi.getPurUnit()));
                            if (kcdw != null) pbi.setStockUnitName(kcdw.get(pi.getStockUnit()));
                        }
                    }
                }
            }
        }
        return purOrder;
    }

    /**
     * 入库时，根据采购单ID查询采购单
     * @param purOrderId 采购单ID
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryByIdForStock(String purOrderId) throws Exception {
        if (StringUtils.isNotBlank(purOrderId)) {
            Map<String,Object> purOrder = purOrderMapper.findByIdForStock(Long.parseLong(purOrderId));
            List<Map<String, Object>> orderDetail = purOrderMapper.findOrderDetailForStock(Long.parseLong(purOrderId));
            Map<String, Object> result = new HashMap<>();
            result.put("order",purOrder);
            result.put("detail",orderDetail);
            return result;
        }
        return new HashMap<>();
    }

    /**
     * 删除采购单
     * @param purOrderId
     * @return
     */
    @Override
    public boolean delete(String purOrderId) throws Exception {
        if (StringUtils.isNotBlank(purOrderId)) {
            purOrderRepository.deleteById(Long.parseLong(purOrderId));
            return true;
        }
        return false;
    }

    /**
     * 更新入库状态为已入库
     * @param purOrderIds
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateEntry(HashSet<String> purOrderIds) throws Exception {
        if (purOrderIds != null && !purOrderIds.isEmpty()) {
            String ids = "";
            Iterator iterator = purOrderIds.iterator();
            while (iterator.hasNext()) {
                ids += iterator.next().toString();
                if (iterator.hasNext()) ids += ",";
            }
            purOrderRepository.updateEntry(ids);
            return true;
        }
        return false;
    }
}
