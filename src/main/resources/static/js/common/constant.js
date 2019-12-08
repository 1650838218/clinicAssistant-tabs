/*js常量*/

/**
 * button名称
 * @type {{}}
 */
var BTN = {
    add: '添加',
    create: '新建',
    edit: '编辑',
    delete: '删除',
    download: '下载',
    upload: '上传',
    save: '保存',
    save_and_create: '保存并新建',
    cancel: '取消',
    reset: '重置',
    close: '关闭',
    operation: '操作'
};

var TABLE_COLUMN = {
    numbers: '序号',
    operation: '操作'
}

/**
 * 提示信息
 * @type
 */
var MSG = {
    system_exception: '系统异常，请联系系统管理员！',
    select_one: '请先选择一条记录！',
    save_success: '保存成功！',
    save_fail: '保存失败！',
    delete_confirm: '确认删除',
    delete_success: '删除成功！',
    delete_fail: '删除失败！',
    query_success: '查询成功！',
    query_fail: '查询失败！'
};

/**
 * layui icon
 * @type
 */
var LAYER_ICON = {
    warning: 0,
    success: 1,
    error: 2,
    question: 3
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

/** 字典键 */
var DICT_KEY = {
    PUR_ITEM_CGPMFL : 'CGPMFL' // 采购品目分类
    ,PUR_ITEM_JHBZ : 'JHBZ' // 进货包装
    ,PUR_ITEM_LSDW : 'LSDW' // 零售单位
}

//扩展datagrid:动态添加删除editor
/*$.extend($.fn.datagrid.methods, {
    addEditor : function(jq, param) {
        if (param instanceof Array) {
            $.each(param, function(index, item) {
                var e = $(jq).datagrid('getColumnOption', item.field);
                e.editor = item.editor;
            });
        } else {
            var e = $(jq).datagrid('getColumnOption', param.field);
            e.editor = param.editor;
        }
    },
    removeEditor : function(jq, param) {
        if (param instanceof Array) {
            $.each(param, function(index, item) {
                var e = $(jq).datagrid('getColumnOption', item);
                e.editor = {};
            });
        } else {
            var e = $(jq).datagrid('getColumnOption', param);
            e.editor = {};
        }
    }
});*/

/*
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if(o[this.name]) {
            if(!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}*/
