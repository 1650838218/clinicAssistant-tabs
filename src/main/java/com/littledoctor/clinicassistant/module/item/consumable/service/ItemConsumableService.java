package com.littledoctor.clinicassistant.module.item.consumable.service;

import com.littledoctor.clinicassistant.module.item.acupoint.dao.ItemAcupointDao;
import com.littledoctor.clinicassistant.module.item.acupoint.entity.ItemAcupointEntity;
import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.consumable.dao.ItemConsumableDao;
import com.littledoctor.clinicassistant.module.item.consumable.entity.ItemConsumableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 腧穴 品目
 */
@Service
public class ItemConsumableService {

    @Autowired
    private ItemConsumableDao itemConsumableDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemConsumableEntity save(ItemConsumableEntity entity) throws Exception {
        ItemConsumableEntity newEntity =  itemConsumableDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.CONSUMABLE);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemConsumableEntity();
    }
}
