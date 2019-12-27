/** 库存盘点 */
//@ sourceURL=purStockQuery.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    // ,selectC: 'selectC'
});
layui.use(['utils', 'jquery', 'layer', 'table', 'ajax', 'form'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var form = layui.form;
    var rootMapping = '/purchase/stock';
    var stockTableId = 'stock-table';
    var formId = 'query-form';

    // 动态加载品目分类
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="purItemType"]'),
        where: {dictKey: DICT_KEY.PUR_ITEM_CGPMFL},
        tips: '请选择品目分类'
    });

    // 初始化表格
    table.render({
        elem: '#' + stockTableId,
        url: rootMapping + '/queryPage',
        page: true,
        height: 'full-105',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'purItemName', title: '品目名称', width: '14%'},
            {field: 'purItemTypeName', title: '品目分类', width: '8%'},
            {field: 'expireDate', title: '有效期至', width: '10%'},
            {
                field: 'stockCount', title: '库存数量', width: '9%',edit: 'text', templet: function (d) {
                    return parseFloat(d.stockCount).toFixed(2);
                }
            },
            {field: 'stockUnitName', title: '库存单位', width: '8%'},
            {
                field: 'sellingPrice',
                title: '零售价(元)',
                width: '9%',
                edit: 'text',
                templet: function (d) {
                    if (d.sellingPrice) {
                        return parseFloat(d.sellingPrice).toFixed(2);
                    } else {
                        return '';
                    }
                }
            },
            {field: 'stockDetailId', title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '11%', align: 'center'}
        ]]
    });

    // 查询事件
    form.on('submit(submit-btn)', function (data) {
        table.reload(stockTableId,{where: data.field});
        return false;
    });

    // 监听表格编辑事件，当表格内容发生变化时触发
    table.on('edit(' + stockTableId + ')', function (obj) {
        var inputElem = $(this);
        var tdElem = inputElem.closest('td');
        var optionTdElem = tdElem.nextAll('td[data-field="stockDetailId"]');
        optionTdElem.find('a').removeClass('layui-btn-disabled').removeAttr('disabled');// 解禁 保存 按钮
    });

    //监听表格操作列 监听单元格事件
    table.on('tool(' + stockTableId + ')', function (obj) {
        if (obj.event === 'unshelve') {
            unshelveRow(obj);
        } else if (obj.event === 'save') {
            saveRow(obj);
        }
    });

    // 下架
    function unshelveRow(obj) {
        var unshelveBtn = $(obj.tr).find('td[data-field="stockDetailId"] a[lay-event="unshelve"]');
        unshelveBtn.addClass("layui-btn-disabled").attr("disabled",'disabled');
        ajax.postJSON(rootMapping + '/unshelve', obj.data, function (result) {
            if (result) {
                layer.msg('下架成功！');
                table.reload(stockTableId, {});// 刷新列表
            }  else {
                layer.msg('下架失败！');
                unshelveBtn.removeClass("layui-btn-disabled").removeAttr("disabled");
            }
        });
    }

    // 保存行
    function saveRow(obj) {
        var saveBtn = $(obj.tr).find('td[data-field="stockDetailId"] a[lay-event="save"]');
        saveBtn.addClass("layui-btn-disabled").attr("disabled",'disabled');
        ajax.postJSON(rootMapping + '/update', obj.data, function (stockDetail) {
           if (stockDetail != null) {
               layer.msg(MSG.save_success);
           }  else {
               layer.msg(MSG.save_fail);
               saveBtn.removeClass("layui-btn-disabled").removeAttr("disabled");
           }
        });
    }
});

