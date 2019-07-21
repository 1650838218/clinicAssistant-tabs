package com.littledoctor.clinicassistant.common.plugin.select;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2019-07-05 20:55
 * @Description: selectC组件可接收的数据结构
 */
public class SelectCOption {

    private int code;

    private List<SelectOption> data;

    public SelectCOption(List<SelectOption> data) {
        if (data == null || data.size() == 0) {
            this.code = 1;
        } else {
            this.code = 0;
        }
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SelectOption> getData() {
        return data;
    }

    public void setData(List<SelectOption> data) {
        this.data = data;
    }
}
