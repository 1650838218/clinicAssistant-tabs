package com.littledoctor.clinicassistant.module.item.herbalmedicine.service;

import com.littledoctor.clinicassistant.common.constant.Constant;
import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.dao.ItemHerbalMedicineDao;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
@Service
public class ItemHerbalMedicineService {

    @Autowired
    private ItemHerbalMedicineDao itemHerbalMedicineDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemHerbalMedicineEntity save(ItemHerbalMedicineEntity entity) throws Exception {
        ItemHerbalMedicineEntity newEntity =  itemHerbalMedicineDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.HERBAL_MEDICINE);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemHerbalMedicineEntity();
    }

    /**
     * 根据条件查询所有的中药
     * @param initial 首字母
     * @param itemType 中药分类
     * @param itemName 中药名称
     * @return
     * @throws Exception
     */
    public Page<ItemHerbalMedicineEntity> queryPage(String initial, String itemType, String itemName, Pageable page) throws Exception {
        return itemHerbalMedicineDao.findAll(new Specification<ItemHerbalMedicineEntity>() {
            @Override
            public Predicate toPredicate(Root<ItemHerbalMedicineEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(initial.trim())) {
                    list.add(criteriaBuilder.like(root.get("abbrPinyin"), initial.trim().toUpperCase() + "%"));
                }
                if (StringUtils.isNotBlank(itemType)) {
                    list.add(criteriaBuilder.equal(root.get("itemType"), itemType));
                }
                if (StringUtils.isNotBlank(itemName.trim())) {
                    Predicate p1 = criteriaBuilder.like(root.get("itemName"), "%" + itemName.trim() + "%");
                    Predicate p2 = criteriaBuilder.like(root.get("abbrPinyin"), itemName.trim().toUpperCase() + "%");
                    Predicate p3 = criteriaBuilder.like(root.get("fullPinyin"), itemName.trim().toLowerCase() + "%");
                    list.add(criteriaBuilder.or(p1,p2,p3));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        }, page);
    }
}
