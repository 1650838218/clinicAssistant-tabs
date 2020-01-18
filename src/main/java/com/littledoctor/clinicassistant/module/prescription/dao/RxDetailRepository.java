package com.littledoctor.clinicassistant.module.prescription.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @业务信息: 处方
 * @Filename: RxDetailRepository.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-05   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-08-05   周俊林
 */
public interface RxDetailRepository extends JpaRepository<RxDetail, Long>, JpaSpecificationExecutor<RxDetail> {

    /**
     * 根据目录ID删除处方
      * @param catalogId
     */
    @Modifying
    @Query(value = "delete from RxDetail where catalogId = ?1")
    void deleteByCatalogId(Long catalogId);

    /**
     * 根据目录ID查询处方
     * @param catalogId
     * @return
     */
    RxDetail findByCatalogId(Long catalogId);

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.rxId, t.rxName) from RxDetail t ")
    List<SelectOption> getSelectOption();

    /**
     * 获取selecOption
     * @return
     * @param keywords
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.rxId, t.rxName) " +
            "from RxDetail t where t.rxName like concat('%',?1,'%') or t.rxAbbr like concat('%',?1,'%') ")
    List<SelectOption> getSelectOption(String keywords);
}
