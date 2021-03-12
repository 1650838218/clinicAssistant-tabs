/** 菜单管理 */
//@ sourceURL=dictionary.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    ajax: 'ajax'
    ,utils: 'utils'
});
layui.use(['form', 'jquery', 'layer', 'table', 'ajax','utils'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/system/dictionary';
    var leftTableId = 'left-table';
    var detailTableId = 'detail-table';
    var currentDictKey = '';
    form.render();

    $('#add-btn').on('click', resetForm);// 新增字典
    $('#del-btn').on('click', delDictionaryFun);// 删除字典
    form.on('submit(submit-btn)', saveDictionary);// 保存字典

    // 初始化表格
    var leftTable = table.render({
        elem: '#left-table',
        url: rootMapping + '/queryPage',
        height: 'full-80',
        page: {
            limit: 10,
            groups: 2,
            layout: ['prev', 'page', 'next','count']
        },
        text: {
            none: '暂无数据！'
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cols: [[
            {field: 'dictName', title: '字典名称'}
        ]],
        skin: 'nob',
        size: 'sm',
        done: function (res, curr, count) {
            $('#add-btn').click();// 清空表单
            $('#del-btn').addClass('layui-btn-disabled').attr('disabled','disabled');// 禁用 删除按钮
            // 选中当前行
            if (utils.isNotNull(currentDictKey)) {
                var tableData = table.cache[leftTableId];
                if (utils.isNotEmpty(tableData)) {
                    for (var i = 0; i < tableData.length; i++) {
                        if (tableData[i].dictKey === currentDictKey) {
                            var rowIndex = tableData[i][table.config.indexName];
                            $('.left-panel .layui-table tbody tr[data-index="' + rowIndex + '"]').find('div').addClass('select');
                            $('#del-btn').removeClass('layui-btn-disabled').removeAttr('disabled');
                            getByDictKey(currentDictKey);
                        }
                    }
                }
            }
        }
    });

    // 监听行单击事件
    table.on('row(left-table)', function (obj) {
        $('.left-panel .layui-table tbody tr div').removeClass('select');
        $(obj.tr).find('div').addClass('select');
        $('#del-btn').removeClass('layui-btn-disabled').removeAttr('disabled');
        // 获取id，根据ID查询多级字典，表单设置值
        var dictKey = obj.data.dictKey;
        getByDictKey(dictKey);

    });

    // 左侧列表的点击事件 根据key查询字典
    function getByDictKey(dictKey) {
        if (dictKey) {
            currentDictKey = dictKey;
            try {
                $.getJSON(rootMapping + '/findAllByDictKey', {"dictKey": dictKey}, function (dictionaryVo) {
                    if (dictionaryVo) {
                        assigForm(dictionaryVo.dictType, '编辑字典');// 给表单赋值
                        detailTable.reload({data: dictionaryVo.dictItem})// 加载表格
                    } else {
                        layer.alert(MSG.query_fail, {icon: 2});
                    }
                });
            } catch (e) {
                // console.log(e);
                layer.alert(MSG.query_fail, {icon: 2});
            }
        } else {
            layer.msg(MSG.select_one);
        }
    };

    // 搜索
    var lastKeyword = ''
    $(".left-panel .left-search .search-input").on("change", function () {
        var keyword = $(this).val();
        if (keyword != lastKeyword) {
            lastKeyword = keyword;
            leftTable.reload({where:{keyword:keyword}});
        }
    });

    // 初始化表格
    var detailTable = table.render({
        elem: '#detail-table',
        height: 'full-186',
        limit: 50,// 每个字典类型最多可以录入50个字典项
        // size: 'sm',
        cols: [[
            {field: 'dictName', title: '字典项名称', width: '35%', edit: 'text'},
            {field: 'dictValue', title: '字典项值', width: '20%', edit: 'text'},
            {field: 'dictOrder', title: '顺序号', width: '10%', edit: 'text'},
            {field: 'isUse', title: '是否可用', templet: '#is-use-switch', width: '15%', align: 'center'},
            {field: 'dictId', title: TABLE_COLUMN.operation, toolbar: '#operate-column', align: 'center'}
        ]],
        data: [{"dictName": "","dictValue": "","isUse": "1","dictId": ''}]
    });

    //监听表格操作列
    table.on('tool(' + detailTableId + ')', function (obj) {
        var data = obj.data;
        if (obj.event === 'create') {
            var dataBak = [];// 缓存表格已有的数据
            var oldData = table.cache[detailTableId];
            var newRow = {"dictName": "","dictValue": "", "isUse": "1", "dictId": ''};
            // 获取当前行的位置
            var rowIndex = -1;
            try {
                rowIndex = obj.tr[0].rowIndex;
            } catch (e) {
                layer.alert(MSG.system_exception, {icon: 2});
            }
            //将之前的数组备份
            for (var i = 0; i < oldData.length; i++) {
                if ($.type(oldData[i]) !== 'array') dataBak.push(oldData[i]);
            }
            // 插入空白行
            if (rowIndex != -1) {
                dataBak.splice(rowIndex + 1, 0, newRow);
            } else {
                dataBak.push(newRow);
            }
            var scrollTop = $('div[lay-id="' + detailTableId + '"] .layui-table-body').scrollTop();// 获取滚动条位置
            detailTable.reload({data: dataBak});   // 将新数据重新载入表格
            $('div[lay-id="' + detailTableId + '"] .layui-table-body').scrollTop(scrollTop + 39);// 滚动条滚动到可视区域
        } else if (obj.event === 'delete') {
            // 此处的删除是假删除，必须要将表格保存后才会生效
            // 不去后台删除的原因是：表格会统一跟随字典类型统一保存，页面上被删除的字典项会在保存时自动跟字典类型解除外键关系
            obj.del();// 先删除行
            // 再判断表格行是否全部删除
            var oldData = table.cache[detailTableId];
            if (oldData.length > 0) {
                // 做for循环的原因：obj.del只是把该行数据删掉，但是oldData的长度不变，必须要判断行数据是否为空
                for (var i = 0; i < oldData.length; i++) {
                    if ($.type(oldData[i]) !== 'array') {
                        return;
                    }
                }
            }
            // 如果表格行全部被删除，则新增一个空白行
            var newRow = [{"dictName": "","dictValue": "","isUse": "1","dictId": ''}];
            detailTable.reload({data: newRow});   // 将新数据重新载入表格
        }
    });

    // 监听switch开关
    form.on('switch(is-use)', function (obj) {
        var selectIfKey = obj.othis;// 获取当前控件
        var parentTr = selectIfKey.parents("tr");// 获取当前所在行
        var parentTrIndex = parentTr.attr("data-index");// 获取当前所在行的索引
        var tableData = table.cache[detailTableId];
        if (tableData.length > parentTrIndex) {
            tableData[parentTrIndex].isUse = obj.elem.checked ? 1 : 0;
        }
    });

    /**
     * 重置表单
     */
    function resetForm() {
        // 表单清空
        assigForm({
            dictId: '',
            dictName: '',
            dictKey: ''
        },"新增字典");
        // 重置表格
        var newRow = [{
            "dictName": "",
            "dictValue": "",
            "isUse": "1",
            "dictId": ''
        }];
        table.reload(detailTableId, {
            data: newRow   // 将新数据重新载入表格
        });
    }

    /**
     * 给表单赋值
     * @param data
     */
    function assigForm(data,title) {
        // 表单赋值
        form.val("dictionary-form", data);
        $('.layui-card-header h3 b').html(title);
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
                url = rootMapping + '/repeatTypeName';
                msg = '字典名称已被占用，请重新填写！';
            } else if (inputName === 'dictTypeKey') {
                url = rootMapping + '/repeatTypeKey';
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

    /**
     * 删除菜单
     */
    function delDictionaryFun() {
        if (currentDictKey) {
            layer.confirm(MSG.delete_confirm + '此字典吗？', {icon: 3, title: '提示'}, function (index) {
                ajax.delete(rootMapping + '/deleteByDictKey/' + currentDictKey, function (data, textStatus, jqXHR) {
                    if (data) {
                        layer.msg(MSG.delete_success);
                        currentDictKey = undefined;
                        $('.left-search .search-input').val('');
                        leftTable.reload({where:{keyword:''}});
                        resetForm();
                    } else {
                        layer.msg(MSG.delete_fail);
                    }
                });
                layer.close(index);
            });
        } else {
            layer.msg(MSG.select_one);
        }
    };

    // 表格数据校验
    function verifyTable(tableData) {
        if (!tableData) {
            layer.alert('表格数据为空，请填写表格数据！', {icon: 2});
            return false;
        }
        // 删除空数组
        for (var i = 0; i < tableData.length; i++) {
            if (Array.isArray(tableData[i]) && tableData[i].length === 0) {
                tableData.splice(i, 1);
            }
        }
        if (!tableData || tableData.length <= 0) {
            layer.alert('表格数据为空，请填写表格数据！', {icon: 2});
            return false;
        }
        for (var i = 0; i < tableData.length; i++) {
            // 判断是否是空行  TODO 好像有bug
            if (!tableData[i].dictName || !tableData[i].dictValue) {// 表格数据校验
                layer.alert('表格数据不完整，请补充完整！', {icon: 2});
                return false;
            }
        }
        // 校验是否有重复数据
        for (var i = 0; i < tableData.length - 1; i++) {
            var row1 = tableData[i];
            for (var j = i + 1; j < tableData.length; j++) {
                var row2 = tableData[j];
                if (row1.dictName === row2.dictName
                    || row1.dictValue === row2.dictValue) {
                    layer.alert('字典项名称和字典项值不能重复，请重新填写！', {icon: 2});
                    return false;
                }
            }
        }
        return tableData;
    }

    /**
     * 保存字典
     * @param data
     * @returns {boolean}
     */
    function saveDictionary(obj) {
        $(obj.elem).addClass('layui-btn-disabled');// 按钮禁用，防止重复提交
        $(obj.elem).attr('disabled', 'disabled');
        var dictionary = obj.field;// 表单值
        dictionary.isUse = 1;
        var dictItems = table.cache[detailTableId];// 获取表格数据
        dictItems = verifyTable(dictItems);// 表格数据校验
        if (!dictItems) {
            $(obj.elem).removeClass('layui-btn-disabled');// 按钮可用
            $(obj.elem).removeAttr('disabled');
            return false;
        }
        ajax.postJSON(rootMapping + '/save', {dictType: dictionary,dictItem: dictItems}, function (returnResult) {
            layer.msg(returnResult.msg);
            if (returnResult.success) {
                currentDictKey = returnResult.listObj[0].dictKey;
                $('.left-search .search-input').val('');
                leftTable.reload({where:{keyword: ''}});
            } else {
                console.log(returnResult.errorMsg);
            }
            $(obj.elem).removeClass('layui-btn-disabled');// 按钮可用
            $(obj.elem).removeAttr('disabled');
        },obj.elem);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    };
});

