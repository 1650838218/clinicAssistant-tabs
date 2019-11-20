package com.littledoctor.clinicassistant.module.system.dictionary.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.entity.TreeNodeType;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.system.dictionary.dao.DictionaryRepository;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.vo.DictionaryVo;
import com.littledoctor.clinicassistant.module.system.menu.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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

    /**
     * 分页查询
     * @param keyword
     * @param page
     * @return
     */
    @Override
    public Page<DictionaryEntity> queryPage(String keyword, Pageable page) {
        return dictionaryRepository.findAll(new Specification<DictionaryEntity>() {
            @Override
            public Predicate toPredicate(Root<DictionaryEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("dictType"), 1));
                if (StringUtils.isNotBlank(keyword)) {
                    Predicate preDictName = criteriaBuilder.like(root.get("dictName"), "%" + keyword.trim() + "%");
                    Predicate preDictKey = criteriaBuilder.like(root.get("dictKey"), "%" + keyword.trim() + "%");
                    list.add(criteriaBuilder.or(preDictName,preDictKey));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        }, page);
    }

    /**
     * 保存数据字典
     * @param dictionaryVo
     * @return
     */
    @Override
    public ReturnResult save(DictionaryVo dictionaryVo) throws Exception {
        if (!ObjectUtils.isEmpty(dictionaryVo)) {
            DictionaryEntity dictType = dictionaryVo.getDictType();
            List<DictionaryEntity> dictItem = dictionaryVo.getDictItem();
            if (!ObjectUtils.isEmpty(dictType) && !ObjectUtils.isEmpty(dictItem)) {
                // 先删除后插入
                this.deleteByDictKey(dictType.getDictKey());
                List<DictionaryEntity> entityList = new ArrayList<>();
                dictType.setDictType(1);
                dictType.setIsValid(1);
                entityList.add(dictType);
                for (int i = 0; i < dictItem.size(); i++) {
                    DictionaryEntity de = dictItem.get(i);
                    de.setIsValid(1);
                    de.setDictType(2);
                    de.setDictKey(dictType.getDictKey());
                    entityList.add(de);
                }
                List<DictionaryEntity> result = dictionaryRepository.saveAll(entityList);
                ReturnResult<DictionaryEntity> returnResult = new ReturnResult(true, Message.SAVE_SUCCESS);
                returnResult.setListObj(result);
                return returnResult;
            }
        }
        return new ReturnResult(false, Message.SAVE_FAILED);
    }

    /**
     * 根据ID删除数据字典
     * @param dictId
     * @return
     */
    @Override
    public boolean delete(Long dictId) throws Exception {
        dictionaryRepository.deleteById(dictId);
        return true;
    }

    /**
     * 根据字典键删除数据字典
     * @param dictKey
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteByDictKey(String dictKey) throws Exception {
        dictionaryRepository.deleteByDictKey(dictKey);
        return true;
    }

    /**
     * 根据ID查询字典
     * @param dictId
     * @return
     * @throws Exception
     */
    @Override
    public DictionaryVo getById(Long dictId) throws Exception {
        if (!ObjectUtils.isEmpty(dictId)) {
            DictionaryEntity de = dictionaryRepository.findById(dictId).get();// 根据ID查询字典
            if (!ObjectUtils.isEmpty(de)) {
                return this.getByDictKey(de.getDictKey());
            }
        }
        return new DictionaryVo();
    }

    /**
     * 检查多级字典名称是否重复
     * @param dictId
     * @param dictName
     * @return false 重复 true 不重复
     */
    @Override
    public boolean repeatDictName(Long dictId, String dictName) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(dictName)) {
            return dictionaryRepository.count(new Specification<DictionaryEntity>() {
                @Override
                public Predicate toPredicate(Root<DictionaryEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("dictName"), dictName));
                    if (!ObjectUtils.isEmpty(dictId)) {
                        list.add(criteriaBuilder.notEqual(root.get("dictId"), dictId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 检查多级字典键是否重复
     * @param dictId
     * @param dictKey
     * @return false 重复 true 不重复
     */
    @Override
    public boolean repeatDictKey(Long dictId, String dictKey) {
        if (StringUtils.isNotBlank(dictKey)) {
            return dictionaryRepository.count(new Specification<DictionaryEntity>() {
                @Override
                public Predicate toPredicate(Root<DictionaryEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("dictKey"), dictKey));
                    if (!ObjectUtils.isEmpty(dictId)) {
                        list.add(criteriaBuilder.notEqual(root.get("dictId"), dictId));
                    }
                    return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                }
            }) <= 0;
        }
        return false;
    }

    /**
     * 根据字典键查询字典，常用于下拉框
     * @param dictKey
     * @return
     */
    @Override
    public DictionaryVo getByDictKey(String dictKey) throws Exception {
        if (!StringUtils.isEmpty(dictKey)) {
            List<DictionaryEntity> result = dictionaryRepository.getByDictKey(dictKey);
            if (!ObjectUtils.isEmpty(result)) {
                DictionaryVo dvo = new DictionaryVo();
                dvo.setDictItem(new ArrayList<>());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getDictType() == 1) {
                        dvo.setDictType(result.get(i));
                    } else {
                        dvo.getDictItem().add(result.get(i));
                    }
                }
                return dvo;
            }
        }
        return new DictionaryVo();
    }

    /**
     * 根据字典键查询字典项
     * @param dictKey
     * @return
     */
    @Override
    public List<DictionaryEntity> getDictItemByDictKey(String dictKey) throws Exception {
        if (!StringUtils.isEmpty(dictKey)) {
            return dictionaryRepository.getDictItemByDictKey(dictKey);
        }
        return new ArrayList<>();
    }

    /**
     * 根据字典键查询字典
     * @param dictKey
     * @return
     */
    @Override
    public Map<String, String> getItemMapByKey(String dictKey) throws Exception {
        if (!StringUtils.isEmpty(dictKey)) {
            List<DictionaryEntity> items = this.getDictItemByDictKey(dictKey);
            if (items != null && items.size() > 0) {
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < items.size(); i++) {
                    map.put(items.get(i).getDictValue(), items.get(i).getDictName());
                }
                return map;
            }
        }
        return new HashMap<>();
    }

    /**
     * 根据字典键和真实值查询显示值
     * @param dictKey
     * @param dictValue
     * @return
     * @throws Exception
     */
    @Override
    public String getDictNameByDictKeyAndDictValue(String dictKey, String dictValue) throws Exception {
        if (!StringUtils.isEmpty(dictKey) && !StringUtils.isEmpty(dictValue)) {
            return dictionaryRepository.getDictNameByDictKeyAndDictValue(dictKey, dictValue);
        }
        return "";
    }
}
