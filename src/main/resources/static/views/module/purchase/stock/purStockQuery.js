/** 库存盘点 */
//@ sourceURL=purStockQuery.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    ,eleTree: 'eleTree'
});
layui.use(['form', 'utils', 'jquery', 'layer', 'ajax','eleTree','table'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var eleTree = layui.eleTree;
    var table = layui.table;
    var leftTree = undefined;// 左侧菜单树
    var currentPurItemId = undefined;
    var currentPurItemParentId = undefined;
    var rootMapping = '/purchase/stock';
    var stockTableId = 'stock-table';
    var formId = 'query-form';

    // 设置高度
    $('.left-panel').height($(window).height() - 40);
    $('#left-tree').height($('.left-panel').height() - 40);
    $('.right-panel').height($(window).height() - 20);

    // 加载左侧品目目录
    leftTree = eleTree.render({
        elem: '#left-tree',
        url: "/purchase/item/queryTree",
        method: "get",
        emptText: '暂无数据！',
        highlightCurrent: true,// 高亮显示当前节点
        defaultExpandAll: false,// 默认展开所有节点
        renderAfterExpand: true, // 是否在第一次展开某个树节点后才渲染其子节点
        expandOnClickNode: true,// 单击展开
        checkOnClickNode: true,// 单击选中
        showCheckbox: false,// 是否显示复选框
        searchNodeMethod: function(value,data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        done: function (res) {
            if (currentPurItemId) {
                leftTree.expandNode(currentPurItemParentId); //展开某节点的所有子节点
                $('#left-tree div.eleTree-node[data-id="' + currentPurItemId + '"] > .eleTree-node-content').trigger('click');
            } else {
                utils.btnDisabled($('.left-panel button[lay-event="deletePurItem"]'));
            }
        }
    });

    // 搜索
    $(".left-panel .left-search .eleTree-search").on("input change",function() {
        if (!!leftTree) leftTree.search($.trim($(this).val()));
        leftTree.expandAll();
    });

    // 左侧树的点击事件 根据ID查询品目
    eleTree.on("nodeClick(left-tree)", function (d) {
        var purItemId = d.data.currentData.id;
        var children = d.data.currentData.children;
        if (!children) {
            currentPurItemId = purItemId;
            var text = $(d.node).parents('.eleTree-node').find('.eleTree-node-content-label:eq(0)').text();
            if (text.indexOf('（') > -1) {
                text = text.substr(0, text.indexOf('（'));
            }
            utils.btnEnabled($('.left-panel button[lay-event="deletePurItem"]'));
            // getById(purItemId, text);
        } else {
            currentPurItemId = undefined;
            eventFunction.addPurItem();
            utils.btnDisabled($('.left-panel button[lay-event="deletePurItem"]'));
        }

    });

    // 初始化表格
    table.render({
        elem: '#' + stockTableId,
        // url: rootMapping + '/queryPage',
        data:[],
        page: true,
        height: 'full-105',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        text: {
            none: '请选择一个品目！'
        },
        cols: [[
            {field: 'expireDate', title: '采购单号', width: '10%'},
            {field: 'expireDate', title: '采购日期', width: '10%'},
            {field: 'expireDate', title: '供应商', width: '10%'},
            {field: 'expireDate', title: '批号', width: '10%'},
            {field: 'expireDate', title: '有效期至', width: '10%'},
            {
                field: 'stockCount', title: '采购数量', width: '10%',edit: 'text', templet: function (d) {
                    return parseFloat(d.stockCount).toFixed(2);
                }
            },
            {
                field: 'stockCount', title: '库存数量', width: '10%',edit: 'text', templet: function (d) {
                    return parseFloat(d.stockCount).toFixed(2);
                }
            },
            {
                field: 'sellingPrice',
                title: '零售价(元)',
                width: '10%',
                edit: 'text',
                templet: function (d) {
                    if (d.sellingPrice) {
                        return parseFloat(d.sellingPrice).toFixed(2);
                    } else {
                        return '';
                    }
                }
            },
            {field: 'stockDetailId', title: TABLE_COLUMN.operation, toolbar: '#operate-column', width: '11%', align: 'center'}
        ]]
    });

    // 根据id查询，并对表单赋值
    function getById(purItemId, text) {
        if (utils.isNotNull(purItemId)) {
            try {
                $.getJSON(rootMapping + '/getById', {purItemId: purItemId}, function (purItem) {
                    if (purItem != null && utils.isNotNull(purItem.purItemId)) {
                        $('.layui-card-header h3 b').text('编辑品目');
                        if (text) $('.layui-card-header h3 span').removeClass().addClass('subhead-green').text('（' + text + '）');
                        var formFilter = 'type' + purItem.purItemType + '-form';
                        form.val(formFilter, purItem);
                        var stockUnit = $('#' + formFilter).find('select[name="stockUnit"] option:selected');
                        console.log(stockUnit.val());
                        if (stockUnit.val()) {
                            $('#' + formFilter + ' .stockWarnUnit').text(stockUnit.text());
                        } else {
                            $('#' + formFilter + ' .stockWarnUnit').text('<零售单位>');
                        }
                        $('.layui-card-body form').hide();
                        $('.layui-card-body form[lay-filter="' + formFilter + '"]').show();
                    } else {
                        layer.alert('未查询到该品目，请重试！',{icon: 0});
                    }
                });
            } catch (e) {
                console.log(e);
                layer.alert('查询失败，请联系系统管理员！',{icon: 2});
            }
        }else {
            layer.msg('品目ID为空，无法查询！');
        }
    }

    // 统一事件处理
    var eventFunction = {

    };

    // 按钮绑定单击事件
    $(document).on('click','.layui-btn[lay-event]',function(){
        var event = $(this).attr('lay-event');
        if (typeof eventFunction[event] === 'function') {
            eventFunction[event].call(this);
        }
    });
});