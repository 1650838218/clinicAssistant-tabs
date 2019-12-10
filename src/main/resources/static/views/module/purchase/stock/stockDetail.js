/** 采购单 */
//@ sourceURL=stockDetail.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['form','utils', 'jquery', 'layer', 'table', 'ajax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var laydate = layui.laydate;
    var rootMapping = '/purchase/stock';
    var itemTableId = 'stockdetail-table';
    var formId = 'stockdetail-form';
    form.render();

    // 初始化表格
    table.render({
        elem: '#' + itemTableId,
        id: itemTableId,
        height: 'full-145',
        cols: [[
            {field: 'stockDetailId', title: TABLE_COLUMN.numbers, type: 'numbers',fixed: 'left'},
            {field: 'pharmacyItemId', title: '药品名称', width: '180',fixed: 'left', templet: function (d) {
                    return d.pharmacyItemName;
                }},
            {field: 'specifications', title: '规格', width: '150'},
            {field: 'manufacturer', title: '制造商', width: '200'},
            {field: 'batchNumber', title: '批号', width: '100'},
            {field: 'manufactureDate', title: '生产日期', width: '120'},
            {field: 'expireDate', title: '有效期至', width: '120'},
            {field: 'purchaseCount', title: '采购数量', width: '100',fixed: 'right',templet: function (d) {
                    return parseFloat(d.purchaseCount).toFixed(2);
                }},
            {field: 'purchaseUnitName', title: '采购单位', width: '90',fixed: 'right'},
            {field: 'unitPrice', title: '单价(元)', width: '100',fixed: 'right',templet: function (d) {
                    return parseFloat(d.unitPrice).toFixed(2);
                }},
            {field: 'stockCount', title: '库存数量', width: '100',fixed: 'right', templet: function (d) {
                    return parseFloat(d.stockCount).toFixed(2);
                }},
            {field: 'stockUnitName', title: '库存单位', width: '90',fixed: 'right'},
            {
                field: 'sellingPrice',
                title: '零售价(元)',
                width: '100',
                fixed: 'right',
                edit: 'text',
                templet: function (d) {
                    if (d.sellingPrice) {
                        return parseFloat(d.sellingPrice).toFixed(2);
                    } else {
                        return '';
                    }
                }
            }
        ]],
    });

    // 明细表格校验
    function validateGrid() {
        var tableData = table.cache[itemTableId];
        for (var i = 0, len = tableData.leng; i < len; i++) {
            if (!utils.isNotNull(tableData[i].sellingPrice)) {
                layer.alert('零售价不能为空，请补充完整！', {icon: LAYER_ICON.error});
                return false;
            }
        }
        return true;
    }

    // 根据ID查询采购单
    function queryPurchaseOrderById(purchaseOrderId) {
        if (utils.isNotNull(purchaseOrderId)) {
            $.getJSON("/purchase/order/queryById",{purchaseOrderId: purchaseOrderId}, function (purchaseOrder) {
                if (purchaseOrder != null) {
                    purchaseOrder.totalPrice = purchaseOrder.totalPrice.toFixed(2) + ' 元';
                    form.val('stockdetail-form', purchaseOrder);// 表单赋值
                    form.render();
                    table.reload(itemTableId,{data:purchaseOrder.purchaseOrderDetails});// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 保存入库
    function saveStockDetail(obj, stockDetails) {
        ajax.postJSON(rootMapping + '/save', stockDetails, function (stockDetails) {
            if (stockDetails != null) {
                // queryStockEntryById(bill.stockEntryId);// 根据ID查询入库单，并赋值
                layer.msg('入库成功！',{time:2000}, function () {
                    $('button[lay-filter="close-btn"]').click();// 关闭当前页面，刷新采购单的状态
                });
            } else {
                layer.msg('入库失败！');
            }
            utils.btnEnabled($(obj.elem));
        }, $(obj.elem));
    }

    /**
     * 保存入库
     * @param data
     * @returns {boolean}
     */
    form.on('submit(submit-btn)', function(obj){
        utils.btnDisabled($(obj.elem));
        if (validateGrid()) {
            var stockDetails = table.cache[itemTableId];// 获取表格数据
            var purchaseOrderId = $('#' + formId).find('input[name="purchaseOrderId"]').val();
            // console.log(billItems);
            for (var i = 0, len = stockDetails.length; i < len; i++) {
                stockDetails[i].purchaseOrderId = purchaseOrderId;
                stockDetails[i].stockState = 1;
            }
            saveStockDetail(obj, stockDetails);
        } else {
            utils.btnEnabled($(obj.elem));
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消按钮点击事件
    $('button[lay-filter="close-btn"]').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

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

