/*中药材标签*/

layui.define(["jquery", "layer"], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var hint = layui.hint();

    var MOD_NAME = "tcmTag";

    //外部接口
    var tcmTag = {
        // 获取所有的中药材标签的数据
        getData: function (id) {
            return thisTag.dataArray[id];
        },
        // 获取单剂金额
        getSingleMoney: function (id) {
            var tagPanel = $('#' + id);
            return tagPanel.find('.show-money').text().replace(/[^0-9.]/ig, "");
        }
    }

    //操作当前实例
    var thisTag = function() {
        var that = this,options = that.config,id = options.id;
        if(id){
            thisTag.that[id] = that; //记录当前实例对象
            thisTag.config[id] = options; //记录当前实例配置项
        }
    };

    //记录所有实例
    thisTag.that = {}; //记录所有实例对象
    thisTag.config = {}; //记录所有实例配置项
    thisTag.dataArray = {}; // 记录所有实例的数据

    //获取当前实例配置项
    var getThisTableConfig = function(id){
        var config = thisTag.config[id];
        if(!config) hint.error('The ID option was not found in the tag instance');
        return config || null;
    }

    //构造器
    var Class = function(options){
        var that = this;
        that.config = $.extend({}, that.config, options);
        that.render();
    };

    //默认配置
    Class.prototype.config = {
        id:'',
        width: '100%',
        height: '370px',
        doseCountSelecter: '', // 剂数，jQuery选择器
        totalMoneySelecter: '' // 总金额，jQuery选择器
    };

    // 药材 combobox
    Class.prototype.medicalCombobox = function() {
        var that = this;
        return {
            delay:500,
            prompt:'药材名称',
            panelHeight:'auto',
            panelMaxHeight: 200,
            valueField: 'purStockId',
            textField: 'itemName',
            mode: 'remote',
            url: '/purchase/stock/getCombogridForHerbalMedicine',
            queryParams: {
                type: 1
            },
            method: 'get',
            formatter: function (row) {
                return row.itemName + ' ' + parseFloat(row.sellingPrice).toFixed(2) + '元/' + row.stockUnitName;
            },
            onHidePanel: function () {
                var combo = $(this);
                var selectValue = combo.combobox('getValue');
                if (selectValue) {
                    var data = combo.combobox('getData');
                    $.each(data, function (i, n) {
                        if (n.purStockId === selectValue) {
                            n.dose = 0;// 将剂量初始化为0，避免后面的计算报错
                            that.addTag(n);
                            combo.combobox('clear');
                            return false;
                        }
                    });
                }
            }
        };
    };

    // 处方 combobox
    Class.prototype.prescriptionCombobox = function() {
        var that = this;
        return {
            delay:500,
            prompt: '处方名称',
            panelHeight:'auto',
            panelMaxHeight: 200,
            valueField: 'realValue',
            textField: 'displayValue',
            mode: 'remote',
            url: 'item/prescription/getSelectOption',
            method: 'get',
            onHidePanel: function () {
                var combo = $(this);
                var selectValue = combo.combobox('getValue');
                if (selectValue) {
                    that.clear();// 清空处方面板中的药材
                    // 根据处方ID查询处方组成，并添加
                    $.getJSON('item/prescription/getMedicalByPrescriptionId',{prescriptionId: selectValue},function (result) {
                        if (result) {
                            var medicals = result.medicals;
                            var notExist = result.notExist;
                            if (medicals && medicals.length > 0) {
                                for (var i = 0, len = medicals.length; i < len; i++) {
                                    that.addTag(medicals[i]);
                                }
                                that.updateMoney();// 修改单剂金额
                                combo.combobox('clear');
                            }
                            if (notExist && notExist.length > 0) {
                                layer.alert();
                            }
                        }
                    });
                }
            }
        };
    };

    //tag渲染
    Class.prototype.render = function(){
        var that = this;
        var options = that.config;
        var tagPanel = $('#' + options.id);

        var panelHtml = '<div class="easyui-panel">' +
                            '<div id="search-toolbar" class="datagrid-toolbar">' +
                                '<label>搜索药材：</label><input class="search-medicinal"></input>' +
                                // '<label>搜索处方：</label><input class="search-prescription"></input>' +
                                '<label class="show-money">单剂金额：0.00元</label>' +
                                '<label class="show-count">共0味药材</label>' +
                            '</div>' +
                            '<div class="tcm-tag-body"></div>' +
                        '</div>';
        tagPanel.html(panelHtml);
        //style="width:' + options.width + ';height:' + options.height + ';"
        tagPanel.find('.easyui-panel').panel({
            width: options.width,
            height: options.height
        });
        tagPanel.find('.search-medicinal').combobox(that.medicalCombobox());// 初始化药材搜索框
        tagPanel.find('.search-prescription').combobox(that.prescriptionCombobox());// 初始化处方搜索框
    };

    // 清空
    Class.prototype.clear = function() {
        var that = this;
        var options = that.config;
        var tagPanel = $('#' + options.id);
        tagPanel.find('.tcm-tag-body .tcm-tag i.layui-icon').click();
    }

    // 添加
    Class.prototype.addTag = function(data) {
        var that = this;
        var options = that.config;
        if (thisTag.dataArray[options.id]) {
            // 判断被添加的药材是否已存在
            for (var i = 0; i < thisTag.dataArray[options.id].length; i++) {
                if (thisTag.dataArray[options.id][i].itemName === data.itemName) {
                    layer.msg(data.itemName + '已存在！');
                    return;
                }
            }
        }
        var tagPanel = $('#' + options.id);
        var tagBody = tagPanel.find('.tcm-tag-body');
        var tagHtml = '<div id="' + (options.id + '--' + data.itemId) + '" class="tcm-tag" title="' + (parseFloat(data.sellingPrice).toFixed(2) + '元/' + data.stockUnitName) + '">' +
                            '<p class="tcm-tag-name">' + data.itemName + '</p>' +
                            '<input type="number" value="' + (data.dose ? data.dose : 0) + '"/>' +
                            '<p>' + data.stockUnitName + '</p>' +
                            '<i class="layui-icon layui-icon-close" title="删除"></i>' +
                        '</div>';
        tagBody.append(tagHtml);
        if (thisTag.dataArray[options.id]) {
            thisTag.dataArray[options.id].push(data);
        } else {
            var newArray = [];
            newArray.push(data);
            thisTag.dataArray[options.id] = newArray;
        }
        tagPanel.find('.show-count').text('共' + thisTag.dataArray[options.id].length + '味药材');

        // 绑定删除事件
        tagBody.find('#' + (options.id + '--' + data.itemId) + ' i.layui-icon').click(function () {
            var tag = $(this).parent();
            var tagId = tag.attr('id').split('--');
            var allData = thisTag.dataArray[tagId[0]];
            $.each(allData, function (i,n) {
                if (n.itemId === parseInt(tagId[1])) {
                    allData.splice(i, 1);// 删除
                    tag.remove();
                    that.updateMoney();// 修改单剂金额
                    that.updateCount();// 修改单剂药味数
                    return false;
                }
            });
        });
        // 监听剂量的修改
        tagBody.find('#' + (options.id + '--' + data.itemId) + ' input').on('input propertychange', function () {
            var val = $(this).val();
            var tag = $(this).parent();
            var tagId = tag.attr('id').split('--');
            var allData = thisTag.dataArray[tagId[0]];
            $.each(allData, function (i,n) {
                if (n.itemId === parseInt(tagId[1])) {
                    n.dose = val;// 将剂量缓存
                    tag.attr('title',parseFloat(n.sellingPrice).toFixed(2) + '元/' + n.stockUnitName + '  共' + parseFloat(n.sellingPrice).toFixed(2) * val + '元');
                    that.updateMoney();// 修改单剂金额
                    return false;
                }
            });
        });
    }

    // 修改单剂金额
    Class.prototype.updateMoney = function() {
        var that = this;
        var options = that.config;
        var tagPanel = $('#' + options.id);
        var tagBody = tagPanel.find('.tcm-tag-body');

        var money = 0;
        var allData = thisTag.dataArray[options.id];
        for (var j = 0; j < allData.length; j++) {
            money = money + allData[j].sellingPrice * allData[j].dose;
        }
        tagPanel.find('.show-money').text('单剂金额：' + money.toFixed(2) + '元');// 修改单剂金额
        var doseCount = parseInt($(options.doseCountSelecter).val());
        if (!isNaN(doseCount)) {
            $(options.totalMoneySelecter).val((doseCount * money).toFixed(2)).trigger('change');// 修改总金额
        }
    }

    // 修改单剂药味数
    Class.prototype.updateCount = function() {
        var that = this;
        var options = that.config;
        var tagPanel = $('#' + options.id);
        tagPanel.find('.show-count').text('共' + thisTag.dataArray[options.id].length + '味药材');// 修改单剂药味数
    }

    // 检查配伍  十八反十九畏
    Class.prototype.checkCompatibility = function() {
        // 半蒌贝蔹芨攻乌  藻戟遂芫俱战草 诸参辛芍叛藜芦
        var eighteenImcompatible = [
            ['甘草','大戟'],['甘草','芫花'],['甘草','甘遂'],['甘草','海藻'],
            ['川乌','贝母'],['川乌','川贝'],['川乌','浙贝'],['川乌','瓜蒌'],['川乌','天花粉'],['川乌','半夏'],['川乌','白蔹'],['川乌','白及'],
            ['草乌','贝母'],['草乌','川贝'],['草乌','浙贝'],['草乌','瓜蒌'],['草乌','天花粉'],['草乌','半夏'],['草乌','白蔹'],['草乌','白及'],
            ['乌头','贝母'],['乌头','川贝'],['乌头','浙贝'],['乌头','瓜蒌'],['乌头','天花粉'],['乌头','半夏'],['乌头','白蔹'],['乌头','白及'],
            ['附子','贝母'],['附子','川贝'],['附子','浙贝'],['附子','瓜蒌'],['附子','天花粉'],['附子','半夏'],['附子','白蔹'],['附子','白及'],
            ['黎芦','参'],['黎芦','人参'],['黎芦','西洋参'],['黎芦','党参'],['黎芦','太子参'],['黎芦','丹参'],['黎芦','沙参'],['黎芦','南沙参'],['黎芦','北沙参'],['黎芦','苦参'],['黎芦','玄参'],['黎芦','细辛'],['黎芦','芍药'],['黎芦','白芍'],['黎芦','赤芍']
        ];
        // 硫黄畏朴硝，水银畏砒霜，狼毒畏密陀僧，巴豆畏牵牛，丁香畏郁金，川乌、草乌畏犀角，牙硝畏三棱，官桂畏赤石脂，人参畏五灵脂
        var nineteenCounteraction = [
            ['硫黄','朴硝'],['水银','砒霜'],['狼毒','密陀僧'],['巴豆','牵牛'],['丁香','郁金'],['川乌','犀角'],['草乌','犀角'],['牙硝','三棱'],['官桂','赤石脂'],['人参','五灵脂']
        ];
        var that = this;
        var options = that.config;
        var tagPanel = $('#' + options.id);
        var tagBody = tagPanel.find('.tcm-tag-body');
        var allData = thisTag.dataArray[options.id];
        var result = [];
        if (allData != null && allData.length > 0) {
            // 检查十八反
            for (var i = 0; i < eighteenImcompatible.length; i++) {
                var pair = eighteenImcompatible[i];
                var rp = [];
                for (var j = 0; j < allData.length; j++) {
                    if (allData[j].itemName.indexOf(pair[0]) > -1) {
                        rp.push(j);
                    } else if (allData[j].itemName.indexOf(pair[1]) > -1) {
                        rp.push(j);
                    }
                    if (rp.length == 2) break;
                }
                if (rp.length == 2) result.push(rp);
            }
            // 检查十九畏
            for (var i = 0; i < nineteenCounteraction.length; i++) {
                var pair = nineteenCounteraction[i];
                var rp = [];
                for (var j = 0; j < allData.length; j++) {
                    if (allData[j].itemName.indexOf(pair[0]) > -1) {
                        rp.push(j);
                    } else if (allData[j].itemName.indexOf(pair[1]) > -1) {
                        rp.push(j);
                    }
                    if (rp.length == 2) break;
                }
                if (rp.length == 2) result.push(rp);
            }
        }
        if (result.length > 0) {
            for (var i = 0; i < result.length; i++) {
                var pair = result[i];

            }
        }
    }

    //核心入口
    tcmTag.render = function(options){
        var inst = new Class(options);
        return thisTag.call(inst);
    };

    exports(MOD_NAME, tcmTag);
});
