var reset = function () {
    $('#startDate').datebox('setValue', null);
    $('#endDate').datebox('setValue', null);
    $("#reason").val(null);
};

var submit = function () {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var reason = $('#reason').val();

    if (startDate == '' || endDate == '' || reason == '') {
        $.messager.alert('操作有误!', '请填写所有表单信息!');
        return;
    }

    $.ajax({
        url: '/worker/addDayOff',
        contentType: 'application/json',
        type: 'post',
        data: JSON.stringify({
            startDate: startDate,
            endDate: endDate,
            reason: reason,
        }),

        success: function (data) {
            console.log(data);
            if (data.status === 200) {
                $.messager.alert('操作成功!', '申请记录已经提交!');
            } else {
                $.messager.alert('操作失败!', data.msg);
            }
        },
    });
};


//Utils
function parseDate(dateStr) {
    var strArray = dateStr.split("-");
    if (strArray.length == 3) {
        return new Date(strArray[0], strArray[1] - 1, strArray[2]);
    } else {
        return new Date();
    }
}

$(function () {

    //请假窗口
    $('#div-addDayOff').dialog({
        title: '员工请假',
        width: 300,
        height: 300,
        modal: true,
        iconCls: 'icon-login',
        onClose: function () {
            $(this).dialog('destroy');          //销毁
            $('#tabs').tabs('close', '新增请假') //关闭标签页
        },

    });

    $('#startDate').datebox({
        width: 180,
        required: true,
        editable: false,
        onSelect: function (date) {
            var endDate = $('#endDate').datebox('getValue');
            if (endDate != null || endDate != '') {
                var end = parseDate(endDate);
                if (date < end) {
                    $.messager.alert('操作有误!', '当前时间矛盾,已重置结束时间!');
                    $('#endDate').datebox('setValue', null);
                    return;
                }
            }
        },
    });

    $('#endDate').datebox({
        width: 180,
        required: true,
        editable: false,
        onSelect: function (date) {
            var startDate = $('#startDate').datebox('getValue');
            if (startDate == '') {
                $.messager.alert('操作失败!', '请先选择开始日期!');
                $('#endDate').datebox('setValue', null);
                return;
            }
            var start = parseDate(startDate);
            if (date < start) {
                $.messager.alert('操作失败!', '结束日期必须大于开始或者等于日期!');
                $('#endDate').datebox('setValue', null);
                return;
            }
        },
    });


    $('#startDate').datebox('calendar').calendar({
        firstDay: 1,
        validator: function (date) {
            var now = new Date();
            var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date >= today;
        },
    });

    $('#endDate').datebox('calendar').calendar({
        firstDay: 1,
        validator: function (date) {
            var now = new Date();
            var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date >= today;
        },
    })

    $.parser.parse();
})
;
