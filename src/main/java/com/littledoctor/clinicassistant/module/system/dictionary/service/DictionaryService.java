package com.littledoctor.clinicassistant.module.system.dictionary.service;

import com.littledoctor.clinicassistant.common.constant.Constant;
import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.system.dictionary.dao.DictionaryRepository;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.vo.DictionaryVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
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
@Transactional
public class DictionaryService {

    private Logger log = LoggerFactory.getLogger(DictionaryService.class);

    @Autowired
    private DictionaryRepository dictionaryRepository;

    /**
     * 分页查询
     * @param keyword
     * @param page
     * @return
     */
    public Page<DictionaryEntity> queryPage(String keyword, Pageable page) {
        if (page != null) {
            page = PageRequest.of(page.getPageNumber() - 1,page.getPageSize());
        } else {
            page = PageRequest.of(0, Constant.PAGE_SIZE);
        }
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
    public ReturnResult save(DictionaryVo dictionaryVo) throws Exception {
        if (!ObjectUtils.isEmpty(dictionaryVo)) {
            DictionaryEntity dictType = dictionaryVo.getDictType();
            List<DictionaryEntity> dictItem = dictionaryVo.getDictItem();
            if (!ObjectUtils.isEmpty(dictType) && !ObjectUtils.isEmpty(dictItem)) {
                // 先删除后插入
                this.deleteByDictKey(dictType.getDictKey());
                List<DictionaryEntity> entityList = new ArrayList<>();
                dictType.setDictType(1);
                dictType.setIsUse(1);
                entityList.add(dictType);
                for (int i = 0; i < dictItem.size(); i++) {
                    DictionaryEntity de = dictItem.get(i);
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
    public boolean deleteByDictId(Long dictId) throws Exception {
        dictionaryRepository.deleteById(dictId);
        return true;
    }

    /**
     * 根据字典键删除数据字典
     * @param dictKey
     * @return
     * @throws Exception
     */
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
    public DictionaryEntity getById(Long dictId) throws Exception {
        if (!ObjectUtils.isEmpty(dictId)) {
            return dictionaryRepository.findById(dictId).get();// 根据ID查询字典
        }
        return new DictionaryEntity();
    }

    /**
     * 检查多级字典名称是否重复
     * @param dictId
     * @param dictName
     * @return false 重复 true 不重复
     */
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
     * 根据字典键查询字典（包含所有的）
     * @param dictKey
     * @return
     * @throws Exception
     */
    public DictionaryVo findAllByDictKey(String dictKey) throws Exception {
        if (!StringUtils.isEmpty(dictKey)) {
            List<DictionaryEntity> result = dictionaryRepository.findAllByDictKey(dictKey);
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
     * 根据字典键查询字典（只包含可用的）
     * @param dictKey
     * @return
     */
    public DictionaryVo findUsedByDictKey(String dictKey) throws Exception {
        if (!StringUtils.isEmpty(dictKey)) {
            List<DictionaryEntity> result = dictionaryRepository.findUsedByDictKey(dictKey);
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
    public String getDictNameByDictKeyAndDictValue(String dictKey, String dictValue) throws Exception {
        if (!StringUtils.isEmpty(dictKey) && !StringUtils.isEmpty(dictValue)) {
            return dictionaryRepository.getDictNameByDictKeyAndDictValue(dictKey, dictValue);
        }
        return "";
    }

    /**
     * 根据字典键和真实值查询字典项
     * @param dictKey
     * @param dictValues
     * @return
     * @throws Exception
     */
    public List<DictionaryEntity> getDictItemByDictKeyAndDictValues(String dictKey, List<String> dictValues) throws Exception {
        if (!StringUtils.isEmpty(dictKey) && !ObjectUtils.isEmpty(dictValues)) {
            return dictionaryRepository.getDictItemByDictKeyAndDictValues(dictKey, dictValues);
        }
        return new ArrayList<>();
    }
}
