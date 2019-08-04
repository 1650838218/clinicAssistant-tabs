/** 处方管理 */
//@ sourceURL=prescription.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    // eleTree: 'eleTree' //扩展模块
    ajax: 'ajax'
    ,utils: 'utils'
});
layui.use(['form', 'jquery', 'layer', 'table', 'ajax','utils'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/prescription';
    var leftTreeId = 'catalogue';
    form.render();

    // 设置左侧目录树的高度
    var bodyHeight = $(document.body).height();
    $('.left-tree').height(bodyHeight > 530 ? bodyHeight - 80 : 450);

    // ztree setting
    var setting = {
        view: {
            showLine: false,
            showIcon: false,
            expandSpeed: "normal",
            addHoverDom: function addHoverDom(treeId, treeNode) {
                var sObj = $("#" + treeNode.tId + "_span");
                if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length > 0) return;
                var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='新增下级' onfocus='this.blur();'></span>";
                sObj.after(addStr);
                var btn = $("#addBtn_"+treeNode.tId);
                if (btn) btn.bind("click", function(){
                    var leftTree = $.fn.zTree.getZTreeObj(leftTreeId);
                    // 添加节点，且进入修改状态
                    var newNode = leftTree.addNodes(treeNode, {catalogueName:"新增目录", catalogueType: 1});
                    leftTree.editName(newNode[0]);
                    return false;
                });
            },
            removeHoverDom: function removeHoverDom(treeId, treeNode) {
                $("#addBtn_"+treeNode.tId).unbind().remove();
            },
            selectedMulti: false
        },
        data: {
            key: {
                name: "catalogueName"
            },
            simpleData: {
                enable: true,
                idKey: "catalogueId",
                pIdKey: "parentCatalogueId",
                rootPId: null
            }
        },
        edit: {
            drag: {},
            editNameSelectAll: true,
            enable: true,
            removeTitle: BTN.delete,
            renameTitle: BTN.edit
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                // var leftTree = $.fn.zTree.getZTreeObj(treeId);
                // if (treeObj != null) treeObj.expandNode(treeNode);
            },
            beforeRename: function(treeId, treeNode, newName, isCancel) {
                // 节点名称不能为空，不能重复
                var leftTree = $.fn.zTree.getZTreeObj(treeId);
                if (newName != null && newName.length > 0) {
                    // 校验节点名称是否重复，没有请求后台，而是遍历整个树，判断名称是否存在
                    var selectNode = leftTree.getNodeByParam('name', newName, null);
                    if (selectNode == null) {
                        return true;
                    } else {
                        if (selectNode.tId === treeNode.tId) {
                            return true;
                        } else {
                            layer.msg(newName + '已存在！', function () {
                                leftTree.editName(treeNode);
                            });
                            return false;
                        }
                    }
                } else {
                    layer.msg('目录名称不能为空！', function () {
                        leftTree.editName(treeNode);
                    });
                    return false;
                }
            },
            onRename: function (event, treeId, treeNode, isCancel) {
                // 保存节点
                var catalogue = {};
                catalogue.catalogueId = treeNode.catalogueId;
                catalogue.catalogueName = treeNode.catalogueName;
                catalogue.catalogueType = 1;
                catalogue.parentCatalogueId = treeNode.getParentNode() == null ? '' : treeNode.getParentNode().catalogueId;
                $.ajax({
                    type: 'POST',
                    url: rootMapping + '/catalogue/save',
                    data: JSON.stringify(catalogue),
                    dataType: "JSON",
                    contentType: 'application/json',
                    success: function (result) {
                        if (result == null) {
                            // 目录名称编辑后重新保存，保存失败则刷新
                            layer.alert(treeNode.catalogueName + '保存失败，请重试！', {icon: LAYER_ICON.error}, function (index) {
                                queryCatalogue();
                                layer.close(index);
                            });
                        }
                    },
                    error: function (e) {
                        // 目录名称编辑后重新保存，保存失败则刷新
                        layer.alert(treeNode.catalogueName + '保存失败，请重试！', {icon: LAYER_ICON.error}, function (index) {
                            queryCatalogue();
                            layer.close(index);
                        });
                    }
                });
            },
            beforeRemove: function (treeId, treeNode) {
                layer.confirm(MSG.delete_confirm + '“' + treeNode.catalogueName + '”吗？',{icon:LAYER_ICON.question}, function (index) {
                    var leftTree = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.catalogueId) {
                        ajax.delete(rootMapping + '/catalogue/delete/' + treeNode.catalogueId, function (success) {
                            if (success) {
                                layer.msg(MSG.delete_success);
                                leftTree.removeNode(treeNode, false);
                            } else {
                                layer.msg(MSG.delete_fail);
                            }
                        });
                    } else {
                        layer.msg(MSG.delete_success);
                        leftTree.removeNode(treeNode, false);
                    }
                    // 节点被全部删除
                    if (leftTree.getNodes() == null) {
                        $('#blank-tip').show();
                    }
                    layer.close(index);
                });
                return false;
            }
        }
    };

    // 查询处方目录
    queryCatalogue();
    function queryCatalogue(catalogueName) {
        $.getJSON(rootMapping + '/queryCatalogue', {catalogueName: catalogueName}, function (catalogueList) {
            if (catalogueList != null && catalogueList.length > 0) {
                $.fn.zTree.init($("#" + leftTreeId), setting, catalogueList);
                fuzzySearch(leftTreeId, '.left-search input', null, true);
            } else {
                $('#blank-tip').show();
                $('.left-tree .ztree').hide();
            }
        });
    }

    // 新增第一个目录的点击事件
    $('#add-first-btn').click(function () {
        $('.left-tree .ztree').show();
        var leftTree = $.fn.zTree.init($("#" + leftTreeId), setting, []);
        var newNode = leftTree.addNodes(null, {catalogueName:"新增目录", catalogueType: 1});
        leftTree.editName(newNode[0]);
        $('#blank-tip').hide();
    });
});

