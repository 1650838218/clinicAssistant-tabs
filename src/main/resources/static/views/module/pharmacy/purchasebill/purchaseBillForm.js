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
    var rootMapping = '/pharmacy/purchasebill';
    var itemTableId = 'purchasebill-table';
    var formId = 'purchasebill-form';
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
        elem: '#' + formId + ' input[name="purchaseBillDate"]',
        value: new Date(),
        isInitValue: true
    });
    // 动态加载数量单位下拉框
    utils.splicingOption({
        elem: $('#count-unit-select select[name="countUnit"]'),
        where: {dictTypeKey: 'SLDW'},
        defaultValue:1
    });
    // 生成单号
    $('#purchasebill-form input[name="purchaseBillCode"]').val(new Date().format('yyyyMMddhhmmssS'));


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

    // 初始化表格
    $('#' + itemTableId).datagrid({
        fitColumns: true,
        autoRowHeight: true,
        singleSelect: true,
        rownumbers:true,
        toolbar: [{
            text:'添加',
            iconCls:'icon-add',
            handler:function(){
                if (endEditing()){
                    $('#' + itemTableId).datagrid('appendRow',{});
                    editIndex = $('#' + itemTableId).datagrid('getRows').length - 1;
                    $('#' + itemTableId).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
                }
                setEditing(editIndex);//此句较为重要
            }
        },'-',{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                if (editIndex == undefined) {
                    layer.msg(MSG.select_one);
                    return;
                }
                $('#' + itemTableId).datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
                editIndex = undefined;
            }
        }],
        columns: [[
            {
                field: 'medicineListId', title: '药品名称', width: '210',
                editor: {
                    type: 'combobox',
                    options: {
                        valueField: 'realValue',
                        textField: 'displayValue',
                        method: 'get',
                        url: '/pharmacy/medicineList/getSelectOption',
                        mode: 'remote',
                        required: true,
                        panelHeight:'auto',
                        panelMaxHeight: 200
                    }
                },
                formatter:function(value,row){
                    return row.goodsName;
                }
            },
            {field: 'specifications', title: '规格', width: '200', editor: {type:'textbox',options:{type:'text'}}},
            {field: 'manufacturer', title: '制造商', width: '150', editor: {type:'textbox',options:{type:'text'}}},
            {field: 'count', title: '数量', width: '80', editor: {type:'numberbox',options:{precision:2,required: true}}},
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
            {field: 'unitPrice', title: '单价(元)', width: '100', editor: {type:'numberbox',options:{precision:2,required: true}}},
            {field: 'totalPrice', title: '总价(元)', width: '100', editor: {type:'numberbox',options:{precision:2,required: true}}}
        ]],
        data: [{}],
        onClickCell: function (rowIndex, field, value) {
            if (editIndex != rowIndex){
                if (endEditing()){
                    $('#' + itemTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
                    var ed = $('#' + itemTableId).datagrid('getEditor', {index:rowIndex,field:field});
                    if (ed){
                        ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                    }
                    setEditing(rowIndex);
                    editIndex = rowIndex;
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
        var rowCount = $('$' + itemTableId).datagrid('getRows').length;
        if (rowCount === 0) {
            layer.msg("采购单至少要有一条明细数据！");
            return false;
        }
        if (endEditing()) {
            for (var i = 0; i < rowCount; i++) {
                if (!$('#' + itemTableId).datagrid('validateRow', i)) {
                    $('#' + itemTableId).datagrid('selectRow', i).datagrid('beginEdit', i);
                    return false;
                }
            }
        } else {
            setTimeout(function(){
                $('#' + itemTableId).datagrid('selectRow', editIndex);
            },0);
            return false;
        }
        return true;
    }

    // 根据ID查询采购单
    function queryPurchaseBillById(purchaseBillId) {
        if (utils.isNotNull(purchaseBillId)) {
            $.getJSON(rootMapping + "queryById",{purchaseBillId: purchaseBillId}, function (purchaseBill) {
                if (purchaseBill != null) {
                    $('#purchasebill-form').val('purchasebill-form', purchaseBill);// 表单赋值
                    form.render();
                    $('#' + itemTableId).datagrid('loadData', purchaseBill.purchaseBillItems);// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    /**
     * 保存采购单
     * @param data
     * @returns {boolean}
     */
    function savePurchaseBill(obj) {
        if (validateGrid()) {
            $(obj.elem).addClass('layui-btn-disabled');// 按钮禁用，防止重复提交
            $(obj.elem).attr('disabled', 'disabled');
            var purchaseBill = obj.field;// 表单值
            var billItems = $('#' + itemTableId).datagrid('getData');// 获取表格数据
            console.log(billItems);
            purchaseBill.purchaseBillItems = billItems;
            ajax.postJSON(rootMapping + '/save', purchaseBill, function (bill) {
                if (bill != null && bill.purchaseBillId != null) {
                    queryPurchaseBillById(bill.purchaseBillId);// 根据ID查询采购单，并赋值
                    layer.msg(MSG.save_success);
                } else {
                    layer.msg(MSG.save_fail);
                }
                $(obj.elem).removeClass('layui-btn-disabled');// 按钮可用
                $(obj.elem).removeAttr('disabled');
            });
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    };






    /**
     * 重置表单
     */
    function resetForm() {
        // 表单清空
        assigForm({
            dictionaryId: '',
            menuId: '',
            menuName: '',
            dictionaryName: '',
            dictionaryKey: ''
        });
        // 删除高亮
        leftTree.unHighLight();
        // 重置表格
        var newRow = [{
            "dictItemName": "",
            "dictItemValue": "",
            "isUse": "1",
            "dictItemId": ''
        }];
        table.reload(dictItemTableId, {
            data: newRow   // 将新数据重新载入表格
        });
    }

    // 表单自定义校验规则
    form.verify({
        repeat: function (value, item) { //value：表单的值、item：表单的DOM对象
            var inputName = $(item).attr('name');
            var url = '';
            var msg = '';
            var data = {};
            data[inputName] = value.trim();
            data.dictTypeId = $('#dictionary-info input[name="dictTypeId"]').val();
            if (inputName === 'dictTypeName') {
                url = rootMappint + '/repeatTypeName';
                msg = '字典名称已被占用，请重新填写！';
            } else if (inputName === 'dictTypeKey') {
                url = rootMappint + '/repeatTypeKey';
                msg = '字典键已被占用，请重新填写！';
            }
            if (utils.isNotNull(url)) {
                ajax.getJSONAsync(url, data, function (result) {
                    if (result) msg = '';
                }, false);
            }
            return msg;
        },
        regExp: function (value, item) {
            var inputName = $(item).attr('name');
            var msg = '';
            var reg = '';
            if (inputName === 'dictTypeKey') {
                reg = /^[a-zA-Z][a-zA-Z_]*$/;
                msg = '字典键以英文字母开头，只能输入字母和下划线，请重新填写！';
            }
            if (!!msg && !reg.test(value)) {
                return msg;
            }
        }
    });


});

