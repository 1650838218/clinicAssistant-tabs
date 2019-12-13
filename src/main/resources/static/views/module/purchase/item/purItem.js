/** 药品清单 */
//@ sourceURL=purItem.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    ,eleTree: 'eleTree'
});
layui.use(['form', 'utils', 'jquery', 'layer', 'ajax','eleTree'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var eleTree = layui.eleTree;
    var rootMapping = '/purchase/item';
    var leftTree = undefined;// 左侧菜单树
    var currentPurItemId = undefined;
    var currentPurItemParentId = undefined;
    form.render();

    // 设置高度
    $('.left-panel').height($(window).height() - 40);
    $('#left-tree').height($('.left-panel').height() - 40);
    $('.right-panel').height($(window).height() - 20);
    $('.right-panel .blank-tip').height($('.right-panel').height() - 43 -20);

    // 加载左侧品目目录
    leftTree = eleTree.render({
        elem: '#left-tree',
        url: rootMapping + "/queryTree",
        method: "get",
        emptText: '暂无数据！',
        highlightCurrent: true,// 高亮显示当前节点
        defaultExpandAll: false,// 默认展开所有节点
        renderAfterExpand: true, // 是否在第一次展开某个树节点后才渲染其子节点
        expandOnClickNode: true,// 单击展开
        checkOnClickNode: true,// 单击选中
        showCheckbox: false,// 是否显示复选框
        searchNodeMethod: function(value,data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        done: function (res) {
            if (currentPurItemId) {
                leftTree.expandNode(currentPurItemParentId); //展开某节点的所有子节点
                $('#left-tree div.eleTree-node[data-id="' + currentPurItemId + '"] > .eleTree-node-content').trigger('click');
            } else {
                eventFunction.addPurItem();
                utils.btnDisabled($('.left-panel button[lay-event="deletePurItem"]'));
            }
        }
    });

    // 搜索
    $(".left-panel .left-search .eleTree-search").on("input change",function() {
        if (!!leftTree) leftTree.search($.trim($(this).val()));
        leftTree.expandAll();
    });

    // 左侧树的点击事件 根据ID查询品目
    eleTree.on("nodeClick(left-tree)", function (d) {
        var purItemId = d.data.currentData.id;
        var children = d.data.currentData.children;
        if (!children) {
            currentPurItemId = purItemId;
            var text = $(d.node).parents('.eleTree-node').find('.eleTree-node-content-label:eq(0)').text();
            if (text.indexOf('（') > -1) {
                text = text.substr(0, text.indexOf('（'));
            }
            utils.btnEnabled($('.left-panel button[lay-event="deletePurItem"]'));
            getById(purItemId, text);
        } else {
            currentPurItemId = undefined;
            eventFunction.addPurItem();
            utils.btnDisabled($('.left-panel button[lay-event="deletePurItem"]'));
        }

    });

    // 动态加载采购单位下拉框
    utils.splicingOption({
        elem: $('#add-form select[name="purItemType"]'),
        where: {dictKey: DICT_KEY.PUR_ITEM_CGPMFL},
        tips: '请选择品目分类'
    });

    // 监听品目分类下拉框的选中
    form.on('select(purItemType)', function (data) {
        if (data.value) {
            // 显示对应的form表单
            var formId = 'type' + data.value + '-form';// 拼接formID
            var optionText = $(data.elem).find('option[value="'+ data.value + '"]').text();// 品目分类
            utils.clearForm('#' + formId);// 清空表单
            form.val(formId,{purItemType:data.value,isPoison: '0'});// 设置初始值
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

    // 监听品目名称输入事件，自动填写全拼，简拼
    $('.layui-form input[name="purItemName"]').on('input propertychange', function () {
        var formElem = $(this).parents('form');
        var purItemName = $(this).val().trim();
        if (utils.isNotNull(purItemName)) {
            var fullPinyin = pinyinUtil.getPinyin(purItemName, '', false, true);// 不使用声调，支持多音字
            var abbrPinyin = pinyinUtil.getFirstLetter(purItemName, true);// 支持多音字
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
            var url = '';
            var msg = '';
            var data = {};
            data[inputName] = value.trim();
            data.purItemId = formElem.find('input[name="purItemId"]').val();
            if (inputName === 'purItemName') {
                url = rootMapping + '/notRepeatName';
                msg = '品目名称重复，请重新填写！';
            } else if (inputName === 'barcode') {
                url = rootMapping + '/notRepeatBarcode';
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
        ajax.postJSON(rootMapping + '/save', formData, function (purItems) {
            if (purItems != null && utils.isNotNull(purItems.purItemId)) {
                layer.msg(MSG.save_success);
                currentPurItemId = purItems.purItemId;
                currentPurItemParentId = 'dictVal_' + purItems.purItemType;
                leftTree = leftTree.reload();
            } else {
                layer.msg(MSG.save_fail);
            }
        }, data.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('.cancel-btn').click(function () {
        var purItemId = $(this).parents('form').find('input[name="purItemId"]').val();
        if (purItemId !== null && purItemId !== undefined && purItemId !== '') {
            getById(purItemId);// 重新查询
        } else {
            leftTree.unCheckNodes() //取消所有选中的节点
            eventFunction.addPurItem();
        }
    });

    // 根据id查询，并对表单赋值
    function getById(purItemId, text) {
        if (utils.isNotNull(purItemId)) {
            try {
                $.getJSON(rootMapping + '/getById', {purItemId: purItemId}, function (purItem) {
                    if (purItem != null && utils.isNotNull(purItem.purItemId)) {
                        $('.layui-card-header h3 b').text('编辑品目');
                        if (text) $('.layui-card-header h3 span').removeClass().addClass('subhead-green').text('（' + text + '）');
                        var formFilter = 'type' + purItem.purItemType + '-form';
                        form.val(formFilter, purItem);
                        var stockUnit = $('#' + formFilter).find('select[name="stockUnit"] option:selected');
                        console.log(stockUnit.val());
                        if (stockUnit.val()) {
                            $('#' + formFilter + ' .stockWarnUnit').text(stockUnit.text());
                        } else {
                            $('#' + formFilter + ' .stockWarnUnit').text('<零售单位>');
                        }
                        $('.layui-card-body form').hide();
                        $('.layui-card-body form[lay-filter="' + formFilter + '"]').show();
                    } else {
                        layer.alert('未查询到该品目，请重试！',{icon: 0});
                    }
                });
            } catch (e) {
                console.log(e);
                layer.alert('查询失败，请联系系统管理员！',{icon: 2});
            }
        }else {
            layer.msg('品目ID为空，无法查询！');
        }
    }

    // 统一事件处理
    var eventFunction = {
        // 新增品目
        addPurItem:function () {
            leftTree.unCheckNodes();// 取消选中
            $('.eleTree-node-content').removeClass('eleTree-node-content-active');
            currentPurItemId = undefined;//
            utils.btnDisabled($('.left-panel button[lay-event="deletePurItem"]'));
            // 显示对应的form表单
            form.val('add-form',{purItemType: ''});
            $('.layui-card-body form').hide();
            $('.layui-card-body form[id="add-form"]').show();
            // 修改标题
            $('.layui-card-header h3 b').text('新增品目');
            $('.layui-card-header h3 span').removeClass().addClass('subhead-red').text('（请选择品目分类）');
        },
        // 删除品目
        deletePurItem: function () {
            layer.confirm(MSG.delete_confirm + '该品目吗？', {icon: 3}, function () {
                if (utils.isNotNull(currentPurItemId)) {
                    ajax.delete(rootMapping + '/delete/' + currentPurItemId, function (success) {
                        if (success) {
                            layer.msg(MSG.delete_success);
                            currentPurItemId = undefined;
                            leftTree = leftTree.reload();
                        } else {
                            layer.msg(MSG.delete_fail);
                        }
                    });
                } else {
                    layer.msg('品目ID为空，无法删除！', {icon: 2});
                }
            });
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