package com.littledoctor.clinicassistant.module.item.skill.service;

import com.littledoctor.clinicassistant.module.item.add.dao.ItemAddDao;
import com.littledoctor.clinicassistant.module.item.add.entity.ItemAllEntity;
import com.littledoctor.clinicassistant.module.item.constant.ItemType;
import com.littledoctor.clinicassistant.module.item.prescription.dao.ItemPrescriptionDao;
import com.littledoctor.clinicassistant.module.item.prescription.entity.ItemPrescriptionEntity;
import com.littledoctor.clinicassistant.module.item.skill.dao.ItemSkillDao;
import com.littledoctor.clinicassistant.module.item.skill.entity.ItemSkillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 医疗项目 品目
 */
@Service
public class ItemSkillService {

    @Autowired
    private ItemSkillDao itemSkillDao;

    @Autowired
    private ItemAddDao itemAddDao;

    /**
     * 保存
     * @param entity
     * @return
     */
    @Transactional
    public ItemSkillEntity save(ItemSkillEntity entity) throws Exception {
        ItemSkillEntity newEntity =  itemSkillDao.saveAndFlush(entity);
        if (newEntity.getItemId() != null) {
            ItemAllEntity iae = new ItemAllEntity();
            iae.setItemId(newEntity.getItemId());
            iae.setItemName(newEntity.getItemName());
            iae.setItemType(ItemType.SKILL);
            itemAddDao.saveAndFlush(iae);
            return newEntity;
        }
        return new ItemSkillEntity();
    }
}
