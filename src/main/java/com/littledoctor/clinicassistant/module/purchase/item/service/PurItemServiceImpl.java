package com.littledoctor.clinicassistant.module.purchase.item.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.dao.PurItemRepository;
import com.littledoctor.clinicassistant.module.purchase.item.entity.PurItemEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @Date: 2018/10/19 22:48
 * @Description: 采购品目
 */
@Service
public class PurItemServiceImpl implements PurItemService {

    @Autowired
    private PurItemRepository purItemRepository;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 分页查询
     * @param keywords 查询条件 药品名称/全拼/简拼/条形码
     * @param page
     * @return
     */
    @Override
    public Page<PurItemEntity> queryPage(String keywords, Pageable page) {
        return purItemRepository.findAll(new Specification<PurItemEntity>() {
            @Override
            public Predicate toPredicate(Root<PurItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(keywords)) {
                    list.add(criteriaBuilder.equal(root.get("barcode"), keywords));
                    list.add(criteriaBuilder.like(root.get("pharmacyItemName"), "%" + keywords + "%"));
                    list.add(criteriaBuilder.like(root.get("abbreviation"), keywords.toUpperCase() + "%"));
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
     * 保存
     * @param purItemEntity
     * @return
     */
    @Override
    public PurItemEntity save(PurItemEntity purItemEntity) {
        if (purItemEntity != null) {
            return purItemRepository.saveAndFlush(purItemEntity);
        }
        return null;
    }

    /**
     * 根据ID删除
     * @param pharmacyItemId
     * @return
     */
    @Override
    public boolean delete(String pharmacyItemId) {
        if (StringUtils.isNotBlank(pharmacyItemId)) {
            purItemRepository.deleteById(Integer.parseInt(pharmacyItemId));
            return true;
        }
        return false;
    }

    /**
     * 根据ID查询
     * @param pharmacyItemId
     * @return
     */
    @Override
    public PurItemEntity getById(String pharmacyItemId) {
        if (StringUtils.isNotBlank(pharmacyItemId)) {
            return purItemRepository.findById(Integer.parseInt(pharmacyItemId)).get();
        }
        return null;
    }

    /**
     * 判断条形码是否不重复，是否不存在
     * @param pharmacyItemId
     * @param barcode
     * @return true 不存在  false 已存在，默认false
     */
    @Override
    public boolean notRepeatBarcode(String pharmacyItemId, String barcode) {
        if (StringUtils.isNotBlank(barcode)) {
            return purItemRepository.count(new Specification<PurItemEntity>() {
                @Override
                public Predicate toPredicate(Root<PurItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("barcode"), barcode));
                    if (StringUtils.isNotBlank(pharmacyItemId)) {
                        list.add(criteriaBuilder.notEqual(root.get("pharmacyItemId"), pharmacyItemId));
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
     * @param pharmacyItemId
     * @param pharmacyItemName
     * @return true 不存在  false 已存在，默认false
     */
    @Override
    public boolean notRepeatName(String pharmacyItemId, String pharmacyItemName) {
        if (StringUtils.isNotBlank(pharmacyItemName)) {
            return purItemRepository.count(new Specification<PurItemEntity>() {
                @Override
                public Predicate toPredicate(Root<PurItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("pharmacyItemName"), pharmacyItemName));
                    if (StringUtils.isNotBlank(pharmacyItemId)) {
                        list.add(criteriaBuilder.notEqual(root.get("pharmacyItemId"), pharmacyItemId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 根据名称查询药品清单
     * @param name 可以是药品名称，药品全拼，药品简拼
     * @return
     */
    @Override
    public List<PurItemEntity> queryByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return purItemRepository.findAll(new Specification<PurItemEntity>() {
                @Override
                public Predicate toPredicate(Root<PurItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.like(root.get("pharmacyItemName"), "%" + name + "%"));
                    list.add(criteriaBuilder.like(root.get("abbreviation"), name + "%"));
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
            return purItemRepository.getSelectOption();
        } else {
            return purItemRepository.getSelectOption(keywords.trim());
        }
    }

    /**
     * 根据品目ID判断该品目是否存在
     * @param pharmacyItemId
     * @return
     */
    @Override
    public boolean isExist(String pharmacyItemId) {
        if (StringUtils.isBlank(pharmacyItemId)) {
            return purItemRepository.count(new Specification<PurItemEntity>() {
                @Override
                public Predicate toPredicate(Root<PurItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("pharmacyItemId"), pharmacyItemId);
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
    public List<PurItemEntity> getCombogrid(String keywords) throws Exception {
        List<PurItemEntity> result = new ArrayList<>();
        if (StringUtils.isBlank(keywords)) {
            result = purItemRepository.findAll();
        } else {
            result = this.queryByName(keywords);
        }
        if (result != null && result.size() > 0) {
            // 查询药品类型字典，将显示值set进去
            Map<String, String> ypfl = dictionaryService.getItemMapByKey("YPFL");// 药品分类
            Map<String, String> sldw = dictionaryService.getItemMapByKey("SLDW");// 数量单位
            for (int i = 0, len = result.size(); i < len; i++) {
                if (ypfl != null) result.get(i).setPharmacyItemTypeName(ypfl.get(result.get(i).getPharmacyItemType()));
                if (sldw != null) result.get(i).setPurchaseUnitName(sldw.get(result.get(i).getPurchaseUnit()));
            }
        }
        return result;
    }
}
