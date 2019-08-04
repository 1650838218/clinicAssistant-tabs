package com.littledoctor.clinicassistant.module.prescription.dao;

import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:42
 * @Description: 处方目录
 */
public interface RxCatalogueRepository extends JpaRepository<RxCatalogue, Integer>, JpaSpecificationExecutor<RxCatalogue> {

}
