/** 新增品目 */
//@ sourceURL=addItem.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['form', 'utils', 'jquery', 'layer', 'ajax'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/item';

    // 设置高度
    $('#left-tree').height($('.left-panel').height() - 40);
    $('.right-panel').height($(window).height() - 20);
    $('.right-panel .blank-tip').height($('.right-panel').height() - 43 -20);

    // 动态加载品目分类下拉框
    utils.splicingOption({
        elem: $('#add-form select[name="itemType"]'),
        where: {dictKey: DICT_KEY.ITEM_PMFL},
        tips: '请选择品目分类'
    });

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

    // 动态加载出处下拉框
    utils.splicingOption({
        elem: $('.layui-form select[name="source"]'),
        where: {dictKey: DICT_KEY.ITEM_FJCC},
        tips: '请选择出处'
    });

    // 监听品目分类下拉框的选中
    form.on('select(itemType)', function (data) {
        if (data.value) {
            // 显示对应的form表单
            var formId = data.value + 'Form';// 拼接formID
            var optionText = $(data.elem).find('option[value="'+ data.value + '"]').text();// 品目分类
            utils.clearForm('#' + formId);// 清空表单
            form.val(formId,{isPoison: '0'});// 设置初始值
            // 将 data.value 变大写，且单词前面加下划线；eg：HerbalMedicine => _HERBAL_MEDICINE
            var dictKey = 'ITEM';
            for (var i = 0; i < data.value.length; i++) {
                var char = data.value.charAt(i);
                if (char >= 'A' && char <= 'Z') {
                    dictKey += "_" + char;
                } else {
                    dictKey += char.toUpperCase();
                }
            }
            // 动态加载品目分类下拉框
            utils.splicingOption({
                elem: $('#' + formId + ' select[name="itemType"]'),
                where: {dictKey: DICT_KEY[dictKey]},
                tips: '请选择所属分类'
            });
            form.render(null, formId);
            $('.layui-card-body form').hide();
            $('.layui-card-body form[id="' + formId + '"]').show();
            // 修改标题
            $('.layui-card-header h3 b').text('新增品目');
            $('.layui-card-header h3 span').removeClass().addClass('subhead-green').text('（' + optionText + '）');
        }
    });

    // 监听 零售单位 下拉框
    form.on('select(stockUnit)', function (data) {
        var optionText = $(data.elem).find('option[value="'+ data.value + '"]').text();// 零售单位
        $(data.elem).parents('form').find('.stockWarnUnit').text(optionText);
    });

    // 监听品目名称输入事件，自动填写全拼，简拼
    $('.layui-form input[name="itemName"]').on('input propertychange', function () {
        var formElem = $(this).parents('form');
        var itemName = $(this).val().trim();
        if (utils.isNotNull(itemName)) {
            var fullPinyin = pinyinUtil.getPinyin(itemName, '', false, true);// 不使用声调，支持多音字
            var abbrPinyin = pinyinUtil.getFirstLetter(itemName, true);// 支持多音字
            formElem.find('input[name="fullPinyin"]').val(fullPinyin);
            formElem.find('input[name="abbrPinyin"]').val(abbrPinyin);
        } else {
            formElem.find('input[name="fullPinyin"]').val('');
            formElem.find('input[name="abbrPinyin"]').val('');
        }
    });

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
                url = '/itemAdd/notRepeatName';
                var placeholder = $(item).attr('placeholder');
                var eleName = placeholder.substring(3);
                msg = eleName + '重复，请重新填写！';
            } else if (inputName === 'barcode') {
                url = '/itemAdd/notRepeatBarcode';
                msg = '条形码重复，请重新填写！';
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
                msg = '条形码只能输入数字、字母，请重新填写！';
            }
            if (!!msg && !reg.test(value)) {
                return msg;
            }
        }
    });

    // 保存
    form.on('submit(submit-btn)', function (data) {
        var formData = data.field;
        var formId = data.form.id;
        var mapping = "/item" + formId.substring(0,formId.length - 4) + "/save"
        ajax.postJSON(mapping, formData, function (item) {
            if (item != null && utils.isNotNull(item.itemId)) {
                layer.msg(MSG.save_success);
                form.val(formId,{itemId:item.itemId});
            } else {
                layer.msg(MSG.save_fail);
            }
        }, data.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('.cancel-btn').click(function () {
        // 显示对应的form表单
        form.val('add-form',{itemType: ''});
        $('.layui-card-body form').hide();
        $('.layui-card-body form[id="add-form"]').show();
        // 修改标题
        $('.layui-card-header h3 b').text('新增品目');
        $('.layui-card-header h3 span').removeClass().addClass('subhead-red').text('（请选择品目分类）');
    });
});