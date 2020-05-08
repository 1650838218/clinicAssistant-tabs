package com.littledoctor.clinicassistant.module.item.prescription.service;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.patentmedicine.dao.ItemPatentMedicineDao;
import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.ItemPatentMedicineEntity;
import com.littledoctor.clinicassistant.module.item.prescription.dao.ItemPrescriptionDao;
import com.littledoctor.clinicassistant.module.item.prescription.entity.ItemPrescriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 方剂 品目
 */
@Service
public class ItemPrescriptionService {

    @Autowired
    private ItemPrescriptionDao itemPrescriptionDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemPrescriptionEntity save(ItemPrescriptionEntity entity) throws Exception {
        ItemPrescriptionEntity newEntity =  itemPrescriptionDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.PRESCRIPTION);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemPrescriptionEntity();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public ItemPrescriptionEntity findById(Long id) {
        if (id != null) {
            return itemPrescriptionDao.findById(id).get();
        }
        return new ItemPrescriptionEntity();
    }

    /**
     * 查询目录
     * @return
     */
    public List<TreeEntity> queryCatalog() {
        return itemPrescriptionDao.queryCatalog();
    }
}
