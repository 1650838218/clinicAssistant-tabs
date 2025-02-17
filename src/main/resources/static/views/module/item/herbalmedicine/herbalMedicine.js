/** 查询中药 */
//@ sourceURL=herbalMedicine.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    ajax: 'ajax'
    ,utils: 'utils'
});
layui.use(['form', 'jquery', 'layer', 'ajax','utils'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/item/herbalMedicine';
    var leftTreeId = 'catalog';
    var formId = 'herbalmedicine-form';
    var keyword = '';
    form.render();

    // 设置左侧目录树的高度
    var bodyHeight = $(document.body).height();
    $('.left-tree').height(bodyHeight > 530 ? bodyHeight - 80 : 450);
    $('.left-tree .ztree').height(bodyHeight > 530 ? bodyHeight - 80 : 450);
    // 设置右侧面板高度
    $('.right-panel .blank-tip').height(bodyHeight > 530 ? bodyHeight - 283 : 247).css('padding-top','200px');

    // ztree setting
    var setting = {
        view: {
            showLine: false,
            showIcon: false,
            expandSpeed: "normal",
            selectedMulti: false
        },
        data: {
            key: {
                name: "label"
            },
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode,clickFlag) {
                if (clickFlag === 1) {
                    // 普通选中
                    if (treeNode.isParent) {
                        // 清空表单
                        utils.clearForm('#' + formId);// 清空表单
                        form.render();
                        $('#' + formId).hide();
                        $('.right-panel .blank-tip').show();
                        utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
                    } else {
                        utils.btnEnabled($('.left-panel button[lay-event="delBtn"]'));
                        findById(treeNode.id);// 查询中药
                    }
                } else if (clickFlag === 0) {
                    // 取消选中
                    $('#' + formId).hide();
                    $('.right-panel .blank-tip').show();
                    utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
                }
            }
        }
    };

    // 动态加载品目分类下拉框
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="itemType"]'),
        where: {dictKey: DICT_KEY.ITEM_HERBAL_MEDICINE},
        tips: '请选择所属分类'
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

    // 监听 零售单位 下拉框
    form.on('select(stockUnit)', function (data) {
        var optionText = $(data.elem).find('option[value="'+ data.value + '"]').text();// 零售单位
        $(data.elem).parents('form').find('.stockWarnUnit').text(optionText);
    });

    // 搜索
    $(".left-panel .left-search .layui-input").on("input change",function() {
        if ($.trim($(this).val()) === keyword) {
            return;
        } else {
            queryCatalog($.trim($(this).val()));
            keyword = $.trim($(this).val());
        }
    });

    // 查询目录
    function queryCatalog(keyword,selectNodeId) {
        $.getJSON(rootMapping + '/queryCatalog', {keyword: keyword}, function (resultList) {
            if (resultList != null && resultList.length > 0) {
                $('.left-tree .blank-tip').hide();
                $('.left-tree .ztree').show();
                $.fn.zTree.destroy(leftTreeId);
                var zTreeObject = $.fn.zTree.init($("#" + leftTreeId), setting, resultList);
                zTreeObject.expandAll(!!keyword);// 展开所有节点
                if (utils.isNotNull(selectNodeId)) {
                    var currentNodes = zTreeObject.getNodeByParam('id', selectNodeId);
                    if (currentNodes != null) {
                        zTreeObject.selectNode(currentNodes);
                    }
                } else {
                    $('#' + formId).hide();
                    $('.right-panel .blank-tip').show();
                    utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
                }
            } else {
                $('.left-tree .blank-tip').show();
                $('.left-tree .ztree').hide();
                $('#' + formId).hide();
                $('.right-panel .blank-tip').show();
                utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
            }
        });
    }
    queryCatalog();

    // 根据ID查询
    function findById(id) {
        if (utils.isNotNull(id)) {
            $.getJSON(rootMapping + '/findById', {id: id}, function (result) {
                if (result != null && utils.isNotNull(result.itemId)) {
                    form.val(formId, result);
                    $('.right-panel .blank-tip').hide();
                    $('#' + formId).show();
                } else {
                    layer.alert('未找到该中药，请重试！',{icon: 0}, function (index) {
                        queryCatalog();
                        utils.clearForm('#' + formId);// 清空表单
                        form.render();
                        layer.close(index);
                    });
                }
            });
        } else {
            layer.msg('中药ID为空，无法查询！', {icon:2});
        }
    }

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

    // 保存
    form.on('submit(submit-btn)', function (data) {
        var formData = data.field;
        var formId = data.form.id;
        var mapping = rootMapping + "/save";
        ajax.postJSON(mapping, formData, function (item) {
            if (item != null && utils.isNotNull(item.itemId)) {
                layer.msg(MSG.save_success);
                form.val(formId,{itemId:item.itemId});
                queryCatalog($.trim($('.left-panel .left-search .layui-input').val()),item.itemId);
            } else {
                layer.msg(MSG.save_fail);
            }
        }, data.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('#cancel-btn').click(function () {
        var itemId = $('#' + formId + ' input[name="itemId"]').val();
        if (utils.isNotNull(itemId)) {
            findById(itemId);// 重新查询
        } else {
            utils.clearForm('#' + formId);// 清空表单
            form.render();
        }
    });

    // 统一事件处理
    var eventFunction = {
        // 新增
        addBtn: function () {
            utils.clearForm('#' + formId);// 清空表单
            $('.right-panel .blank-tip').hide();
            $('#' + formId).show();
            form.val(formId, {isPoison:0,purUnit:2,stockUnit:1,unitConvert:500});
            form.render();
            var zTreeObject = $.fn.zTree.getZTreeObj(leftTreeId);
            if (zTreeObject != null) zTreeObject.cancelSelectedNode();
            utils.btnDisabled($('.left-panel button[lay-event="delBtn"]'));
        },
        // 删除
        delBtn: function () {
            var zTreeObject = $.fn.zTree.getZTreeObj(leftTreeId);
            var selectNodes = zTreeObject.getSelectedNodes();
            if (utils.isNotEmpty(selectNodes) && !selectNodes[0].isParent) {
                layer.confirm(MSG.delete_confirm + '该中药吗？', {icon: 3}, function () {
                    ajax.delete(rootMapping + '/delete/' + selectNodes[0].id, function (success) {
                        if (success) {
                            layer.msg(MSG.delete_success);
                            zTreeObject.removeNode(selectNodes[0]);
                        } else {
                            layer.msg(MSG.delete_fail);
                        }
                    });
                });
            } else {
                layer.msg('请选择一个中药！', {icon: LAYER_ICON.warning});
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