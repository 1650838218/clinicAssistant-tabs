package com.littledoctor.clinicassistant.module.purchase.item.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.dao.OldItemRepository;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 22:48
 * @Description: 采购品目
 */
//@Service
public class ItemServiceImpl implements ItemService {

//    @Autowired
    private OldItemRepository oldItemRepository;

//    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 保存
     * @param itemEntity
     * @return
     */
    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        if (itemEntity != null) {
            return oldItemRepository.saveAndFlush(itemEntity);
        }
        return null;
    }

    /**
     * 判断条形码是否不重复，是否不存在
     * @param itemId
     * @param barcode
     * @return true 不存在  false 已存在，默认false
     */
    @Override
    public boolean notRepeatBarcode(String itemId, String barcode) {
        if (StringUtils.isNotBlank(barcode)) {
            return oldItemRepository.count(new Specification<ItemEntity>() {
                @Override
                public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("barcode"), barcode));
                    if (StringUtils.isNotBlank(itemId)) {
                        list.add(criteriaBuilder.notEqual(root.get("itemId"), itemId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 判断药品名称是否不重复，是否不存在
     *
     * @param itemId
     * @param itemName
     * @return true 不存在  false 已存在，默认false
     */
    @Override
    public boolean notRepeatName(String itemId, String itemName) {
        if (StringUtils.isNotBlank(itemName)) {
            return oldItemRepository.count(new Specification<ItemEntity>() {
                @Override
                public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("itemName"), itemName));
                    if (StringUtils.isNotBlank(itemId)) {
                        list.add(criteriaBuilder.notEqual(root.get("itemId"), itemId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 分页查询
     * @param keywords 查询条件 药品名称/全拼/简拼/条形码
     * @param page
     * @return
     */
    @Override
    public Page<ItemEntity> queryPage(String keywords, Pageable page) {
        return oldItemRepository.findAll(new Specification<ItemEntity>() {
            @Override
            public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(keywords)) {
                    list.add(criteriaBuilder.equal(root.get("barcode"), keywords));
                    list.add(criteriaBuilder.like(root.get("itemName"), "%" + keywords + "%"));
                    list.add(criteriaBuilder.like(root.get("abbrPinyin"), keywords.toUpperCase() + "%"));
                    list.add(criteriaBuilder.like(root.get("fullPinyin"), keywords.toLowerCase() + "%"));
                }
                if (list.size() > 0) {
                    return criteriaBuilder.or(list.toArray(new Predicate[list.size()]));
                }
                return null;
            }
        }, page);
    }

    /**
     * 根据ID删除
     * @param itemId
     * @return
     */
    @Override
    public boolean delete(String itemId) {
        if (StringUtils.isNotBlank(itemId)) {
            oldItemRepository.deleteById(Long.parseLong(itemId));
            return true;
        }
        return false;
    }

    /**
     * 根据ID查询
     * @param itemId
     * @return
     */
    @Override
    public ItemEntity getById(String itemId) {
        if (StringUtils.isNotBlank(itemId)) {
            return oldItemRepository.findById(Long.parseLong(itemId)).get();
        }
        return null;
    }

    /**
     * 根据名称查询药品清单
     * @param name 可以是药品名称，药品全拼，药品简拼
     * @return
     */
    @Override
    public List<ItemEntity> queryByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return oldItemRepository.findAll(new Specification<ItemEntity>() {
                @Override
                public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.like(root.get("itemName"), "%" + name + "%"));
                    list.add(criteriaBuilder.like(root.get("abbrPinyin"), name + "%"));
                    list.add(criteriaBuilder.like(root.get("fullPinyin"), name + "%"));
                    return criteriaBuilder.or(list.toArray(new Predicate[list.size()]));
                }
            });
        }
        return null;
    }

    /**
     * 获取下拉框的option list
     * @return
     * @param keywords
     */
    @Override
    public List<SelectOption> getSelectOption(String keywords) throws Exception {
        if (StringUtils.isBlank(keywords)) {
            return oldItemRepository.getSelectOption();
        } else {
            return oldItemRepository.getSelectOption(keywords.trim());
        }
    }

    /**
     * 根据品目ID判断该品目是否存在
     * @param itemId
     * @return
     */
    @Override
    public boolean isExist(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return oldItemRepository.count(new Specification<ItemEntity>() {
                @Override
                public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("itemId"), itemId);
                }
            }) > 0;
        }
        return false;
    }

    /**
     * 获取下拉表格
     * @param keywords
     * @return
     */
    @Override
    public List<ItemEntity> getCombogrid(String keywords) throws Exception {
        List<ItemEntity> result = new ArrayList<>();
        if (StringUtils.isBlank(keywords)) {
            result = oldItemRepository.findAll();
        } else {
            result = this.queryByName(keywords);
        }
        if (result != null && result.size() > 0) {
            // 查询采购分类字典，将显示值set进去
            Map<String, String> ypfl = dictionaryService.getItemMapByKey(DictionaryKey.ITEM_PMFL);// 采购品目分类
            Map<String, String> sldw = dictionaryService.getItemMapByKey(DictionaryKey.PUR_ITEM_JHBZ);// 进货包装，采购单位
            for (int i = 0, len = result.size(); i < len; i++) {
                if (ypfl != null) result.get(i).setItemTypeName(ypfl.get(result.get(i).getItemType()));
                if (sldw != null) result.get(i).setPurUnit(sldw.get(result.get(i).getPurUnit()));
            }
        }
        return result;
    }

    /**
     * 查询品目的id和名称，并装配到TreeEntity中
     * @return
     * @throws Exception
     */
    @Override
    public List<ItemEntity> findTreeEntity() throws Exception {
        return oldItemRepository.findAll();
    }

    /**
     * 根据ID查询品目
     * @param purItemIds
     * @return
     * @throws Exception
     */
    @Override
    public List<ItemEntity> findAllById(List<Long> purItemIds) throws Exception {
        return oldItemRepository.findAllById(purItemIds);
    }
}
