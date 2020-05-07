package com.littledoctor.clinicassistant.module.item.herbalmedicine.controller;

import com.littledoctor.clinicassistant.common.constant.Constant;
import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.entity.ItemHerbalMedicineEntity;
import com.littledoctor.clinicassistant.module.item.herbalmedicine.service.ItemHerbalMedicineService;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 中药 品目
 */
@Controller
@RequestMapping(value = "/itemHerbalMedicine")
public class ItemHerbalMedicineController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemHerbalMedicineService itemHerbalMedicineService;

    /**
     * 保存
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ItemHerbalMedicineEntity save(@RequestBody ItemHerbalMedicineEntity entity) {
        try {
            return itemHerbalMedicineService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemHerbalMedicineEntity();
    }

    /**
     * 查询
     * @param initial 首字母
     * @param itemType 中药分类
     * @param itemName 中药名称
     * @return
     */
    @RequestMapping(value = "/queryPage", method = RequestMethod.GET)
    public LayuiTableEntity<ItemHerbalMedicineEntity> queryPage(String initial, String itemType, String itemName, Pageable page) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            Page<ItemHerbalMedicineEntity> result = itemHerbalMedicineService.queryPage(initial, itemType, itemName,page);
            return new LayuiTableEntity<ItemHerbalMedicineEntity>(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new LayuiTableEntity<>();
    }
}
