package com.littledoctor.clinicassistant.module.purchase.item.controller;

import com.littledoctor.clinicassistant.common.constant.Constant;
import com.littledoctor.clinicassistant.common.constant.DictionaryKey;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.SelectOption;
import com.littledoctor.clinicassistant.module.purchase.item.entity.ItemEntity;
import com.littledoctor.clinicassistant.module.purchase.item.service.ItemService;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/18 22:31
 * @Description: 品目guanli
 */
//@RestController
//@RequestMapping(value = "/item")
public class OldItemController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 保存
     * @param itemEntity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ItemEntity save(@RequestBody ItemEntity itemEntity) {
        try {
            Assert.notNull(itemEntity, Message.PARAMETER_IS_NULL);
            return itemService.save(itemEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemEntity();
    }

    /**
     * 判断条形码是否不重复，是否不存在
     * @param itemId
     * @param barcode
     * @return true 不存在  false 已存在，默认false
     */
    @RequestMapping(value = "/notRepeatBarcode", method = RequestMethod.GET)
    public boolean notRepeatBarcode(String itemId, @RequestParam String barcode) {
        try {
            return itemService.notRepeatBarcode(itemId, barcode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 判断品目名称是否不重复，是否不存在
     * @param itemId
     * @param purItemName
     * @return true 不存在  false 已存在，默认false
     */
    @RequestMapping(value = "/notRepeatName", method = RequestMethod.GET)
    public boolean notRepeatName(String itemId, @RequestParam String purItemName) {
        try {
            return itemService.notRepeatName(itemId, purItemName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 分页查询
     * @param keywords 查询条件 药品名称/全拼/简拼/条形码
     * @param page
     * @return
     */
    @RequestMapping(value = "/queryPage")
    public LayuiTableEntity<ItemEntity> queryPage(String keywords, Pageable page) {
        try {
            if (page.getPageNumber() != 0) page = PageRequest.of(page.getPageNumber() - 1, page.getPageSize());
            Page<ItemEntity> result = itemService.queryPage(keywords,page);
            return new LayuiTableEntity<ItemEntity>(result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return new LayuiTableEntity<>();
    }

    /**
     * 查询所有品目目录
     * @return
     */
    @RequestMapping(value = "/queryTree", method = RequestMethod.GET)
    public Map<String, Object> queryTree() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("data", new ArrayList<>());
        try {
            // 查询采购品目分类
            List<DictionaryEntity> dictItems = dictionaryService.getDictItemByDictKey(DictionaryKey.ITEM_PMFL);
            if (!ObjectUtils.isEmpty(dictItems)) {
                // 查询所有的品目
                List<ItemEntity> purItemList = itemService.findTreeEntity();
                if (!ObjectUtils.isEmpty(purItemList)) {
                    Map<String, Integer> indexMap = new HashMap<>();
                    List<TreeEntity> result = new ArrayList<>();
                    for (int i = 0; i < dictItems.size(); i++) {
                        TreeEntity te = new TreeEntity();
                        te.setId("dictVal_" + dictItems.get(i).getDictValue());
                        te.setLabel(dictItems.get(i).getDictName());
                        te.setpId(Constant.ROOT_NODE_ID);
                        te.setChildren(new ArrayList<>());
                        result.add(te);
                        // key dictValue, value: 索引
                        indexMap.put(dictItems.get(i).getDictValue(), i);
                    }
                    // 遍历品目
                    for (int i = 0; i < purItemList.size(); i++) {
                        String key = purItemList.get(i).getItemType();// 品目分类
                        if (indexMap.containsKey(key)) { // 根据 品目分类 找到 字典
                            Integer index = indexMap.get(key);
                            TreeEntity te = new TreeEntity();
                            te.setpId("dictVal_" + key);
                            te.setId(String.valueOf(purItemList.get(i).getItemId()));
                            te.setLabel(purItemList.get(i).getItemName());
                            result.get(index).getChildren().add(te);
                        }
                    }
                    // 设置数量
                    for (int i = 0; i < result.size(); i++) {
                        int childrenSize = result.get(i).getChildren().size();
                        if (childrenSize > 0) {
                            result.get(i).setLabel(result.get(i).getLabel() + "（" + childrenSize + "）");
                        }
                    }
                    map.put("data", result);
                }
            }
        } catch (Exception e) {
            map.put("code", "1");
            map.put("data", null);
            log.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 根据ID删除
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable(value = "itemId") String itemId) {
        try {
            Assert.hasLength(itemId, Message.PARAMETER_IS_NULL);
            return itemService.delete(itemId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据ID查询
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ItemEntity getById(@RequestParam String itemId) {
        try {
            return itemService.getById(itemId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ItemEntity();
    }

    /**
     * 根据名称查询采购品目
     * @param name 可以是药品名称，药品全拼，药品简拼
     * @return
     */
    @RequestMapping(value = "/queryByName", method = RequestMethod.GET)
    public List<ItemEntity> queryByName(@RequestParam String name) {
        try {
//            Assert.hasLength(name, Message.PARAMETER_IS_NULL);
            return itemService.queryByName(name);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取下拉框的option list
     * @return
     */
    @RequestMapping(value = "/getSelectOption", method = RequestMethod.GET)
    public List<SelectOption> getSelectOption(@RequestParam(value = "q", required = false) String keywords) {
        try {
            return itemService.getSelectOption(keywords);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取下拉表格的list
     * @return
     */
    @RequestMapping(value = "/getCombogrid", method = RequestMethod.GET)
    public List<ItemEntity> getCombogrid(@RequestParam(value = "q", required = false) String keywords) {
        try {
            return itemService.getCombogrid(keywords);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 根据品目ID判断该品目是否存在
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/isExist", method = RequestMethod.GET)
    public boolean isExist(@RequestParam(value = "") String itemId) {
        try {
            return itemService.isExist(itemId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
