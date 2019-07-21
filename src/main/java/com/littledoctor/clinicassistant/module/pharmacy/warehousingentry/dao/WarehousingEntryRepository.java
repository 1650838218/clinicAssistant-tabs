package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.dao;

import com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.entity.WarehousingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-13 15:29
 * @Description: 入库单
 */
@Repository
public interface WarehousingEntryRepository extends JpaRepository<WarehousingEntry, Integer>, JpaSpecificationExecutor<WarehousingEntry> {
}
