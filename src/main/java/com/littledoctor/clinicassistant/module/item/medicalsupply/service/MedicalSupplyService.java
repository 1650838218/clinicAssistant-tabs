package com.littledoctor.clinicassistant.module.item.medicalsupply.service;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.module.item.medicalsupply.dao.MedicalSupplyDao;
import com.littledoctor.clinicassistant.module.item.medicalsupply.entity.MedicalSupplyEntity;
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
 * @Description: 医疗用品 品目
 */
@Service
public class MedicalSupplyService {

    @Autowired
    private MedicalSupplyDao medicalSupplyDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public MedicalSupplyEntity save(MedicalSupplyEntity entity) throws Exception {
        return  medicalSupplyDao.saveAndFlush(entity);
    }

    /**
     * 判断名称是否不重复，是否不存在
     * @param itemId
     * @param itemName
     * @return true 不存在  false 已存在，默认false
     */
    public boolean notRepeatName(String itemId, String itemName) throws Exception {
        if (StringUtils.isNotBlank(itemName) ) {
            return medicalSupplyDao.count(new Specification<MedicalSupplyEntity>() {
                @Override
                public Predicate toPredicate(Root<MedicalSupplyEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
    public MedicalSupplyEntity findById(Long id) throws Exception {
        if (id != null) {
            return medicalSupplyDao.findById(id).get();
        }
        return new MedicalSupplyEntity();
    }

    /**
     * 查询目录
     * @param keyword
     * @return
     */
    public List<TreeEntity> queryCatalog(String keyword) throws Exception {
        List<MedicalSupplyEntity> resultList = medicalSupplyDao.findAll(new Specification<MedicalSupplyEntity>() {
            @Override
            public Predicate toPredicate(Root<MedicalSupplyEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(keyword.trim())) {
                    Predicate p1 = criteriaBuilder.like(root.get("itemName"), "%" + keyword.trim() + "%");
                    Predicate p2 = criteriaBuilder.like(root.get("abbrPinyin"), keyword.trim().toUpperCase() + "%");
                    Predicate p3 = criteriaBuilder.like(root.get("fullPinyin"), keyword.trim().toLowerCase() + "%");
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
            for (int i = 0; i < resultList.size(); i++) {
                MedicalSupplyEntity item = resultList.get(i);
                TreeEntity tree = new TreeEntity();
                tree.setId(item.getItemId().toString());
                tree.setpId(null);
                tree.setLabel(item.getItemName());
                treeList.add(tree);
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
        medicalSupplyDao.deleteById(id);
        return true;
    }
}
