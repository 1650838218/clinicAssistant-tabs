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
    var leftTableId = 'left-table';
    var rootMapping = '/purchase/item';
    var leftTree = undefined;// 左侧菜单树
    form.render();

    // 设置高度
    $('.left-panel').height($(window).height() - 40);
    $('#left-tree').height($('.left-panel').height() - 40);
    $('.right-panel').height($(window).height() - 20);
    $('.right-panel .blank-tip').height($('.right-panel').height() - 43 -20);

    // 加载左侧菜单树
    leftTree = eleTree.render({
        elem: '#left-tree',
        url: rootMapping + "/queryTree",
        method: "get",
        emptText: '暂无数据！',
        highlightCurrent: true,// 高亮显示当前节点
        defaultExpandAll: false,// 默认展开所有节点
        expandOnClickNode: false,// 单击展开
        checkOnClickNode: true,// 单击选中
        showCheckbox: false,// 是否显示复选框
        searchNodeMethod: function(value,data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        done: function (res) {
            /*if (currentMenuId) {
                $('.ele1 div.eleTree-node[data-id="' + currentMenuId + '"] > .eleTree-node-content').trigger('click');
            }*/
        }
    });
    /*
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

        // 新增 品目
        $('#add-btn').click(function () {
            /!*utils.clearForm('#item-form');// 清空表单
            form.val('item-form', {
                pharmacyItemType: 1,
                poisonous: 'false',
                stockUnit: 1
            });// 设置初始值
            form.render();*!/
            var index = layer.open({
                title: '新增品目',
                content: '',
                area: ['500px','300px'],// 宽500，高300
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {

                },
                btn2: function () {

                }
            });
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
        });*/

    // 统一事件处理
    var eventFunction = {
        // 新增品目
        addPurItem:function () {
            var formHtml = '';
            formHtml += '<form class="layui-form" lay-filter="purItemTypeDialog">';
            formHtml += '   <div class="layui-form-item">';
            formHtml += '       <label class="layui-form-label"><span class="required">*</span>品目名称：</label>';
            formHtml += '       <div class="layui-input-block">';
            formHtml += '           <input type="text" name="title" placeholder="请填写品目名称" autocomplete="off" class="layui-input">';
            formHtml += '       </div>';
            formHtml += '   </div>';
            formHtml += '   <div class="layui-form-item">';
            formHtml += '       <label class="layui-form-label">选择框</label>';
            formHtml += '       <div class="layui-input-block">';
            formHtml += '           <select name="city" lay-verify="required">';
            formHtml += '               <option value=""></option>';
            formHtml += '               <option value="0">北京</option>';
            formHtml += '               <option value="1">上海</option>';
            formHtml += '               <option value="2">广州</option>';
            formHtml += '               <option value="3">深圳</option>';
            formHtml += '               <option value="4">杭州</option>';
            formHtml += '           </select>';
            formHtml += '       </div>';
            formHtml += '   </div>';
            formHtml += '</form>';
            var index = layer.open({
                title: '新增品目',
                content: formHtml,
                area: ['500px','300px'],// 宽500，高300
                btn: ['确定', '取消'],
                btnAlign: 'c',
                success: function (layero, index) {
                    form.render(null,'purItemTypeDialog');
                },
                yes: function () {

                },
                btn2: function () {

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