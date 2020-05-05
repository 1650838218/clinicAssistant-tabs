package com.littledoctor.clinicassistant.module.item.herbalmedicine.service;

import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.dao.ItemHerbalMedicineDao;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
@Service
public class ItemHerbalMedicineService {

    @Autowired
    private ItemHerbalMedicineDao itemHerbalMedicineDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemHerbalMedicineEntity save(ItemHerbalMedicineEntity entity) throws Exception {
        ItemHerbalMedicineEntity newEntity =  itemHerbalMedicineDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.HERBAL_MEDICINE);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemHerbalMedicineEntity();
    }
}
