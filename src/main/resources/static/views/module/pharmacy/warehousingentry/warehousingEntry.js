/** 采购单 */
//@ sourceURL=purchaseOrderForm.js
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
    var rootMapping = '/pharmacy/warehousingentry';
    var itemTableId = 'warehousingentry-table';
    var formId = 'warehousingentry-form';
    form.render();

    // 动态加载日期控件
    laydate.render({
        elem: '#' + formId + ' input[name="warehousingEntryDate"]',
        value: new Date(),
        isInitValue: true
    });
    // 生成单号
    var date = new Date();
    var ymd = date.format('yyMMdd');
    var num = date.getHours() + date.getMinutes() + date.getSeconds() + date.getMilliseconds();
    $('#' + formId + ' input[name="warehousingEntryCode"]').val(ymd + num);

    // 初始化表格
    table.render({
        elem: '#' + itemTableId,
        height: 'full-100',
        cols: [[
            {field: 'purchaseOrderItemId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'pharmacyItemName', title: '药品名称', width: '12%',fixed: 'left'},
            {field: 'specifications', title: '规格', width: '10%'},
            {field: 'manufacturer', title: '制造商'},
            {field: 'batchNumber', title: '批号', width: '10%'},
            {field: 'manufactureDate', title: '生产日期', width: '10%'},
            {field: 'expireDate', title: '有效期至', width: '10%'},
            {field: 'purchaseCount', title: '数量', width: '7%',fixed: 'right',templet: function (d) {
                    return parseFloat(d.purchaseCount).toFixed(2);
                }},
            {field: 'purchaseUnitName', title: '单位', width: '7%',fixed: 'right'},
            {field: 'unitPrice', title: '单价(元)', width: '8%',fixed: 'right',templet: function (d) {
                    return parseFloat(d.unitPrice).toFixed(2);
                }},
            {field: '', title: '库存数量', width: '8%',fixed: 'right', templet: function (d) {
                    // 异步请求换算单位
                    return parseFloat(d.purchaseCount);
                }},
            {field: 'stockUnitName', title: '库存单位', width: '8%',fixed: 'right', templet: function (d) {
                    // 异步请求换算单位
                    return parseFloat(d.purchaseCount);
                }},
            {field: '', title: '零售价', width: '8%',fixed: 'right'}
        ]],
    });

    // 明细表格校验
    function validateGrid() {
        var gridData = $('#' + itemTableId).datagrid('getData');
        var rowCount = gridData.total;
        if (rowCount === 0) {
            layer.msg("采购单至少要有一条明细数据！");
            return false;
        }
        if (endEditing()) {
            for (var i = 0; i < rowCount; i++) {
                var goodName = gridData.rows[i].goodsName;
                var totalPrice = gridData.rows[i].totalPrice;
                if (!utils.isNotNull(goodName) || !utils.isNotNull(totalPrice)) {
                    beginEditing(i, 'medicineListId');
                    layer.msg("请将明细数据补充完整！");
                    return false;
                }
            }
        } else {
            setTimeout(function(){
                $('#' + itemTableId).datagrid('selectRow', editIndex);
            },0);
            layer.msg("请将明细数据补充完整！");
            return false;
        }
        return true;
    }

    // 根据ID查询采购单
    function queryPurchaseOrderById(purchaseOrderId) {
        if (utils.isNotNull(purchaseOrderId)) {
            $.getJSON(rootMapping + "/queryById",{purchaseOrderId: purchaseOrderId, queryType: 'entry'}, function (purchaseOrder) {
                if (purchaseOrder != null) {
                    form.val('purchaseorder-form', purchaseOrder);// 表单赋值
                    form.render();
                    $('#' + itemTableId).datagrid('loadData', purchaseOrder.purchaseOrderDetails);// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 保存采购单
    function savePurchaseOrder(obj, purchaseOrder) {
        ajax.postJSON(rootMapping + '/save', purchaseOrder, function (bill) {
            if (bill != null && bill.purchaseOrderId != null) {
                queryPurchaseOrderById(bill.purchaseOrderId);// 根据ID查询采购单，并赋值
                layer.msg(MSG.save_success);
            } else {
                layer.msg(MSG.save_fail);
            }
            utils.btnEnabled($(obj.elem));
        }, $(obj.elem));
    }

    /**
     * 保存采购单
     * @param data
     * @returns {boolean}
     */
    form.on('submit(submit-btn)', function(obj){
        utils.btnDisabled($(obj.elem));
        if (validateGrid()) {
            var purchaseOrder = obj.field;// 表单值
            var billItems = $('#' + itemTableId).datagrid('getData');// 获取表格数据
            // console.log(billItems);
            purchaseOrder.purchaseOrderItems = billItems.rows;
            if (!generalBranchCheck(purchaseOrder.totalPrice, purchaseOrder.purchaseOrderItems)) {
                layer.confirm('采购单总金额大于明细总价之和，数据存在错误风险，确认保存吗？',
                    {
                        icon: LAYER_ICON.question,
                        btn2: function(index, layero) {
                            utils.btnEnabled($(obj.elem));
                        },
                        cancel: function (index, layero) {
                            utils.btnEnabled($(obj.elem));
                        }
                    },
                    function (index) {
                        savePurchaseOrder(obj, purchaseOrder);
                        layer.close(index);
                    });
            }else {
                savePurchaseOrder(obj, purchaseOrder);
            }
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

