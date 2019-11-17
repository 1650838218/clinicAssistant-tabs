package com.littledoctor.clinicassistant.module.system.dictionary.dao;

import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/11/1 21:19
 * @Description:
 */
public interface DictionaryRepository extends JpaRepository<DictionaryEntity, Long>, JpaSpecificationExecutor<DictionaryEntity> {

    /**
     * 根据字典键查询字典
     * @param dictKey
     * @return
     */
    @Query(value = "select t from DictionaryEntity t where t.dictKey = ?1 and t.isValid = 1")
    List<DictionaryEntity> getByKey(String dictKey);
}
