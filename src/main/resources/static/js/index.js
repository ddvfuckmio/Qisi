$(function (){
    // $('#login').form({
    // url:'/test/login',
    // onSubmit: function(){
    // },
    // success:function(data){
    // 	var response = eval('(' + data + ')');
    // 	if(response.status == 200) {
    //         console.log(response);
    //         // var url = `${pageContext.request.contextPath}`+"courses";
    //         // console.log(url);
    //         window.location.href= "http://localhost:8080/courses";
    //     }
    // },
   // });
   //  ?

    $('#users').datagrid({
        method:'get',
        url:'/test/users',
        title:'用户列表',
        iconCls:'icon-search',
        pagination: true,
        columns:[[
            {field:'username',title:'用户名',width:100},
            {field:'password',title:'密码',width:300},
            {field:'job',title:'职业',width:100}
        ]]
    });
});
