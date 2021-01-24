/** 就诊预检挂号 */
//@ sourceURL=register.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
});
layui.use(['utils', 'jquery', 'layer', 'table', 'ajax', 'form', 'util'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var form = layui.form;
    var rootMapping = '/rxdaily';
    var registerTableId = 'register-table';

    // 初始化表格
    var registerTable = table.render({
        elem: '#register-table',
        url: rootMapping + '/getTodayRxDailyMainForNotPayment',
        limit: 300,// 最多可有300行
        cols: [[
            {field: 'registerId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'registerNumber', title: '号牌', width: '6%', align: 'center'},
            {field: 'patientName', title: '姓名', width: '10%', align: 'center'},
            {field: 'patientSex', title: '性别', width: '6%', align: 'center', templet: function (d) {
                    return d.patientSex == 1 ? '男' : d.patientSex == 2 ? '女' : d.patientSex == 3 ? '孕' : '';
                }},
            {field: 'patientAge', title: '年龄', align: 'center', width: '6%'},
            {field: 'patientPhone', title: '电话', width: '15%'},
            {field: 'patientAddress', title: '住址', width: '24%'},
            {field: 'arriveTime', title: '就诊时间', width: '20%', align: 'center', templet: function (d) {
                    return d.arriveTime ? layui.util.toDateString(d.arriveTime) : '';
                }},
            {title: TABLE_COLUMN.operation, toolbar: '#operate-column', align: 'center'}
        ]],
        parseData: function (res) {
        },
        done: function (res, curr, count) {

        }
    });

    //监听表格操作列
    table.on('tool(' + registerTableId + ')', function (obj) {
        var data = obj.data;
        if (obj.event === 'prescribe') {
            prescribe(obj);
        }
    });

    // 处方
    function prescribe(obj) {
        var dataBak = [];// 缓存表格已有的数据
        var oldData = table.cache[skillTableId];
        var newRow = {
            itemId: '',
            itemName: '',
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

    // 新建按钮点击事件
    $('button[name="createBtn"]').click(function () {
        // 查询该患者是第几号
        var registerNmumber = '';
        $.getJSON(rootMapping + '/getRegisterNumber', function (result) {
            registerNmumber = result ? result : '';
            form.val('patientInfoForm', {registerNumber: registerNmumber});
        });
        var formItems = [
            {type: 'hidden', name: 'rxDailyId'},
            {type: 'input', label: '号牌', name: 'registerNumber'},
            {type: 'input', label: '姓名', name: 'patientName'},
            {type: 'radio', label: '性别', name: 'patientSex', option:[{value: 1, text: '男', check: true},{value: 2, text: '女'},{value: 3, text: '孕'}]},
            {type: 'input', label: '年龄', name: 'patientAge'},
            {type: 'input', label: '电话', name: 'patientPhone'},
            {type: 'input', label: '住址', name: 'patientAddress'}
        ];
        layer.open({
            title: '患者信息',
            content: utils.layerFormHtml(formItems, 'patientInfoForm'),
            area: ['420px','390px'],
            btn: ['确定','取消'],
            btnAlign: 'c',
            resize: false,
            success: function(layero, index) {
                form.render('radio', 'patientInfoForm');
                form.val('patientInfoForm', {registerNumber: registerNmumber});
            },
            yes: function (index, layero) {
                // 获取表单信息，保存
                ajax.postJSON(rootMapping + '/register', layero.find('form').serializeObject(), function (result) {
                    layer.msg(result.msg);
                    if (result.success) {
                        layer.close(index);
                        // window.location.reload();
                        registerTable.reload();
                    }
                }, layero.find('a.layui-layer-btn0'));
            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });
    });

});

