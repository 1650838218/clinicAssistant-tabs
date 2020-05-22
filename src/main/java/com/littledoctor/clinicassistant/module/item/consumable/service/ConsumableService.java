package com.littledoctor.clinicassistant.module.item.consumable.service;

import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.item.consumable.dao.ConsumableDao;
import com.littledoctor.clinicassistant.module.item.consumable.entity.ConsumableEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 其他耗材 品目
 */
@Service
public class ConsumableService {

    @Autowired
    private ConsumableDao consumableDao;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 查询所有其他耗材
     * @return
     */
    public List<ConsumableEntity> findAll() throws Exception {
        return consumableDao.findAll();
    }

    /**
     * 保存其他耗材
     * @param entity
     * @return
     */
    public ConsumableEntity save(ConsumableEntity entity) throws Exception {
        String jhbz = dictionaryService.getDictNameByDictKeyAndDictValue(DictionaryKey.PUR_ITEM_JHBZ, entity.getPurUnit());
        entity.setPurUnitName(jhbz);
        return consumableDao.saveAndFlush(entity);
    }

    /**
     * 删除其他耗材
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
