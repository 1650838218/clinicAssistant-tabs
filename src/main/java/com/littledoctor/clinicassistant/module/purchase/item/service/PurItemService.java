package com.littledoctor.clinicassistant.module.purchase.item.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.entity.PurItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 22:48
 * @Description: 采购品目
 */
public interface PurItemService {
    /**
     * 分页查询
     *
     * @param keywords 查询条件 药品名称/全拼/简拼/条形码
     * @param page
     * @return
     */
    Page<PurItemEntity> queryPage(String keywords, Pageable page);

    /**
     * 保存
     * @param purItemEntity
     * @return
     */
    PurItemEntity save(PurItemEntity purItemEntity);

    /**
     * 根据ID删除
     * @param pharmacyItemId
     * @return
     */
    boolean delete(String pharmacyItemId);

    /**
     * 根据ID查询
     * @param pharmacyItemId
     * @return
     */
    PurItemEntity getById(String pharmacyItemId);

    /**
     * 判断条形码是否不重复，是否不存在
     *
     * @param pharmacyItemId
     * @param barcode
     * @return true 不存在  false 已存在，默认false
     */
    boolean notRepeatBarcode(String pharmacyItemId, String barcode);

    /**
     * 判断药品名称是否不重复，是否不存在
     *
     * @param pharmacyItemId
     * @param pharmacyItemName
     * @return true 不存在  false 已存在，默认false
     */
    boolean notRepeatName(String pharmacyItemId, String pharmacyItemName);

    /**
     * 根据名称查询药品清单
     * @param name 可以是药品名称，药品全拼，药品简拼
     * @return
     */
    List<PurItemEntity> queryByName(String name);

    /**
     * 获取下拉框的option list
     * @return
     * @param keywords
     */
    List<SelectOption> getSelectOption(String keywords) throws Exception;

    /**
     * 根据品目ID判断该品目是否存在
     * @param pharmacyItemId
     * @return
     */
    boolean isExist(String pharmacyItemId);

    /**
     * 获取下拉表格
     * @param keywords
     * @return
     */
    List<PurItemEntity> getCombogrid(String keywords) throws Exception;
}