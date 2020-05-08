package com.littledoctor.clinicassistant.module.item.prescription.controller;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.module.item.patentmedicine.entity.ItemPatentMedicineEntity;
import com.littledoctor.clinicassistant.module.item.patentmedicine.service.ItemPatentMedicineService;
import com.littledoctor.clinicassistant.module.item.prescription.entity.ItemPrescriptionEntity;
import com.littledoctor.clinicassistant.module.item.prescription.service.ItemPrescriptionService;
import com.littledoctor.clinicassistant.module.prescription.entity.RxCatalog;
import com.littledoctor.clinicassistant.module.prescription.entity.RxDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 方剂 品目
 */
@Controller
@RequestMapping(value = "/item/prescription")
public class ItemPrescriptionController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemPrescriptionService itemPrescriptionService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemPrescriptionEntity save(@RequestBody ItemPrescriptionEntity entity) {
        try {
            return itemPrescriptionService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemPrescriptionEntity();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ItemPrescriptionEntity findById(@RequestParam Long id) {
        try {
            return itemPrescriptionService.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemPrescriptionEntity();
    }

    /**
     * 查询目录
     * @return
     */
    @RequestMapping(value = "/queryCatalog", method = RequestMethod.GET)
    public List<TreeEntity> queryCatalog() {
        try {
            return itemPrescriptionService.queryCatalog();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
