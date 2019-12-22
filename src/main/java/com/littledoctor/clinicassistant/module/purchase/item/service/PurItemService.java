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
     * @param purItemId
     * @return
     */
    boolean delete(String purItemId);

    /**
     * 根据ID查询
     * @param purItemId
     * @return
     */
    PurItemEntity getById(String purItemId);

    /**
     * 判断条形码是否不重复，是否不存在
     *
     * @param purItemId
     * @param barcode
     * @return true 不存在  false 已存在，默认false
     */
    boolean notRepeatBarcode(String purItemId, String barcode);

    /**
     * 判断药品名称是否不重复，是否不存在
     *
     * @param purItemId
     * @param purItemName
     * @return true 不存在  false 已存在，默认false
     */
    boolean notRepeatName(String purItemId, String purItemName);

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
     * @param purItemId
     * @return
     */
    boolean isExist(String purItemId);

    /**
     * 获取下拉表格
     * @param keywords
     * @return
     */
    List<PurItemEntity> getCombogrid(String keywords) throws Exception;

    /**
     * 查询品目的id和名称，并装配到TreeEntity中
     * @return
     * @throws Exception
     */
    List<PurItemEntity> findTreeEntity() throws Exception;

    /**
     * 根据ID查询品目
     * @param purItemIdList
     * @return
     * @throws Exception
     */
    List<PurItemEntity> findAllById(List<Long> purItemIdList) throws Exception;
}
