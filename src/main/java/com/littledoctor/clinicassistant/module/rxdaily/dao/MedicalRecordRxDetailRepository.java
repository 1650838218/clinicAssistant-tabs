package com.littledoctor.clinicassistant.module.rxdaily.dao;

import com.littledoctor.clinicassistant.module.rxdaily.entity.MedicalRecordRxDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:27
 * @Description:
 */
public interface MedicalRecordRxDetailRepository extends JpaRepository<MedicalRecordRxDetail, Long>, JpaSpecificationExecutor<MedicalRecordRxDetail> {

}
