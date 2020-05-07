/** 查询中药 */
//@ sourceURL=itemHerbalMedicine.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['form', 'utils', 'jquery', 'layer', 'ajax', 'laypage'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var laypage = layui.laypage;

    $('.right-panel').height($(window).height() - 20);// 设置高度

    // 拼接Html
    function joinResultHtml(data) {
        var html = '';
        for (var i = 0; i < data.length; i++) {
            // 拼接
            html += '<div class="cmdlist-container">';
            html += '   <a href="javascript:;"><div class="cmdlist-text"><p class="info" item-id="' + data[i].itemId + '">' + data[i].itemName + '</p></div></a>';
            html += '</div>';
        }
        $('.layui-card-body .search-result').html(html);
    }

    // 初始化分页
    function initPage(count) {
        laypage.render({
            elem: 'page',
            count: count, //数据总数
            jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if (!first) {
                    // 获取查询条件

                }
            }
        });
    }

    // 查询中药
    $.getJSON('/itemHerbalMedicine/queryPage', {page:1,size:10}, function (result) {
        var count = result.count;
        var data = result.data;
        if (utils.isNotEmpty(data)) {
            $('.layui-card-header .subhead-blue').html('&nbsp;&nbsp;共&nbsp;' + count + '味');// 修改共多少味药材
            initPage(count);// 初始化分页
            joinResultHtml(data);// 拼接html
        } else {
            // TODO 请添加中药品目
        }
    });

    // 查询中药分类
    utils.queryDictItem('HerbalMedicine',function (dictItemList) {
        if (utils.isNotEmpty(dictItemList)) {
            var liHtml = '';
            for (var i = 0; i < dictItemList.length; i++) {
                liHtml += '<li><a href="javascript:;" rel="nofollow" data-id="' + dictItemList[i]['dictValue'] + '"><i></i>' + dictItemList[i]['dictName'] + '</a></li>';
            }
            $('.selector .s-line:eq(1) .sl-wrap .sl-value .sl-v-list ul').html(liHtml);
        } else {
            // TODO 请配置中药分类
        }
    });

    // 根据筛选条件查询中药
    function queryHerbalMedicine(condition) {
        if (utils.isNotNull(condition)) {
            condition.page = '';
        } else {

        }
        $.getJSON('/itemHerbalMedicine/queryPage', condition, function (result) {
            var count = result.count;
            var data = result.data;
            if (utils.isNotEmpty(data)) {
                var html = '';
                laypage.render({
                    elem: 'page',
                    count: count, //数据总数
                    jump: function(obj){
                        console.log(obj)
                    }
                });
                for (var i = 0; i < data.length; i++) {
                    // 拼接
                    html += '<div class="cmdlist-container">';
                    html += '   <a href="javascript:;"><div class="cmdlist-text"><p class="info" item-id="' + data[i].itemId + '">' + data[i].itemName + '</p></div></a>';
                    html += '</div>';
                }
                $('.layui-card-body .search-result').html(html);
            } else {
                // TODO 请添加中药品目
            }
        });
    }
});