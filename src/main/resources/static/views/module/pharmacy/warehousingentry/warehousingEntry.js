/** 采购单 */
//@ sourceURL=purchaseBillForm.js
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
    var rootMapping = '/pharmacy/warehousingentry';
    var itemTableId = 'warehousingentry-table';
    var formId = 'warehousingentry-form';
    form.render();

    // 动态加载日期控件
    laydate.render({
        elem: '#' + formId + ' input[name="warehousingEntryDate"]',
        value: new Date(),
        isInitValue: true
    });
    // 生成单号
    $('#' + formId + ' input[name="warehousingEntryCode"]').val(new Date().format('yyyyMMddhhmmssS'));

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
        $('#' + itemTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
        var ed = $('#' + itemTableId).datagrid('getEditor', {index:rowIndex,field:field});
        if (ed){
            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
        }
        setEditing(rowIndex);
        editIndex = rowIndex;
    }

    // 初始化表格
    $('#' + itemTableId).datagrid({
        fitColumns: true,
        autoRowHeight: true,
        singleSelect: true,
        rownumbers:true,
        columns: [[
            {
                field: 'goodsName', title: '药品名称', width: '210',
                formatter:function(value,row){
                    return row.goodsName;
                }
            },
            {field: 'specifications', title: '规格', width: '200', editor: {type:'textbox',options:{type:'text'}}},
            {field: 'manufacturer', title: '制造商', width: '150', editor: {type:'textbox',options:{type:'text'}}},
            {field: 'count', title: '数量', width: '80',
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
            },
            {
                field: 'countUnit', title: '单位', width: '120',
                editor: {
                    type: 'combobox',
                    options: {
                        valueField: 'dictItemValue',
                        textField: 'dictItemName',
                        method: 'get',
                        url: '/system/dictionary/oneLevel/getItemByKey?dictTypeKey=SLDW',
                        // required: true,
                        mode: 'remote',
                        panelHeight:'auto',
                        panelMaxHeight: 200,
                        editable: false
                    }
                },
                formatter:function(value,row){
                    return row.countUnitName;
                }
            },
            {field: 'unitPrice', title: '单价(元)', width: '100',
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
            },
            {field: 'totalPrice', title: '总价(元)', width: '100',
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
        ]],
        data: [{}],
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
                field: 'medicineListId'
            });
            row.goodsName = $(ed.target).combobox('getText');
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'countUnit'
            });
            row.countUnitName = $(ed.target).combobox('getText');
        }
    });

    //计算表格每一行的总价
    function setEditing(rowIndex){
        var editors = $('#' + itemTableId).datagrid('getEditors', rowIndex);
        var countEditor = editors[3];// 数量
        var unitPriceEditor = editors[5];// 单价
        var totalPriceEditor = editors[6];// 总价
        $(countEditor.target.siblings("span").children("input").first()).on("change", function(){
            calculate();
        });
        $(unitPriceEditor.target.siblings("span").children("input").first()).on("change", function(){
            calculate();
        });
        function calculate(){
            var cost = ($(countEditor.target.siblings("span").children("input").first()).val()) * ($(unitPriceEditor.target.siblings("span").children("input").first()).val());
            console.log(cost);
            $(totalPriceEditor.target).numberbox("setValue",cost);
        }
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
                var goodName = gridData.rows[i].goodsName;
                var totalPrice = gridData.rows[i].totalPrice;
                if (!utils.isNotNull(goodName) || !utils.isNotNull(totalPrice)) {
                    beginEditing(i, 'medicineListId');
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
    function queryPurchaseBillById(purchaseBillId) {
        if (utils.isNotNull(purchaseBillId)) {
            $.getJSON(rootMapping + "/queryById",{purchaseBillId: purchaseBillId}, function (purchaseBill) {
                if (purchaseBill != null) {
                    form.val('purchasebill-form', purchaseBill);// 表单赋值
                    form.render();
                    $('#' + itemTableId).datagrid('loadData', purchaseBill.purchaseBillItems);// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 总分校验，总金额不能大于明细金额之和；true：通过，false：不通过
    function generalBranchCheck(totalAmount, billItems) {
        var totalItems = 0;
        for (var i = 0; i < billItems.length; i++) {
            totalItems += (billItems[i].totalPrice - 0);
        }
        return totalAmount <= totalItems;
    }

    // 保存采购单
    function savePurchaseBill(obj, purchaseBill) {
        ajax.postJSON(rootMapping + '/save', purchaseBill, function (bill) {
            if (bill != null && bill.purchaseBillId != null) {
                queryPurchaseBillById(bill.purchaseBillId);// 根据ID查询采购单，并赋值
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
            var purchaseBill = obj.field;// 表单值
            var billItems = $('#' + itemTableId).datagrid('getData');// 获取表格数据
            // console.log(billItems);
            purchaseBill.purchaseBillItems = billItems.rows;
            if (!generalBranchCheck(purchaseBill.totalPrice, purchaseBill.purchaseBillItems)) {
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
                        savePurchaseBill(obj, purchaseBill);
                        layer.close(index);
                    });
            }else {
                savePurchaseBill(obj, purchaseBill);
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

    // 格式化总金额
    $('#'+ formId + ' input[name="totalPrice"]').bind('change', function () {
        var value = $(this).val();
        $(this).val((value - 0).toFixed(2));
    });

    // 解析url中的参数，如果包含采购单id，则自动查询采购单详情
    var search = window.location.search;
    if (search.length > 0) {
        var condition = search.substr(1).split('&');
        for (var i = 0; i < condition.length; i++) {
            var keyVal = condition[i].split('=');
            if (keyVal[0] === 'purchaseBillId') {
                queryPurchaseBillById(keyVal[1]);
                break;
            }
        }
    }
});

