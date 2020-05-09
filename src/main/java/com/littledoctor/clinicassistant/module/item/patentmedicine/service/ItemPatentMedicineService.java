package com.littledoctor.clinicassistant.module.item.patentmedicine.service;

import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.patentmedicine.dao.ItemPatentMedicineDao;
import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.ItemPatentMedicineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 成药 品目
 */
@Service
public class ItemPatentMedicineService {

    @Autowired
    private ItemPatentMedicineDao itemPatentMedicineDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemPatentMedicineEntity save(ItemPatentMedicineEntity entity) throws Exception {
        ItemPatentMedicineEntity newEntity =  itemPatentMedicineDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.PATENT_MEDICINE);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemPatentMedicineEntity();
    }
}
