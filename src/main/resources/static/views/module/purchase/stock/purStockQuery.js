/** 库存盘点 */
//@ sourceURL=purStockQuery.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    // ,selectC: 'selectC'
});
layui.use(['utils', 'jquery', 'layer', 'table', 'ajax', 'form','element'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var form = layui.form;
    var element = layui.element;
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
        height: 'full-145',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'purStockId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'purItemName', title: '品目名称', width: '20%'},
            {field: 'dictName', title: '品目分类'},
            {field: 'batchNumber', title: '批号'},
            {field: 'expireDate', title: '有效期至'},
            {field: 'stockCount', title: '库存数量',edit: 'text'},
            {
                field: 'sellingPrice',
                title: '零售价(元)',
                edit: 'text',
                templet: function (d) {
                    if (isNaN(d.sellingPrice)) {
                        return '';
                    } else {
                        return Number(d.sellingPrice).toFixed(4);
                    }
                }
            },
            {field: 'stockDetailId', title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '16%', align: 'center'}
        ]]
    });

    // 当前库存 搜索
    $('#query-form input[name="keywords"]').on('change', function () {
        table.reload(stockTableId,{where: {keywords: $(this).val()}});
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
        } else if (obj.event === 'look') {
            lookRow(obj);
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
            } else {
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

    // 查看行
    function lookRow(obj) {
        // 获取被选中的供应商
        var purStockId = obj.data.purStockId;
        if (utils.isNotNull(purStockId)) {
            // ajax请求，根据库存ID查询
            $.getJSON('/purchase/stock/findByIdForOrder',{purStockId: purStockId}, function (purStock) {
                if (purStock != null) {
                    var content = '';
                    content += '<form class="layui-form" action="">';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">品目名称：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.supplierName + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">品目分类：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.linkMan1 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购单号：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.phone1 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购日期：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.linkMan2 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">供应商：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.address + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购数量：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.phone2 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购价格(元)：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.mainProducts + '</div>';
                    content += '</div>';
                    content += '</form>';
                    layer.open({ title: '品目采购信息',area: '500px', content: content});
                } else {
                    layer.alert('未找到该品目采购信息！',{icon: LAYER_ICON.warning});
                }
            });
            // 显示供应商信息
        } else {
            layer.msg('请先选择一个库存品目！');
        }
    }
});

