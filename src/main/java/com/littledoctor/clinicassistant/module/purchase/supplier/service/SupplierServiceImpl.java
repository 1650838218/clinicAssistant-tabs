package com.littledoctor.clinicassistant.module.purchase.supplier.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.supplier.dao.SupplierRepository;
import com.littledoctor.clinicassistant.module.purchase.supplier.entity.SupplierEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 12:58
 * @Description: 供应商
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * 查询所有供应商
     * @return
     */
    @Override
    public List<SupplierEntity> findAll() throws Exception {
        return supplierRepository.findAll();
    }

    /**
     * 保存供应商
     * @param supplierEntity
     * @return
     */
    @Override
    public SupplierEntity save(SupplierEntity supplierEntity) throws Exception {
        return supplierRepository.saveAndFlush(supplierEntity);
    }

    /**
     * 删除供应商
     * @param supplierId
     * @return
     */
    @Override
    public boolean delete(String supplierId) throws Exception {
        if (StringUtils.isNotBlank(supplierId)) {
            supplierRepository.deleteById(Integer.parseInt(supplierId));
            return true;
        }
        return false;
    }

    /**
     * 获取select option list
     * @return
     */
    @Override
    public List<SelectOption> getSelectOption() throws Exception {
        return supplierRepository.getSelectOption();
    }

    /**
     * 根据ID查询供应商
     * @return
     * @throws Exception
     * @param supplierId
     */
    @Override
    public SupplierEntity findById(String supplierId) throws Exception {
        if (StringUtils.isNotBlank(supplierId)) {
            return supplierRepository.findById(Integer.parseInt(supplierId)).get();
        }
        return null;
    }

    /**
     * 分页查询供应商
     * @param keywords
     * @param page
     * @return
     */
    @Override
    public Page<SupplierEntity> queryPage(String keywords, Pageable page) {
        return supplierRepository.findAll(new Specification<SupplierEntity>() {
            @Override
            public Predicate toPredicate(Root<SupplierEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (StringUtils.isNotBlank(keywords)) {
                    return criteriaBuilder.like(root.get("supplierName"), "%" + keywords.toLowerCase() + "%");
                }
                return null;
            }
        }, page);
    }
}
