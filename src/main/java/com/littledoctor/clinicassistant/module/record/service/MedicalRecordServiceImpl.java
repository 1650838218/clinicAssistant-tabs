package com.littledoctor.clinicassistant.module.record.service;

import com.littledoctor.clinicassistant.module.record.dao.MedicalRecordRepository;
import com.littledoctor.clinicassistant.module.record.dao.MedicalRecordRxDetailRepository;
import com.littledoctor.clinicassistant.module.record.dao.MedicalRecordRxRepository;
import com.littledoctor.clinicassistant.module.record.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:26
 * @Description: 病历
 */
@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordRxRepository medicalRecordRxRepository;

    @Autowired
    private MedicalRecordRxDetailRepository medicalRecordRxDetailRepository ;

    /**
     * 保存病历
     * @param medicalRecordVo
     * @return
     * @throws Exception
     */
    @Override
    public  save(MedicalRecordVo medicalRecordVo) throws Exception {
        if (!ObjectUtils.isEmpty(medicalRecordVo)) {
            MedicalRecord medicalRecord = medicalRecordVo.getMedicalRecord();
            if (!ObjectUtils.isEmpty(medicalRecord)) {
                medicalRecord = medicalRecordRepository.saveAndFlush(medicalRecord);
                List<RxVo> rxVoList = medicalRecordVo.getRxVoList();
                if (!ObjectUtils.isEmpty(rxVoList)) {
                    for (int i = 0; i < rxVoList.size(); i++) {
                        RxVo rxVo = rxVoList.get(i);
                        if (!ObjectUtils.isEmpty(rxVo)) {
                            MedicalRecordRx rx = rxVo.getMedicalRecordRx();
                            List<MedicalRecordRxDetail> detailList = rxVo.getDetailList();
                            rx.setRecordId(medicalRecord.getRecordId());
                            for (int j = 0; j < detailList.size(); j++) {
                                detailList.get(j).setRecordId(medicalRecord.getRecordId());
                                detailList.get(j).setRxType(rx.getRxType());
                            }
                            medicalRecordRxRepository.saveAndFlush(rx);
                            medicalRecordRxDetailRepository.saveAll(detailList);
                        }
                    }
                }
            }
        }
        return new MedicalRecordVo();
    }
}
