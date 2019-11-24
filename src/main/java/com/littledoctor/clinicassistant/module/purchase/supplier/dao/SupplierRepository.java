package com.littledoctor.clinicassistant.module.purchase.supplier.dao;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 12:57
 * @Description: 供应商
 */
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer>, JpaSpecificationExecutor<SupplierEntity> {

    /**
     * 获取selecOption
     * @return
     */
    @Query(value = "select new com.littledoctor.clinicassistant.common.entity.SelectOption(t.supplierId, t.supplierName) from SupplierEntity t")
    List<SelectOption> getSelectOption();
}
