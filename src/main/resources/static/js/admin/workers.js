var load = function () {

    var queryParams = $('#workers').datagrid('options').queryParams;

    var username = $('#username').val();
    var realName = $('#realName').val();
    var department = $('#department').val();
    var sex = $('#sex').val();


    queryParams.username = username;
    queryParams.realName = realName;
    queryParams.department = department;
    queryParams.sex = sex;


    $('#workers').datagrid('reload');

    queryParams.username = '';
    queryParams.realName = '';
    queryParams.sex = '';
    queryParams.department = '';
};

$(function () {

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
                field: 'phone', title: '电话号码', width: 100
            },
            {
                field: 'department', title: '部门', width: 100
            },
            {
                field: 'email', title: '邮箱地址', width: 130
            }
        ]]
    });

});
