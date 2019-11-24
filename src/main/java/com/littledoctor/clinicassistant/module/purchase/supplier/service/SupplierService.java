package com.littledoctor.clinicassistant.module.purchase.supplier.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 12:58
 * @Description: 供应商
 */
public interface SupplierService {
    /**
     * 查询所有供应商
     * @return
     */
    List<SupplierEntity> findAll() throws Exception;

    /**
     * 保存供应商
     * @param supplierEntity
     * @return
     */
    SupplierEntity save(SupplierEntity supplierEntity) throws Exception;

    /**
     * 删除供应商
     * @param supplierId
     * @return
     */
    boolean delete(String supplierId) throws Exception;

    /**
     * 获取select option list
     * @return
     */
    List<SelectOption> getSelectOption() throws Exception;

    /**
     * 根据ID查询供应商
     * @return
     * @param supplierId
     */
    SupplierEntity findById(String supplierId) throws Exception;

    /**
     * 分页查询供应商
     * @param keywords
     * @param page
     * @return
     */
    Page<SupplierEntity> queryPage(String keywords, Pageable page);
}
