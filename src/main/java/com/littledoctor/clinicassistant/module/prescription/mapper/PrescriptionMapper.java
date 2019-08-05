package com.littledoctor.clinicassistant.module.prescription.mapper;

import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;

public interface PrescriptionMapper {
    int deleteByPrimaryKey(Integer classicsId);

    int insert(Prescription record);

    int insertSelective(Prescription record);

    Prescription selectByPrimaryKey(Integer classicsId);

    int updateByPrimaryKeySelective(Prescription record);

    int updateByPrimaryKey(Prescription record);
}