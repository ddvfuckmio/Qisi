var load = function () {

    var queryParams = $('#workers').datagrid('options').queryParams;

    var username = $('#username').val();
    var realName = $('#realName').val();
    var department = $('#department').combobox('getText');
    var sex = $('#sex').combobox('getText');

    if (department === '不选择') {
        department = '';
    }

    if (sex === '不选择') {
        sex = ''
    }

    queryParams.username = username;
    queryParams.realName = realName;
    queryParams.department = department;
    queryParams.sex = sex;


    $('#workers').datagrid('reload');

};

var reset = function () {
    $('#username').val = '';
    $('#realName').val = '';

    $('#department').combobox('setValue', 1);
    $('#department').combobox('setText', '不选择');

    $('#sex').combobox('setValue', 1);
    $('#sex').combobox('setText', '不选择');
};

var addWorker = function () {

};

$(function () {

    $('#sex').combobox({
        data: [{
            "id": 1,
            "text": '不选择',
            "selected": true
        }, {
            "id": 2,
            "text": '男'
        }, {
            "id": 3,
            "text": '女'
        }],
        valueField: 'id',
        textField: 'text'
    });

    $('#department').combobox({
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

    $('#workers').datagrid({
        method: 'get',
        url: '/admin/workers',
        queryParams: {
            username: "",
            sex: "",
            department: "",
            realName: ""
        },

        iconCls: 'icon-search',
        pagination: true,
        columns: [[
            {
                field: 'username', title: '用户名', width: 100
            },
            {
                field: 'realName', title: '真实姓名', width: 100
            },
            {
                field: 'sex', title: '性别', width: 50
            },
            {
                field: 'phone', title: '电话号码', width: 150
            },
            {
                field: 'department', title: '部门', width: 150
            },
            {
                field: 'email', title: '邮箱地址', width: 150
            }
        ]]

    });

});
