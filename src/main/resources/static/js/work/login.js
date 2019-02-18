function login(url) {
    if (!($('#username').validatebox('isValid') && $('#password').validatebox('isValid'))) {
        $.messager.alert('登录失败!', '登录信息有误!');
    } else {
        $.ajax({
            url: url,
            contentType: 'application/json',
            type: 'post',
            data: JSON.stringify({
                username: $('#username').val(),
                password: $('#password').val(),
            }),

            beforeSend: function () {
                $.messager.progress({
                    text: '正在登陆中...',
                });
            },

            success: function (data) {
                $.messager.progress('close');
                console.log(data);
                if (data.status === 200) {
                    window.location.href = "http://localhost:8080/pages/workMain";
                } else {
                    $.messager.alert('登录失败!', '用户名或密码错误!');
                }
            },
        });
    }
}

$(function () {

    //登陆页面
    $('#login').dialog({
        title: '员工登陆',
        width: 300,
        height: 180,
        modal: true,
        iconCls: 'icon-login',
        buttons: '#login-button',
    });

    //验证用户名
    $('#username').validatebox({
        required: true,
        missingMessage: '用户名不能为空!',
    });

    //验证登陆密码
    $('#password').validatebox({
        required: true,
        // validType: 'length[6,30]',
        // invalidMessage: '密码格式有误',
        missingMessage: '用户密码不能为空!',
    });

    //登陆事件
    $('#worker-login').click(function () {
        login('/worker/login');
    });

    $('#admin-login').click(function () {
        login( '/admin/login');
    });
});
