var loadUsername = function () {
    console.log('加载用户名栏目模块...');
    $.ajax({
        url: '/worker/profile',
        type: 'get',
        success: function (data) {
            if (data.username) {
                $('#username-bar').html('您好 [' + data.realName + '] | <a style="color: cornflowerblue" href="/worker/logout">登出</a>');
            }
        }
    });
};

var treeUrl;
var loadTreeJson = function () {
    console.log('加载侧边栏数据模块...');
    $.ajax({
        url: '/tree/json',
        type: 'get',
        async: false,
        success: function (data) {
            if (data.state === 200) {
                treeUrl = data.url;
            }
        }
    });
};

$(function () {

    loadUsername();

    loadTreeJson();

    $('#tabs').tabs({
        fit: true,
        border: false
    });

    $('#tt').tree({
        url: treeUrl,
        method: 'get',
        lines: true,

        onClick: function (node) {
            if (node.url) {
                if ($('#tabs').tabs('exists', node.text)) {
                    $('#tabs').tabs('select', node.text);
                } else {
                    $('#tabs').tabs('add', {
                        title: node.text,
                        iconCls: node.iconCls,
                        closable: true,
                        href: '/' + node.url
                    })
                }
            }
        }

    });

});
