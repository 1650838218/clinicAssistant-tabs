package com.littledoctor.clinicassistant.module.rxdaily.service;

import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.rxdaily.dao.RxDailyRepository;
import com.littledoctor.clinicassistant.module.rxdaily.dao.MedicalRecordRxDetailRepository;
import com.littledoctor.clinicassistant.module.rxdaily.dao.MedicalRecordRxRepository;
import com.littledoctor.clinicassistant.module.rxdaily.dao.SettleAccountRepository;
import com.littledoctor.clinicassistant.module.rxdaily.entity.*;
import com.littledoctor.clinicassistant.module.rxdaily.mapper.RxDailyMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-24 10:26
 * @Description: 病历
 */
@Service
@Transactional
public class RxDailyServiceImpl implements RxDailyService {

    @Autowired
    private RxDailyRepository rxDailyRepository;

    @Autowired
    private MedicalRecordRxRepository medicalRecordRxRepository;

    @Autowired
    private MedicalRecordRxDetailRepository medicalRecordRxDetailRepository ;

    @Autowired(required = false)
    private RxDailyMapper rxDailyMapper;

    @Autowired
    private SettleAccountRepository settleAccountRepository;

    /**
     * 保存病历
     * @param medicalRecordVo
     * @return
     * @throws Exception
     */
    @Override
    public ReturnResult save(MedicalRecordVo medicalRecordVo) throws Exception {
        if (!ObjectUtils.isEmpty(medicalRecordVo)) {
            RxDaily rxDaily = medicalRecordVo.getRxDaily();// 患者信息
            if (!ObjectUtils.isEmpty(rxDaily)) {
                rxDaily.setUpdateTime(new Date());
                rxDaily = rxDailyRepository.saveAndFlush(rxDaily);// 保存患者信息
                List<RxVo> rxVoList = medicalRecordVo.getRxVoList();// 处方信息
                if (!ObjectUtils.isEmpty(rxVoList)) {
                    rxDailyMapper.deleteMedicalRecordRx(rxDaily.getRxDailyId());// 删除旧处方
                    // 插入处方信息
                    for (int i = 0; i < rxVoList.size(); i++) {// 保存处方信息
                        RxVo rxVo = rxVoList.get(i);
                        if (!ObjectUtils.isEmpty(rxVo)) {
                            MedicalRecordRx rx = rxVo.getMedicalRecordRx();
                            List<MedicalRecordRxDetail> detailList = rxVo.getDetailList();
                            rx.setRecordId(rxDaily.getRxDailyId());
                            for (int j = 0; j < detailList.size(); j++) {
                                detailList.get(j).setRecordId(rxDaily.getRxDailyId());
                                detailList.get(j).setRxType(rx.getRxType());
                            }
                            medicalRecordRxRepository.saveAndFlush(rx);// 保存处方
                            medicalRecordRxDetailRepository.saveAll(detailList);// 保存处方具体内容
                        }
                    }
                }
                // 结算信息
                SettleAccount settleAccount = medicalRecordVo.getSettleAccount();
                settleAccount.setRecordId(rxDaily.getRxDailyId());
                settleAccountRepository.saveAndFlush(settleAccount);
                // 返回信息
                ReturnResult result = new ReturnResult(true, Message.SAVE_SUCCESS);
                result.setObject(rxDaily);
                return result;
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
            RxDaily rxDaily = rxDailyRepository.findById(Long.parseLong(recordId)).get();
            vo.setRxDaily(rxDaily);
            if (!ObjectUtils.isEmpty(rxDaily) && !ObjectUtils.isEmpty(rxDaily.getRxType())) {
                String[] types = rxDaily.getRxType().split(",");
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
     * 保存结算信息
     * @param settleAccount
     * @return
     * @throws Exception
     */
    @Override
    public ReturnResult settleAccount(SettleAccount settleAccount) throws Exception {
        if (!ObjectUtils.isEmpty(settleAccount) && !ObjectUtils.isEmpty(settleAccount.getRecordId())) {
            List<SettleAccount> settleAccountList = this.findSettleAccountByRecordId(settleAccount.getRecordId());
            if (!ObjectUtils.isEmpty(settleAccountList)) {
                SettleAccount old = settleAccountList.get(0);
                settleAccount.setSettleAccountId(old.getSettleAccountId());
                settleAccount.setPaymentTime(new Date());
                settleAccount.setPaymentState(2);
                settleAccountRepository.saveAndFlush(settleAccount);
                ReturnResult result = new ReturnResult(true, Message.SAVE_SUCCESS);
                result.setObject(settleAccount);
                return result;
            }
        }
        return new ReturnResult(false, Message.SAVE_FAILED);
    }

    /**
     * 根据病历ID查询结算信息
     * @param recordId
     * @return
     */
    @Override
    public List<SettleAccount> findSettleAccountByRecordId(Long recordId) throws Exception {
        if (!ObjectUtils.isEmpty(recordId)) {
            return settleAccountRepository.findAll(new Specification<SettleAccount>() {
                @Override
                public Predicate toPredicate(Root<SettleAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("recordId"), recordId);
                }
            });
        }
        return new ArrayList<>();
    }

    /**
     * 挂号，保存处方笺
     * @param rxDaily
     * @return
     * @throws Exception
     */
    @Override
    public RxDaily createRegister(RxDaily rxDaily) throws Exception {
        rxDaily.setRxDailyId(null);
        rxDaily.setUpdateTime(new Date());
        rxDaily.setArriveTime(new Date());
        rxDaily.setPaymentState(0);
        return rxDailyRepository.save(rxDaily);
    }

    /**
     * 修改挂号 处方笺
     * @param rxDaily
     * @return
     */
    @Override
    public RxDaily updateRegister(RxDaily rxDaily) throws Exception {
        RxDaily old = rxDailyRepository.findById(rxDaily.getRxDailyId()).get();
        old.setRegisterNumber(rxDaily.getRegisterNumber());
        old.setPatientName(rxDaily.getPatientName());
        old.setPatientSex(rxDaily.getPatientSex());
        old.setPatientAge(rxDaily.getPatientAge());
        old.setPatientAddress(rxDaily.getPatientAddress());
        old.setPatientJob(rxDaily.getPatientJob());
        old.setPatientPhone(rxDaily.getPatientPhone());
        old.setPaymentState(0);
        old.setUpdateTime(new Date());
        return rxDailyRepository.save(old);
    }

    /**
     * 获取下一个号码
     * @return
     * @throws Exception
     */
    @Override
    public int getRegisterNumber() throws Exception {
        return rxDailyRepository.getRegisterNumber();
    }

    /**
     * 查询当天没有结算的挂号单 处方笺
     * @return
     */
    @Override
    public List<RxDaily> getTodayRxDailyMainForNotPayment() throws Exception{
        return rxDailyRepository.findAll(new Specification<RxDaily>() {
            @Override
            public Predicate toPredicate(Root<RxDaily> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("paymentState"), 0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Predicate p2 = criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("arriveTime"), calendar.getTime());
                return criteriaBuilder.and(p1, p2);
            }
        }, new Sort(Sort.Direction.ASC, "registerNumber"));
    }

    /**
     * 删除挂号单
     * @param rxDailyId
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteRegister(Long rxDailyId) throws Exception {
        rxDailyRepository.deleteById(rxDailyId);
        return true;
    }

    /**
     * 根据ID查询挂号单
     * @param rxDailyId
     * @return
     * @throws Exception
     */
    @Override
    public RxDaily getRegisterById(Long rxDailyId) throws Exception {
        return rxDailyRepository.findById(rxDailyId).get();
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
