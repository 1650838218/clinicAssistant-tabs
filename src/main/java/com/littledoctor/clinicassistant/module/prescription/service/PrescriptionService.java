package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetail;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetailVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalog;

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
    List<RxCatalog> queryCatalog() throws Exception;

    /**
     * 保存处方目录
     * @param rxCatalog
     * @return
     */
    RxCatalog saveCatalog(RxCatalog rxCatalog) throws Exception;

    /**
     * 删除处方目录
     * @param catalogId
     * @return
     */
    boolean deleteCatalog(String catalogId) throws Exception;

    /**
     * 根据目录ID查询处方
     * @param catalogId
     * @return
     */
    RxDetail findPrescriptionByCatalogId(String catalogId) throws Exception;

    /**
     * 根据ID查询处方
     * @param rxId
     * @return
     */
    RxDetail findPrescriptionById(String rxId) throws Exception;

    /**
     * 保存处方
     * @param rxDetailVo
     * @return
     */
    RxDetail savePrescription(RxDetailVo rxDetailVo) throws Exception;

    /**
     * 根据ID查询处方分类
     * @param catalogId
     * @return
     */
    RxCatalog findCatalogById(Long catalogId) throws Exception;

    /**
     * 获取下拉框的option list
     * @return
     * @param keywords
     */
    List<SelectOption> getSelectOption(String keywords) throws Exception;

    /**
     * 根据处方ID查询处方组成，并将处方组成转换成药材信息
     * 包括：药材名称，品目ID，单价，库存单位，剂量等
     * @param rxId
     * @return
     */
    Map<String, Object> getMedicalByRxId(String rxId) throws Exception;
}
