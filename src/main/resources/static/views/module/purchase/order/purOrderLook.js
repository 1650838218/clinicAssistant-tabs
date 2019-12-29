/** 采购单 */
//@ sourceURL=purOrderLook.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
});
layui.use(['form','utils', 'jquery', 'layer', 'table'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var utils = layui.utils;
    var rootMapping = '/purchase/order';
    var itemTableId = 'purorder-table';
    var formId = 'purorder-form';
    form.render();

    // 初始化表格
    var tableConfig = {
        elem: '#' + itemTableId,
        height: 'full-100',
        page: true,
        cols: [[
            {field: 'purOrderDetailId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'purItemName', title: '品目名称'},
            {field: 'batchNumber', title: '批号', width: '13%'},
            {field: 'manufactureDate', title: '生产日期', width: '13%'},
            {field: 'expireDate', title: '有效期至', width: '13%'},
            {field: 'purCount', title: '采购数量', width: '10%',templet: function (d) {
                    return d.purCount + '（' + d.purUnitName + ')';
                }
             },
            // {field: 'purUnitName', title: '采购单位', width: '10%'},
            {field: 'unitPrice', title: '单价(元)', width: '10%',templet: function (d) {
                    return parseFloat(d.unitPrice).toFixed(2);
                }},
            {field: 'totalPrice', title: '总价(元)', width: '10%',templet: function (d) {
                    return parseFloat(d.totalPrice).toFixed(2);
                }}
        ]],
    };

    // 根据ID查询采购单
    function queryPurOrderById(purOrderId) {
        if (utils.isNotNull(purOrderId)) {
            $.getJSON(rootMapping + "/queryById",{purOrderId: purOrderId}, function (purOrder) {
                if (purOrder != null) {
                    purOrder.totalPrice = purOrder.totalPrice.toFixed(2) + ' 元';
                    form.val(formId, purOrder);// 表单赋值
                    form.render();
                    table.render($.extend({},tableConfig,{data:purOrder.purOrderDetails}));// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 解析url中的参数，如果包含采购单id，则自动查询采购单详情
    var search = window.location.search;
    if (search.length > 0) {
        var condition = search.substr(1).split('&');
        for (var i = 0; i < condition.length; i++) {
            var keyVal = condition[i].split('=');
            if (keyVal[0] === 'purOrderId') {
                queryPurOrderById(keyVal[1]);
                break;
            }
        }
    }
});

