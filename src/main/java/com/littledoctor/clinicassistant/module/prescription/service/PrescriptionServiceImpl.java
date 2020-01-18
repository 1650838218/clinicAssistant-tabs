package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetail;
import com.littledoctor.clinicassistant.module.purchase.stock.service.PurStockService;
import com.littledoctor.clinicassistant.module.prescription.dao.RxDetailRepository;
import com.littledoctor.clinicassistant.module.prescription.dao.RxCatalogRepository;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetailVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalog;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:35
 * @Description: 处方管理
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired(required = false)
    private RxCatalogRepository rxCatalogRepository;

    @Autowired(required = false)
    private RxDetailRepository rxDetailRepository;

    @Autowired
    private PurStockService purStockService;

    /**
     * 查询处方目录
     * @return
     */
    @Override
    public List<RxCatalog> queryCatalog() throws Exception {
        return rxCatalogRepository.findAll(Sort.by("catalogId"));
    }

    /**
     * 保存处方目录
     * @param rxCatalog
     * @return
     * @throws Exception
     */
    @Override
    public RxCatalog saveCatalog(RxCatalog rxCatalog) throws Exception {
        return rxCatalog != null ? rxCatalogRepository.saveAndFlush(rxCatalog) : null;
    }

    /**
     * 删除处方目录
     * @param catalogId
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteCatalog(String catalogId) throws Exception {
        if (StringUtils.isNotBlank(catalogId)) {
            rxCatalogRepository.deleteById(Long.parseLong(catalogId));
            rxDetailRepository.deleteByCatalogId(Long.parseLong(catalogId));
            return true;
        }
        return false;
    }

    /**
     * 根据目录ID查询处方
     * @param catalogId
     * @return
     * @throws Exception
     */
    @Override
    public RxDetail findPrescriptionByCatalogId(String catalogId) throws Exception {
        if (StringUtils.isNotBlank(catalogId)) {
            return rxDetailRepository.findByCatalogId(Long.parseLong(catalogId));
        }
        return null;
    }

    /**
     * 根据ID查询处方
     * @param rxId
     * @return
     */
    @Override
    public RxDetail findPrescriptionById(String rxId) throws Exception {
        if (StringUtils.isNotBlank(rxId)) {
            return rxDetailRepository.findById(Long.parseLong(rxId)).get();
        }
        return null;
    }

    /**
     * 保存处方
     * @param rxDetailVo
     * @return
     */
    @Override
    public RxDetail savePrescription(RxDetailVo rxDetailVo) throws Exception {
        if (rxDetailVo != null) {
            RxDetail rxDetail = rxDetailVo.getRxDetail();
            RxCatalog rxCatalog = rxDetailVo.getRxCatalog();
            if (rxDetail != null) {
                if (rxDetail.getRxId() == null && rxDetail.getCatalogId() == null && rxCatalog != null) {// 新增
                    RxCatalog newRxCatalog = rxCatalogRepository.saveAndFlush(rxCatalog);// 保存目录
                    rxDetail.setCatalogId(newRxCatalog.getCatalogId());
                    return rxDetailRepository.saveAndFlush(rxDetail);// 保存处方
                } else { // 修改
                    rxCatalogRepository.updateName(rxDetail.getRxName(), rxDetail.getCatalogId());
                    return rxDetailRepository.saveAndFlush(rxDetail);// 保存处方
                }
            }
        }
        return null;
    }

    /**
     * 根据ID查询处方分类
     * @param catalogId
     * @return
     */
    @Override
    public RxCatalog findCatalogById(Long catalogId) {
        if (catalogId != null) {
            return rxCatalogRepository.findById(catalogId).get();
        }
        return null;
    }

    /**
     * 获取下拉框的option list
     * @param keywords 处方名称或简称
     * @return
     */
    @Override
    public List<SelectOption> getSelectOption(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return rxDetailRepository.getSelectOption();
        } else {
            return rxDetailRepository.getSelectOption(keywords.trim());
        }
    }

    /**
     * 根据处方ID查询处方组成，并将处方组成转换成药材信息
     * 包括：药材名称，品目ID，单价，库存单位，剂量等
     * @param rxId
     * @return
     */
    @Override
    public Map<String, Object> getMedicalByRxId(String rxId) throws Exception {
        if (StringUtils.isNotBlank(rxId)) {
            RxDetail p = this.findPrescriptionById(rxId);
            if (p != null) {
                String component = p.getRxComponent();
                if (StringUtils.isNotBlank(component)) {
                    String[] medicals = component.split("\\s+");// 将处方组成用空格分割成数组
                    if (!ObjectUtils.isEmpty(medicals)) {
                        List<Map<String, Object>> medicalList = new ArrayList<>();// 库存有药
                        List<String> notExist = new ArrayList<>();// 库存不存在的药材
                        for (int i = 0, len = medicals.length; i < len; i++) {
                            String medicalName = medicals[i].split("\\d+")[0];// 提取药材名称
                            String dose = medicals[i].replaceAll("[^0-9]", "");// 提取剂量
                            Map<String, Object> medicalInfo = purStockService.findByName(medicalName);// 根据药材名称查询药材信息
                            if (ObjectUtils.isEmpty(medicalInfo)) {
                                notExist.add(medicalName);// 库存中没有此药材
                            } else {
                                medicalInfo.put("dose", dose);// 剂量
                                medicalList.add(medicalInfo);
                            }
                        }
                        Map<String, Object> result = new HashMap<>();
                        result.put("medicals", medicalList);
                        result.put("notExist", notExist);
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
