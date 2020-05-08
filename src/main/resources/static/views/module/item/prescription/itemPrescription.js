/** 方剂管理 */
//@ sourceURL=prescription.js
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
    var rootMapping = '/item/prescription';
    var leftTreeId = 'catalog';
    var formId = 'prescription-form';
    form.render();

    // 设置左侧目录树的高度
    var bodyHeight = $(document.body).height();
    $('.left-tree').height(bodyHeight > 530 ? bodyHeight - 80 : 450);
    // 设置右侧面板高度
    $('.right-panel .blank-tip').height(bodyHeight > 530 ? bodyHeight - 83 : 447).css('padding-top','200px');

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
                name: "catalogName"
            },
            simpleData: {
                enable: true,
                idKey: "catalogId",
                pIdKey: "parentCatalogId",
                rootPId: null
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode,clickFlag) {
                if (clickFlag === 1) {
                    $('.right-panel .blank-tip').hide();
                    $('#' + formId).show();
                    if (treeNode.catalogType === 1) {
                        // 清空表单
                        utils.clearForm('#' + formId);// 清空表单
                        form.render();
                    } else {
                        getPrescriptionByCatalogId(treeNode.catalogId);// 查询方剂
                    }
                } else if (clickFlag === 0) {
                    $('#' + formId).hide();
                    $('.right-panel .blank-tip').show();
                }
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
                var catalog = {};
                catalog.catalogId = treeNode.catalogId;
                catalog.catalogName = treeNode.catalogName;
                catalog.catalogType = 1;
                catalog.parentCatalogId = treeNode.getParentNode() == null ? '' : treeNode.getParentNode().catalogId;
                catalog.catalogOrder = treeNode.getIndex();
                $.ajax({
                    type: 'POST',
                    url: rootMapping + '/catalog/save',
                    data: JSON.stringify(catalog),
                    dataType: "JSON",
                    contentType: 'application/json',
                    success: function (result) {
                        if (result == null) {
                            // 目录名称编辑后重新保存，保存失败则刷新
                            layer.alert(treeNode.catalogName + '保存失败，请重试！', {icon: LAYER_ICON.error}, function (index) {
                                queryCatalog();
                                layer.close(index);
                            });
                        } else {
                            treeNode.catalogId = result.catalogId;
                            // 清空表单
                            utils.clearForm('#' + formId);// 清空表单
                            form.render();
                            $('.right-panel .blank-tip').hide();
                            $('#' + formId).show();
                        }
                    },
                    error: function (e) {
                        // 目录名称编辑后重新保存，保存失败则刷新
                        layer.alert(treeNode.catalogName + '保存失败，请重试！', {icon: LAYER_ICON.error}, function (index) {
                            queryCatalog();
                            layer.close(index);
                        });
                    }
                });
            },
            beforeRemove: function (treeId, treeNode) {
                layer.confirm(MSG.delete_confirm + '“' + treeNode.catalogName + '”吗？',{icon:LAYER_ICON.question}, function (index) {
                    var leftTree = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.catalogId) {
                        ajax.delete(rootMapping + '/catalog/delete/' + treeNode.catalogId, function (success) {
                            if (success) {
                                layer.msg(MSG.delete_success);
                                leftTree.removeNode(treeNode, false);
                                // 表单显示空白
                                $('#' + formId).hide();
                                $('.right-panel .blank-tip').show();
                                if (leftTree.getNodes() == null) {
                                    $('#blank-tip').show();
                                }
                            } else {
                                layer.msg(MSG.delete_fail);
                            }
                        });
                    } else {
                        layer.msg(MSG.delete_success);
                        leftTree.removeNode(treeNode, false);
                        if (leftTree.getNodes() == null) {
                            $('#blank-tip').show();
                        }
                    }
                    layer.close(index);
                });
                return false;
            }
        }
    };

    // 查询方剂目录
    function queryCatalog(catalogName) {
        $.getJSON(rootMapping + '/queryCatalog', {catalogName: catalogName}, function (catalogList) {
            if (catalogList != null && catalogList.length > 0) {
                $.fn.zTree.init($("#" + leftTreeId), setting, catalogList);
            } else {
                $('#blank-tip').show();
                $('.left-tree .ztree').hide();
            }
        });
    }
    queryCatalog();

    // 根据方剂ID查询方剂
    function findPrescriptionById(prescriptionId) {
        if (utils.isNotNull(prescriptionId)) {
            $.getJSON(rootMapping + '/findPrescriptionById', {prescriptionId: prescriptionId}, function (rxDetail) {
                if (rxDetail != null) {
                    form.val(formId, rxDetail);
                } else {
                    layer.alert('未找到该方剂，请重试！',{icon: 0}, function (index) {
                        queryCatalog();
                        utils.clearForm('#' + formId);// 清空表单
                        form.render();
                        layer.close(index);
                    });
                }
            });
        } else {
            layer.msg('方剂ID为空，无法查询！', {icon:2});
        }
    }

    // 保存方剂
    form.on('submit(submit-btn)', function (data) {
        var formData = {};
        formData.rxDetail = data.field;
        var leftTree = $.fn.zTree.getZTreeObj(leftTreeId);
        var selectNode = leftTree.getSelectedNodes();
        if (selectNode != null && selectNode.length > 0) {
            if (!formData.rxDetail.rxId) {// 新增方剂，方剂ID为空
                var rxCatalog = {};
                rxCatalog.parentCatalogId = selectNode[0].catalogId;
                rxCatalog.catalogName = formData.rxDetail.rxName;
                rxCatalog.catalogType = 2;
                rxCatalog.catalogOrder = selectNode[0].children == null ? 0 : selectNode[0].children.length;
                formData.rxCatalog = rxCatalog;
            }
            ajax.postJSON(rootMapping + '/prescription/save', formData, function (rxDetail) {
                if (rxDetail != null && utils.isNotNull(rxDetail.rxId) && utils.isNotNull(rxDetail.catalogId)) {
                    layer.msg(MSG.save_success);
                    form.val(formId, rxDetail);
                    // 手动增加目录节点
                    $.getJSON(rootMapping + '/findCatalogById', {catalogId: rxDetail.catalogId}, function (catalog) {
                        var leftTree = $.fn.zTree.getZTreeObj(leftTreeId);
                        var selectNode = leftTree.getSelectedNodes();
                        if (selectNode != null && selectNode.length > 0) {
                            if (selectNode[0].catalogType === 1) {// 当前方剂是新增的
                                var newNode = leftTree.addNodes(selectNode[0], catalog);
                                leftTree.selectNode(newNode[0]);
                            } else {// 当前方剂是修改的
                                // leftTree.addNodes(selectNode[0].getParentNode(), catalog);
                                selectNode[0].catalogName = catalog.catalogName;
                                leftTree.updateNode(selectNode[0]);
                            }
                        }
                    });
                } else {
                    layer.msg(MSG.save_fail);
                }
            }, data.elem);
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消
    $('#cancel-btn').click(function () {
        var rxId = $('#' + formId + ' input[name="rxId"]').val();
        if (rxId !== null && rxId !== undefined && rxId !== '') {
            findPrescriptionById(rxId);// 重新查询
        } else {
            utils.clearForm('#' + formId);// 清空表单
            form.render();
        }
    });

    // 统一事件处理
    var eventFunction = {
        // 新增
        addCatalogBtn: function () {
            var html = '';
            html += '<form class="layui-form" lay-filter="addCatalogForm">';
            html += '   <div class="layui-form-item">';
            html += '       <label class="layui-form-label">目录名称：</label>';
            html += '       <div class="layui-input-block">';
            html += '           <input type="text" name="title" required  lay-verify="required" placeholder="请输入目录名称" autocomplete="off" class="layui-input">';
            html += '       </div>';
            html += '   </div>';
            html += '   <div class="layui-form-item">';
            html += '       <label class="layui-form-label">目录类型：</label>';
            html += '       <div class="layui-input-block">';
            html += '           <select name="city" lay-verify="required">';
            html += '               <option value="1">分类</option>';
            html += '               <option value="2" selected>方剂</option>';
            html += '           </select>';
            html += '       </div>';
            html += '   </div>';
            html += '</form>';
            layer.open({
                title: '新增目录',
                content: html,
                area:['500px', '300px'],
                btnAlign: 'c',
                success: function () {
                    form.render('select','addCatalogForm');
                },
                yes: function () {

                },
                cancel: function () {

                }
            });
        },
        // 删除
        // 取消
        cancelBtn: function () {
            var treeObject = $.fn.zTree.getZTreeObj(leftTreeId);
            var selectNodes = treeObject.getSelectedNodes();
            if (selectNodes) {
                var node = selectNodes[0];
                if (node.catalogType == 1) {
                    getRxCatalogById(node.catalogId);// 方剂目录
                } else if(node.catalogType == 2) {
                    findPrescriptionById(node.catalogId); // 方剂
                }
            } else {
                layer.msg('请选择一个方剂！');
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

