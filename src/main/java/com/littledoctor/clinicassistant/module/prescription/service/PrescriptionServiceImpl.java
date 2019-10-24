package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.common.plugin.SelectOption;
import com.littledoctor.clinicassistant.module.pharmacy.stock.service.StockDetailService;
import com.littledoctor.clinicassistant.module.prescription.dao.PrescriptionRepository;
import com.littledoctor.clinicassistant.module.prescription.dao.RxCatalogueRepository;
import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.PrescriptionVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
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

    @Autowired
    private RxCatalogueRepository rxCatalogueRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private StockDetailService stockDetailService;

    /**
     * 查询处方目录
     * @return
     */
    @Override
    public List<RxCatalogue> queryCatalogue() throws Exception {
        return rxCatalogueRepository.findAll(Sort.by("catalogueId"));
    }

    /**
     * 保存处方目录
     * @param rxCatalogue
     * @return
     * @throws Exception
     */
    @Override
    public RxCatalogue saveCatalogue(RxCatalogue rxCatalogue) throws Exception {
        return rxCatalogue != null ? rxCatalogueRepository.saveAndFlush(rxCatalogue) : null;
    }

    /**
     * 删除处方目录
     * @param catalogueId
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteCatalogue(String catalogueId) throws Exception {
        if (StringUtils.isNotBlank(catalogueId)) {
            rxCatalogueRepository.deleteById(Integer.parseInt(catalogueId));
            prescriptionRepository.deleteByCatalogueId(Integer.parseInt(catalogueId));
            return true;
        }
        return false;
    }

    /**
     * 根据目录ID查询处方
     * @param catalogueId
     * @return
     * @throws Exception
     */
    @Override
    public Prescription findPrescriptionByCatalogueId(String catalogueId) throws Exception {
        if (StringUtils.isNotBlank(catalogueId)) {
            return prescriptionRepository.findByCatalogueId(Integer.parseInt(catalogueId));
        }
        return null;
    }

    /**
     * 根据ID查询处方
     * @param prescriptionId
     * @return
     */
    @Override
    public Prescription findPrescriptionById(String prescriptionId) throws Exception {
        if (StringUtils.isNotBlank(prescriptionId)) {
            return prescriptionRepository.findById(Integer.parseInt(prescriptionId)).get();
        }
        return null;
    }

    /**
     * 保存处方
     * @param prescriptionVo
     * @return
     */
    @Override
    public Prescription savePrescription(PrescriptionVo prescriptionVo) throws Exception {
        if (prescriptionVo != null) {
            Prescription prescription = prescriptionVo.getPrescription();
            RxCatalogue rxCatalogue = prescriptionVo.getRxCatalogue();
            if (prescription != null) {
                if (prescription.getPrescriptionId() == null && prescription.getCatalogueId() == null && rxCatalogue != null) {// 新增
                    RxCatalogue newRxCatalogue = rxCatalogueRepository.saveAndFlush(rxCatalogue);// 保存目录
                    prescription.setCatalogueId(newRxCatalogue.getCatalogueId());
                    return prescriptionRepository.saveAndFlush(prescription);// 保存处方
                } else { // 修改
                    rxCatalogueRepository.updateName(prescription.getPrescriptionName(), prescription.getCatalogueId());
                    return prescriptionRepository.saveAndFlush(prescription);// 保存处方
                }
            }
        }
        return null;
    }

    /**
     * 根据ID查询处方分类
     * @param catalogueId
     * @return
     */
    @Override
    public RxCatalogue findCatalogueById(Integer catalogueId) {
        if (catalogueId != null) {
            return rxCatalogueRepository.findById(catalogueId).get();
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
            return prescriptionRepository.getSelectOption();
        } else {
            return prescriptionRepository.getSelectOption(keywords.trim());
        }
    }

    /**
     * 根据处方ID查询处方组成，并将处方组成转换成药材信息
     * 包括：药材名称，品目ID，单价，库存单位，剂量等
     * @param prescriptionId
     * @return
     */
    @Override
    public Map<String, Object> getMedicalByPrescriptionId(String prescriptionId) throws Exception {
        if (StringUtils.isNotBlank(prescriptionId)) {
            Prescription p = this.findPrescriptionById(prescriptionId);
            if (p != null) {
                String component = p.getPrescriptionComponent();
                if (StringUtils.isNotBlank(component)) {
                    String[] medicals = component.split("\\s+");// 将处方组成用空格分割成数组
                    if (!ObjectUtils.isEmpty(medicals)) {
                        List<Map<String, Object>> medicalList = new ArrayList<>();// 库存有药
                        List<String> notExist = new ArrayList<>();// 库存不存在的药材
                        for (int i = 0, len = medicals.length; i < len; i++) {
                            String medicalName = medicals[i].split("\\d+")[0];// 提取药材名称
                            String dose = medicals[i].replaceAll("[^0-9]", "");// 提取剂量
                            Map<String, Object> medicalInfo = stockDetailService.findByName(medicalName);// 根据药材名称查询药材信息
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
