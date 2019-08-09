/** 病历 */
//@ sourceURL=medicalRecordForm.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['element','form','utils', 'jquery', 'layer', 'table', 'ajax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var laydate = layui.laydate;
    var rootMapping = '/record';
    var decoctionTableId = 'decoction-table';// 中药方 grid
    var patentMedicineTableId = 'patent-medicine-table';// 中成药方 grid
    var skillTableId = 'skill-table';// 医技项目 grid
    var recordFormId = 'record-form';// 病历表单ID
    var decoctionFormId = 'decoction-form'; // 中药方 表单ID
    var patentMedicineFormId = 'patent-medicine-form';// 中成药方 表单ID
    var skillFormId = 'skill-form';// 医技项目 表单ID
    form.render();

    // 设置就诊时间
    $('#' + recordFormId + ' input[name="arriveTime"]').val(new Date().format('yyyy/M/d h:m'));

    // 监听处方复选框的选择事件
    form.on('checkbox(prescriptionType)', function(data){
        // console.log(data.elem); //得到checkbox原始DOM对象
        // console.log(data.elem.checked); //是否被选中，true或者false
        // console.log(data.value); //复选框value值，也可以通过data.elem.value得到
        // console.log(data.othis); //得到美化后的DOM对象
        if (data.elem.checked) {
            $('.layui-tab ul li:eq(0)').hide();
            $('.layui-tab ul li:eq(' + data.value + ')').show();
            var thisTab = $('.layui-tab ul li.layui-this');
            if (thisTab == null || thisTab.length == 0) {
                $('.layui-tab ul li:visible').first().click();
            }
        } else {
            $('.layui-tab ul li:eq(' + data.value + ')').removeClass('layui-this').hide();
            $('.layui-tab .layui-tab-content .layui-tab-item:eq(' + data.value + ')').removeClass('layui-show');
            var checked = $('.layui-tab ul li:visible');
            if (checked == null || checked.length == 0) {
                $('.layui-tab ul li:eq(0)').show();
                $('.layui-tab .layui-tab-content .layui-tab-item:eq(0)').addClass('layui-show');
            } else {
                var thisTab = $('.layui-tab ul li.layui-this');
                if (thisTab == null || thisTab.length == 0) {
                    checked.first().click();
                }
            }
        }
    });

    // 判断是否可以进行编辑，返回true 可以编辑，false 不可以编辑
    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined) return true;
        if ($('#' + decoctionTableId).datagrid('validateRow', editIndex)){
            $('#' + decoctionTableId).datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    // 开始编辑一行
    function beginEditing(rowIndex,field) {
        editIndex = rowIndex;
        $('#' + decoctionTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
        var ed = $('#' + decoctionTableId).datagrid('getEditor', {index:rowIndex,field:field});
        if (ed){
            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
        }
    }

    // 初始化表格
    $('#' + decoctionTableId).datagrid({});
    /*$('#' + decoctionTableId).datagrid({
        toolbar: [
            {
                text: '添加',
                iconCls: 'icon-add',
                handler: function () {
                    if (endEditing()) {
                        $('#' + decoctionTableId).datagrid('appendRow', {});
                        editIndex = $('#' + decoctionTableId).datagrid('getRows').length - 1;
                        $('#' + decoctionTableId).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
                    }
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
                    $('#' + decoctionTableId).datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
                    editIndex = undefined;
                    // 如果明细行被全部删除，则新增一个空白行
                    if ($('#' + decoctionTableId).datagrid('getData').total === 0) {
                        $('#' + decoctionTableId).datagrid('appendRow', {});
                    }
                }
            }
        ],
        data: [{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}],
        onClickCell: function (rowIndex, field, value) {
            if (editIndex != rowIndex){
                if (endEditing()){
                    beginEditing(rowIndex, field);
                } else {
                    setTimeout(function(){
                        $('#' + decoctionTableId).datagrid('selectRow', editIndex);
                    },0);
                }
            }
        },
        onEndEdit: function (index, row) {
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'stockDetailId'
            });
            row.pharmacyItemName = $(ed.target).combogrid('getText');
        }
    });*/

    // 动态设置列的editor和其他属性
/*    var columns = [
        {
            field: 'stockDetailId',
            editor: {
                type: 'combogrid',
                options: {
                    idField:'stockDetailId',
                    textField:'pharmacyItemName',
                    method: 'get',
                    url: '/pharmacy/stock/getCombogrid',
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
                    onSelect: function(index,stockDetail){
                        // 设置库存单位（销售单位）
                        var editor1 = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex,field: 'stockUnit'});
                        $(editor1.target).textbox('setValue', stockDetail.stockUnitName);
                        // 设置单价
                        var editor2 = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex,field: 'unitPrice'});
                        $(editor2.target).textbox('setValue', stockDetail.sellingPrice);
                    },
                    onChange: function (newValue, oldValue) {
                        if (!utils.isNotNull(newValue)) {
                            // 清空库存单位（销售单位）和单价
                            var editor1 = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex,field: 'stockUnit'});
                            $(editor1.target).textbox('clear');
                            var editor2 = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex,field: 'unitPrice'});
                            $(editor2.target).textbox('clear');
                        }
                    },
                    onHidePanel: function () {
                        // 验证药品名称是否为空
                        var editor = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex,field: 'stockDetailId'});
                        var stockDetailId = $(editor.target).combogrid('getValue');
                        if (!stockDetailId) {
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
            field: 'dose',
            editor: {
                type: 'numberbox',
                options: {
                    precision:2,
                    required: true,
                    onChange: function (newValue,oldValue) {
                        var unitPrice = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex, field: 'unitPrice'});
                        var totalPrice = $('#' + decoctionTableId).datagrid('getEditor',{index: editIndex, field: 'totalPrice'});
                        $(totalPrice.target).numberbox('setValue', newValue * $(unitPrice.target).numberbox('getValue'));
                    }
                }
            },
            formatter:function(value,row){
                if (value != null) return (value - 0).toFixed(2);
            }
        },
        {
            field: 'stockUnit',
            editor: {
                type: 'textbox',
                options: {
                    editable: false,
                    readonly: true
                }
            },
            formatter:function(value,row){
                return row.stockUnit;
            }
        },
        {
            field: 'unitPrice',
            editor: {
                type: 'textbox',
                options: {
                    editable: false,
                    readonly: true
                }
            },
            formatter:function(value,row){
                return row.unitPrice;
            }
        },
        {
            field: 'totalMoney',
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
        var e = $('#' + decoctionTableId).datagrid('getColumnOption', columns[i].field);
        e.editor = columns[i].editor;
        e.formatter = columns[i].formatter;
    }*/

    // 明细表格校验
    function validateGrid() {
        var gridData = $('#' + decoctionTableId).datagrid('getData');
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
                $('#' + decoctionTableId).datagrid('selectRow', editIndex);
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
                    $('#' + decoctionTableId).datagrid('loadData', purchaseOrder.purchaseOrderDetails);// 加载采购单明细
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
            var orderItems = $('#' + decoctionTableId).datagrid('getData');// 获取表格数据
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
/*    $('#'+ formId + ' input[name="totalPrice"]').bind('change', function () {
        var value = $(this).val();
        $(this).val((value - 0).toFixed(2));
    });*/

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

