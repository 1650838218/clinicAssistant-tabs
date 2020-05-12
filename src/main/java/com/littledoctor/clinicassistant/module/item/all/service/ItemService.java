package com.littledoctor.clinicassistant.module.item.all.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.module.item.all.entity.ItemEntity;
import com.littledoctor.clinicassistant.module.item.all.mapper.ItemMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 品目管理  新增品目
 */
@Service
public class ItemService {

    @Autowired(required = false)
    private ItemMapper itemMapper;

    /**
     * 采购单  查询采购品目下拉表格
     * @param keywords
     * @return
     */
    public List<ItemEntity> getPurchaseItem(String keywords) throws Exception {
        List<ItemEntity> result = new ArrayList<>();
        if (StringUtils.isNotBlank(keywords)) {
            return itemMapper.getPurchaseItem(keywords);
        }
        return result;
    }
}
