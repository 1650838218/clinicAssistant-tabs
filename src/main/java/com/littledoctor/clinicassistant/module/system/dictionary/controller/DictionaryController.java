package com.littledoctor.clinicassistant.module.system.dictionary.controller;

import com.littledoctor.clinicassistant.common.entity.LayuiTableEntity;
import com.littledoctor.clinicassistant.common.entity.ReturnResult;
import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.util.TreeUtils;
import com.littledoctor.clinicassistant.common.util.ControllerUtils;
import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;
import com.littledoctor.clinicassistant.module.system.dictionary.service.DictionaryService;
import com.littledoctor.clinicassistant.module.system.dictionary.vo.DictionaryVo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Auther: 周俊林
 * @Date: 2018/11/2
 * @Description: 数据字典
 */
@RestController
@RequestMapping(value = "/system/dictionary")
public class DictionaryController {

    private Logger log = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 分页查询
     * @param keyword
     * @param page
     * @return
     */
    @RequestMapping(value = "/queryPage")
    public LayuiTableEntity<DictionaryEntity> queryPage(String keyword, Pageable page) {
        try {
            Page<DictionaryEntity> result = dictionaryService.queryPage(keyword,page);
            return new LayuiTableEntity<DictionaryEntity>(result);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new LayuiTableEntity<DictionaryEntity>();
        }
    }

    /**
     * 保存数据字典
     * @param dictionaryVo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnResult save(@RequestBody DictionaryVo dictionaryVo) {
        try {
            Assert.notNull(dictionaryVo,"保存数据字典时实体不能为空！");
            return dictionaryService.save(dictionaryVo);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ReturnResult(false, Message.SAVE_FAILED, e.getMessage());
        }
    }

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Integer id) {
        try {
            Assert.notNull(id,"删除数据字典时id不能为空");
            return dictionaryService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    /**
     * 根据ID查询字典
     * @param dictionaryId
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public DictionaryType getById(Integer dictionaryId) {
        try {
            Assert.notNull(dictionaryId, Message.PARAMETER_IS_NULL);
            return dictionaryService.getById(dictionaryId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据字典键查询字典，常用于下拉框
     * @param dictTypeKey
     * @return
     */
    @RequestMapping(value = "/getByKey", method = RequestMethod.GET)
    public DictionaryType getByKey(@RequestParam String dictTypeKey) {
        try {
//            Assert.notNull(dictionaryId, Message.PARAMETER_IS_NULL);
            return dictionaryService.getByKey(dictTypeKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new DictionaryType();
    }

    /**
     * 根据字典键查询字典，常用于下拉框
     * @param dictTypeKey
     * @return
     */
    @RequestMapping(value = "/getItemByKey", method = RequestMethod.GET)
    public List<DictionaryItem> getItemByKey(@RequestParam String dictTypeKey) {
        try {
            DictionaryType dictionaryType = dictionaryService.getByKey(dictTypeKey);
            if (dictionaryType != null) return dictionaryType.getDictItem();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 检查字典名称是否重复
     * @param dictTypeId
     * @param dictTypeName
     * @return false 重复 true 不重复
     */
    @RequestMapping(value = "/repeatTypeName", method = RequestMethod.GET)
    public boolean repeatTypeName(String dictTypeId, String dictTypeName) {
        try {
            Assert.notNull(dictTypeName, Message.PARAMETER_IS_NULL);
            return dictionaryService.repeatTypeName(dictTypeId, dictTypeName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 检查字典键是否重复
     * @param dictTypeId
     * @param dictTypeKey
     * @return false 重复 true 不重复
     */
    @RequestMapping(value = "/repeatTypeKey", method = RequestMethod.GET)
    public boolean repeatTypeKey(String dictTypeId, String dictTypeKey) {
        try {
            Assert.notNull(dictTypeKey, Message.PARAMETER_IS_NULL);
            return dictionaryService.repeatTypeKey(dictTypeId, dictTypeKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
