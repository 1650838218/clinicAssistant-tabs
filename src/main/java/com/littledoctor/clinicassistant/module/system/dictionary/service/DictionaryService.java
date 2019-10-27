package com.littledoctor.clinicassistant.module.system.dictionary.service;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryItem;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2018/11/2
 * @Description: 数据字典
 */
public interface DictionaryService {
    /**
     * 分页查询
     * @param code
     * @param text
     * @param page
     * @return
     */
    Page<DictionaryType> queryPage(String code, String text, Pageable page);

    /**
     * 保存数据字典
     * @param dictionaryType
     * @return
     */
    DictionaryType save(DictionaryType dictionaryType) throws Exception;

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    boolean delete(Integer id) throws Exception;

    /**
     * 获取字典树
     * @return
     */
    List<TreeEntity> findTreeEntity() throws Exception;

    /**
     * 根据id查询字典
     * @param dictionaryId
     * @return
     */
    DictionaryType getById(Integer dictionaryId) throws Exception;

    /**
     * 检查字典名称是否重复
     * @param dictTypeId
     * @param dictTypeName
     * @return
     */
    boolean repeatTypeName(String dictTypeId, String dictTypeName);

    /**
     * 检查字典键是否重复
     * @param dictTypeId
     * @param dictTypeKey
     * @return
     */
    boolean repeatTypeKey(String dictTypeId, String dictTypeKey);

    /**
     * 根据字典键查询字典，常用于下拉框
     * @param dictTypeKey
     * @return
     */
    DictionaryType getByKey(String dictTypeKey) throws Exception;

    /**
     * 根据字典键查询字典
     * @param dictTypeKey
     * @return
     */
    List<DictionaryItem> getItemListByKey(String dictTypeKey) throws Exception;

    /**
     * 根据字典键查询字典
     * @param dictTypeKey
     * @return
     */
    Map<String, String> getItemMapByKey(String dictTypeKey) throws Exception;

    /**
     * 根据字典键和真实值，查询显示值
     * @param dictTypeKey
     * @param dictItemValue
     * @return
     */
    String queryItemName(String dictTypeKey, Integer dictItemValue) throws Exception;
}
