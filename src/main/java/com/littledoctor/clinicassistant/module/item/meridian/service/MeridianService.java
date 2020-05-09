package com.littledoctor.clinicassistant.module.item.meridian.service;

import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.meridian.dao.MeridianDao;
import com.littledoctor.clinicassistant.module.item.meridian.entity.MeridianEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 经络 品目
 */
@Service
public class MeridianService {

    @Autowired
    private MeridianDao meridianDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public MeridianEntity save(MeridianEntity entity) throws Exception {
        MeridianEntity newEntity =  meridianDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.MERIDIAN);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new MeridianEntity();
    }
}
