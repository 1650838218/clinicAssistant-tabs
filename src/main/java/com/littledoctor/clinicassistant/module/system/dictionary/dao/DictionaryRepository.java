package com.littledoctor.clinicassistant.module.system.dictionary.dao;

import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/11/1 21:19
 * @Description:
 */
public interface DictionaryRepository extends JpaRepository<DictionaryEntity, Long>, JpaSpecificationExecutor<DictionaryEntity> {

    /**
     * 根据字典键查询字典(只包含可用的)
     * @param dictKey
     * @return
     */
    @Query(value = "select t from DictionaryEntity t where t.dictKey = ?1 and t.isUse = 1 order by t.dictOrder")
    List<DictionaryEntity> findUsedByDictKey(String dictKey);

    /**
     * 根据字典键查询字典(只包含可用的)
     * @param dictKey
     * @return
     */
    @Query(value = "select t from DictionaryEntity t where t.dictKey = ?1 order by t.dictOrder")
    List<DictionaryEntity> findAllByDictKey(String dictKey);

    /**
     * 根据字典键删除字典
     * @param dictKey
     * @return
     */
    @Modifying
    @Query(value = "delete from DictionaryEntity t where t.dictKey = ?1")
    int deleteByDictKey(String dictKey);

    /**
     * 根据字典键查询字典项
     * @param dictKey
     * @return
     */
    @Query(value = "select t from DictionaryEntity t where t.dictKey = ?1 and t.isUse = 1 and t.dictType = 2 order by t.dictOrder")
    List<DictionaryEntity> getDictItemByDictKey(String dictKey);

    /**
     * 查询字典显示值
     * @param dictKey
     * @param dictValue
     * @return
     */
    @Query(value = "select t.dictName from DictionaryEntity t where t.dictKey = ?1 and t.dictValue = ?2 and t.dictType = 2")
    String getDictNameByDictKeyAndDictValue(String dictKey, String dictValue);
}
