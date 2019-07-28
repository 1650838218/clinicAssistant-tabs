/** 采购单 */
//@ sourceURL=purchaseOrderForm.js
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
    var rootMapping = '/pharmacy/purchaseorder';
    var itemTableId = 'purchaseorder-table';
    var formId = 'purchaseorder-form';
    var medicineLayer = null;
    form.render();

    // 动态加载供应商
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="supplierId"]'),
        tips: '请选择供应商',
        url:'/pharmacy/supplier/getSelectOption',
        realValueName: 'realValue',
        displayValueName: 'displayValue',
    });
    // 动态加载日期控件
    laydate.render({
        elem: '#' + formId + ' input[name="purchaseOrderDate"]',
        value: new Date(),
        isInitValue: true
    });
    // 生成单号
    var date = new Date();
    var ymd = date.format('yyMMdd');
    var num = date.getHours() + date.getMinutes() + date.getSeconds() + date.getMilliseconds();
    $('#purchaseorder-form input[name="purchaseOrderCode"]').val(ymd + num);


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
                        $('#' + itemTableId).datagrid('appendRow', {pharmacyItem: {}});
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
                        $('#' + itemTableId).datagrid('appendRow', {pharmacyItem: {}});
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
        data: [{pharmacyItem:{}}],
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
                field: 'pharmacyItemId'
            });
            row.pharmacyItemName = $(ed.target).combogrid('getText');
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'purchaseUnit'
            });
            row.purchaseUnitName = $(ed.target).combobox('getText');
        }
    });

    // 动态设置列的editor和其他属性
    var columns = [
        {
            field: 'pharmacyItemId',
            editor: {
                type: 'combogrid',
                options: {
                    idField:'pharmacyItemId',
                    textField:'pharmacyItemName',
                    method: 'get',
                    url: '/pharmacy/pharmacyitem/getCombogrid',
                    mode: 'remote',
                    columns:[[
                        {field:'pharmacyItemName',title:'药品名称',width:150},
                        {field:'pharmacyItemTypeName',title:'药品类型',width:80},
                        {field:'specifications',title:'规格',width:120},
                        {field:'manufacturer',title:'制造商',width:200}
                    ]],
                    required: true,
                    panelHeight:'auto',
                    panelMaxHeight: 200,
                    panelWidth:568,
                    hasDownArrow: false,
                    onSelect: function(index,pharmacyItem){
                        // 设置规格和制造商的值
                        var editor1 = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'specifications'});
                        $(editor1.target).textbox('setValue', pharmacyItem.specifications);
                        var editor2 = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'manufacturer'});
                        $(editor2.target).textbox('setValue', pharmacyItem.manufacturer);
                    },
                    onChange: function (newValue, oldValue) {
                        if (!utils.isNotNull(newValue)) {
                            // 清空规格和制造商
                            var editor1 = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'specifications'});
                            $(editor1.target).textbox('clear');
                            var editor2 = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'manufacturer'});
                            $(editor2.target).textbox('clear');
                        }
                    },
                    onHidePanel: function () {
                        // 验证药品名称是否为空
                        var editor = $('#' + itemTableId).datagrid('getEditor',{index: editIndex,field: 'pharmacyItemId'});
                        var pharmacyItemId = $(editor.target).combogrid('getValue');
                        if (!pharmacyItemId) {
                            $(editor.target).combogrid('clear');
                        }
                    }
                }
            },
            formatter:function(value,row){
                return row.pharmacyItemName;
            }
        },
        {
            field: 'specifications',
            editor: {
                type: 'textbox',
                options: {
                    editable: false,
                    readonly: true
                }
            },
            formatter:function(value,row){
                return row.specifications;
            }
        },
        {
            field: 'manufacturer',
            editor: {
                type: 'textbox',
                options: {
                    editable: false,
                    readonly: true
                }
            },
            formatter:function(value,row){
                return row.manufacturer;
            }
        },
        {
            field: 'purchaseCount',
            editor: {
                type:'numberbox',
                options:{
                    precision:2,
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
            field: 'purchaseUnit',
            editor: {
                type: 'combobox',
                options: {
                    valueField: 'dictItemValue',
                    textField: 'dictItemName',
                    method: 'get',
                    url: '/system/dictionary/oneLevel/getItemByKey?dictTypeKey=SLDW',
                    required: true,
                    mode: 'remote',
                    panelHeight:'auto',
                    panelMaxHeight: 200,
                    editable: false,
                    hasDownArrow: false
                }
            },
            formatter:function(value,row){
                return row.purchaseUnitName;
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
                        var purchaseCount = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'purchaseCount'});
                        var totalPrice = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'totalPrice'});
                        $(totalPrice.target).numberbox('setValue', newValue * $(purchaseCount.target).numberbox('getValue'));
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
                var pharmacyItemId = gridData.rows[i].pharmacyItemId;
                var totalPrice = gridData.rows[i].totalPrice;
                if (!utils.isNotNull(pharmacyItemId) || !utils.isNotNull(totalPrice)) {
                    beginEditing(i, 'pharmacyItemId');
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
    function queryPurchaseOrderById(purchaseOrderId) {
        if (utils.isNotNull(purchaseOrderId)) {
            $.getJSON(rootMapping + "/queryById",{purchaseOrderId: purchaseOrderId}, function (purchaseOrder) {
                if (purchaseOrder != null) {
                    purchaseOrder.totalPrice = parseFloat(purchaseOrder.totalPrice).toFixed(2);
                    form.val('purchaseorder-form', purchaseOrder);// 表单赋值
                    form.render();
                    $('#' + itemTableId).datagrid('loadData', purchaseOrder.purchaseOrderDetails);// 加载采购单明细
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
    function savePurchaseOrder(obj, purchaseOrder) {
        purchaseOrder.purchaseOrderType = 1;// 采购单类型，药品采购单
        ajax.postJSON(rootMapping + '/save', purchaseOrder, function (order) {
            if (order != null && order.purchaseOrderId != null) {
                queryPurchaseOrderById(order.purchaseOrderId);// 根据ID查询采购单，并赋值
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
            var purchaseOrder = obj.field;// 表单值
            var orderItems = $('#' + itemTableId).datagrid('getData');// 获取表格数据
            // console.log(orderItems);
            purchaseOrder.purchaseOrderDetails = orderItems.rows;
            if (!generalBranchCheck(purchaseOrder.totalPrice, purchaseOrder.purchaseOrderDetails)) {
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
                        savePurchaseOrder(obj, purchaseOrder);
                        layer.close(index);
                });
            }else {
                savePurchaseOrder(obj, purchaseOrder);
            }
        } else {
            utils.btnEnabled($(obj.elem));
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消按钮点击事件
    $('button[lay-filter="cancel-btn"]').click(function () {
        window.location.reload();
    });

    // 关闭按钮点击事件
    $('button[lay-filter="close-btn"]').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    // 转到采购单列表
/*    $('button[lay-filter="golist-btn"]').click(function () {
        var treeMenu = $('#LAY-system-side-menu', parent.document);
        treeMenu.find('li[data-name="pharmacy"] dl dd[data-name="supplier"] a').click();
    });*/

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
            $.getJSON('/pharmacy/supplier/findById',{supplierId: supplierId}, function (supplier) {
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
            if (keyVal[0] === 'purchaseOrderId') {
                $('button[lay-filter="cancel-btn"]').hide();
                $('button[lay-filter="close-btn"]').show();
                queryPurchaseOrderById(keyVal[1]);
                break;
            }
        }
    }
});

