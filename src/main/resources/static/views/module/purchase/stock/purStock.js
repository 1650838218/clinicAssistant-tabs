/** 入库单 */
//@ sourceURL=purStock.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['form','utils', 'jquery', 'layer', 'ajax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    // var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/purchase/stock';
    var itemTableId = 'stock-table';
    var formId = 'stock-form';
    form.render();

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
            ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'stockUnitName'
            });
            row.stockUnitName = $(ed.target).combobox('getText');
        }
    });

    // 动态设置列的editor和其他属性
    var columns = [
        {
            field: 'itemId',
            formatter:function(value,row){
                return row.itemName;
            }
        },
        {
            field: 'stockCount',
            editor: {
                type:'numberbox',
                options:{
                    required: true,
                    min: 1,
                    onChange: function (newValue,oldValue) {
                        var selectedRow = $('#' + itemTableId).datagrid('getSelected');
                        var breakevenPrice = $('#' + itemTableId).datagrid('getEditor',{index: editIndex, field: 'breakevenPrice'});
                        $(breakevenPrice.target).textbox('setValue', (selectedRow.totalPrice / newValue).toFixed(4));
                    }
                }
            }
        },
        {
            field: 'stockUnitName',
            editor: {
                type: 'combobox',
                options: {
                    required: true,
                    editable: false,
                    readonly: false,
                    valueField: 'dictValue',
                    textField: 'dictName',
                    url: '/system/dictionary/getDictItemByDictKey?dictKey=' + DICT_KEY.PUR_ITEM_LSDW,
                    method: 'get'
                }
            },
            formatter: function(value,row){
                return row.stockUnitName;
            }
        },
        {
            field: 'breakevenPrice',
            editor: {
                type: 'textbox',
                options: {
                    editable: false,
                    readonly: true
                }
            }
        },
        {
            field: 'sellingPrice',
            editor: {
                type:'numberbox',
                options:{
                    precision:4,
                    required: true
                }
            },
            formatter: function (value, row) {
                if (value != null) return (value - 0).toFixed(4);
            }
        }
    ];
    for (var i = 0, l = columns.length; i < l; i++) {
        var e = $('#' + itemTableId).datagrid('getColumnOption', columns[i].field);
        // console.log(columns[i]);
        e.editor = columns[i].editor;
        e.formatter = columns[i].formatter;
    }

    // 明细表格校验
    function validateGrid() {
        if (endEditing()) {
            var gridData = $('#' + itemTableId).datagrid('getData');
            var rowCount = gridData.total;
            for (var i = 0; i < rowCount; i++) {
                var itemId = gridData.rows[i].itemId;
                var stockCount = gridData.rows[i].stockCount;
                var stockUnitName = gridData.rows[i].stockUnitName;
                var sellingPrice = gridData.rows[i].sellingPrice;
                if (!utils.isNotNull(itemId) || !utils.isNotNull(stockCount) || !utils.isNotNull(stockUnitName) || !utils.isNotNull(sellingPrice)) {
                    layer.msg("请将明细数据补充完整！");
                    if (!utils.isNotNull(itemId)) {
                        beginEditing(i, 'itemId');
                    } else if (!utils.isNotNull(stockCount)) {
                        beginEditing(i, 'stockCount');
                    } else if (!utils.isNotNull(stockUnitName)) {
                        beginEditing(i, 'stockUnitName');
                    } else if (!utils.isNotNull(sellingPrice)) {
                        beginEditing(i, 'sellingPrice');
                    }
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
            $.getJSON("/purchase/order/queryByIdForStock",{purOrderId: purOrderId}, function (purOrder) {
                if (purOrder != null && purOrder.order != null && purOrder.detail != null) {
                    purOrder.order.totalPrice = purOrder.order.totalPrice.toFixed(2) + ' 元';
                    form.val(formId, purOrder.order);// 表单赋值
                    form.render();
                    // console.log(purOrder.detail);
                    if (purOrder.detail) {
                        for (var i = 0; i < purOrder.detail.length; i++) {
                            purOrder.detail[i].stockPrice = utils.isNotNull(purOrder.detail[i].stockPrice) ? purOrder.detail[i].stockPrice : '无';
                            purOrder.detail[i].breakevenPrice = utils.isNotNull(purOrder.detail[i].stockCount) ? (purOrder.detail[i].totalPrice / purOrder.detail[i].stockCount).toFixed(4) : '无';
                        }
                    }
                    $('#' + itemTableId).datagrid('loadData', purOrder.detail);
                    // tableConfig.data = purOrder.detail;
                    // table.render(tableConfig);// 加载采购单明细
                }
            });
        } else {
            layer.alert('采购单ID为空！', {icon: LAYER_ICON.error});
        }
    }

    // 保存入库
    function saveStockDetail(obj, purStocks) {
        ajax.postJSON(rootMapping + '/save', purStocks, function (purStocks) {
            if (purStocks != null) {
                layer.msg('入库成功！',{time:2000}, function () {
                    $('button[lay-filter="close-btn"]').click();// 关闭当前页面，刷新采购单的状态
                });
            } else {
                layer.msg('入库失败！');
            }
            utils.btnEnabled($(obj.elem));
        }, $(obj.elem));
    }

    /**
     * 保存入库
     * @param data
     * @returns {boolean}
     */
    form.on('submit(submit-btn)', function(obj){
        utils.btnDisabled($(obj.elem));
        if (validateGrid()) {
            var purStocks = $('#' + itemTableId).datagrid('getData').rows;// 获取表格数据
            var purOrderId = $('#' + formId).find('input[name="purOrderId"]').val();
            // console.log(billItems);
            for (var i = 0, len = purStocks.length; i < len; i++) {
                purStocks[i].purOrderId = purOrderId;
                purStocks[i].stockState = 1;// 库存状态(1：正常；2：已退货；3：已过期；4：下架)
            }
            saveStockDetail(obj, purStocks);
        } else {
            utils.btnEnabled($(obj.elem));
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    // 取消按钮点击事件
    $('button[lay-filter="close-btn"]').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    // 解析url中的参数，如果包含采购单id，则自动查询采购单详情
    var search = window.location.search;
    if (search.length > 0) {
        var condition = search.substr(1).split('&');
        for (var i = 0; i < condition.length; i++) {
            var keyVal = condition[i].split('=');
            if (keyVal[0] === 'purOrderId') {
                queryPurOrderById(keyVal[1]);
                break;
            }
        }
    }

    // 取消编辑
    $(document).on("click", function(event){
        if ($(event.target).parents('.datagrid-row').length > 0 || $(event.target).hasClass('datagrid-row')) {

        } else {
            endEditing();
        }
    });
});

