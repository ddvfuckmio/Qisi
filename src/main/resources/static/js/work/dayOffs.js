function cancelAjax(id) {
    $.ajax({
        url: '/worker/dayOffCancel?id=' + id,
        type: 'post',
        success: function (data) {
            console.log(data);
            if (data.status === 200) {
                $('#dayOffs').datagrid('reload', {});
                $.messager.alert('操作成功!', '成功取消请假申请!');
            } else {
                $.messager.alert('操作失败!', data.msg);
            }
        }
    });
}

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

    var cancelDayOff = function (value, row, index) {
        return "<a style='color: blue' href='javascript:void(0);' onclick='cancelAjax(" + row.id + ")'>" + '取消申请' + "</a>";
    };

    var formatStatus = function (value, row, index) {
        switch (row.state) {
            case 1:
                return '等待人事审阅';
            case 2:
                return '人事已批准';
            case 3:
                return '申请已转交至上级';
            case 4:
                return '申请拒绝';
        }
    };

    $('#dayOffs').datagrid({
        method: 'get',
        url: '/worker/dayOffs',
        title: '员工请假记录',
        iconCls: 'icon-search',
        pagination: true,
        columns: [[
            {field: 'username', title: '用户名', width: 100},
            {
                field: 'startDate', title: '开始时间', width: 150, formatter: formatRowStart,
            },
            {
                field: 'endDate', title: '结束时间', width: 150, formatter: formatRowEnd,

            },
            {field: 'reason', title: '申请理由', width: 400},
            {field: 'state', title: '审批状态', width: 100, formatter: formatStatus,},
            {
                field: 'id', title: '操作', width: 100, formatter: cancelDayOff

            }
        ]]
    });


});
