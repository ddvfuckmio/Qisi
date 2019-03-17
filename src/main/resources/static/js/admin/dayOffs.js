// var load = function () {
//
//     var queryParams = $('#workers').datagrid('options').queryParams;
//
//     var username = $('#username').val();
//     var realName = $('#realName').val();
//     var department = $('#department').combobox('getText');
//     var sex = $('#sex').combobox('getText');
//
//     if (department === '不选择') {
//         department = '';
//     }
//
//     if (sex === '不选择') {
//         sex = ''
//     }
//
//     queryParams.username = username;
//     queryParams.realName = realName;
//     queryParams.department = department;
//     queryParams.sex = sex;
//
//
//     $('#workers').datagrid('reload');
//
// };
//
// var reset = function () {
//     $('#username').val = '';
//     $('#realName').val = '';
//
//     $('#department').combobox('setValue', 1);
//     $('#department').combobox('setText', '不选择');
//
//     $('#sex').combobox('setValue', 1);
//     $('#sex').combobox('setText', '不选择');
// };

var operator = function (id, state) {
    console.log(id, state);

    $.ajax({
        url: '/admin/operatorDayOff',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            state: state,
        }),
        success:
            function (data) {
                console.log(data);
                if (data.status === 200) {
                    $('#dayOffs').datagrid('reload', {});
                    $.messager.alert('操作成功!', '成功审批该条申请!');
                } else {
                    $.messager.alert('操作失败!', data.msg);
                }
            }


    })

};

$(function () {

    //unix时间戳转 年-月-日格式
    var formatDate = function (times) {
        var date = new Date(times);
        var year = date.getFullYear();
        var month = ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : '0' + (date.getMonth() + 1))
        var day = (date.getDate() > 10 ? date.getDate() : '0' + date.getDate())
        return year + '-' + month + '-' + date.getDate();
    };

    var formatRowStart = function (value, row, index) {
        return formatDate(new Date(row.startDate));
    };

    var formatRowEnd = function (value, row, index) {
        return formatDate(new Date(row.endDate));
    };

    var formatRowCreatedAt = function (value, row, index) {
        return formatDate(new Date(row.endDate));
    };

    var cancelDayOff = function (value, row, index) {
        var url = "<a style='color: blue' href='javascript:void(0);' onclick='operator(" + row.id + ",2)'>" + '同意申请' + "</a>";
        url = url + "       " + "<a style='color: blue' href='javascript:void(0);' onclick='operator(" + row.id + ",3)'>" + ' 提交上级' + "</a>";
        url = url + "       " + "<a style='color: blue' href='javascript:void(0);' onclick='operator(" + row.id + ",4)'>" + '驳回申请' + "</a>";
        return url;
    };


    var formatStatus = function (value, row, index) {
        switch (row.state) {
            case 1:
                return '等待人事审阅!';
            case 2:
                return '请假已经批准!';
            case 3:
                return '等待上级领导批准!';
            case 4:
                return '请假驳回!';
        }
    };

    $('#dayOffs').datagrid({
        method: 'get',
        url: '/admin/dayOffs',
        queryParams: {},

        iconCls: 'icon-search',
        pagination: true,
        columns: [[
            {
                field: 'username', title: '用户名', width: 100
            },
            {
                field: 'startDate', title: '开始时间', width: 150, formatter: formatRowStart
            },
            {
                field: 'endDate', title: '结束时间', width: 150, formatter: formatRowEnd
            },
            {
                field: 'reason', title: '请假说明', width: 150
            },
            {
                field: 'state', title: '状态', width: 150, formatter: formatStatus
            },
            {
                field: 'createdAt', title: '申请时间', width: 150, formatter: formatRowCreatedAt
            },
            {
                field: 'id', title: '操作', width: 180, formatter: cancelDayOff
            }
        ]]

    });

});
