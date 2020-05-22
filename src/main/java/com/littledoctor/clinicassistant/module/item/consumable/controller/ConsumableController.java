package com.littledoctor.clinicassistant.module.item.consumable.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.module.item.consumable.entity.ConsumableEntity;
import com.littledoctor.clinicassistant.module.item.consumable.service.ConsumableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2020/5/4
 * @Description: 其他耗材 品目
 */
@RestController
@RequestMapping(value = "/item/consumable")
public class ConsumableController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumableService consumableService;


    /**
     * 查询所有的其他耗材
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public LayuiTableEntity<ConsumableEntity> findAll() {
        try {
            return new LayuiTableEntity<ConsumableEntity>(consumableService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存其他耗材
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ConsumableEntity save(@RequestBody ConsumableEntity entity) {
        try {
            Assert.notNull(entity, Message.PARAMETER_IS_NULL);
            return consumableService.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ConsumableEntity();
    }

    /**
     * 删除其他耗材
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Long id) {
        try {
            Assert.notNull(id, Message.PARAMETER_IS_NULL);
            return consumableService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取下拉框的option list
     * @return
     */
    @RequestMapping(value = "/getSelectOption", method = RequestMethod.GET)
    public List<SelectOption> getSelectOption() {
        try {
            return consumableService.getSelectOption();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
}
