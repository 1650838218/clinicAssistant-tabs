package com.littledoctor.clinicassistant.module.purchase.order.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.module.purchase.item.service.ItemService;
import com.littledoctor.clinicassistant.module.purchase.order.dao.OrderDao;
import com.littledoctor.clinicassistant.module.purchase.order.entity.OrderEntity;
import com.littledoctor.clinicassistant.module.purchase.order.mapper.OrderMapper;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import com.littledoctor.clinicassistant.module.purchase.supplier.service.SupplierService;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired(required = false)
    private OrderMapper orderMapper;

    /**
     * 分页查询订单
     * @param page
     * @param itemName
     * @param purOrderDate
     * @param supplierId
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> queryPage(Pageable page, String itemName, String purOrderDate, String supplierId) throws Exception {
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
        int count = orderMapper.count(itemName, supplierId, startDate, endDate);
        List<Map<String, Object>> data = orderMapper.findAll(itemName,supplierId,startDate,endDate,offSet,pageSize);
        return new PageImpl<>(data, page, count);
    }

    /**
     * 保存采购单
     * @param orderEntity
     * @return
     */
    public OrderEntity save(OrderEntity orderEntity) {
        orderEntity.setCreateTiem(new Date());
        orderEntity.setEntry(false);
        orderEntity.setUpdateTime(new Date());
        return orderDao.saveAndFlush(orderEntity);
    }

    /**
     * 根据采购单ID查询采购单
     * @param purOrderId
     * @return
     */
    public OrderEntity queryById(String purOrderId) throws Exception {
        OrderEntity orderEntity = orderDao.findById(Long.parseLong(purOrderId)).get();
        if (orderEntity != null) {
            // 设置供应商名称
            SupplierEntity supplierEntity = supplierService.findById(String.valueOf(orderEntity.getSupplierId()));
            orderEntity.setSupplierName(supplierEntity.getSupplierName());
        }
        return orderEntity;
    }

    /**
     * 入库时，根据采购单ID查询采购单
     * @param purOrderId 采购单ID
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryByIdForStock(String purOrderId) throws Exception {
        if (StringUtils.isNotBlank(purOrderId)) {
            Map<String,Object> purOrder = orderMapper.findByIdForStock(Long.parseLong(purOrderId));
            List<Map<String, Object>> orderDetail = orderMapper.findOrderDetailForStock(Long.parseLong(purOrderId));
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
    public boolean delete(String purOrderId) throws Exception {
        if (StringUtils.isNotBlank(purOrderId)) {
            orderDao.deleteById(Long.parseLong(purOrderId));
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
    public boolean updateEntry(HashSet<String> purOrderIds) throws Exception {
        if (purOrderIds != null && !purOrderIds.isEmpty()) {
            String ids = "";
            Iterator iterator = purOrderIds.iterator();
            while (iterator.hasNext()) {
                ids += iterator.next().toString();
                if (iterator.hasNext()) ids += ",";
            }
            orderDao.updateEntry(ids);
            return true;
        }
        return false;
    }

    /**
     * 采购单  查询采购品目下拉表格
     * @param keywords
     * @return
     */
    public List<Map<String, Object>> getPurchaseItem(String keywords) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        if (StringUtils.isNotBlank(keywords)) keywords = keywords.trim();
        result = orderMapper.getPurchaseItem(keywords);
        if (!ObjectUtils.isEmpty(result)) {
            Map<String, String> pmflMap = dictionaryService.getItemMapByKey(DictionaryKey.ITEM_PMFL);
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> entity = result.get(i);
                entity.put("itemTypeName",pmflMap.get(entity.get("itemType")));
            }
        }
        return result;
    }
}
