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
     * @param dictId
     * @return
     */
    @RequestMapping(value = "/deleteByDictId/{dictId}", method = RequestMethod.DELETE)
    public boolean deleteByDictId(@PathVariable("dictId") Long dictId) {
        try {
            Assert.notNull(dictId,"删除数据字典时id不能为空");
            return dictionaryService.deleteByDictId(dictId);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    /**
     * 删除数据字典
     * @param dictKey
     * @return
     */
    @RequestMapping(value = "/deleteByDictKey/{dictKey}", method = RequestMethod.DELETE)
    public boolean deleteByDictKey(@PathVariable("dictKey") String dictKey) {
        try {
            Assert.hasLength(dictKey,"删除数据字典时字典键不能为空");
            return dictionaryService.deleteByDictKey(dictKey);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }


    /**
     * 根据ID查询字典
     * @param dictId
     * @return
     */
    @RequestMapping(value = "/getByDictId", method = RequestMethod.GET)
    public DictionaryEntity getByDictId(Long dictId) {
        try {
            Assert.notNull(dictId, Message.PARAMETER_IS_NULL);
            return dictionaryService.getById(dictId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据字典键查询字典（包含不启用的）
     * @param dictKey
     * @return
     */
    @RequestMapping(value = "/findAllByDictKey", method = RequestMethod.GET)
    public DictionaryVo findAllByDictKey(@RequestParam String dictKey) {
        try {
            Assert.hasLength(dictKey, Message.PARAMETER_IS_NULL);
            return dictionaryService.findAllByDictKey(dictKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new DictionaryVo();
    }

    /**
     * 根据字典键查询字典（只包含启用的）
     * @param dictKey
     * @return
     */
    @RequestMapping(value = "/findUsedByDictKey", method = RequestMethod.GET)
    public DictionaryVo findUsedByDictKey(@RequestParam String dictKey) {
        try {
            Assert.hasLength(dictKey, Message.PARAMETER_IS_NULL);
            return dictionaryService.findUsedByDictKey(dictKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new DictionaryVo();
    }

    /**
     * 根据字典键查询字典项，常用于下拉框
     * @param dictKey
     * @return
     */
    @RequestMapping(value = "/getDictItemByDictKey", method = RequestMethod.GET)
    public List<DictionaryEntity> getDictItemByDictKey(@RequestParam String dictKey) {
        try {
            return dictionaryService.getDictItemByDictKey(dictKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 检查字典名称是否重复
     * @param dictId
     * @param dictName
     * @return false 重复 true 不重复
     */
    @RequestMapping(value = "/repeatDictName", method = RequestMethod.GET)
    public boolean repeatDictName(Long dictId, String dictName) {
        try {
            Assert.notNull(dictName, Message.PARAMETER_IS_NULL);
            return dictionaryService.repeatDictName(dictId, dictName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 检查字典键是否重复
     * @param dictId
     * @param dictKey
     * @return false 重复 true 不重复
     */
    @RequestMapping(value = "/repeatDictKey", method = RequestMethod.GET)
    public boolean repeatDictKey(Long dictId, String dictKey) {
        try {
            Assert.notNull(dictKey, Message.PARAMETER_IS_NULL);
            return dictionaryService.repeatDictKey(dictId, dictKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
