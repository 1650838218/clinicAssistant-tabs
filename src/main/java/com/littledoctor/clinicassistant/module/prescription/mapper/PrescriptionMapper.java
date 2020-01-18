package com.littledoctor.clinicassistant.module.prescription.mapper;

import com.littledoctor.clinicassistant.module.prescription.entity.RxDetail;

public interface PrescriptionMapper {
    int deleteByPrimaryKey(Integer classicsId);

    int insert(RxDetail record);

    int insertSelective(RxDetail record);

    RxDetail selectByPrimaryKey(Integer classicsId);

    int updateByPrimaryKeySelective(RxDetail record);

    int updateByPrimaryKey(RxDetail record);
}