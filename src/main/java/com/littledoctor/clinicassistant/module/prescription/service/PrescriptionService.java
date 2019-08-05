package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:34
 * @Description: 处方管理
 */
public interface PrescriptionService {

    /**
     * 根据目录名称，简称查询目录
     * @return
     */
    List<RxCatalogue> queryCatalogue() throws Exception;

    /**
     * 保存处方目录
     * @param rxCatalogue
     * @return
     */
    RxCatalogue saveCatalogue(RxCatalogue rxCatalogue) throws Exception;

    /**
     * 删除处方目录
     * @param catalogueId
     * @return
     */
    boolean deleteCatalogue(String catalogueId) throws Exception;

    /**
     * 根据目录ID查询处方
     * @param catalogueId
     * @return
     */
    Prescription findPrescriptionByCatalogueId(String catalogueId) throws Exception;

    /**
     * 根据ID查询处方
     * @param prescriptionId
     * @return
     */
    Prescription findPrescriptionById(String prescriptionId) throws Exception;

    /**
     * 保存处方
     * @param prescription
     * @param rxCatalogue
     * @return
     */
    Prescription savePrescription(Prescription prescription, RxCatalogue rxCatalogue) throws Exception;
}
