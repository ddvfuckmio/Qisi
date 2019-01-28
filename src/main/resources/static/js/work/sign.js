$(function () {
    $('#sign-in').linkbutton({
        text: '上班打卡',
        size: 'large',
        iconCls: 'icon-search',
    });

    $('#sign-in').bind('click', function () {
        workerSign(1);
    });

    $('#sign-out').bind('click', function () {
        workerSign(2);
    });

    var workerSign = function (type) {
        $.ajax({
            url: '/worker/sign?type=' + type,
            type: "POST",
            success: function (data) {
                if (data.status === 200) {
                    $.messager.alert('签到成功!', data.msg);
                } else {
                    $.messager.alert('签到失败!', data.msg);
                }
            }
        })
    };

});
