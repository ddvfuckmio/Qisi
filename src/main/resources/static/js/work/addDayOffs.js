var reset = function () {
    $('#startDate').datebox('setValue', null);
    $('#endDate').datebox('setValue', null);
    $("#reason").val(null);
};

var submit = function () {
    console.log($('#startDate').val);
};

$(function () {

    //请假窗口
    $('#div-addDayOff').dialog({
        title: '员工请假',
        width: 300,
        height: 300,
        modal: true,
        iconCls: 'icon-login',
        onClose: function () {
            $(this).dialog('destroy');//销毁
            $('#tabs').tabs('close', '新增请假')
        },

    });

    $('#startDate').datebox({
        width: 180,
        required: true,
        editable: false,
        calendar: null,
    });

    $('#endDate').datebox({
        width: 180,
        required: true,
        editable: false
    });

    // $('#startDate').datebox('calendar').calendar({
    //     validator: function (date) {
    //         console.log('111');
    //         var now = new Date();
    //         var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 30);
    //         var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
    //         return d1 <= date && date <= d2;  //设置时间区间，大于等于d1,小于等于d2.
    //         // return date <= d2;
    //     }
    // });



    $('#startDate').datebox().datebox('calendar').calendar({
        validator: function (date) {
            console.log('222');
            var now = new Date();
            var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 10);
            return d1 <= date && date <= d2;
        }
    });

    $.parser.parse();
});
