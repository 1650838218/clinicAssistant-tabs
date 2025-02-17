package com.littledoctor.clinicassistant.module.system.dictionary.vo;

import com.littledoctor.clinicassistant.module.system.dictionary.entity.DictionaryEntity;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-11-17 17:00
 * @Description: 字典vo类
 */
public class DictionaryVo {

    /** 字典类型 */
    private DictionaryEntity dictType;

    /** 字典项 */
    private List<DictionaryEntity> dictItem;

    public DictionaryEntity getDictType() {
        return dictType;
    }

    public void setDictType(DictionaryEntity dictType) {
        this.dictType = dictType;
    }

    public List<DictionaryEntity> getDictItem() {
        return dictItem;
    }

    public void setDictItem(List<DictionaryEntity> dictItem) {
        this.dictItem = dictItem;
    }
}
