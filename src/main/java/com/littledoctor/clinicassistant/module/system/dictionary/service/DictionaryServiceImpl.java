package com.littledoctor.clinicassistant.module.system.dictionary.service;

import com.littledoctor.clinicassistant.common.plugin.TreeEntity;
import com.littledoctor.clinicassistant.common.plugin.TreeNodeType;
import com.littledoctor.clinicassistant.module.system.dictionary.dao.DictionaryRepository;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryItem;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryType;
import com.littledoctor.clinicassistant.module.system.menu.entity.Menu;
import com.littledoctor.clinicassistant.module.system.menu.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2018/11/2
 * @Description: 数据字典
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private Logger log = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     * @param code
     * @param text
     * @param page
     * @return
     */
    @Override
    public Page<DictionaryType> queryPage(String code, String text, Pageable page) {
        return dictionaryRepository.findAll(new Specification<DictionaryType>() {
            @Override
            public Predicate toPredicate(Root<DictionaryType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(code)) {
                    list.add(criteriaBuilder.like(root.get("key").get("code"), "%" + code + "%"));
                }
                if (!StringUtils.isEmpty(text)) {
                    list.add(criteriaBuilder.like(root.get("text"), "%" + text + "%"));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        }, page);
    }

    /**
     * 保存数据字典
     * @param dictionaryType
     * @return
     */
    @Override
    public DictionaryType save(DictionaryType dictionaryType) throws Exception {
        dictionaryType.setDictTypeKey(dictionaryType.getDictTypeKey().trim());
        dictionaryType.setDictTypeName(dictionaryType.getDictTypeName().trim());
        DictionaryType dt = dictionaryRepository.saveAndFlush(dictionaryType);
        // 设置菜单名称
        if (dt.getMenuId() != null) {
            Menu menu = menuService.getById(dt.getMenuId().toString());
            dt.setMenuName(menu.getMenuName());
        }
        return dt;
    }

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) throws Exception {
        dictionaryRepository.deleteById(id);
        return true;
    }

    /**
     * 获取字典树
     * @return
     */
    @Override
    public List<TreeEntity> findTreeEntity() throws Exception {
        List<TreeEntity> result = new ArrayList<>();
        // 查询菜单
        List<TreeEntity> menuList = menuService.findTreeEntity();
        if (!CollectionUtils.isEmpty(menuList)) result.addAll(menuList);
        // 查询字典类型
        List<TreeEntity> dictionaryList = dictionaryRepository.findTreeEntity();
        if (!CollectionUtils.isEmpty(dictionaryList)) {
            // 设置节点类型
            for (int i = 0; i < dictionaryList.size(); i++) {
                dictionaryList.get(i).setNodeType(TreeNodeType.DICTIONARY_TYPE);
            }
            result.addAll(dictionaryList);
        }
        return result;
    }

    /**
     * 根据ID查询字典
     * @param dictionaryId
     * @return
     * @throws Exception
     */
    @Override
    public DictionaryType getById(Integer dictionaryId) throws Exception {
        if (dictionaryId != null) {
            DictionaryType dt = dictionaryRepository.findById(dictionaryId).get();
            if (dt.getMenuId() != null) {
                Menu menu = menuService.getById(dt.getMenuId().toString());
                dt.setMenuName(menu.getMenuName());
            }
            return dt;
        }
        return new DictionaryType();
    }

    /**
     * 检查多级字典名称是否重复
     * @param dictTypeId
     * @param dictTypeName
     * @return false 重复 true 不重复
     */
    @Override
    public boolean repeatTypeName(String dictTypeId, String dictTypeName) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(dictTypeName)) {
            return dictionaryRepository.count(new Specification<DictionaryType>() {
                @Override
                public Predicate toPredicate(Root<DictionaryType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("dictTypeName"), dictTypeName));
                    if (org.apache.commons.lang.StringUtils.isNotBlank(dictTypeId)) {
                        list.add(criteriaBuilder.notEqual(root.get("dictTypeId"), dictTypeId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 检查多级字典键是否重复
     * @param dictTypeId
     * @param dictTypeKey
     * @return false 重复 true 不重复
     */
    @Override
    public boolean repeatTypeKey(String dictTypeId, String dictTypeKey) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(dictTypeKey)) {
            return dictionaryRepository.count(new Specification<DictionaryType>() {
                @Override
                public Predicate toPredicate(Root<DictionaryType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("dictTypeKey"), dictTypeKey));
                    if (org.apache.commons.lang.StringUtils.isNotBlank(dictTypeId)) {
                        list.add(criteriaBuilder.notEqual(root.get("dictTypeId"), dictTypeId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 根据字典键查询字典，常用于下拉框
     * @param dictTypeKey
     * @return
     */
    @Override
    public DictionaryType getByKey(String dictTypeKey) throws Exception {
        if (!StringUtils.isEmpty(dictTypeKey)) {
            return dictionaryRepository.getByKey(dictTypeKey);
        }
        return null;
    }

    /**
     * 根据字典键查询字典
     * @param dictTypeKey
     * @return
     */
    @Override
    public List<DictionaryItem> getItemListByKey(String dictTypeKey) throws Exception {
        if (!StringUtils.isEmpty(dictTypeKey)) {
            return dictionaryRepository.getByKey(dictTypeKey).getDictItem();
        }
        return null;
    }

    /**
     * 根据字典键查询字典
     * @param dictTypeKey
     * @return
     */
    @Override
    public Map<String, String> getItemMapByKey(String dictTypeKey) throws Exception {
        if (!StringUtils.isEmpty(dictTypeKey)) {
            List<DictionaryItem> items = dictionaryRepository.getByKey(dictTypeKey).getDictItem();
            if (items != null && items.size() > 0) {
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < items.size(); i++) {
                    map.put(items.get(i).getDictItemValue(), items.get(i).getDictItemName());
                }
                return map;
            }
        }
        return null;
    }

    /**
     * 根据字典键和真实值查询显示值
     * @param dictTypeKey
     * @param dictItemValue
     * @return
     * @throws Exception
     */
    @Override
    public String queryItemName(String dictTypeKey, Integer dictItemValue) throws Exception {
        if (!StringUtils.isEmpty(dictTypeKey) && dictItemValue != null) {
            DictionaryType dt = this.getByKey(dictTypeKey);
            if (dt.getDictItem() != null && dt.getDictItem().size() > 0) {
                for (int i = 0; i < dt.getDictItem().size(); i++) {
                    DictionaryItem di = dt.getDictItem().get(i);
                    if (dictItemValue.equals(di.getDictItemId())) {
                        return di.getDictItemName();
                    }
                }
            }
        }
        return null;
    }
}
