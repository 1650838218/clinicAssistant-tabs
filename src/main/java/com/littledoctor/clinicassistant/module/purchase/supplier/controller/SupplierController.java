package com.littledoctor.clinicassistant.module.purchase.supplier.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import com.littledoctor.clinicassistant.module.purchase.supplier.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 12:56
 * @Description: 供应商
 */
@RestController
@RequestMapping(value = "/purchase/supplier")
public class SupplierController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SupplierService supplierService;

    /**
     * 分页查询供应商
     * @param keywords
     * @param page
     * @return
     */
    @RequestMapping(value = "/queryPage")
    public LayuiTableEntity<SupplierEntity> queryPage(String keywords, Pageable page) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            Page<SupplierEntity> result = supplierService.queryPage(keywords,page);
            return new LayuiTableEntity<SupplierEntity>(result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 查询所有的供货商
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public LayuiTableEntity<SupplierEntity> findAll() {
        try {
            return new LayuiTableEntity<SupplierEntity>(supplierService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 保存供货商
     * @param supplierEntity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SupplierEntity save(@RequestBody SupplierEntity supplierEntity) {
        try {
            Assert.notNull(supplierEntity, Message.PARAMETER_IS_NULL);
            return supplierService.save(supplierEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除供应商
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/delete/{supplierId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("supplierId") String supplierId) {
        try {
            Assert.notNull(supplierId, Message.PARAMETER_IS_NULL);
            return supplierService.delete(supplierId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据ID查询供应商
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public SupplierEntity findById(String supplierId) {
        try {
            Assert.notNull(supplierId, Message.PARAMETER_IS_NULL);
            return supplierService.findById(supplierId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取下拉框的option list
     * @return
     */
    @RequestMapping(value = "/getSelectOption", method = RequestMethod.GET)
    public List<SelectOption> getSelectOption() {
        try {
            return supplierService.getSelectOption();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
