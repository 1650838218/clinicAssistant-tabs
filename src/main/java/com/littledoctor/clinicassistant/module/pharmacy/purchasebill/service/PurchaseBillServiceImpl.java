package com.littledoctor.clinicassistant.module.pharmacy.purchasebill.service;

import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.dao.PurchaseBillOTMRepository;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.dao.PurchaseBillRepository;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBill;
import com.littledoctor.clinicassistant.module.pharmacy.purchasebill.entity.PurchaseBillOTM;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.entity.Supplier;
import com.littledoctor.clinicassistant.module.pharmacy.supplier.service.SupplierService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-04 16:42
 * @Description: 采购单
 */
@Service
public class PurchaseBillServiceImpl implements PurchaseBillService {

    @Autowired
    private PurchaseBillOTMRepository purchaseBillOTMRepository;

    @Autowired
    private PurchaseBillRepository purchaseBillRepository;

    @Autowired
    private SupplierService supplierService;

    /**
     * 分页查询订单
     * @param page
     * @param purchaseBillCode
     * @param purchaseBillDate
     * @param supplierId
     * @return
     * @throws Exception
     */
    @Override
    public Page<PurchaseBill> queryPage(Pageable page, String purchaseBillCode, String purchaseBillDate, String supplierId) throws Exception {
        Page<PurchaseBill> purchaseBillPage = purchaseBillRepository.findAll(new Specification<PurchaseBill>() {
            @Override
            public Predicate toPredicate(Root<PurchaseBill> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (StringUtils.isNotBlank(purchaseBillCode)) {
                    predicateList.add(criteriaBuilder.equal(root.get("purchaseBillCode"), purchaseBillCode));
                }
                if (StringUtils.isNotBlank(purchaseBillDate)) {
                    String[] dates = purchaseBillDate.split(" - ");
                    if (dates != null && dates.length == 2) {
                        predicateList.add(criteriaBuilder.between(root.get("purchaseBillDate"), dates[0], dates[1]));
                    }
                }
                if (StringUtils.isNotBlank(supplierId)) {
                    predicateList.add(criteriaBuilder.equal(root.get("supplierId"), supplierId));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }, page);
        if (purchaseBillPage.getContent() != null && purchaseBillPage.getContent().size() > 0) {
            List<Supplier> supplierList = supplierService.findAll();
            if (supplierList != null && supplierList.size() > 0) {
                for (int i = 0; i < purchaseBillPage.getContent().size(); i++) {
                    PurchaseBill pb = purchaseBillPage.getContent().get(i);
                    for (int j = 0; j < supplierList.size(); j++) {
                        Supplier supplier = supplierList.get(j);
                        if (pb.getSupplierId().equals(supplier.getSupplierId())) {
                            pb.setSupplierName(supplier.getSupplierName());
                        }
                    }
                }
            }
        }
        return purchaseBillPage;
    }

    /**
     * 保存采购单
     * @param purchaseBillOTM
     * @return
     */
    @Override
    public PurchaseBillOTM save(PurchaseBillOTM purchaseBillOTM) {
        purchaseBillOTM.setCreateTiem(new Date());
        purchaseBillOTM.setWarehousingEntry(false);
        purchaseBillOTM.setUpdateTime(new Date());
        return purchaseBillOTMRepository.saveAndFlush(purchaseBillOTM);
    }

    /**
     * 根据采购单ID查询采购单
     * @param purchaseBillId
     * @return
     */
    @Override
    public PurchaseBillOTM queryById(String purchaseBillId) {
        return purchaseBillOTMRepository.findById(Integer.parseInt(purchaseBillId)).get();
    }

    /**
     * 删除采购单
     * @param purchaseBillId
     * @return
     */
    @Override
    public boolean delete(String purchaseBillId) {
        if (StringUtils.isNotBlank(purchaseBillId)) {
            purchaseBillOTMRepository.deleteById(Integer.parseInt(purchaseBillId));
            return true;
        }
        return false;
    }
}
