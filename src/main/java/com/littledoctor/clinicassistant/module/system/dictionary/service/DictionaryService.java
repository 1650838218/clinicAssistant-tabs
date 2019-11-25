package com.littledoctor.clinicassistant.module.system.dictionary.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.vo.DictionaryVo;
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
     * @param keyword
     * @param page
     * @return
     */
    Page<DictionaryEntity> queryPage(String keyword, Pageable page) throws Exception;

    /**
     * 保存数据字典
     * @param dictionary
     * @return
     */
    ReturnResult save(DictionaryVo dictionary) throws Exception;

    /**
     * 删除数据字典
     * @param dictId
     * @return
     */
    boolean deleteByDictId(Long dictId) throws Exception;

    /**
     * 根据id查询字典
     * @param dictId
     * @return
     */
    DictionaryEntity getById(Long dictId) throws Exception;

    /**
     * 根据字典键删除字典
     * @param dictKey
     * @return
     * @throws Exception
     */
    boolean deleteByDictKey(String dictKey) throws Exception;

    /**
     * 检查字典名称是否重复
     * @param dictId
     * @param dictName
     * @return
     */
    boolean repeatDictName(Long dictId, String dictName) throws Exception;

    /**
     * 检查字典键是否重复
     * @param dictId
     * @param dictKey
     * @return
     */
    boolean repeatDictKey(Long dictId, String dictKey) throws Exception;

    /**
     * 根据字典键查询字典（所有的）
     * @param dictKey
     * @return
     */
    DictionaryVo findAllByDictKey(String dictKey) throws Exception;

    /**
     * 根据字典键查询字典（只包含可用的）
     * @param dictKey
     * @return
     */
    DictionaryVo findUsedByDictKey(String dictKey) throws Exception;

    /**
     * 根据字典键查询字典（只包含可用的）
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
