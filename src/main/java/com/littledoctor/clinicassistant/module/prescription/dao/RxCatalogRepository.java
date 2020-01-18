package com.littledoctor.clinicassistant.module.prescription.dao;

import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:42
 * @Description: 处方目录
 */
public interface RxCatalogRepository extends JpaRepository<RxCatalog, Long>, JpaSpecificationExecutor<RxCatalog> {

    /**
     * 修改目录名称
     * @param catalogName
     * @param catalogId
     * @return
     */
    @Modifying
    @Query(value = "update RxCatalog set catalogName = ?1 where catalogId = ?2")
    int updateName(String catalogName, Long catalogId);
}
