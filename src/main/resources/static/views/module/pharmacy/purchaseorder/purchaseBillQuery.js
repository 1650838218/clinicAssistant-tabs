// 采购单查询

//@ sourceURL=purchaseBillQuery.js

layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    // ,selectC: 'selectC'
});
layui.use(['utils', 'jquery', 'layer', 'table', 'ajax', 'laydate', 'form'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var laydate = layui.laydate;
    var form = layui.form;
    var rootMappint = '/pharmacy/purchaseorder';
    var purchaseBillTableId = 'purchaseorder-table';
    var formId = 'query-form';

    // 动态加载供应商
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="supplierId"]'),
        tips: '请选择供应商',
        url:'/pharmacy/supplier/getSelectOption',
        realValueName: 'realValue',
        displayValueName: 'displayValue',
    });
    // 动态加载日期控件
    laydate.render({
        elem: '#' + formId + ' input[name="purchaseBillDate"]',
        range: true
    });
    console.log($('#' + formId).serialize());
    console.log($('#' + formId).serializeArray());

    // 初始化表格
    table.render({
        elem: '#' + purchaseBillTableId,
        url: rootMappint + '/queryPage',
        page: true,
        height: 'full-105',
        // where: $('#' + formId).serializeObject(),
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'purchaseBillId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'purchaseBillCode', title: '单号', width: '17%'},
            {field: 'purchaseBillDate', title: '日期', width: '10%'},
            {field: 'supplierName', title: '供应商'},
            {field: 'supplierPhone', title: '电话', width: '12%'},
            {field: 'totalPrice', title: '金额（元）', width: '10%'},
            {title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '20%', align: 'center'}
        ]],
        done: function (res, curr, count) {
        }
    });

    // 查询事件
    form.on('submit(submit-btn)', function (data) {
        table.reload(purchaseBillTableId,{where: data.field});
        return false;
    });

    //监听表格操作列
    table.on('tool(' + purchaseBillTableId + ')', function (obj) {
        if (obj.event === 'look') {
            lookRow(obj);
        } else if (obj.event === 'update') {
            updateRow(obj);
        } else if (obj.event === 'delete') {
            deleteRow(obj);
        } else if (obj.event === 'warehousingEntry') {
            warehousingEntryRow(obj);// 入库
        }
    });

    // 查看详情
    function lookRow(obj) {
        var height = $('.center-panel').height();
        var width = $('.center-panel').width();
        var index = layer.open({
            type: 2,
            area: [width+'px', height+'px'],
            title: false,
            fixed: false, //不固定
            content: '/views/module/pharmacy/purchaseorder/purchaseBillLook.html?purchaseBillId='+obj.data.purchaseBillId,
        });
        layer.full(index);
    }

    // 删除行
    function deleteRow(obj) {
        // 此处的删除是真删除
        layer.confirm(MSG.delete_confirm + '该采购单吗？', {icon: 0}, function (index) {
            var purchaseBillId = obj.data.purchaseBillId;
            if (utils.isNotNull(purchaseBillId)) {
                ajax.delete(rootMappint + '/delete/' + purchaseBillId, function (success) {
                    if (success) {
                        layer.msg(MSG.delete_success);
                        table.reload(purchaseBillTableId, {});
                    } else {
                        layer.msg(MSG.delete_fail);
                    }
                });
            }
            layer.close(index);
        });
    }

    // 更新行
    function updateRow(obj) {
        var height = $('.center-panel').height();
        var width = $('.center-panel').width();
        var index = layer.open({
            type: 2,
            area: [width+'px', height+'px'],
            title: false,
            fixed: false, //不固定
            content: '/views/module/pharmacy/purchaseorder/purchaseOrderForm.html?purchaseBillId='+obj.data.purchaseBillId,
        });
        layer.full(index);
    }

    // 入库
    function warehousingEntryRow(obj) {
        var height = $('.center-panel').height();
        var width = $('.center-panel').width();
        var index = layer.open({
            type: 2,
            area: [width+'px', height+'px'],
            title: false,
            fixed: false, //不固定
            content: '/views/module/pharmacy/purchaseorder/purchaseOrderForm.html?purchaseBillId='+obj.data.purchaseBillId,
        });
        layer.full(index);
    }
});