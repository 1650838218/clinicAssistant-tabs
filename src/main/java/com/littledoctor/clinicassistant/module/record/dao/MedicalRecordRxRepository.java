package com.littledoctor.clinicassistant.module.record.dao;

import com.littledoctor.clinicassistant.module.record.entity.MedicalRecord;
import com.littledoctor.clinicassistant.module.record.entity.MedicalRecordRx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:27
 * @Description:
 */
public interface MedicalRecordRxRepository extends JpaRepository<MedicalRecordRx, Long>, JpaSpecificationExecutor<MedicalRecordRx> {

}
