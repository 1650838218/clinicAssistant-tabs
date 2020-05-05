package com.littledoctor.clinicassistant.module.purchase.item.service;

import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/19 22:48
 * @Description: 品目管理
 */
public interface ItemService {
    /**
     * 分页查询
     *
     * @param keywords 查询条件 药品名称/全拼/简拼/条形码
     * @param page
     * @return
     */
    Page<ItemEntity> queryPage(String keywords, Pageable page);

    /**
     * 保存
     * @param itemEntity
     * @return
     */
    ItemEntity save(ItemEntity itemEntity);

    /**
     * 根据ID删除
     * @param itemId
     * @return
     */
    boolean delete(String itemId);

    /**
     * 根据ID查询
     * @param itemId
     * @return
     */
    ItemEntity getById(String itemId);

    /**
     * 判断条形码是否不重复，是否不存在
     *
     * @param itemId
     * @param barcode
     * @return true 不存在  false 已存在，默认false
     */
    boolean notRepeatBarcode(String itemId, String barcode);

    /**
     * 判断药品名称是否不重复，是否不存在
     *
     * @param itemId
     * @param itemName
     * @return true 不存在  false 已存在，默认false
     */
    boolean notRepeatName(String itemId, String itemName);

    /**
     * 根据名称查询药品清单
     * @param name 可以是药品名称，药品全拼，药品简拼
     * @return
     */
    List<ItemEntity> queryByName(String name);

    /**
     * 获取下拉框的option list
     * @return
     * @param keywords
     */
    List<SelectOption> getSelectOption(String keywords) throws Exception;

    /**
     * 根据品目ID判断该品目是否存在
     * @param itemId
     * @return
     */
    boolean isExist(String itemId);

    /**
     * 获取下拉表格
     * @param keywords
     * @return
     */
    List<ItemEntity> getCombogrid(String keywords) throws Exception;

    /**
     * 查询品目的id和名称，并装配到TreeEntity中
     * @return
     * @throws Exception
     */
    List<ItemEntity> findTreeEntity() throws Exception;

    /**
     * 根据ID查询品目
     * @param purItemIdList
     * @return
     * @throws Exception
     */
    List<ItemEntity> findAllById(List<Long> purItemIdList) throws Exception;
}
