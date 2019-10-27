package com.littledoctor.clinicassistant.module.record.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.record.dao.MedicalRecordRepository;
import com.littledoctor.clinicassistant.module.record.dao.MedicalRecordRxDetailRepository;
import com.littledoctor.clinicassistant.module.record.dao.MedicalRecordRxRepository;
import com.littledoctor.clinicassistant.module.record.entity.*;
import com.littledoctor.clinicassistant.module.record.mapper.MedicalRecordMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired(required = false)
    private MedicalRecordMapper medicalRecordMapper;

    /**
     * 保存病历
     * @param medicalRecordVo
     * @return
     * @throws Exception
     */
    @Override
    public ReturnResult save(MedicalRecordVo medicalRecordVo) throws Exception {
        if (!ObjectUtils.isEmpty(medicalRecordVo)) {
            MedicalRecord medicalRecord = medicalRecordVo.getMedicalRecord();// 患者信息
            if (!ObjectUtils.isEmpty(medicalRecord)) {
                medicalRecord.setPaymentState(1);
//                medicalRecord.setCreateTime(new Date());
                medicalRecord = medicalRecordRepository.saveAndFlush(medicalRecord);// 保存患者信息
                List<RxVo> rxVoList = medicalRecordVo.getRxVoList();// 处方信息
                if (!ObjectUtils.isEmpty(rxVoList)) {
                    medicalRecordMapper.deleteMedicalRecordRx(medicalRecord.getRecordId());// 删除旧处方
                    // 插入处方信息
                    for (int i = 0; i < rxVoList.size(); i++) {// 保存处方信息
                        RxVo rxVo = rxVoList.get(i);
                        if (!ObjectUtils.isEmpty(rxVo)) {
                            MedicalRecordRx rx = rxVo.getMedicalRecordRx();
                            List<MedicalRecordRxDetail> detailList = rxVo.getDetailList();
                            rx.setRecordId(medicalRecord.getRecordId());
                            for (int j = 0; j < detailList.size(); j++) {
                                detailList.get(j).setRecordId(medicalRecord.getRecordId());
                                detailList.get(j).setRxType(rx.getRxType());
                            }
                            medicalRecordRxRepository.saveAndFlush(rx);// 保存处方
                            medicalRecordRxDetailRepository.saveAll(detailList);// 保存处方具体内容
                        }
                    }
                    ReturnResult result = new ReturnResult(true, Message.SAVE_SUCCESS);
                    result.setObject(medicalRecord);
                    return result;
                }
            }
        }
        return new ReturnResult(false, Message.SAVE_FAILED);
    }

    /**
     * 根据ID查询 病历
     * @param recordId
     * @return
     * @throws Exception
     */
    @Override
    public MedicalRecordVo findById(String recordId) throws Exception {
        if (StringUtils.isNotBlank(recordId)) {
            MedicalRecordVo vo = new MedicalRecordVo();
            // 查询患者信息
            MedicalRecord medicalRecord = medicalRecordRepository.findById(Long.parseLong(recordId)).get();
            vo.setMedicalRecord(medicalRecord);
            if (!ObjectUtils.isEmpty(medicalRecord) && !ObjectUtils.isEmpty(medicalRecord.getPrescriptionType())) {
                String[] types = medicalRecord.getPrescriptionType().split(",");
                List<RxVo> list = new ArrayList<>();
                for (int i = 0; i < types.length; i++) {
                    list.add(findRxVo(recordId, types[i]));// 查询处方
                }
                vo.setRxVoList(list);
            }
            return vo;
        }
        return new MedicalRecordVo();
    }

    /**
     * 根据病历ID和处方类型查询处方
     * @param recordId
     * @param type
     * @return
     */
    private RxVo findRxVo(String recordId, String type) {
        if (StringUtils.isNotBlank(recordId) && StringUtils.isNotBlank(type)) {
            // 查询处方
            List<MedicalRecordRx> rxList = medicalRecordRxRepository.findAll(new Specification<MedicalRecordRx>() {
                @Override
                public Predicate toPredicate(Root<MedicalRecordRx> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    Predicate p1 = criteriaBuilder.equal(root.get("recordId"), Long.parseLong(recordId));
                    Predicate p2 = criteriaBuilder.equal(root.get("rxType"), Integer.parseInt(type));
                    return criteriaBuilder.and(p1,p2);
                }
            });
            // 查询处方详情
            List<MedicalRecordRxDetail> rxDetailList = medicalRecordRxDetailRepository.findAll(new Specification<MedicalRecordRxDetail>() {
                @Override
                public Predicate toPredicate(Root<MedicalRecordRxDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    Predicate p1 = criteriaBuilder.equal(root.get("recordId"), Long.parseLong(recordId));
                    Predicate p2 = criteriaBuilder.equal(root.get("rxType"), Integer.parseInt(type));
                    return criteriaBuilder.and(p1,p2);
                }
            });
            RxVo rxVo = new RxVo();
            if (!ObjectUtils.isEmpty(rxList)) rxVo.setMedicalRecordRx(rxList.get(0));
            rxVo.setDetailList(rxDetailList);
            return rxVo;
        }
        return new RxVo();
    }
}
