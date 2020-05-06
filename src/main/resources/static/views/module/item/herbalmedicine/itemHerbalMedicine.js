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

    // 设置高度
    $('.right-panel').height($(window).height() - 20);

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
        $.getJSON('/itemHerbalMedicine/query', {}, function (result) {
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
                    html += '   <a href="javascript:;"><img src="/lib/layuiadmin/style/res/template/portrait.png"></a>';
                    html += '   <a href="javascript:;"><div class="cmdlist-text"><p class="info">麻黄</p></div></a>';
                    html += '</div>';
                }
                $('.layui-card-body .search-result').html(html);
            } else {
                // TODO 请添加中药品目
            }
        });
    }
});