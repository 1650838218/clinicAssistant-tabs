package com.littledoctor.clinicassistant.module.prescription.dao;

import com.littledoctor.clinicassistant.common.plugin.select.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @业务信息: 处方
 * @Filename: PrescriptionRepository.java
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
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>, JpaSpecificationExecutor<Prescription> {

    /**
     * 根据目录ID删除处方
      * @param catalogueId
     */
    @Modifying
    @Query(value = "delete from Prescription where catalogueId = ?1")
    void deleteByCatalogueId(Integer catalogueId);

    /**
     * 根据目录ID查询处方
     * @param catalogueId
     * @return
     */
    Prescription findByCatalogueId(Integer catalogueId);

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.plugin.select.SelectOption(t.prescriptionId, t.prescriptionName) from Prescription t ")
    List<SelectOption> getSelectOption();

    /**
     * 获取selecOption
     * @return
     * @param keywords
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.plugin.select.SelectOption(t.prescriptionId, t.prescriptionName) " +
            "from Prescription t where t.prescriptionName like concat('%',?1,'%') or t.abbreviation like concat('%',?1,'%') ")
    List<SelectOption> getSelectOption(String keywords);
}
