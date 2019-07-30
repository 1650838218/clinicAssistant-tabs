package com.littledoctor.clinicassistant.module.pharmacy.warehousingentry.entity;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2019-05-11 18:24
 * @Description: 入库单明细
 */
@Entity
@Table(name = "WAREHOUSING_ENTRY_DETAIL")
public class WarehousingEntryDetail {

    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WAREHOUSING_ENTRY_DETAIL_ID", nullable = false)
    private Integer warehousingEntryDetailId;

    /** 药品清单ID */
    @Column(name = "PHARMACY_ITEM_ID")
    private Integer pharmacyItemId;

    /** 数量，购进的数量 */
    @Column(name = "STOCK_COUNT")
    private Double stockCount;

    /** 零售价 */
    @Column(name = "SELLING_PRICE")
    private Double sellingPrice;

    public Integer getWarehousingEntryDetailId() {
        return warehousingEntryDetailId;
    }

    public void setWarehousingEntryDetailId(Integer warehousingEntryDetailId) {
        this.warehousingEntryDetailId = warehousingEntryDetailId;
    }

    public Integer getPharmacyItemId() {
        return pharmacyItemId;
    }

    public void setPharmacyItemId(Integer pharmacyItemId) {
        this.pharmacyItemId = pharmacyItemId;
    }

    public Double getStockCount() {
        return stockCount;
    }

    public void setStockCount(Double stockCount) {
        this.stockCount = stockCount;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
