package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.module.prescription.dao.PrescriptionRepository;
import com.littledoctor.clinicassistant.module.prescription.dao.RxCatalogueRepository;
import com.littledoctor.clinicassistant.module.prescription.entity.Prescription;
import com.littledoctor.clinicassistant.module.prescription.entity.PrescriptionVo;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
            return prescriptionRepository.findByCatalogueId(catalogueId);
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
}
