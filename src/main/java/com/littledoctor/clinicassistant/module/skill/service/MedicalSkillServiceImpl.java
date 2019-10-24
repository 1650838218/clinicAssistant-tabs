package com.littledoctor.clinicassistant.module.skill.service;

import com.littledoctor.clinicassistant.common.plugin.SelectOption;
import com.littledoctor.clinicassistant.module.skill.dao.MedicalSkillRepository;
import com.littledoctor.clinicassistant.module.skill.entity.MedicalSkill;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-08-10 12:43
 * @Description: 医技项目
 */
@Service
public class MedicalSkillServiceImpl implements MedicalSkillService {

    @Autowired
    private MedicalSkillRepository medicalSkillRepository;

    /**
     * 查询所有医技项目
     * @return
     */
    @Override
    public List<MedicalSkill> findAll() throws Exception {
        return medicalSkillRepository.findAll();
    }

    /**
     * 保存医技项目
     * @param medicalSkill
     * @return
     */
    @Override
    public MedicalSkill save(MedicalSkill medicalSkill) throws Exception {
        return medicalSkillRepository.saveAndFlush(medicalSkill);
    }

    /**
     * 删除医技项目
     * @param medicalSkillId
     * @return
     */
    @Override
    public boolean delete(String medicalSkillId) throws Exception {
        if (StringUtils.isNotBlank(medicalSkillId)) {
            medicalSkillRepository.deleteById(Integer.parseInt(medicalSkillId));
            return true;
        }
        return false;
    }

    /**
     * 获取select option list
     * @return
     */
    @Override
    public List<SelectOption> getSelectOption() throws Exception {
        return medicalSkillRepository.getSelectOption();
    }
}
