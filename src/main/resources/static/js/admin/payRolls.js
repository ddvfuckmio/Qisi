var payRollDate = null;
var load = function () {

    var queryParams = $('#workerPayRolls').datagrid('options').queryParams;

    var department = $('#department').combobox('getText');

    if (department === '不选择') {
        department = '';
    }

    queryParams.department = department;

    $('#workerPayRolls').datagrid('reload');

};

var reset = function () {
    $('#username').val = '';
    $('#realName').val = '';

    $('#department').combobox('setValue', 1);
    $('#department').combobox('setText', '不选择');

    $('#sex').combobox('setValue', 1);
    $('#sex').combobox('setText', '不选择');
};

//Utils
function parseDate(dateStr) {
    var strArray = dateStr.split("-");
    if (strArray.length == 3) {
        return new Date(strArray[0], strArray[1] - 1);
    } else {
        return new Date();
    }
}

//unix时间戳转 年-月-日格式
var formatDate = function (times) {
    var date = new Date(times);
    var year = date.getFullYear();
    var month = ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : '0' + (date.getMonth() + 1))
    return year + '-' + month;
};

var formatRowPayRollDate = function (value, row, index) {
    return formatDate(row.payRollDate);
};


$(function () {

    $('#department').combobox({
        height: 25,
        data: [{
            "id": 1,
            "text": "不选择",
            "value": "",
            "selected": true
        }, {
            "id": 2,
            "text": "行政部",
            "value": "行政部"
        }, {
            "id": 3,
            "text": "采购部",
            "value": "采购部"
        }, {
            "id": 4,
            "text": "公关部",
            "value": "公关部"
        }, {
            "id": 5,
            "text": "政党部",
            "value": "政党部"
        }, {
            "id": 6,
            "text": "临时部门",
            "value": "临时部门"
        }, {
            "id": 7,
            "text": "人事部门",
            "value": "人事部门"
        }, {
            "id": 8,
            "text": "销售部",
            "value": "销售部"
        }],
        valueField: 'id',
        textField: 'text'
    });

    $('#payRollDate').datebox({
        width: 120,
        required: true,
        editable: false,
        onSelect: function (date) {
            payRollDate = new Date(date.getFullYear(), date.getMonth() + 1);
        }
    });

    $('#month').combobox({
        data: [{
            "id": 1,
            "text": '1',
            "selected": true
        }, {
            "id": 2,
            "text": '2'
        }, {
            "id": 3,
            "text": '3'
        }, {
            "id": 4,
            "text": '4'
        }, {
            "id": 5,
            "text": '5'
        }, {
            "id": 6,
            "text": '6'
        }, {
            "id": 7,
            "text": '7'
        }, {
            "id": 8,
            "text": '8'
        }, {
            "id": 9,
            "text": '9'
        }, {
            "id": 10,
            "text": '10'
        }, {
            "id": 11,
            "text": '11'
        }, {
            "id": 12,
            "text": '12'
        }],
        valueField: 'id',
        textField: 'text'
    });


    $('#workerPayRolls').datagrid({
        method: 'get',
        url: '/admin/workerPayRoll',
        queryParams: {
            department: "",
            payRollDate: ""
        },

        iconCls: 'icon-search',
        pagination: true,
        columns: [[
            {
                field: 'username', title: '用户名', width: 120
            },
            {
                field: 'delayCount', title: '迟到次数', width: 120
            },
            {
                field: 'earlyCount', title: '早退次数', width: 120
            },
            {
                field: 'dayOffCount', title: '请假天数', width: 120
            },
            {
                field: 'absentCount', title: '旷工天数', width: 120
            },
            {
                field: 'department', title: '部门', width: 150
            },
            {
                field: 'payrollDate', title: '考核月份', width: 150, formatter: null
            }
        ]]

    });

});
