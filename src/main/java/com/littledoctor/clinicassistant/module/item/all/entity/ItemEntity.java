package com.littledoctor.clinicassistant.module.item.all.entity;

/**
 * 品目管理总表(item_all)
 * 
 * @author 周俊林
 * @version 1.0.0 2020-05-04
 */
public class ItemEntity {

    /** 品目ID */
    private Long itemId;

    /** 品目名称 */
    private String itemName;

    /** 品目分类（1：中草药；2：中成药；3：医疗耗材；4：其他耗材；字典键：CGPMFL） */
    private String itemType;

    private String itemTypeName;

    /** 规格 */
    private String specifications;

    /** 厂家制造商 */
    private String producer;

    /** 进货包装 */
    private String purUnitName;

    public String getPurUnitName() {
        return purUnitName;
    }

    public void setPurUnitName(String purUnitName) {
        this.purUnitName = purUnitName;
    }

    /**
     * 获取品目ID
     * 
     * @return 品目ID
     */
    public Long getItemId() {
        return this.itemId;
    }

    /**
     * 设置品目ID
     * 
     * @param itemId
     *          品目ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取品目名称
     * 
     * @return 品目名称
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 设置品目名称
     * 
     * @param itemName
     *          品目名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取品目分类（1：中草药；2：中成药；3：中药饮片；4：医疗耗材；5：其他耗材；字典键：CGPMFL）
     * 
     * @return 品目分类（1
     */
    public String getItemType() {
        return this.itemType;
    }

    /**
     * 设置品目分类（1：中草药；2：中成药；3：中药饮片；4：医疗耗材；5：其他耗材；字典键：CGPMFL）
     * 
     * @param itemType
     *          品目分类（1
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}