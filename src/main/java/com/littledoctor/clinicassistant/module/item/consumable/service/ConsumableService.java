package com.littledoctor.clinicassistant.module.item.consumable.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.item.consumable.dao.ConsumableDao;
import com.littledoctor.clinicassistant.module.item.consumable.entity.ConsumableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 腧穴 品目
 */
@Service
public class ConsumableService {

    @Autowired
    private ConsumableDao consumableDao;

    /**
     * 查询所有医技项目
     * @return
     */
    public List<ConsumableEntity> findAll() throws Exception {
        return consumableDao.findAll();
    }

    /**
     * 保存医技项目
     * @param entity
     * @return
     */
    public ConsumableEntity save(ConsumableEntity entity) throws Exception {
        return consumableDao.saveAndFlush(entity);
    }

    /**
     * 删除医技项目
     * @param id
     * @return
     */
    public boolean delete(Long id) throws Exception {
        if (id != null) {
            consumableDao.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 获取select option list
     * @return
     */
    public List<SelectOption> getSelectOption() throws Exception {
        return consumableDao.getSelectOption();
    }
}
