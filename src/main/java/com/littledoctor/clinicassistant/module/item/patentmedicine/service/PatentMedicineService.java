package com.littledoctor.clinicassistant.module.item.patentmedicine.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.patentmedicine.dao.PatentMedicineDao;
import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.PatentMedicineEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
 * @Description: 成药 品目
 */
@Service
public class PatentMedicineService {

    @Autowired
    private PatentMedicineDao patentMedicineDao;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public PatentMedicineEntity save(PatentMedicineEntity entity) throws Exception {
        String jhbz = dictionaryService.getDictNameByDictKeyAndDictValue(DictionaryKey.PUR_ITEM_JHBZ, entity.getPurUnit());
        String kcdw = dictionaryService.getDictNameByDictKeyAndDictValue(DictionaryKey.PUR_ITEM_LSDW, entity.getStockUnit());
        entity.setPurUnitName(jhbz);
        entity.setStockUnitName(kcdw);
        return patentMedicineDao.saveAndFlush(entity);
    }

    /**
     * 判断名称是否不重复，是否不存在
     * @param itemId
     * @param itemName
     * @return true 不存在  false 已存在，默认false
     */
    public boolean notRepeatName(String itemId, String itemName) throws Exception {
        if (StringUtils.isNotBlank(itemName) ) {
            return patentMedicineDao.count(new Specification<PatentMedicineEntity>() {
                @Override
                public Predicate toPredicate(Root<PatentMedicineEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
     * 根据ID查询
     * @param id
     * @return
     */
    public PatentMedicineEntity findById(Long id) throws Exception {
        if (id != null) {
            return patentMedicineDao.findById(id).get();
        }
        return new PatentMedicineEntity();
    }

    /**
     * 查询目录
     * @param keyword
     * @return
     */
    public List<TreeEntity> queryCatalog(String keyword) throws Exception {
        List<PatentMedicineEntity> resultList = patentMedicineDao.findAll(new Specification<PatentMedicineEntity>() {
            @Override
            public Predicate toPredicate(Root<PatentMedicineEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(keyword.trim())) {
                    Predicate p1 = criteriaBuilder.like(root.get("itemName"), "%" + keyword.trim() + "%");
                    Predicate p2 = criteriaBuilder.like(root.get("abbrPinyin"), "%" + keyword.trim().toUpperCase() + "%");
                    Predicate p3 = criteriaBuilder.like(root.get("fullPinyin"), "%" + keyword.trim().toLowerCase() + "%");
                    return criteriaBuilder.or(p1, p2, p3);
                } else {
                    return null;
                }
            }
        });
        if (ObjectUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        } else {
            List<TreeEntity> treeList = new ArrayList<>();
            List<String> valueList = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                PatentMedicineEntity item = resultList.get(i);
                TreeEntity tree = new TreeEntity();
                tree.setId(item.getItemId().toString());
                tree.setpId(ItemType.PATENT_MEDICINE + "_" + item.getItemType());
                tree.setLabel(item.getItemName());
                treeList.add(tree);
                valueList.add(item.getItemType());
            }
            List<DictionaryEntity> dictList = dictionaryService.getDictItemByDictKeyAndDictValues(ItemType.PATENT_MEDICINE, valueList);
            if (!ObjectUtils.isEmpty(dictList)) {
                for (int i = dictList.size() - 1; i > -1 ; i--) {
                    DictionaryEntity dict = dictList.get(i);
                    TreeEntity tree = new TreeEntity();
                    tree.setId(ItemType.PATENT_MEDICINE + "_" + dict.getDictValue());
                    tree.setpId(null);
                    tree.setLabel(dict.getDictName());
                    treeList.add(0, tree);
                }
            }
            return treeList;
        }
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception {
        patentMedicineDao.deleteById(id);
        return true;
    }
}
