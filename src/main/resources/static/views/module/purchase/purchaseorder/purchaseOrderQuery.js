// 采购单查询
//@ sourceURL=purchaseOrderQuery.js
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
    var rootMappint = '/purchase/purchaseorder';
    var purchaseOrderTableId = 'purchaseorder-table';
    var formId = 'query-form';

    // 动态加载供应商
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="supplierId"]'),
        tips: '请选择供应商',
        url:'/purchase/supplier/getSelectOption',
        realValueName: 'realValue',
        displayValueName: 'displayValue',
    });
    // 动态加载日期控件
    laydate.render({
        elem: '#' + formId + ' input[name="purchaseOrderDate"]',
        range: true
    });

    // 初始化表格
    table.render({
        elem: '#' + purchaseOrderTableId,
        url: rootMappint + '/queryPage',
        page: true,
        height: 'full-105',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'purchaseOrderId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'purchaseOrderCode', title: '单号', width: '15%'},
            {field: 'purchaseOrderDate', title: '日期', width: '12%'},
            {field: 'purchaseOrderType', title: '类型', width: '10%',
                templet: function (d) {
                    if (parseInt(d.purchaseOrderType) === 1) {
                        return "药品采购单"
                    } else {
                        return "耗材采购单"
                    }
                }
            },
            {field: 'supplierName', title: '供应商', event: 'lookSupplier',style:'cursor: pointer;',
                templet: function (d) {
                    return d.supplierName + '<i class="layui-icon layui-icon-about supplier-name-icon" title="查看供应商详情"></i>';
                }
            },
            {
                field: 'totalPrice', title: '金额(元)', width: '15%',
                templet: function (d) {
                    return parseFloat(d.totalPrice).toFixed(2);
                }
            },
            {title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '23%', align: 'center'}
        ]],
        done: function (res, curr, count) {
        }
    });

    // 查询事件
    form.on('submit(submit-btn)', function (data) {
        table.reload(purchaseOrderTableId,{where: data.field});
        return false;
    });

    //监听表格操作列 监听单元格事件
    table.on('tool(' + purchaseOrderTableId + ')', function (obj) {
        if (obj.event === 'look') {
            lookRow(obj);
        } else if (obj.event === 'update') {
            updateRow(obj);
        } else if (obj.event === 'delete') {
            deleteRow(obj);
        } else if (obj.event === 'entry') {
            stockEntryRow(obj);// 入库
        } else if (obj.event === 'lookSupplier') { // 监听单元格事件
            lookSupplier(obj);// 查看供应商详情
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
            content: '/views/module/purchase/purchaseorder/purchaseOrderLook.html?purchaseOrderId='+obj.data.purchaseOrderId,
        });
        layer.full(index);
    }

    // 删除行
    function deleteRow(obj) {
        // 此处的删除是真删除
        layer.confirm(MSG.delete_confirm + '该采购单吗？', {icon: 0}, function (index) {
            var purchaseOrderId = obj.data.purchaseOrderId;
            if (utils.isNotNull(purchaseOrderId)) {
                ajax.delete(rootMappint + '/delete/' + purchaseOrderId, function (success) {
                    if (success) {
                        layer.msg(MSG.delete_success);
                        table.reload(purchaseOrderTableId, {});
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
            content: '/views/module/purchase/purchaseorder/purchaseOrderForm.html?purchaseOrderId='+obj.data.purchaseOrderId,
            cancel: function(index, layero) {
                table.reload(purchaseOrderTableId, {});
            },
            end: function () {
                table.reload(purchaseOrderTableId, {});
            }
        });
        layer.full(index);
    }

    // 入库
    function stockEntryRow(obj) {
        var height = $('.center-panel').height();
        var width = $('.center-panel').width();
        var index = layer.open({
            type: 2,
            area: [width+'px', height+'px'],
            title: false,
            fixed: false, //不固定
            content: '/views/module/purchase/stock/stockDetail.html?purchaseOrderId='+obj.data.purchaseOrderId,
            cancel: function(index, layero) {
                table.reload(purchaseOrderTableId, {});
            },
            end: function () {
                table.reload(purchaseOrderTableId, {});
            }
        });
        layer.full(index);
    }

    // 查看供应商
    function lookSupplier(obj) {
        // 获取被选中的供应商
        var supplierId = obj.data.supplierId;
        if (utils.isNotNull(supplierId)) {
            // ajax请求，根据供应商ID查询供应商
            $.getJSON('/purchase/supplier/findById',{supplierId: supplierId}, function (supplier) {
                if (supplier != null) {
                    var content = '';
                    content += '<form class="layui-form" action="">';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">供应商名称：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.supplierName + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系人1：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.linkMan1 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系电话1：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.phone1 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系人2：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.linkMan2 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系电话2：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.phone2 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">主营：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.mainProducts + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">地址：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.address + '</div>';
                    content += '</div>';
                    content += '</form>';
                    layer.open({ title: '供应商信息',area: '500px', content: content});
                } else {
                    layer.alert('未找到该供应商，请检查该供应商是否存在！',{icon: LAYER_ICON.warning});
                }
            });
            // 显示供应商信息
        } else {
            layer.msg('请先选择一个供应商！');
        }
    }
});