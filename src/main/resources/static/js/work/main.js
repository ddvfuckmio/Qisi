$(function () {

    $('#tabs').tabs({
        fit: true,
        border: false,
    });

    $('#tt').tree({
        url: '/js/work/json/tree.json',
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
                        href: '/'+node.url,
                    })
                }
            }
        },
    });

});
