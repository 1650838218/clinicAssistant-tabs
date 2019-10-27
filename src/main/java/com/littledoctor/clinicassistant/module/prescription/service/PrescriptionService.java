package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.PrescriptionVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;

import java.util.List;
import java.util.Map;

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
     * @param prescriptionVo
     * @return
     */
    Prescription savePrescription(PrescriptionVo prescriptionVo) throws Exception;

    /**
     * 根据ID查询处方分类
     * @param catalogueId
     * @return
     */
    RxCatalogue findCatalogueById(Integer catalogueId);

    /**
     * 获取下拉框的option list
     * @return
     * @param keywords
     */
    List<SelectOption> getSelectOption(String keywords);

    /**
     * 根据处方ID查询处方组成，并将处方组成转换成药材信息
     * 包括：药材名称，品目ID，单价，库存单位，剂量等
     * @param prescriptionId
     * @return
     */
    Map<String, Object> getMedicalByPrescriptionId(String prescriptionId) throws Exception;
}
