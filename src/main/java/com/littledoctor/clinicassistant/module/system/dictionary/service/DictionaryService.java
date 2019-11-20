package com.littledoctor.clinicassistant.module.system.dictionary.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.vo.DictionaryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Dictionary;
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
     * @param keyword
     * @param page
     * @return
     */
    Page<DictionaryEntity> queryPage(String keyword, Pageable page);

    /**
     * 保存数据字典
     * @param dictionary
     * @return
     */
    ReturnResult save(DictionaryVo dictionary) throws Exception;

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    boolean delete(Long dictId) throws Exception;

    /**
     * 根据id查询字典
     * @param dictId
     * @return
     */
    DictionaryVo getById(Long dictId) throws Exception;

    boolean deleteByDictKey(String dictKey) throws Exception;

    /**
     * 检查字典名称是否重复
     * @param dictId
     * @param dictName
     * @return
     */
    boolean repeatDictName(Long dictId, String dictName);

    /**
     * 检查字典键是否重复
     * @param dictId
     * @param dictKey
     * @return
     */
    boolean repeatDictKey(Long dictId, String dictKey);

    /**
     * 根据字典键查询字典，常用于下拉框
     * @param dictKey
     * @return
     */
    DictionaryVo getByDictKey(String dictKey) throws Exception;

    /**
     * 根据字典键查询字典
     * @param dictKey
     * @return
     */
    List<DictionaryEntity> getDictItemByDictKey(String dictKey) throws Exception;

    /**
     * 根据字典键查询字典
     * @param dictKey
     * @return
     */
    Map<String, String> getItemMapByKey(String dictKey) throws Exception;

    /**
     * 根据字典键和真实值，查询显示值
     * @param dictKey
     * @param dictValue
     * @return
     */
    String getDictNameByDictKeyAndDictValue(String dictKey, String dictValue) throws Exception;
}
