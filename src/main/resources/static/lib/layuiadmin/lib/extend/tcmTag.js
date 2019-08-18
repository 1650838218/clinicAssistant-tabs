/*中药材标签*/

layui.define(["jquery", "layer"], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    var MOD_NAME = "tcmTag";

    var dataArray = {};// 保存所有的数据
    var count = 0;// 计数器

    //外部接口
    var tcmTag = {
        /**
         * 增加一个标签
         * @param elem 装载标签的容器 jquery选择器
         * @param key {name:'',unit:''} 中药字段名和单位字段名
         * @param data json对象
         * @param callback 回调
         */
        addTag: function (options) {
            var elem = options.elem;
            var key = options.key;
            var data = options.data;
            var afterAdd = options.afterAdd;
            var afterDelete = options.afterDelete;
            var onChange = options.onChange;
            if (!elem || !key || !key.name || !key.unit || !data) layer.alert('参数缺失，标签添加失败！');
            var nameKey = key.name;
            var unitKey = key.unit;
            var tagHtml = '<div id="tcm-tag-' + (++count) + '" class="tcm-tag"><p class="tcm-tag-name">' + data[nameKey] + '</p><input type="number"><p>' + data[unitKey] + '</p><i class="layui-icon layui-icon-close" title="删除"></i></div>';
            $(elem).append(tagHtml);
            if (dataArray[elem]) {
                dataArray[elem].push(data);
            } else {
                var newArray = [];
                newArray.push(data);
                dataArray[elem] = newArray;
            }
            // 绑定删除事件
            $(elem + ' #tcm-tag-' + count + ' i.layui-icon').click(function () {
                var tag = $(this).parent();
                var val = tag.find('input').val();
                $.each(dataArray[elem], function (i,n) {
                   if (n[nameKey] === tag.find('.tcm-tag-name').text()) {
                       dataArray[elem].splice(i,1);// 删除
                       tag.remove();
                       if (typeof afterDelete === 'function') afterDelete(dataArray[elem]);
                       return;
                   }
                });
            });
            // 监听用量的修改
            $(elem + ' #tcm-tag-' + count + ' input').on('input propertychange', function () {
                var val = $(this).val();
                var tag = $(this).parent();
                var tagName = tag.find('.tcm-tag-name').text();
                $.each(dataArray[elem], function (i,n) {
                    if (n[nameKey] === tagName) {
                        n.dose = val;// 将剂量缓存
                        if (typeof onChange === 'function') onChange(dataArray[elem],n);
                        return;
                    }
                });
            });
            if (typeof afterAdd === 'function') afterAdd();
        },
        // 获取所有的中药材标签的数据
        getData: function (elem) {
            if (dataArray[elem]) {
                return dataArray[elem];
            }
            return {};
        },
        // 返回标签数量
        getCount: function (elem) {
            if (dataArray[elem]) {
                return dataArray[elem].length;
            }
            return 0;
        },
        on: function (elem) {
            
        }
    }

    exports(MOD_NAME, tcmTag);
});
