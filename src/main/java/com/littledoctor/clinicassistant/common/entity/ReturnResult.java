package com.littledoctor.clinicassistant.common.entity;

import com.littledoctor.clinicassistant.common.msg.Message;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-10-27 11:35
 * @Description: 返回结果
 */
public class ReturnResult {

    /* 操作成功或失败 */
    private Boolean success;

    /* 提示信息 */
    private String msg;

    /* 返回的实体数据 */
    private Object object;

    /* 返回的列表数据 */
    private List<Object> listObj;

    public ReturnResult() {

    }

    public ReturnResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Object> getListObj() {
        return listObj;
    }

    public void setListObj(List<Object> listObj) {
        this.listObj = listObj;
    }
}
