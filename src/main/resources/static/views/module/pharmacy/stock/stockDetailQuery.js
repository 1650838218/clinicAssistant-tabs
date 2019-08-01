/** 库存盘点 */
//@ sourceURL=stockDetailQuery.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    // ,selectC: 'selectC'
});
layui.use(['utils', 'jquery', 'layer', 'table', 'ajax', 'form'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var form = layui.form;
    var rootMappint = '/pharmacy/stock';
    var stockDetailTableId = 'stockdetail-table';
    var formId = 'query-form';

    // 动态加载药品类型
    utils.splicingOption({
        elem: $('#' + formId + ' select[name="pharmacyItemType"]'),
        where: {dictTypeKey: 'YPFL'},
    });

    // 初始化表格
    table.render({
        elem: '#' + stockDetailTableId,
        url: rootMappint + '/queryPage',
        page: true,
        height: 'full-105',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'pharmacyItemName', title: '药品名称', width: '12%'},
            {field: 'specifications', title: '规格', width: '10%'},
            {field: 'manufacturer', title: '制造商'},
            {field: 'batchNumber', title: '批号', width: '10%'},
            {field: 'manufactureDate', title: '生产日期', width: '10%'},
            {field: 'expireDate', title: '有效期至', width: '10%'},
            {
                field: 'purchaseCount', title: '库存数量', width: '7%',edit: 'text', templet: function (d) {
                    return parseFloat(d.purchaseCount).toFixed(2);
                }
            },
            {field: 'purchaseUnitName', title: '库存单位', width: '7%'},
            {
                field: 'sellingPrice',
                title: '零售价(元)',
                width: '7%',
                edit: 'text',
                templet: function (d) {
                    if (d.sellingPrice) {
                        return parseFloat(d.sellingPrice).toFixed(2);
                    } else {
                        return '';
                    }
                }
            },
            {title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '10%', align: 'center'}
        ]]
    });

    // 查询事件
    form.on('submit(submit-btn)', function (data) {
        table.reload(stockDetailTableId,{where: data.field});
        return false;
    });

    //监听表格操作列 监听单元格事件
    table.on('tool(' + stockDetailTableId + ')', function (obj) {
        if (obj.event === 'unshelve') {
            unshelveRow(obj);
        } else if (obj.event === 'save') {
            saveRow(obj);
        }
    });

    // 下架
    function unshelveRow(obj) {

    }

    // 保存行
    function saveRow(obj) {

    }
});

