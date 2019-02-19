var reset = function () {
    $('#password').val(null);
    $('#newPassword').val(null);
};

var submit = function () {
    var password = $('#password').val();
    var newPassword = $('#newPassword').val();

    if (password === '' || newPassword === '') {
        $.messager.alert('操作有失败', '修改信息填写有误!');
        return;
    }

    $.ajax({
        url: '/worker/updatePassword',
        contentType: 'application/json',
        type: 'post',
        data: JSON.stringify({
            password: password,
            newPassword: newPassword
        }),

        success: function (data) {
            console.log(data);
            if (data.status === 200) {
                $.messager.alert('操作成功!', '密码修改成功!');
            } else {
                $.messager.alert('操作失败!', data.msg);
            }
        }

    });
};


$(function () {

    //请假窗口
    $('#div-dialog').dialog({
        title: '修改密码',
        width: 250,
        height: 180,
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
