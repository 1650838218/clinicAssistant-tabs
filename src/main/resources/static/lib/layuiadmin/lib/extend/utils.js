layui.define(['jquery', 'form'], function (exports) {
    var form = layui.form;
        $ = layui.jquery;

    // 获取表单值
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

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
        // 根据字典键查询字典项
        queryDictItem: function (dictKey,success) {
            if (!this.isNotNull(dictKey)) return [];
            $.getJSON('/system/dictionary/getDictItemByDictKey', {dictKey:dictKey}, function (dictItemList) {
                if ($.type(success) === 'function') success(dictItemList);
            });
        },
        // 拼接下拉框option，数据来源于数据字典；elem：select JQuery对象，dictKey：字典键
        splicingOption: function (param) {
            var config = {
                elem: '',//select JQuery对象
                tips: '',// 提示信息
                realValueName: 'dictValue',
                displayValueName: 'dictName',
                url: '/system/dictionary/getDictItemByDictKey',
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
        },
        btnDisabled: function (elem) {
            if (!(elem instanceof $)) {
                elem = $(elem);
            }
            elem.attr('disabled', 'disabled');
            elem.addClass('layui-btn-disabled');// 按钮禁用，防止重复提交
        },
        btnEnabled: function (elem) {
            if (!(elem instanceof $)) {
                elem = $(elem);
            }
            elem.removeClass('layui-btn-disabled');// 按钮可用
            elem.removeAttr('disabled');
        }
    };

    //输出utils接口
    exports('utils', utils);
});