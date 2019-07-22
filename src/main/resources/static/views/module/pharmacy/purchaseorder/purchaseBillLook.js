/** 采购单 */
//@ sourceURL=purchaseBillLook.js
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
            {field: 'purchaseBillItemId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'goodsName', title: '药品名称', width: '20%'},
            {field: 'specifications', title: '规格', width: '15%'},
            {field: 'manufacturer', title: '制造商'},
            {field: 'count', title: '数量'},
            {field: 'countUnitName', title: '单位'},
            {field: 'unitPrice', title: '单价(元)'},
            {field: 'totalPrice', title: '总价(元)'},
        ]],
    });

    // 根据ID查询采购单
    function queryPurchaseBillById(purchaseBillId) {
        if (utils.isNotNull(purchaseBillId)) {
            $.getJSON(rootMapping + "/queryById",{purchaseBillId: purchaseBillId, type: 'EAGER'}, function (purchaseBill) {
                if (purchaseBill != null) {
                    purchaseBill.totalPrice = purchaseBill.totalPrice + '元';
                    form.val('purchaseorder-form', purchaseBill);// 表单赋值
                    form.render();
                    table.reload(itemTableId,{data:purchaseBill.purchaseBillItems});// 加载采购单明细
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
            if (keyVal[0] === 'purchaseBillId') {
                queryPurchaseBillById(keyVal[1]);
                break;
            }
        }
    }
});

