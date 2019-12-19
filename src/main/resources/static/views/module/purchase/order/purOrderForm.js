/** 采购单 */
//@ sourceURL=purOrderForm.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['form','utils', 'jquery', 'layer', 'table', 'ajax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var laydate = layui.laydate;
    var rootMapping = '/purchase/order';
    var itemTableId = 'purorder-table';
    var formId = 'purorder-form';
    var medicineLayer = null;
    form.render();

    // 动态加载供应商
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="supplierId"]'),
        tips: '请选择供应商',
        url:'/purchase/supplier/getSelectOption',
        realValueName: 'realValue',
        displayValueName: 'displayValue',
    });
    // 动态加载日期控件
    laydate.render({
        elem: '#' + formId + ' input[name="purOrderDate"]',
        value: new Date(),
        isInitValue: true
    });
    // 生成单号
    var date = new Date();
    var ymd = date.format('yyMMdd');
    var num = date.getHours() + date.getMinutes() + date.getSeconds() + date.getMilliseconds();
    $('#' + formId + ' input[name="purOrderCode"]').val(ymd + num);


    // 判断是否可以进行编辑，返回true 可以编辑，false 不可以编辑
    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined) return true;
        if ($('#' + itemTableId).datagrid('validateRow', editIndex)){
            $('#' + itemTableId).datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    // 开始编辑一行
    function beginEditing(rowIndex,field) {
        editIndex = rowIndex;
        $('#' + itemTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
        var ed = $('#' + itemTableId).datagrid('getEditor', {index:rowIndex,field:field});
        if (ed){
            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
        }
    }

    // 初始化表格
    $('#' + itemTableId).datagrid({
        toolbar: [
            {
                text: '添加',
                iconCls: 'icon-add',
                handler: function () {
                    if (endEditing()) {
                        $('#' + itemTableId).datagrid('appendRow', {purItem: {}});
                        editIndex = $('#' + itemTableId).datagrid('getRows').length - 1;
                        $('#' + itemTableId).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
                    }
                    // setEditing(editIndex);//此句较为重要
                }
            }, '-',
            {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    if (editIndex == undefined) {
                        layer.msg(MSG.select_one);
                        return;
                    }
                    $('#' + itemTableId).datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
                    editIndex = undefined;
                    // 如果明细行被全部删除，则新增一个空白行
                    if ($('#' + itemTableId).datagrid('getData').total === 0) {
                        $('#' + itemTableId).datagrid('appendRow', {purItem: {}});
                    }
                }
            }, '-',
            {
                text: '附件',
                iconCls: 'icon-remove',
                handler: function () {

                }
            }
        ],
        data: [{purItem:{}}],
        onClickCell: function (rowIndex, field, value) {
            if (editIndex != rowIndex){
                if (endEditing()){
                    beginEditing(rowIndex, field);
                } else {
                    setTimeout(function(){
                        $('#' + itemTableId).datagrid('selectRow', editIndex);
                    },0);
                }
            }
        },
        onEndEdit: function (index, row) {
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'purItemId'
            });
            row.purItemName = $(ed.target).combogrid('getText');
        }
    });

    // 动态设置列的editor和其他属性
    var columns = [
        {
            field: 'purItemId',
            editor: {
                type: 'combogrid',
                options: {
                    idField:'purItemId',
                    textField:'purItemName',
                    method: 'get',
                    url: '/purchase/item/getCombogrid',
                    mode: 'remote',
                    columns:[[
                        {field:'purItemName',title:'品目名称',width:150},
                        {field:'purItemTypeName',title:'品目分类',width:80},
                        {field:'specifications',title:'规格',width:120},
                        {field:'producer',title:'厂家/品牌/制造商',width:200}
                    ]],
                    required: true,
                    panelHeight:'auto',
                    panelMaxHeight: 200,
                    panelWidth:568,
                    hasDownArrow: false,
                    onSelect: function(index,purItem){
                        // 设置进货包装
                        var editor3 = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'purUnitName'});
                        $(editor3.target).textbox('setValue', purItem.purUnitName);
                    },
                    onChange: function (newValue, oldValue) {
                        if (!utils.isNotNull(newValue)) {
                            // 清空进货包装
                            var editor3 = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'purUnitName'});
                            $(editor3.target).textbox('clear');
                        }
                    },
                    onHidePanel: function () {
                        // 验证药品名称是否为空
                        var editor = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'purItemId'});
                        var purItemId = $(editor.target).combogrid('getValue');
                        if (!purItemId) {
                            $(editor.target).combogrid('clear');// 为空则清空输入框
                        }
                    }
                }
            },
            formatter:function(value,row){
                return row.purItemName;
            }
        },
        {
            field: 'purCount',
            editor: {
                type:'numberbox',
                options:{
                    precision:4,
                    required: true,
                    onChange: function (newValue,oldValue) {
                        var unitPrice = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'unitPrice'});
                        var totalPrice = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'totalPrice'});
                        $(totalPrice.target).numberbox('setValue', newValue * $(unitPrice.target).numberbox('getValue'));
                    }
                }
            },
            formatter: function (value, row) {
                if (value != null) return (value - 0).toFixed(2);
            }
        },
        {
            field: 'purUnitName',
            editor: {
                type: 'textbox',
                options: {
                    editable: false,
                    readonly: true
                }
            },
            formatter:function(value,row){
                return row.purUnitName;
            }
        },
        {
            field: 'unitPrice',
            editor: {
                type:'numberbox',
                options:{
                    precision:2,
                    required: true,
                    onChange: function (newValue,oldValue) {
                        var purCount = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'purCount'});
                        var totalPrice = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'totalPrice'});
                        $(totalPrice.target).numberbox('setValue', newValue * $(purCount.target).numberbox('getValue'));
                    }
                }
            },
            formatter: function (value, row) {
                if (value != null) return (value - 0).toFixed(2);
            }
        },
        {
            field: 'totalPrice',
            editor: {
                type:'numberbox',
                options:{
                    precision:2,
                    required: true
                }
            },
            formatter: function (value, row) {
                if (value != null) return (value - 0).toFixed(2);
            }
        }
    ];
    for (var i = 0, l = columns.length; i < l; i++) {
        var e = $('#' + itemTableId).datagrid('getColumnOption', columns[i].field);
        console.log(columns[i]);
        e.editor = columns[i].editor;
        e.formatter = columns[i].formatter;
    }

    // 明细表格校验
    function validateGrid() {
        var gridData = $('#' + itemTableId).datagrid('getData');
        var rowCount = gridData.total;
        if (rowCount === 0) {
            layer.msg("采购单至少要有一条明细数据！");
            return false;
        }
        if (endEditing()) {
            for (var i = 0; i < rowCount; i++) {
                var purItemId = gridData.rows[i].purItemId;
                var totalPrice = gridData.rows[i].totalPrice;
                if (!utils.isNotNull(purItemId) || !utils.isNotNull(totalPrice)) {
                    beginEditing(i, 'purItemId');
                    layer.msg("请将明细数据补充完整！");
                    return false;
                }
            }
        } else {
            setTimeout(function(){
                $('#' + itemTableId).datagrid('selectRow', editIndex);
            },0);
            layer.msg("请将明细数据补充完整！");
            return false;
        }
        return true;
    }

    // 根据ID查询采购单
    function queryPurOrderById(purOrderId) {
        if (utils.isNotNull(purOrderId)) {
            $.getJSON(rootMapping + "/queryById",{purOrderId: purOrderId}, function (purOrder) {
                if (purOrder != null) {
                    purOrder.totalPrice = parseFloat(purOrder.totalPrice).toFixed(2);
                    form.val('order-form', purOrder);// 表单赋值
                    form.render();
                    $('#' + itemTableId).datagrid('loadData', purOrder.purOrderDetails);// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 总分校验，总金额不能大于明细金额之和；true：通过，false：不通过
    function generalBranchCheck(totalAmount, orderItems) {
        var totalItems = 0;
        for (var i = 0; i < orderItems.length; i++) {
            totalItems += (orderItems[i].totalPrice - 0);
        }
        return totalAmount <= totalItems;
    }

    // 保存采购单
    function savePurOrder(obj, purOrder) {
        purOrder.purOrderType = 1;// 采购单类型，药品采购单
        ajax.postJSON(rootMapping + '/save', purOrder, function (order) {
            if (order != null && order.purOrderId != null) {
                queryPurOrderById(order.purOrderId);// 根据ID查询采购单，并赋值
                layer.msg(MSG.save_success);
            } else {
                layer.msg(MSG.save_fail);
            }
            utils.btnEnabled($(obj.elem));
        }, $(obj.elem));
    }

    /**
     * 保存采购单
     * @param data
     * @returns {boolean}
     */
    form.on('submit(submit-btn)', function(obj){
        utils.btnDisabled($(obj.elem));
        if (validateGrid()) {
            var purOrder = obj.field;// 表单值
            var orderItems = $('#' + itemTableId).datagrid('getData');// 获取表格数据
            // console.log(orderItems);
            purOrder.purOrderDetails = orderItems.rows;
            if (!generalBranchCheck(purOrder.totalPrice, purOrder.purOrderDetails)) {
                layer.confirm('采购单总金额大于明细总价之和，数据存在错误风险，确认保存吗？',
                    {
                        icon: LAYER_ICON.question,
                        btn2: function(index, layero) {
                            utils.btnEnabled($(obj.elem));
                        },
                        cancel: function (index, layero) {
                            utils.btnEnabled($(obj.elem));
                        }
                    },
                    function (index) {
                        savePurOrder(obj, purOrder);
                        layer.close(index);
                });
            }else {
                savePurOrder(obj, purOrder);
            }
        } else {
            utils.btnEnabled($(obj.elem));
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 新建按钮点击事件
    $('button[lay-filter="create-btn"]').click(function () {
        window.location.reload();
    });

    // 转到查询列表
    $('button[lay-filter="query-btn"]').click(function () {
        top.layui.index.openTabsPage('/views/module/purchase/order/purOrderQuery.html','查询采购单');
    });

    // 关闭按钮点击事件
    $('button[lay-filter="close-btn"]').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    // 格式化总金额
    $('#'+ formId + ' input[name="totalPrice"]').bind('change', function () {
        var value = $(this).val();
        $(this).val((value - 0).toFixed(2));
    });

    // 弹框显示供应商信息
    $('.layui-form-label-icon .layui-icon').click(function () {
        // 获取被选中的供应商
        var supplierId = $('#' + formId).find('select[name="supplierId"]').val();
        if (utils.isNotNull(supplierId)) {
            // ajax请求，根据供应商ID查询供应商
            $.getJSON('/purchase/supplier/findById',{supplierId: supplierId}, function (supplier) {
                if (supplier != null) {
                    var content = '';
                    content += '<form class="layui-form" action="">';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">供应商名称：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.supplierName + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系人1：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.linkMan1 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系电话1：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.phone1 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系人2：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.linkMan2 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">联系电话2：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.phone2 + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">主营：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.mainProducts + '</div>';
                    content += '</div>';
                    content += '<div class="layui-form-item">';
                    content += '<label class="layui-form-label">地址：</label>';
                    content += '<div class="layui-form-mid layui-word-aux">' + supplier.address + '</div>';
                    content += '</div>';
                    content += '</form>';
                    layer.open({ title: '供应商信息',area: '500px', content: content});
                } else {
                    layer.alert('未找到该供应商，请检查该供应商是否存在！',{icon: LAYER_ICON.warning});
                }
            });
            // 显示供应商信息
        } else {
            layer.msg('请先选择一个供应商！');
        }
    });

    // 解析url中的参数，如果包含采购单id，则自动查询采购单详情
    var search = window.location.search;
    if (search.length > 0) {
        var condition = search.substr(1).split('&');
        for (var i = 0; i < condition.length; i++) {
            var keyVal = condition[i].split('=');
            if (keyVal[0] === 'purOrderId') {
                $('button[lay-filter="cancel-btn"]').hide();
                $('button[lay-filter="close-btn"]').show();
                queryPurOrderById(keyVal[1]);
                break;
            }
        }
    }
});



