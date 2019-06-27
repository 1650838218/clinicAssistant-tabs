layui.define(['jquery', 'form'], function (exports) {
    var form = layui.form;
        $ = layui.jquery;
    var utils = {
        error: function (msg) {
            console.error(msg);
        },
        /**
         * 在一个数组里面查询一个对象
         * var r = 1;
         * var arr = [{name:'a',id:1},{name:'b',id:2}]
         * var result = utils.find(arr,function(item){
         *   return r === item.id;
         * });
         *  // result : {name:'a',id:1}
         */
        isFunction: function (obj) {
            return typeof obj === 'function';
        },
        isString: function (obj) {
            return typeof obj === 'string';
        },
        isObject: function (obj) {
            return typeof obj === 'object';
        },
        isNotNull: function (obj) {
            // 判断字符串不为空
            return obj !== null && obj !== undefined && obj !== '';
        },
        isNotEmpty: function (obj) {
            // 判断数组不空
            return obj !== null && obj !== undefined && obj.length > 0;
        },
        clearForm: function (elem) {
            $(elem).find(":input[type!='button'][type!='file'][type!='image'][type!='radio'][type!='checkbox'][type!='reset'][type!='submit']").val("");
            $(elem).find(":checkbox,:radio").prop("checked",false);
            $(elem).filter(":input[type!='button'][type!='file'][type!='image'][type!='radio'][type!='checkbox'][type!='reset'][type!='submit']").val("");
            $(elem).filter(":checkbox,:radio").prop("checked",false);
        },
        // 拼接下拉框option，数据来源于数据字典；elem：select JQuery对象，dictTypekey：字典键
        splicingOption: function (param) {
            var config = {
                elem: '',//select JQuery对象
                tips: '',// 提示信息
                realValueName: 'dictItemValue',
                displayValueName: 'dictItemName',
                url: '/system/dictionary/oneLevel/getItemByKey',
                where: {},
                defaultValue: ''
            };
            config = $.extend({}, config, param);
            if (!this.isNotNull(config.elem) || !(config.elem instanceof $)) return;
            if (!this.isNotNull(config.url)) return;
            $.getJSON(config.url, config.where, function (list) {
                var optionHtml = '';
                if (config.tips)  optionHtml += '<option value="">' + config.tips + '</option>';
                if (list != null && list.length > 0) {
                    for (var i = 0; i < list.length; i++) {
                        if (list[i][config.realValueName] == config.defaultValue) {
                            optionHtml += '<option value="' + list[i][config.realValueName] + '" selected>' + list[i][config.displayValueName] + '</option>';
                        } else {
                            optionHtml += '<option value="' + list[i][config.realValueName] + '">' + list[i][config.displayValueName] + '</option>';
                        }
                    }
                }
                config.elem.html(optionHtml);
                form.render('select');
            });
        }
    };

    //输出utils接口
    exports('utils', utils);
});