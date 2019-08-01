/** 采购单 */
//@ sourceURL=purchaseOrderLook.js
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
    var rootMapping = '/pharmacy/purchaseorder';
    var itemTableId = 'purchaseorder-table';
    var formId = 'purchaseorder-form';
    form.render();

    // 初始化表格
    table.render({
        elem: '#' + itemTableId,
        height: 'full-100',
        cols: [[
            {field: 'purchaseOrderItemId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'pharmacyItemName', title: '药品名称', width: '12%'},
            {field: 'specifications', title: '规格', width: '10%'},
            {field: 'manufacturer', title: '制造商'},
            {field: 'batchNumber', title: '批号', width: '10%'},
            {field: 'manufactureDate', title: '生产日期', width: '10%'},
            {field: 'expireDate', title: '有效期至', width: '10%'},
            {field: 'purchaseCount', title: '数量', width: '7%',templet: function (d) {
                    return parseFloat(d.purchaseCount).toFixed(2);
                }},
            {field: 'purchaseUnitName', title: '单位', width: '7%'},
            {field: 'unitPrice', title: '单价(元)', width: '8%',templet: function (d) {
                    return parseFloat(d.unitPrice).toFixed(2);
                }},
            {field: 'totalPrice', title: '总价(元)', width: '8%',templet: function (d) {
                    return parseFloat(d.totalPrice).toFixed(2);
                }}
        ]],
    });

    // 根据ID查询采购单
    function queryPurchaseOrderById(purchaseOrderId) {
        if (utils.isNotNull(purchaseOrderId)) {
            $.getJSON(rootMapping + "/queryById",{purchaseOrderId: purchaseOrderId}, function (purchaseOrder) {
                if (purchaseOrder != null) {
                    purchaseOrder.totalPrice = purchaseOrder.totalPrice.toFixed(2) + ' 元';
                    form.val('purchaseorder-form', purchaseOrder);// 表单赋值
                    form.render();
                    table.reload(itemTableId,{data:purchaseOrder.purchaseOrderDetails});// 加载采购单明细
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
            if (keyVal[0] === 'purchaseOrderId') {
                queryPurchaseOrderById(keyVal[1]);
                break;
            }
        }
    }
});

