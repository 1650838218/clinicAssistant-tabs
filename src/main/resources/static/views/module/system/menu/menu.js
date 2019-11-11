/** 菜单管理 */
//@ sourceURL=menu.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    eleTree: 'eleTree' //扩展模块
});
layui.use(['form', 'eleTree', 'jquery', 'layer'], function () {
    var $ = layui.jquery;
    var eleTree = layui.eleTree;
    var form = layui.form;
    var layer = layui.layer;
    var leftMenuTree;// 左侧菜单树
    var selectMenuTree = '';// 下拉树
    var currentMenuId = '';// 当前选中的菜单ID
    form.render('checkbox', 'component-form-element');

    $('#add-menu-btn').on('click', resetForm);// 新增菜单
    $('#del-menu-btn').on('click', delMenuFun);// 删除菜单
    form.on('submit(submit-btn)', saveMenu);// 保存菜单
    $('#reset-btn').on('click', resetForm);// 重置菜单
    $("input[name='parentMenuName']").on("click", loadSelectTree); // 加载下拉树
    eleTree.on("nodeClick(eleTree-menu)", menuTreeClick);// 左侧菜单树点击事件

    // 加载左侧菜单树
    leftMenuTree = eleTree.render({
        elem: '.ele1',
        url: "/system/menu/queryTree",
        method: "get",
        emptText: '暂无数据！',
        highlightCurrent: true,// 高亮显示当前节点
        defaultExpandAll: true,// 默认展开所有节点
        expandOnClickNode: false,// 单击展开
        checkOnClickNode: true,// 单击选中
        showCheckbox: false,// 是否显示复选框
        searchNodeMethod: function(value,data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        done: function (res) {
            if (currentMenuId) {
                $('.ele1 div.eleTree-node[data-id="' + currentMenuId + '"] > .eleTree-node-content').trigger('click');
            }
        }
    });

    // 搜索
    $(".left-panel .left-search .eleTree-search").on("input change",function() {
        if (!!leftMenuTree) leftMenuTree.search($.trim($(this).val()));
    });

    function renderSelectTree(menuId) {
        selectMenuTree = eleTree.render({
            elem: '.select-tree',
            url: "/system/menu/querySelectTree",
            where: {"menuIds": menuId},
            method: "get",
            defaultExpandAll: true,
            expandOnClickNode: false,
            highlightCurrent: true
        });
    }

    // 左侧菜单树的点击事件 根据ID查询菜单详情
    function menuTreeClick(d) {
        var menuId = d.data.currentData.id;
        currentMenuId = menuId;
        $(".select-tree").hide();// 隐藏下拉树
        try {
            renderSelectTree(menuId);
            $.getJSON('/system/menu/getById', {menuId:menuId}, function (menuData) {
                if (!!menuData && !!menuData.menuId) {
                    assigForm(menuData, '编辑菜单');// 给表单赋值
                } else {
                    layer.alert('查询失败！');
                }
            });
        } catch (e) {
            console.log(e);
            layer.alert('查询失败！');
        }
    };

    /**
     * 重置表单
     */
    function resetForm() {
        // 表单清空
        currentMenuId = '';
        renderSelectTree();
        assigForm({
            menuId : '',
            menuName: '',
            parentMenuId: '',
            parentMenuName: '',
            menuUrl: '',
            menuOrder: '',
            isUse:1
        }, '新增菜单');
        // 删除高亮
        if (!!leftMenuTree) leftMenuTree.reload();
    }

    /**
     * 给表单赋值
     * @param data
     */
    function assigForm(data, title) {
        // 表单赋值
        form.val("component-form-element", data);
        if (data.isUse == "1") {
            $('#menu-info input[name="isUse"]').attr('checked', 'checked');
        } else {
            $('#menu-info input[name="isUse"]').removeAttr('checked');
        }
        if (data.parentMenuId) {
            $('.select-tree div.eleTree-node[data-id="' + data.parentMenuId + '"] > .eleTree-node-content').trigger('click');
        }
        $('.layui-card-header h3 b').html(title);
    }

    /**
     * 删除菜单
     */
    function delMenuFun() {
        var menuId =  $('#menu-info input[name="menuId"]').val();
        if (!!menuId) {
            try {
                layer.confirm('确认删除此菜单吗？',{icon:3,title:'提示'},function (index) {
                    $.ajax({
                        url:'/system/menu/delete/' + menuId,
                        type:'delete',
                        success:function (data, textStatus, jqXHR) {
                            if (data) {
                                layer.msg('删除成功！');
                                resetForm();
                            } else {
                                layer.msg('删除失败！');
                            }
                        },
                        error:function () {
                            layer.msg('删除失败！');
                        }
                    });
                    layer.close(index);
                });
            } catch (e) {
                layer.msg('删除失败！');
            }
        } else {
            layer.msg('请先选择一条记录！');
        }
    };

    /**
     * 保存菜单
     * @param data
     * @returns {boolean}
     */
    function saveMenu(data) {
        try {
            $(data.elem).addClass('layui-btn-disabled');// 按钮禁用，防止重复提交
            data.field.isUse = data.field.isUse == "on" ? "1" : "0";
            $.post('/system/menu/save', data.field, function (menu) {
                if (!!menu && !!menu.menuId) {
                    assigForm(menu, '编辑菜单');// 赋值
                    currentMenuId = menu.menuId;
                    if (!!leftMenuTree) leftMenuTree.reload();
                    layer.msg('保存成功！');
                } else {
                    layer.msg('保存失败！');
                }
                $(data.elem).removeClass('layui-btn-disabled');// 按钮可用
            });
        } catch (e) {
            $(data.elem).removeClass('layui-btn-disabled');// 按钮可用
            layer.msg('保存失败！');
            console.log(e);
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    };

    // 查询加载树形下拉框的内容
    function loadSelectTree(e) {
        e.stopPropagation();
        var menuId = $('#menu-info input[name="menuId"]').val();
        if (!selectMenuTree) {
            renderSelectTree(menuId);
        }
        $(".select-tree").toggle();
    };

    // 下拉树选中事件
    eleTree.on("nodeClick(eleTree-parent-menu)", function (d) {
        $("[name='parentMenuId']").val(d.data.currentData.id);
        $("[name='parentMenuName']").val(d.data.currentData.label);
        $(".select-tree").hide();
    });

    // 下拉树隐藏
    $(document).on("click", function () {
        $(".select-tree").hide();
    });

});

