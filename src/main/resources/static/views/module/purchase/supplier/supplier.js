/** 供应商 */
//@ sourceURL=supplier.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['form', 'utils', 'jquery', 'layer', 'table', 'ajax'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var leftTableId = 'left-table';
    var rootMapping = '/purchase/supplier';
    var currentSupplierId = '';// 当前选中的供应商的ID
    form.render();

    // 初始化表格
    table.render({
        elem: '#left-table',
        url: rootMapping + '/queryPage',
        height: 'full-85',
        page: {
            limit: 10,
            groups: 2,
            layout: ['prev','page','next','count']
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        text: {
            none: '暂无数据！'
        },
        cols: [[
            {field: 'supplierName', title: '供应商名称'}
        ]],
        skin: 'nob',
        // size: 'sm',
        done: function (res, curr, count) {
            $('#add-btn').click();// 清空表单
            $('#del-btn').addClass('layui-btn-disabled').attr('disabled','disabled');// 禁用 删除按钮
            if (currentSupplierId) {
                var tableData = table.cache[leftTableId];
                for (var i = 0; i < tableData.length; i++) {
                    if (Number(tableData[i].supplierId) === Number(currentSupplierId)) {
                        var rowIndex = tableData[i][table.config.indexName];
                        $('.left-panel .layui-table tbody tr div').removeClass('select');
                        $('.left-panel .layui-table tbody tr[data-index="' + rowIndex + '"]').find('div').addClass('select');
                        getById(currentSupplierId);
                    }
                }
            }
        }
    });

    // 新增 供应商
    $('#add-btn').click(function () {
        $('.layui-card-header h3 b').text('新增供应商');
        utils.clearForm('#supplier-form');// 清空表单
        form.render();
    });

    // 删除 供应商
    $('#del-btn').click(function () {
        layer.confirm(MSG.delete_confirm + '该供应商吗？',{icon: 3}, function () {
            var supplierId = $('#supplier-form input[name="supplierId"]').val();
            if (supplierId != null && supplierId != undefined && supplierId != '') {
                ajax.delete(rootMapping + '/delete/' + supplierId, function (success) {
                    if (success) {
                        layer.msg(MSG.delete_success);
                        currentSupplierId = undefined;
                        table.reload(leftTableId);
                    } else {
                        layer.msg(MSG.delete_fail);
                    }
                });
            } else {
                layer.msg('供应商ID为空，无法删除！', {icon:2});
            }
        });
    });

    // 表单自定义校验规则
    form.verify({
        repeat: function (value, item) { //value：表单的值、item：表单的DOM对象
            var inputName = $(item).attr('name');
            var url = rootMapping + '/notRepeatName';
            var msg = '供应商名称已被占用，请重新填写！';
            var data = {};
            data[inputName] = value.trim();
            data.supplierId = $('#supplier-form input[name="supplierId"]').val();
            if (utils.isNotNull(url) && utils.isNotNull(data[inputName])) {
                ajax.getJSONAsync(url, data, function (result) {
                    if (result) msg = '';
                }, false);
            } else {
                msg = '';
            }
            return msg;
        }
    });

    // 保存
    form.on('submit(submit-btn)', function (data) {
        var formData = data.field;
        ajax.postJSON(rootMapping + '/save', formData, function (suppliers) {
            if (suppliers != null && utils.isNotNull(suppliers.supplierId)) {
                layer.msg(MSG.save_success);
                currentSupplierId = suppliers.supplierId;
                table.reload(leftTableId);
            } else {
                layer.msg(MSG.save_fail);
            }
        }, data.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('#cancel-btn').click(function () {
        var supplierId = $('#supplier-form input[name="supplierId"]').val();
        if (supplierId !== null && supplierId !== undefined && supplierId !== '') {
            getById(supplierId);// 重新查询
        } else {
            currentSupplierId = undefined;
            $('#add-btn').click();// 清空表单
        }
    });

    // 监听行单击事件
    table.on('row(left-table)', function (obj) {
        $('.left-panel .layui-table tbody tr div').removeClass('select');
        $(obj.tr).find('div').addClass('select');
        $('#del-btn').removeClass('layui-btn-disabled').removeAttr('disabled');
        // 获取id，根据ID查询多级字典，表单设置值
        currentSupplierId = obj.data.supplierId;
        getById(currentSupplierId);

    });

    // 根据id查询
    function getById(supplierId) {
        if (utils.isNotNull(supplierId)) {
            $.getJSON(rootMapping + '/findById', {supplierId: supplierId}, function (supplier) {
                if (supplier != null) {
                    $('.layui-card-header h3 b').text('修改供应商')
                    form.val('supplier-form', supplier);
                } else {
                    layer.alert('未找到该供应商，请重试！',{icon: 0}, function (index) {
                        table.reload(leftTableId);
                        layer.close(index);
                    });
                }
            });
        }else {
            layer.msg('供应商ID为空，无法查询！', {icon:2});
        }
    }

    // 查询 供应商
    var timeoutId = null;
    var lastKeyword = '';
    $('.left-panel .left-search input').bind('input porpertychange', function () {
        var _keywords = $(this).val();
        if (timeoutId) {
            clearTimeout(timeoutId);
        }
        timeoutId = setTimeout(function() {
            if (lastKeyword === _keywords) {
                return;
            }
            table.reload(leftTableId,{where:{keywords: _keywords}});
            lastKeyword = _keywords;
        }, 500);
    });
});