/** 病历 */
//@ sourceURL=medicalRecordForm.js
layui.config({
    base: '/lib/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    utils: 'utils' //扩展模块
    ,ajax: 'ajax'
    ,tcmTag: 'tcmTag'
});
layui.use(['element','form','utils', 'jquery', 'layer', 'table', 'ajax', 'laydate','tcmTag'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var ajax = layui.ajax;
    var utils = layui.utils;
    var laydate = layui.laydate;
    var element = layui.element;
    var tcmTag = layui.tcmTag;
    var rootMapping = '/record';
    var decoctionTableId = 'decoction-table';// 中药方 grid
    var patentMedicineTableId = 'patent-medicine-table';// 中成药方 grid
    var skillTableId = 'skill-table';// 医技项目 grid
    var recordFormId = 'record-form';// 病历表单ID
    var decoctionFormId = 'decoction-form'; // 中药方 表单ID
    var patentMedicineFormId = 'patent-medicine-form';// 中成药方 表单ID
    var skillFormId = 'skill-form';// 医技项目 表单ID
    var tabInitState = [false, false, false];
    var editIndex = {};
    form.render();

    // 设置就诊时间
    $('#' + recordFormId + ' input[name="arriveTime"]').val(new Date().format('yyyy-M-d h:m'));

    // 监听处方复选框的选择事件
    form.on('checkbox(prescriptionType)', function(data){
        if (data.elem.checked) {
            $('#btn-group').show();
            $('.layui-tab ul li:eq(0)').hide();
            $('.layui-tab ul li:eq(' + data.value + ')').show();
            var thisTab = $('.layui-tab ul li.layui-this');
            if (thisTab == null || thisTab.length == 0) {
                $('.layui-tab ul li:visible').first().click();
            }
        } else {
            $('.layui-tab ul li:eq(' + data.value + ')').removeClass('layui-this').hide();
            $('.layui-tab .layui-tab-content .layui-tab-item:eq(' + data.value + ')').removeClass('layui-show');
            var checked = $('.layui-tab ul li:visible');
            if (checked == null || checked.length == 0) {
                $('.layui-tab ul li:eq(0)').show();
                $('.layui-tab .layui-tab-content .layui-tab-item:eq(0)').addClass('layui-show');
                $('#btn-group').hide();
            } else {
                var thisTab = $('.layui-tab ul li.layui-this');
                if (thisTab == null || thisTab.length == 0) {
                    checked.first().click();
                }
            }
        }
    });

    // 监听标签页的切换
    element.on('tab(medical-record-tabs)', function(data){
        var tabBody = $('.layui-tab .layui-tab-content .layui-tab-item').eq(data.index);
        if (data.index == 1 && !tabInitState[0]) {
            initDecoctionTab();
            tabInitState[0] = true;
            bindInputForTotalMoney();// 监听总金额的变化
            // 监听剂数的变化
            $('#' + decoctionFormId).find('input[name="doseCount"]').on('input propertychange', function () {
                var doseCount = parseInt($(this).val());
                if (!isNaN(doseCount)) {
                    $('#' + decoctionFormId + ' input[name="totalMoney"]').val((doseCount * tcmTag.getSingleMoney('tcm-tag-panel')).toFixed(2)).trigger('change');// 修改总金额
                }
            });
        } else if (data.index == 2 && !tabInitState[1]) {
            initPatentMedicineTable();
            tabInitState[1] = true;
            bindInputForTotalMoney();
        } else if (data.index == 3 && !tabInitState[2]) {
            initSkillTable();
            tabInitState[2] = true;
            bindInputForTotalMoney();
        }
    });

    // 监听总金额的变化
    function bindInputForTotalMoney() {
        $('.layui-tab-item form input[name="totalMoney"]')
            .unbind('change input propertychange')
            .on('change input propertychange', function () {
            var money = 0;
            $('.layui-tab-item form input[name="totalMoney"]').each(function (i) {
                money += parseFloat($(this).val());
            });
            $('#btn-group label').text(money.toFixed(2));
        });
    }

    // 判断是否可以进行编辑，返回true 可以编辑，false 不可以编辑
    function endEditing(tableId){
        if (editIndex[tableId] == undefined) return true;
        if ($('#' + tableId).datagrid('validateRow', editIndex[tableId])){
            $('#' + tableId).datagrid('endEdit', editIndex[tableId]);
            editIndex[tableId] = undefined;
            return true;
        } else {
            return false;
        }
    }

    // 开始编辑一行
    function beginEditing(tableId, rowIndex,field) {
        editIndex[tableId] = rowIndex;
        $('#' + tableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
        var ed = $('#' + tableId).datagrid('getEditor', {index:rowIndex,field:field});
        if (ed){
            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
        }
    }

    // 初始化药材搜索框
    function initDecoctionTab() {
        var option = {
            id: 'tcm-tag-panel',
            width: '100%',
            height: '370px',
            doseCountSelecter: '#' + decoctionFormId + ' input[name="doseCount"]', // 剂数，jQuery选择器
            totalMoneySelecter: '#' + decoctionFormId + ' input[name="totalMoney"]' // 总金额，jQuery选择器
        }
        tcmTag.render(option);// 初始化中药材标签
    }

    // 初始化中成药方表格
    function initPatentMedicineTable() {
        // 初始化搜索框
        $('#patent-medicine-toolbar select').combogrid({
            delay:500,
            prompt: '中成药名称',
            panelHeight:'auto',
            panelMaxHeight: 200,
            panelWidth: 488,
            idField: 'stockDetailId',
            textField: 'pharmacyItemName',
            method: 'get',
            url: '/pharmacy/stock/getCombogrid',
            queryParams: {type: 2},
            mode: 'remote',
            columns: [[
                {field: 'pharmacyItemName', title: '药品名称', width: 150},
                {field: 'specifications', title: '规格', width: 120},
                {field: 'manufacturer', title: '制造商', width: 200}
            ]],
            onHidePanel: function () {
                var combogrid = $(this);
                var grid = combogrid.combogrid('grid');	// get datagrid object
                var selectRow = grid.datagrid('getSelected');	// get the selected row
                if (selectRow) {
                    var allData = $('#'+patentMedicineTableId).datagrid('getData');
                    for (var i = 0; i < allData.total; i++) {
                        var row = allData.rows[i];
                        if (row.pharmacyItemId === selectRow.pharmacyItemId) {
                            combogrid.combogrid('clear');// 清空搜索框
                            layer.msg('此药品已存在！');
                            return false;
                        }
                    }
                    $('#' + patentMedicineTableId).datagrid('appendRow', selectRow);// 将被选中的行添加到表格中
                    combogrid.combogrid('clear');// 清空搜索框
                }
            }
        });

        // 初始化表格
        $('#' + patentMedicineTableId).datagrid({
            toolbar: '#patent-medicine-toolbar',
            data: [],
            emptyMsg:'无数据！',
            onClickCell: function (rowIndex, field, value) {
                if (field == 'medicineCount' && editIndex[patentMedicineTableId] != rowIndex) {
                    if (endEditing(patentMedicineTableId)) {
                        beginEditing(patentMedicineTableId, rowIndex, field);
                    } else {
                        setTimeout(function () {
                            $('#' + patentMedicineTableId).datagrid('selectRow', editIndex[patentMedicineTableId]);
                        }, 0);
                    }
                }
            }
        });

        // 动态设置列的editor和其他属性
        var e = $('#' + patentMedicineTableId).datagrid('getColumnOption', 'medicineCount');
        e.editor = {
            type:'numberbox',
            options:{
                // precision:2,
                required: true,
                onChange: function (newValue,oldValue) {
                    var rowIndex = editIndex[patentMedicineTableId];
                    var allData = $('#' + patentMedicineTableId).datagrid('getData');
                    var rowData = allData.rows[rowIndex];
                    rowData.totalMoney = (newValue * rowData.sellingPrice).toFixed(2);
                    if (!rowData.medicineCount || oldValue) {
                        setTimeout(function () {
                            if (endEditing(patentMedicineTableId)) {
                                // 结束编辑后，修改总金额
                                var totalMoney = 0;
                                for (var i = 0; i < allData.total; i++) {
                                    totalMoney += Number(allData.rows[i].totalMoney);
                                }
                                $('#' + patentMedicineFormId).find('input[name="totalMoney"]').val(totalMoney.toFixed(2)).trigger('change');
                            }
                        }, 0);
                    }
                }
            }
        };
    }

    // 初始化医技项目表格
    function initSkillTable() {
        table.render({
            elem: '#' + skillTableId
            ,id: skillTableId
            ,height: 'full-190'
            ,url: '/skill/findAll' //数据接口
            ,text: {
                none: '无数据！'
            }
            ,size: 'sm' //小尺寸的表格
            ,cols: [[ //表头
                {field: 'skillId', type: 'checkbox'}
                ,{field: 'skillName', title: '项目名称'}
                ,{field: 'unitPrice', title: '单价(元)'}
                ,{field: 'dose', title: '治疗次数', edit: 'text'}
                ,{field: 'totalMoney', title: '总价(元)'}
            ]]
            ,parseData: function (res) {
                if (res.code === 0 && res.count > 0) {
                    for (var i = 0; i < res.count; i++) {
                        res.data[i].totalMoney = '';
                    }
                }
                return res;
            }
        });
    }

    // 医技项目 监听表格编辑事件
    table.on('edit(skill-table)', function (obj) {
        var value = obj.value;
        var re = new RegExp(/^([1-9][0-9]{0,1}|100)$/);
        var msg = '';
        if (!re.test(value)) {
            msg = '请输入1-100之间的整数';
        }
        if (msg) {
            layer.msg(msg);
            var trElem = obj.tr;
            var tdElem = trElem.find('td[data-field="dose"]');
            var oldValue = tdElem.find('div').text();
            $(this).blur();
            obj.update({dose: oldValue});
            tdElem.click();
        } else {
            setTimeout(function () {
                // 修改总价
                obj.update({totalMoney: (value * obj.data.unitPrice).toFixed(2)});
                var totalMoney = 0, allData = table.cache[skillTableId];
                for (var i = 0; i < allData.length; i++) {
                    totalMoney += Number(allData[i].totalMoney);
                }
                $('#' + skillFormId).find('input[name="totalMoney"]').val(totalMoney.toFixed(2)).trigger('change');
            },0);
        }
    });

    // 统一事件处理
    var eventFunction = {
        // 中成药方，删除行
        delete: function () {
            // alert($(this).attr('row-index')); .datagrid('appendRow',{})
            var row = $('#' + patentMedicineTableId).datagrid('getSelected');
            if (row) {
                var index = $('#' + patentMedicineTableId).datagrid('getRowIndex', row);
                $('#' + patentMedicineTableId).datagrid('deleteRow', index);
                var totalMoney = 0;
                var allData = $('#' + patentMedicineTableId).datagrid('getData');
                for (var i = 0; i < allData.total; i++) {
                    totalMoney += Number(allData.rows[i].totalMoney);
                }
                $('#' + patentMedicineFormId).find('input[name="totalMoney"]').val(totalMoney.toFixed(2)).trigger('change');
            } else {
                layer.msg('请选择药品！');
            }
        },
        // 校验
        validateRecord: function () {
            // 不对患者信息进行校验
            var recordFormData = $('#' + recordFormId).serializeObject();
            var prescriptionType = recordFormData.prescriptionType;//
            if (prescriptionType) {
                for (var i = 0; i < prescriptionType.length; i++) {
                    if (prescriptionType[i] === '1') {
                        if (!tabInitState[0]) {
                            layer.msg('请添加中药方或取消勾选中药处方！');
                            return false;
                        }
                        var tagData = tcmTag.getData('tcm-tag-panel');
                        if (!tagData) {
                            layer.msg('请添加中药方或取消勾选中药处方！');
                            return false;
                        } else {
                            // 校验十八反，十九畏，妊娠禁忌和小儿禁忌

                        }
                    } else if (prescriptionType[i] === '2') {
                        if (!tabInitState[1]) {
                            layer.msg('请添加中成药或取消勾选中成药处方！');
                            return false;
                        }
                        var tableData = $('#' + patentMedicineTableId).datagrid('getData');
                        if (tableData.total == 0) {
                            layer.msg('请添加中成药或取消勾选中成药处方！');
                            return false;
                        } else {
                            // 校验妊娠禁忌和小儿禁忌

                        }
                    } else if (prescriptionType[i] === '3') {
                        if (!tabInitState[2]) {
                            layer.msg('请选择医技项目或取消勾选医技项目处方！');
                            return false;
                        }
                        var checkStatus = table.checkStatus(skillTableId); //idTest 即为基础参数 id 对应的值
                        if (checkStatus.data.length == 0) {
                            layer.msg('请选择医技项目或取消勾选医技项目处方！');
                            return false;
                        } else {
                            // 校验被勾选的医技项目是否填写了治疗次数
                            var checkData = checkStatus.data;
                            for (var j = 0; j < checkData.length; j++) {
                                if (!checkData[j].dose) {
                                    layer.msg('医技项目的诊疗次数不能为空且必须大于0！');
                                    flag = false;
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        },
        // 获取病历数据
        getRecordData: function () {
            var medicalRecord = {};
            // 患者信息
            var recordFormData = $('#' + recordFormId).serializeObject();
            recordFormData.patientAge = recordFormData.patientAge + recordFormData.ageUnit;
            medicalRecord.medicalRecord = recordFormData;
            // 处方信息
            var prescriptionType = recordFormData.prescriptionType;
            var prescriptionTypeStr = '';
            var rxVoList = [];
            if (prescriptionType) {
                for (var i = 0; i < prescriptionType.length; i++) {
                    if (prescriptionType[i] === '1') {
                        prescriptionTypeStr += '1,';
                        var rxVo = {};
                        rxVo.medicalRecordRx = $('.layui-tab-item:eq(1)').find('form').serializeObject();
                        rxVo.detailList = $.extend({},tcmTag.getData('tcm-tag-panel'));
                        rxVoList.push(rxVo);
                    } else if (prescriptionType[i] === '2') {
                        prescriptionTypeStr += '2,';
                        var rxVo = {};
                        rxVo.medicalRecordRx = $('.layui-tab-item:eq(2)').find('form').serializeObject();
                        var tableData = $('#' + patentMedicineTableId).datagrid('getData');
                        rxVo.detailList = tableData.rows;
                        rxVoList.push(rxVo);
                    } else if (prescriptionType[i] === '3') {
                        prescriptionTypeStr += '3,';
                        var rxVo = {};
                        rxVo.medicalRecordRx = $('.layui-tab-item:eq(3)').find('form').serializeObject();
                        var checkStatus = table.checkStatus(skillTableId); //idTest 即为基础参数 id 对应的值
                        rxVo.detailList = checkStatus.data;
                        rxVoList.push(rxVo);
                    }
                }
            }
            // 总金额
            medicalRecord.medicalRecord.totalMoney = $('#btn-group label').text();
            // 处方类型
            medicalRecord.medicalRecord.prescriptionType = prescriptionTypeStr.length > 0 ? prescriptionTypeStr.substr(0, prescriptionTypeStr.length - 1) : '';
            medicalRecord.rxVoList = rxVoList;// 处方
            console.log(medicalRecord);
            return medicalRecord;
        },
        // 保存病历
        save: function () {
            if (eventFunction.validateRecord()) {
                var recordData = eventFunction.getRecordData();
                ajax.postJSON(rootMapping + '/save', recordData, function (result) {
                    layer.msg(result.msg);
                    if (result.success) {
                        $('#' + recordFormId + ' input[name="recordId"]').val(result.object.recordId);// 设置ID值
                        // eventFunction.queryRecord(result.object.recordId);// 重新查询渲染一遍
                    }
                }, $('button[lay-event="save"]'));
            }
        },
        // 结算
        pay: function () {
            
        },
        // 下一位
        next: function () {
            if (eventFunction.validateRecord()) {
                var recordData = eventFunction.getRecordData();
                ajax.postJSON(rootMapping + '/save', recordData, function (result) {
                    if (result.success) {
                        window.location.reload();
                    } else {
                        layer.msg(result.msg);
                    }
                }, $('button[lay-event="next"]'));
            }
        },
        // 根据id查询患者病历，并赋值，主要用于保存后的回显
        queryRecord: function (recordId) {
            if (!recordId) return false;
            ajax.getJSON(rootMapping + '/findById', {recordId: recordId}, function (recordVo) {
                if (recordVo && recordVo.medicalRecord.recordId) {
                    // 患者信息回显
                    var medicalRecord = recordVo.medicalRecord;
                    medicalRecord.patientAge =medicalRecord.patientAge.substr(0, medicalRecord.patientAge.length - 1);
                    medicalRecord.ageUnit = medicalRecord.patientAge.charAt(medicalRecord.patientAge.length - 1);
                    form.val(recordFormId, medicalRecord);
                    //
                }
            });
        }
    };

    // 按钮绑定单击事件
    $(document).on('click','.layui-btn[lay-event]',function(){
        // layer.msg($(this).attr('lay-event'));
        var event = $(this).attr('lay-event');
        if (typeof eventFunction[event] === 'function') {
            eventFunction[event].call(this);
        }
    });
});


