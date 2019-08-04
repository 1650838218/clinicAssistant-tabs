package com.littledoctor.clinicassistant.module.prescription.service;

import com.littledoctor.clinicassistant.module.prescription.dao.RxCatalogueRepository;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalogue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-03 22:35
 * @Description: 处方管理
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private RxCatalogueRepository rxCatalogueRepository;

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
            return true;
        }
        return false;
    }
}
