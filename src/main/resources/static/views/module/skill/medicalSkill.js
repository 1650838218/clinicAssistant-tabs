/** 医技项目 */
//@ sourceURL=medicalSkill.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['utils', 'jquery', 'layer', 'table', 'ajax'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var rootMapping = '/skill';
    var skillTableId = 'skill-table';

    // 初始化表格
    table.render({
        elem: '#skill-table',
        url: rootMapping + '/findAll',
        limit: 300,// 最多可有300个医技项目
        cols: [[
            {field: 'skillId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'skillName', title: '项目名称', width: '40%',  edit: 'text'},
            {field: 'unitPrice', title: '价格(元)', width: '30%', edit: 'text'},
            {title: TABLE_COLUMN.operation, toolbar: '#operate-column', align: 'center'}
        ]],
        parseData: function (res) {
            if (res == null || res.data == null || res.data.length <= 0) {
                res.count = 1;
                res.code = 0;
                res.msg = '';
                res.data = [{
                    skillId: '',
                    skillName: '',
                    unitPrice: ''
                }];
            }
        },
        done: function (res, curr, count) {

        }
    });

    // 校验项目名称不能重复
    function isRepeat(skillName) {
        return false;
    }

    // 监听表格编辑
    table.on('edit(' + skillTableId + ')', function (obj) {
        console.log(obj.data);
        var inputElem = $(this);
        var oldValue = inputElem.val();
        var tdElem = inputElem.closest('td');
        var msg = '';
        if (obj.field === 'skillName') {
            // 名称不能重复，不能为空
            if (!obj.value.trim()) {
                msg = '项目名称不能为空！';
            } else if (isRepeat(obj.value.trim())) {
                msg = '项目名称不能重复，请重新输入！';
            } else {
                inputElem.val(obj.value.trim());
            }
        } else if (obj.field === 'unitPrice') {
            if (isNaN(parseFloat(obj.value))) {
                msg = '价格栏只能输入数字！'
            } else {
                inputElem.val(parseFloat(obj.value).toFixed(2));
            }
        }
        if (msg) {
            layer.alert(msg, {icon: LAYER_ICON.error}, function (index) {
                inputElem.val('');
                tdElem.click();
                inputElem.focus();
                layer.close(index);
            });
        }
    });

    //监听表格操作列
    table.on('tool(' + skillTableId + ')', function (obj) {
        var data = obj.data;
        if (obj.event === 'create') {
            createRow(obj);
        } else if (obj.event === 'delete') {
            deleteRow(obj);
        } else if (obj.event === 'save') {
            saveRow(obj);
        }
    });

    // 新增行
    function createRow(obj) {
        var dataBak = [];// 缓存表格已有的数据
        var oldData = table.cache[skillTableId];
        var newRow = {
            skillId: '',
            skillName: '',
            unitPrice: ''
        };
        // 获取当前行的位置
        var rowIndex = -1;
        try {
            rowIndex = obj.tr[0].rowIndex;
        } catch (e) {
            layer.alert(MSG.system_exception, {icon: 2});
        }
        //将之前的数组备份
        for (var i = 0; i < oldData.length; i++) {
            if (!Array.isArray(oldData[i]) || oldData[i].length > 0)
                dataBak.push(oldData[i]);
        }
        // 插入空白行
        if (rowIndex != -1) {
            dataBak.splice(rowIndex + 1, 0, newRow);
        } else {
            dataBak.push(newRow);
        }
        table.reload(skillTableId, {
            url:'',
            data: dataBak   // 将新数据重新载入表格
        });
    }

    // 删除行
    function deleteRow(obj) {
        // 此处的删除是真删除
        layer.confirm(MSG.delete_confirm + '该医技项目吗？', {icon: 0}, function (index) {
            var skillId = obj.data.skillId;
            if (utils.isNotNull(skillId)) {
                ajax.delete(rootMapping + '/delete/' + skillId, function (success) {
                    if (success) {
                        layer.msg(MSG.delete_success);
                        obj.del();
                    } else {
                        layer.msg(MSG.delete_fail);
                    }
                });
            } else {
                layer.msg(MSG.delete_success);
                obj.del();
            }
            layer.close(index);
            // 判断表格行是否全部删除
            var oldData = table.cache[skillTableId];
            if (oldData.length > 0) {
                // 做for循环的原因：obj.del只是把该行数据删掉，但是oldData的长度不变，必须要判断行数据是否为空
                for (var i = 0; i < oldData.length; i++) {
                    if (!Array.isArray(oldData[i]) || oldData[i].length > 0) {
                        return;
                    }
                }
            }
            // 如果表格行全部被删除，则新增一个空白行
            var newRow = [{
                skillId: '',
                skillName: '',
                unitPrice: ''
            }];
            table.reload(skillTableId, {
                url: '',
                data: newRow   // 将新数据重新载入表格
            });
        });
    }

    // 保存行
    function saveRow(obj) {
        // 行数据校验
        var rowData = obj.data;
        var rowIndex = obj.tr[0].rowIndex;
        if (!utils.isNotNull(rowData.skillName.trim())
            || !utils.isNotNull(rowData.unitPrice.trim())) {
            layer.msg('医技项目名称和价格不能为空！', {icon: 5, shift: 6});
        }
        ajax.postJSON(rootMapping + '/save', rowData, function (skill) {
            if (skill != null && utils.isNotNull(skill.skillId)) {
                layer.msg(MSG.save_success);
                /*var oldData = table.cache[skillTableId];
                for (var i = 0; i < oldData.length; i++) {
                    if (oldData[i][table.config.indexName] === rowIndex) {
                        oldData[i].skillId = skill.skillId;
                        oldData[i].skillName = skill.skillName;
                        oldData[i].unitPrice = skill.unitPrice;
                        break;
                    }
                }*/
                table.reload(skillTableId, {
                    url: rootMapping + '/findAll'
                });
            } else {
                layer.msg(MSG.save_fail);
            }
        }, obj.tr.find('td').last().find('a[lay-event="save"]'));
    }
});

