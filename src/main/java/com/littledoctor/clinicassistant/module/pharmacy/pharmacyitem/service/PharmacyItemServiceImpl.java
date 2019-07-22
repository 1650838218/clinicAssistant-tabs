package com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.service;

import com.littledoctor.clinicassistant.common.plugin.select.SelectOption;
import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.dao.PharmacyItemRepository;
import com.littledoctor.clinicassistant.module.pharmacy.pharmacyitem.entity.PharmacyItem;
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

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 22:48
 * @Description: 药房品目
 */
@Service
public class PharmacyItemServiceImpl implements PharmacyItemService {

    @Autowired
    private PharmacyItemRepository pharmacyItemRepository;

    /**
     * 分页查询
     * @param keywords 查询条件 药品名称/全拼/简拼/条形码
     * @param page
     * @return
     */
    @Override
    public Page<PharmacyItem> queryPage(String keywords, Pageable page) {
        return pharmacyItemRepository.findAll(new Specification<PharmacyItem>() {
            @Override
            public Predicate toPredicate(Root<PharmacyItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
     * @param pharmacyItem
     * @return
     */
    @Override
    public PharmacyItem save(PharmacyItem pharmacyItem) {
        if (pharmacyItem != null) {
            return pharmacyItemRepository.saveAndFlush(pharmacyItem);
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
            pharmacyItemRepository.deleteById(Integer.parseInt(pharmacyItemId));
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
    public PharmacyItem getById(String pharmacyItemId) {
        if (StringUtils.isNotBlank(pharmacyItemId)) {
            return pharmacyItemRepository.findById(Integer.parseInt(pharmacyItemId)).get();
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
            return pharmacyItemRepository.count(new Specification<PharmacyItem>() {
                @Override
                public Predicate toPredicate(Root<PharmacyItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
            return pharmacyItemRepository.count(new Specification<PharmacyItem>() {
                @Override
                public Predicate toPredicate(Root<PharmacyItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
    public List<PharmacyItem> queryByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return pharmacyItemRepository.findAll(new Specification<PharmacyItem>() {
                @Override
                public Predicate toPredicate(Root<PharmacyItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
            return pharmacyItemRepository.getSelectOption();
        } else {
            return pharmacyItemRepository.getSelectOption(keywords.trim());
        }
    }
}
