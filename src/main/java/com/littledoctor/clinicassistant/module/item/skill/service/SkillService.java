package com.littledoctor.clinicassistant.module.item.skill.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.item.skill.dao.SkillDao;
import com.littledoctor.clinicassistant.module.item.skill.entity.SkillEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗项目 品目
 */
@Service
public class SkillService {

    @Autowired
    private SkillDao skillDao;

    /**
     * 查询所有医技项目
     * @return
     */
    public List<SkillEntity> findAll() throws Exception {
        return skillDao.findAll();
    }

    /**
     * 保存医技项目
     * @param entity
     * @return
     */
    public SkillEntity save(SkillEntity entity) throws Exception {
        return skillDao.saveAndFlush(entity);
    }

    /**
     * 删除医技项目
     * @param id
     * @return
     */
    public boolean delete(Long id) throws Exception {
        if (id != null) {
            skillDao.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 获取select option list
     * @return
     */
    public List<SelectOption> getSelectOption() throws Exception {
        return skillDao.getSelectOption();
    }
}
