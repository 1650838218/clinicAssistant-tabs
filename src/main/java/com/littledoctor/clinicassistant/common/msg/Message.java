package com.littledoctor.clinicassistant.common.msg;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/20 10:16
 * @Description: 各种提示信息
 */
public interface Message {

    /**
     * 数据表格 返回信息 提示信息
     */
    public static final String[] CONTROLLER_TABLE_MSG = {"暂无数据！","","数据查询异常，请联系管理员！"};

    public static final String PARAMETER_IS_NULL = "入参为空！";

    public static final String QUERY_SUCCESS = "查询成功！";

    public static final String QUERY_FAILED = "查询失败！";

    public static final String QUERY_EXCEPTION = "数据查询异常，请联系管理员！";

    public static final String NO_DATA = "暂无数据！";

    public static final String OPERATE_SUCCESS = "操作成功！";

    public static final String OPERATE_FAILED = "操作失败！";

    public static final String SAVE_SUCCESS = "保存成功！";

    public static final String SAVE_FAILED = "保存失败！";

    public static final String CREATE_SUCCESS = "创建成功！";

    public static final String CREATE_FAILED = "创建失败！";

    public static final String UPDATE_SUCCESS = "更新成功！";

    public static final String UPDATE_FAILED = "更新失败！";

    public static final String DELETE_SUCCESS = "删除成功！";

    public static final String DELETE_FAILED = "删除失败！";

}
