/** 入库单 */
//@ sourceURL=purStock.js
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
    var rootMapping = '/purchase/stock';
    var itemTableId = 'stock-table';
    var formId = 'stock-form';
    form.render();

    // 初始化表格
    var tableConfig = {
        elem: '#' + itemTableId,
        id: itemTableId,
        height: 'full-150',
        cols: [[
            {field: 'purOrderDetailId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'itemName', title: '品目名称',width: '20%'},
            {field: 'purCount', title: '采购数量'},
            {field: 'unitPrice', title: '单价(元)'},
            // {field:'unitConvert', title:'单位换算'},
            {field: 'breakevenPrice', title: '保本售价(元)', templet: function (d) {
                    var breakeven = d.totalPrice/d.stockCount;
                    if (isNaN(breakeven)) {
                        return '';
                    } else {
                        return breakeven.toFixed(4);
                    }
                }},
            {field: 'stockPrice', title:'参考售价(元)', templet: function (d) {
                    if (utils.isNotNull(d.stockPrice)) return d.stockPrice; else return '<p style="color: #D3D3D3;">暂无</p>';
                }},
            {field: 'sellingPrice',title: '重定价(元)',edit: 'text',templet: function (d) {
                    if (isNaN(d.sellingPrice)) {
                        return '<p style="color: #D3D3D3;">请填写新售价</p>';
                    } else {
                        return Number(d.sellingPrice).toFixed(4);
                    }
                }}
        ]],
    };

    $(document).on("focus",'.layui-table-edit', function(){
        // if ($(this).val() === '请填写新售价') $(this).val('');
        $(this).select();
    });
    
    // 明细表格校验
    function validateGrid() {
        var tableData = table.cache[itemTableId];
        for (var i = 0, len = tableData.leng; i < len; i++) {
            if (!utils.isNotNull(tableData[i].sellingPrice)) {
                layer.alert('重定价不能为空，请补充完整！', {icon: LAYER_ICON.error});
                return false;
            }
        }
        return true;
    }

    // 根据ID查询采购单
    function queryPurOrderById(purOrderId) {
        if (utils.isNotNull(purOrderId)) {
            $.getJSON("/purchase/order/queryByIdForStock",{purOrderId: purOrderId}, function (purOrder) {
                if (purOrder != null && purOrder.order != null && purOrder.detail != null) {
                    purOrder.order.totalPrice = purOrder.order.totalPrice.toFixed(2) + ' 元';
                    form.val(formId, purOrder.order);// 表单赋值
                    form.render();
                    tableConfig.data = purOrder.detail;
                    table.render(tableConfig);// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 保存入库
    function saveStockDetail(obj, purStocks) {
        ajax.postJSON(rootMapping + '/save', purStocks, function (purStocks) {
            if (purStocks != null) {
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
            var purStocks = table.cache[itemTableId];// 获取表格数据
            var purOrderId = $('#' + formId).find('input[name="purOrderId"]').val();
            // console.log(billItems);
            for (var i = 0, len = purStocks.length; i < len; i++) {
                purStocks[i].purOrderId = purOrderId;
                purStocks[i].stockState = 1;// 库存状态(1：正常；2：已退货；3：已过期；4：下架)
            }
            saveStockDetail(obj, purStocks);
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
            if (keyVal[0] === 'purOrderId') {
                queryPurOrderById(keyVal[1]);
                break;
            }
        }
    }
});

