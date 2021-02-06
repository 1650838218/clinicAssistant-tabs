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
        height: 'full-90',
        cols: [[
            // {field: 'registerId', title: TABLE_COLUMN.numbers, type: 'numbers'},
            {field: 'registerNumber', title: '号牌', width: '6%', align: 'center'},
            {field: 'patientName', title: '姓名', width: '10%', align: 'center'},
            {field: 'patientSex', title: '性别', width: '6%', align: 'center', templet: function (d) {
                    return d.patientSex == 1 ? '男' : d.patientSex == 2 ? '女' : d.patientSex == 3 ? '孕' : '';
                }},
            {field: 'patientAge', title: '年龄', align: 'center', width: '6%'},
            {field: 'patientPhone', title: '电话', width: '15%'},
            {field: 'patientAddress', title: '住址', width: '22%'},
            {field: 'arriveTime', title: '就诊时间', width: '18%', align: 'center', templet: function (d) {
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
        if (obj.event === 'update') {
            openDialog(data); // 修改挂号单
        } else if (obj.event === 'delete') {
            deleteRegister(data.rxDailyId);
        } else if (obj.event === 'charge') {
            charge(data);
        }
    });

    // 删除挂号单
    function deleteRegister(rxDailyId) {
        layer.confirm(MSG.delete_confirm + '该挂号单吗？', {icon: 3}, function () {
            ajax.delete(rootMapping + '/delete/' + rxDailyId, function (result) {
                layer.msg(result.msg);
                registerTable.reload();
            });
        });
    }

    // 收费
    function charge(data) {
        var registerUrl = window.location.href;
        // http://localhost:8090/views/module/register/register.html
        var chargeUrl = '/views/module/record/medicalRecordForm.html?rxDailyId=' + data.rxDailyId;
        window.location.href = chargeUrl;
        // console.log(registerUrl);
    }

    // 新建按钮点击事件
    $('button[name="createBtn"]').click(function () {
        // 查询该患者是第几号
        var registerNmumber = '';
        $.getJSON(rootMapping + '/getRegisterNumber', function (result) {
            registerNmumber = result ? result : '';
            form.val('patientInfoForm', {registerNumber: registerNmumber});
        });
        openDialog({registerNumber: registerNmumber});
    });

    // 打开对话框
    function openDialog(formValue) {
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
                form.val('patientInfoForm', formValue);
                form.render('radio', 'patientInfoForm');
            },
            yes: function (index, layero) {
                // 获取表单信息，保存
                ajax.postJSON(rootMapping + '/saveRegister', layero.find('form').serializeObject(), function (result) {
                    layer.msg(result.msg);
                    if (result.success) {
                        layer.close(index);
                        registerTable.reload();
                    }
                }, layero.find('a.layui-layer-btn0'));
            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });
    }

});

