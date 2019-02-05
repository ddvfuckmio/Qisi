$(function () {

    //unix时间戳转 年-月-日格式
    var formatDate = function (times) {
        var date = new Date(times);
        return date.getFullYear() + '-' + date.getMonth() + 1 + '-' + date.getDate();
    };
})
