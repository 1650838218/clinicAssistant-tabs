/** 药品清单 */
//@ sourceURL=purItem.js
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
    var rootMapping = '/purchase/item';
    form.render();

    // 初始化表格
    table.render({
        elem: '#left-table',
        url: rootMapping + '/queryPage',
        height: 'full-85',
        page: {
            limit: 10,
            groups: 3,
            layout: ['prev', 'page', 'next','count']
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'pharmacyItemName', title: '药品名称'}
        ]],
        skin: 'nob',
        // size: 'sm',
        done: function (res, curr, count) {
            $('#add-btn').click();// 清空表单
            $('#del-btn').addClass('layui-btn-disabled').attr('disabled','disabled');// 禁用 删除按钮
            // 选中当前行
            var pharmacyItemId = $('#item-form input[name="pharmacyItemId"]').val();
            if (utils.isNotNull(pharmacyItemId)) {
                var tableData = table.cache[leftTableId];
                if (utils.isNotEmpty(tableData)) {
                    for (var i = 0; i < tableData.length; i++) {
                        if (parseInt(tableData[i].pharmacyItemId) === parseInt(pharmacyItemId)) {
                            var rowIndex = tableData[i][table.config.indexName];
                            $('.left-panel .layui-table tbody tr[data-index="' + rowIndex + '"]').find('div').addClass('select');
                        }
                    }
                }
            }
        }
    });

    // 动态加载药品类型下拉框
    utils.splicingOption({
        elem: $('#item-form select[name="pharmacyItemType"]'),
        where: {dictTypeKey: 'YPFL'},
    });

    // 动态加载采购单位下拉框
    utils.splicingOption({
        elem: $('#item-form select[name="purchaseUnit"]'),
        where: {dictTypeKey: 'SLDW'},
    });

    // 动态加载库存单位下拉框
    utils.splicingOption({
        elem: $('#item-form select[name="stockUnit"]'),
        where: {dictTypeKey: 'KCDW'},
    });

    // 监听药品名称输入事件
    $('#item-form input[name="pharmacyItemName"]').on('input propertychange', function () {
        var pharmacyItemName = $(this).val().trim();
        if (utils.isNotNull(pharmacyItemName)) {
            var fullPinyin = pinyinUtil.getPinyin(pharmacyItemName, '', false, true);
            var abbreviation = pinyinUtil.getFirstLetter(pharmacyItemName, true);
            $('#item-form input[name="fullPinyin"]').val(fullPinyin);
            $('#item-form input[name="abbreviation"]').val(abbreviation);
        } else {
            $('#item-form input[name="fullPinyin"]').val('');
            $('#item-form input[name="abbreviation"]').val('');
        }
    });

    // 新增 药品
    $('#add-btn').click(function () {
        utils.clearForm('#item-form');// 清空表单
        form.val('item-form', {
            pharmacyItemType: 1,
            poisonous: 'false',
            stockUnit: 1
        });// 设置初始值
        form.render();
    });

    // 删除 药品
    $('#del-btn').click(function () {
        layer.confirm(MSG.delete_confirm + '该药品吗？',{icon: 3}, function () {
            var pharmacyItemId = $('#item-form input[name="pharmacyItemId"]').val();
            if (pharmacyItemId != null && pharmacyItemId != undefined && pharmacyItemId != '') {
                ajax.delete(rootMapping + '/delete/' + pharmacyItemId, function (success) {
                    if (success) {
                        layer.msg(MSG.delete_success);
                        table.reload(leftTableId, {});
                    } else {
                        layer.msg(MSG.delete_fail);
                    }
                });
            } else {
                layer.msg('药品ID为空，无法删除！', {icon:2});
            }
        });
    });

    // 表单自定义校验规则
    form.verify({
        repeat: function (value, item) { //value：表单的值、item：表单的DOM对象
            var inputName = $(item).attr('name');
            var url = '';
            var msg = '';
            var data = {};
            data[inputName] = value.trim();
            data.pharmacyItemId = $('#item-form input[name="pharmacyItemId"]').val();
            if (inputName === 'pharmacyItemName') {
                url = rootMapping + '/notRepeatName';
                msg = '药品名称已被占用，请重新填写！';
            } else if (inputName === 'barcode') {
                url = rootMapping + '/notRepeatBarcode';
                msg = '条形码已被占用，请重新填写！';
            }
            if (utils.isNotNull(url) && utils.isNotNull(data[inputName])) {
                ajax.getJSONAsync(url, data, function (result) {
                    if (result) msg = '';
                }, false);
            } else {
                msg = '';
            }
            return msg;
        },
        regExp: function (value, item) {
            var inputName = $(item).attr('name');
            var msg = '';
            var reg = '';
            if (inputName === 'barcode') {
                reg = /^[a-zA-Z0-9]*$/;
                msg = '条形码只能输入数字和字母，请重新填写！';
            }
            if (!!msg && !reg.test(value)) {
                return msg;
            }
        }
    });

    // 保存
    form.on('submit(submit-btn)', function (data) {
        var formData = data.field;
        ajax.postJSON(rootMapping + '/save', formData, function (pharmacyItems) {
            if (pharmacyItems != null && utils.isNotNull(pharmacyItems.pharmacyItemId)) {
                layer.msg(MSG.save_success);
                table.reload(leftTableId, {});
                getById(pharmacyItems.pharmacyItemId);
            } else {
                layer.msg(MSG.save_fail);
            }
        }, data.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('#cancel-btn').click(function () {
        var pharmacyItemId = $('#item-form input[name="pharmacyItemId"]').val();
        if (pharmacyItemId !== null && pharmacyItemId !== undefined && pharmacyItemId !== '') {
            getById(pharmacyItemId);// 重新查询
        } else {
            $('#add-btn').click();// 清空表单
        }
    });

    // 监听行单击事件
    table.on('row(left-table)', function (obj) {
        $('.left-panel .layui-table tbody tr div').removeClass('select');
        $(obj.tr).find('div').addClass('select');
        $('#del-btn').removeClass('layui-btn-disabled').removeAttr('disabled');
        // $('#del-btn').removeAttr('disabled');
        // 获取id，根据ID查询多级字典，表单设置值
        var pharmacyItemId = obj.data.pharmacyItemId;
        getById(pharmacyItemId);

    });

    // 根据id查询
    function getById(pharmacyItemId) {
        if (utils.isNotNull(pharmacyItemId)) {
            $.getJSON(rootMapping + '/getById', {pharmacyItemId: pharmacyItemId}, function (pharmacyItem) {
                if (pharmacyItem != null) {
                    // console.log(pharmacyItems);
                    form.val('item-form', pharmacyItem);
                } else {
                    layer.alert('未找到该药品，请重试！',{icon: 0}, function (index) {
                        table.reload(leftTableId, {});
                        layer.close(index);
                    });
                }
            });
        }else {
            layer.msg('药品ID为空，无法查询！', {icon:2});
        }
    }

    // 查询 药品
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
            table.reload(leftTableId, {where: {keywords: _keywords}});
            lastKeyword = _keywords;
        }, 500);
    });
});