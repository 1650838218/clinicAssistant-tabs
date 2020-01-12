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
    var warnTableId = 'warn-table';
    var warnFormId = 'warn-form';

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
            {field: 'option', title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '13%', align: 'center'}
        ]]
    });

    // 当前库存 搜索
    $('input[name="keywords"]').on('change', function () {
        var index = $(this).parents('.layui-tab-item').index();
        if (index == 0) {
            var isChecked = $('#query-form input[type="checkbox"][name="expireDate"]').is(":checked");
            table.reload(stockTableId,{where: {keywords: $(this).val(),expireDate: isChecked}});
        } else if (index == 1) {
            table.reload(warnTableId,{where: {keywords: $(this).val()}});
        }
    });

    // 一个月到期
    form.on('checkbox(expire-date)', function(data){
        table.reload(stockTableId,{where: {keywords: $('#query-form input[name="keywords"]').val(), expireDate: data.elem.checked}});
    });

    // 查询事件
    form.on('submit(submit-btn)', function (data) {
        var index = $(data.elem).parents('.layui-tab-item').index();
        if (index == 0) {
            table.reload(stockTableId,{where: data.field});
        } else if (index == 1) {
            table.reload(warnTableId,{where:data.field});
        }
        return false;
    });

    // 监听表格编辑事件，当表格内容发生变化时触发
    table.on('edit(' + stockTableId + ')', function (obj) {
        var newVal = obj.value;
        var field = obj.field;
        var inputElem = $(this);
        var tdElem = inputElem.closest('td');
        var oldVal = tdElem.find('.layui-table-cell').text();
        var regex = '';

        if (field === 'stockCount') {// 修改库存数量
            var stockUnit = oldVal.substring(oldVal.indexOf('（'));
            regex = eval('/^\\d{1,10}(\\.\\d{1,4})?(' + stockUnit + ')?$/');
        } else if (field === 'sellingPrice') {// 修改零售价
            regex = /^\d{1,10}(\.\d{1,4})?$/;
        }
        if (!regex.test(newVal)) {
            layer.alert('请输入不超过4位小数的正数！', {icon: LAYER_ICON.error}, function (index) {
                var jsonVal = {};
                jsonVal[field] = oldVal;
                obj.update(jsonVal);
                tdElem.click();
                inputElem.focus();
                layer.close(index);
            });
        } else {
            if (field === 'stockCount' && !isNaN(newVal)) {
                // 修改库存数量，并且只输入了一个数字，此时需要把单位拼上
                setTimeout(function () {
                    var jsonVal = {};
                    jsonVal[field] = newVal + oldVal.substring(oldVal.indexOf('（'));
                    obj.update(jsonVal);
                    tdElem.find('.layui-table-cell').text(jsonVal[field]);
                },0);
            }
            var optionTdElem = tdElem.nextAll('td[data-field="option"]');
            optionTdElem.find('a').removeClass('layui-btn-disabled').removeAttr('disabled');// 解禁 保存 按钮
        }
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
        layer.confirm('是否确认下架该品目？', {icon: LAYER_ICON.question}, function (index) {
            var unshelveBtn = $(obj.tr).find('td[data-field="option"] a[lay-event="unshelve"]');
            unshelveBtn.addClass("layui-btn-disabled").attr("disabled",'disabled');
            $.getJSON(rootMapping + '/unshelve', {'purStockId':obj.data.purStockId}, function (result) {
                if (result) {
                    layer.msg('下架成功！');
                    table.reload(stockTableId, {});// 刷新列表
                } else {
                    layer.msg('下架失败！');
                    unshelveBtn.removeClass("layui-btn-disabled").removeAttr("disabled");
                }
            });
            layer.close(index);
        });
    }

    // 保存行
    function saveRow(obj) {
        layer.confirm('是否确认修改该品目的库存信息？', {icon: LAYER_ICON.question}, function (index) {
            var saveBtn = $(obj.tr).find('td[data-field="option"] a[lay-event="save"]');
            saveBtn.addClass("layui-btn-disabled").attr("disabled", 'disabled');
            var stockCount = obj.data.stockCount;
            obj.data.stockCount = stockCount.substring(0, stockCount.indexOf('（'));
            ajax.postJSON(rootMapping + '/update', obj.data, function (purStock) {
                if (purStock != null) {
                    layer.msg(MSG.save_success);
                } else {
                    layer.msg(MSG.save_fail);
                    saveBtn.removeClass("layui-btn-disabled").removeAttr("disabled");
                }
            });
        });
    }

    // 查看行
    function lookRow(obj) {
        // 获取被选中的库存品目
        var purStockId = obj.data.purStockId;
        if (utils.isNotNull(purStockId)) {
            // ajax请求，根据库存ID查询
            $.getJSON('/purchase/stock/findByIdForOrder',{purStockId: purStockId}, function (purStock) {
                if (purStock != null) {
                    var content = '';
                    content += '<form class="layui-form" action="">';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">品目名称：</label>';
                    content += '<div class="layui-form-mid">' + purStock.purItemName + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">品目分类：</label>';
                    content += '<div class="layui-form-mid">' + purStock.purItemType + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购单号：</label>';
                    content += '<div class="layui-form-mid">' + purStock.purOrderCode + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购日期：</label>';
                    content += '<div class="layui-form-mid">' + purStock.purOrderDate + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">供应商：</label>';
                    content += '<div class="layui-form-mid">' + purStock.supplierName + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购数量：</label>';
                    content += '<div class="layui-form-mid">' + purStock.purCount + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购单价：</label>';
                    content += '<div class="layui-form-mid">' + purStock.unitPrice + ' 元</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">采购总价：</label>';
                    content += '<div class="layui-form-mid">' + purStock.totalPrice + ' 元</div>';
                    content += '</div>';
                    content += '</form>';
                    layer.open({
                        title: '品目采购信息',
                        area: '500px',
                        content: content
                    });
                } else {
                    layer.alert('未找到该品目采购信息！',{icon: LAYER_ICON.warning});
                }
            });
        } else {
            layer.msg('请先选择一个库存品目！');
        }
    }

    // 库存预警
    table.render({
        elem: '#' + warnTableId,
        url: rootMapping + '/queryPageForWarn',
        page: true,
        height: 'full-145',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'purStockId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'purItemName', title: '品目名称', width: '20%'},
            {field: 'purItemType', title: '品目分类'},
            {field: 'stockCount', title: '库存数量'},
            {field: 'stockWarn',title: '库存预警'}
        ]]
    });

    // 已过期
    table.render({
        elem: '#expire-table',
        url: rootMapping + '/queryPageForExpire',
        page: true,
        height: 'full-145',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'purStockId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'purItemName', title: '品目名称', width: '20%'},
            {field: 'purItemType', title: '品目分类'},
            {field: 'expireDate',title: '有效期至'},
            {field: 'stockCount', title: '库存数量'},
            {field: 'unitPrice', title: '进价(元)'},
            {field: 'loss', title: '损失(元)'}
        ]]
    });

    // 已过期刷新按钮
    $('.expire-btn').click(function () {
        table.reload('expire-table');
    });

    // 选项卡切换
    element.on('tab(lay-tab)', function(data){
        // console.log(this); //当前Tab标题所在的原始DOM元素
        // console.log(data.index); //得到当前Tab的所在下标
        // console.log(data.elem); //得到当前的Tab大容器
        if (data.index == 1) {
            table.resize(warnTableId);
        } else if (data.index == 2) {
            table.resize('expire-table');
        }
    });
});

