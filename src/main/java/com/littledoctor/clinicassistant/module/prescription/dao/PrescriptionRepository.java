package com.littledoctor.clinicassistant.module.prescription.dao;

import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
    @Query(value = "delete from Prescription a where a.catalogueId = ?1")
    void deleteByCatalogueId(String catalogueId);

    /**
     * 根据目录ID查询处方
     * @param catalogueId
     * @return
     */
    Prescription findByCatalogueId(String catalogueId);
}
