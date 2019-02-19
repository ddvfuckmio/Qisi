var reset = function () {
    $('#startDate').datebox('setValue', null);
    $('#endDate').datebox('setValue', null);
    $("#reason").val(null);
};


$(function () {

    //请假窗口
    $('#div-dialog').dialog({
        title: '修改密码',
        width: 250,
        height: 250,
        modal: true,
        iconCls: 'icon-login',
        onClose: function () {
            $(this).dialog('destroy');          //销毁
            $('#tabs').tabs('close', '修改密码') //关闭标签页
        },
    });


    $('#password').validatebox({
        required: true,
        missingMessage: '登录密码不能为空!',
    });

    $('#newPassword').validatebox({
        required: true,
        missingMessage: '新的密码不能为空!',
    });

    $.parser.parse();
});
