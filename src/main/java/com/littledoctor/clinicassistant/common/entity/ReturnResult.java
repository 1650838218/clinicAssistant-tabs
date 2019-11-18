package com.littledoctor.clinicassistant.common.entity;

import com.littledoctor.clinicassistant.common.msg.Message;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-10-27 11:35
 * @Description: 返回结果
 */
public class ReturnResult<T> {

    /* 操作成功或失败 */
    private Boolean success;

    /* 提示信息 */
    private String msg;

    /* 返回的实体数据 */
    private Object object;

    /* 返回的列表数据 */
    private List<T> listObj;

    /** 错误日志 */
    private String errorMsg;

    public ReturnResult() {

    }

    public ReturnResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ReturnResult(boolean success, String msg, String errorMsg) {
        this.success = success;
        this.msg = msg;
        this.errorMsg = errorMsg;
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

    public List<T> getListObj() {
        return listObj;
    }

    public void setListObj(List<T> listObj) {
        this.listObj = listObj;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
