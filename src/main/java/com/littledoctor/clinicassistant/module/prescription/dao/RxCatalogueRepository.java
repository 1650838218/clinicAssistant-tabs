package com.littledoctor.clinicassistant.module.prescription.dao;

import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:42
 * @Description: 处方目录
 */
public interface RxCatalogueRepository extends JpaRepository<RxCatalogue, Integer>, JpaSpecificationExecutor<RxCatalogue> {

    /**
     * 修改目录名称
     * @param prescriptionName
     * @param catalogueId
     * @return
     */
    @Modifying
    @Query(value = "update RxCatalogue set catalogueName = ?1 where catalogueId = ?2")
    int updateName(String prescriptionName, Integer catalogueId);
}
