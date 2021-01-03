/** 医疗用品管理 */
//@ sourceURL=medicalSupply.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    ajax: 'ajax'
    ,utils: 'utils'
});
layui.use(['form', 'jquery', 'layer', 'ajax','table','utils'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/item/medicalSupply';
    var leftTableId = 'left-table';
    var formId = 'medicalsupply-form';
    var currentId = '';// 当前选中的医疗用品的ID
    form.render();

    // 动态加载进货包装下拉框
    utils.splicingOption({
        elem: $('.layui-form select[name="purUnit"]'),
        where: {dictKey: DICT_KEY.PUR_ITEM_JHBZ},
        tips: '请选择进货包装'
    });

    // 动态加载零售单位下拉框
    utils.splicingOption({
        elem: $('.layui-form select[name="stockUnit"]'),
        where: {dictKey: DICT_KEY.PUR_ITEM_LSDW},
        tips: '请选择零售单位'
    });

    // 监听 零售单位 下拉框
    form.on('select(stockUnit)', function (data) {
        var optionText = $(data.elem).find('option[value="'+ data.value + '"]').text();// 零售单位
        $(data.elem).parents('form').find('.stockWarnUnit').text(optionText);
    });

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
            {field: 'itemName', title: '医疗用品名称'}
        ]],
        skin: 'nob',
        // size: 'sm',
        done: function (res, curr, count) {
            $('.left-panel button[lay-event="addBtn"]').click();// 清空表单
            utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
            if (currentId) {
                var tableData = table.cache[leftTableId];
                for (var i = 0; i < tableData.length; i++) {
                    if (Number(tableData[i].itemId) === Number(currentId)) {
                        utils.btnEnabled($('.left-panel button[lay-event="delBtn"]'));
                        var rowIndex = tableData[i][table.config.indexName];
                        $('.left-panel .layui-table tbody tr div').removeClass('select');
                        $('.left-panel .layui-table tbody tr[data-index="' + rowIndex + '"]').find('div').addClass('select');
                        findById(currentId);
                    }
                }
            }
        }
    });

    // 监听行单击事件
    table.on('row(left-table)', function (obj) {
        $('.left-panel .layui-table tbody tr div').removeClass('select');
        $(obj.tr).find('div').addClass('select');
        utils.btnEnabled($('.left-panel button[lay-event="delBtn"]'));
        // 获取id，根据ID查询多级字典，表单设置值
        currentId = obj.data.itemId;
        findById(currentId);

    });

    // 搜索
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

    // 根据医疗用品ID查询医疗用品
    function findById(id) {
        if (utils.isNotNull(id)) {
            $.getJSON(rootMapping + '/findById', {id: id}, function (result) {
                if (result != null && utils.isNotNull(result.itemId)) {
                    form.val(formId, result);
                } else {
                    layer.alert('未找到该医疗用品，请重试！',{icon: 0}, function (index) {
                        utils.clearForm('#' + formId);// 清空表单
                        form.render();
                        layer.close(index);
                    });
                }
            });
        } else {
            layer.msg('医疗用品ID为空，无法查询！', {icon:2});
        }
    }

    // 表单自定义校验规则
    form.verify({
        repeat: function (value, item) { //value：表单的值、item：表单的DOM对象
            var inputName = $(item).attr('name');
            var formElem = $(item).parents('form');
            var formId = formElem.attr('id');
            var url = '';
            var msg = '';
            var data = {};
            data[inputName] = value.trim();
            data.itemId = formElem.find('input[name="itemId"]').val();
            data.itemType = formId.substring(0, formId.length - 4);
            if (inputName === 'itemName') {
                url = rootMapping + '/notRepeatName';
                var placeholder = $(item).attr('placeholder');
                var eleName = placeholder.substring(3);
                msg = eleName + '重复，请重新填写！';
            }
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

    // 保存医疗用品
    form.on('submit(submit-btn)', function (data) {
        var formData = data.field;
        var formId = data.form.id;
        var mapping = rootMapping + "/save";
        ajax.postJSON(mapping, formData, function (item) {
            if (item != null && utils.isNotNull(item.itemId)) {
                layer.msg(MSG.save_success);
                form.val(formId,{itemId:item.itemId});
                currentId = item.itemId;
                table.reload(leftTableId);
            } else {
                layer.msg(MSG.save_fail);
            }
        }, data.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('.cancel-btn').click(function () {
        var itemId = $('#' + formId + ' input[name="itemId"]').val();
        if (utils.isNotNull(itemId)) {
            findById(itemId);// 重新查询
        } else {
            currentId = undefined;
            utils.clearForm('#' + formId);// 清空表单
            form.render();
        }
    });

    // 统一事件处理
    var eventFunction = {
        // 新增
        addBtn: function () {
            utils.clearForm('#' + formId);// 清空表单
            form.render();
            utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
        },
        // 删除
        delBtn: function () {
            var itemId = $('#' + formId + ' input[name="itemId"]').val();
            if (itemId != null && itemId != undefined && itemId != '') {
                layer.confirm(MSG.delete_confirm + '该医疗用品吗？', {icon: 3}, function () {
                    ajax.delete(rootMapping + '/delete/' + itemId, function (success) {
                        if (success) {
                            layer.msg(MSG.delete_success);
                            currentId = undefined;
                            table.reload(leftTableId);
                        } else {
                            layer.msg(MSG.delete_fail);
                        }
                    });
                });
            } else {
                layer.msg('供应商ID为空，无法删除！', {icon: 2});
            }
        }
    };

    // 按钮绑定单击事件
    $(document).on('click','.layui-btn[lay-event]',function(){
        var event = $(this).attr('lay-event');
        if (typeof eventFunction[event] === 'function') {
            eventFunction[event].call(this);
        }
    });
});

