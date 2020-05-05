package com.littledoctor.clinicassistant.module.item.medicalsupply.service;

import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.dao.ItemHerbalMedicineDao;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import com.littledoctor.clinicassistant.module.item.medicalsupply.dao.ItemMedicalSupplyDao;
import com.littledoctor.clinicassistant.module.item.medicalsupply.entity.ItemMedicalSupplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗用品 品目
 */
@Service
public class ItemMedicalSupplyService {

    @Autowired
    private ItemMedicalSupplyDao itemMedicalSupplyDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemMedicalSupplyEntity save(ItemMedicalSupplyEntity entity) throws Exception {
        ItemMedicalSupplyEntity newEntity =  itemMedicalSupplyDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.MEDICAL_SUPPLY);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemMedicalSupplyEntity();
    }
}
