<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Bootstrap core CSS -->
<link href="/admin/public/bootstrap.min.css" title="" rel="stylesheet" />
<link href="/admin/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="/admin/css/plugins/toastr/toastr.min.css" />
<!-- Animation CSS -->
<link rel="stylesheet" href="/admin/css/login.css" />
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="/admin/public/html5shiv.min.js"></script>
	<script src="/admin/public/respond.min.js"></script>
<![endif]-->
<title>在线答题云平台</title>
</head>
<body>
<div id="login_page" class="row col-lg-12">
<h1><strong>在线答题云平台</strong></h1>
<form id="login" class="col-lg-2 col-md-4 form-horizontal">
	<input type="text" class="form-control" name="userName" id="userName" placeholder="请输入用户名">
	<input style="margin:15px 0;" type="password" class="form-control" id="userPwd" name="userPwd" placeholder="请输入密码" />
	<input style="margin-bottom:15px;" type="checkbox" name="check" id="rmbUser" />记住密码
	<div class="col-lg-12">
		<button type="button" onclick="btnClick();" class="btn btn-primary">登录</button>
		<button type="button" class="btn btn-primary">注册</button>
	</div>
</form>
<span>版权所有：    技术支持：鹏翔科技</span>
</div>
<script src="/admin/public/jquery-1.11.3.js"></script>
<script src="/admin/js/plugins/toastr/toastr.min.js"></script>
<script src="/admin/js/cookies.js"></script>
		
	<script type="text/javascript">
	$(function(){
		//判断是否是在iframe中
		if (window.frames.length != parent.frames.length) {
			window.top.location.href="/login";
		}
		$("#userName").focus();
	})
	$("body").keydown(function(event){
		var code = event.keyCode;
		if(code==13){//回车
			btnClick(); //调用btnClick函数
		}
	});
	  function alertNew(msg,flag){
		   if(flag) {
				toastr.success(msg);
			} else {
				toastr.warning(msg);
			}
	   }
		function btnClick(){
			var flag = $("#rmbUser").is(':checked');
			if (flag == true) {
		        var userName = $("#userName").val();
		        var passWord = $("#userPwd").val();
		        $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
		        $.cookie("userName", userName, { expires: 7 }); // 存储一个带7天期限的 cookie
		        $.cookie("passWord", passWord, { expires: 7 }); // 存储一个带7天期限的 cookie
		    }else {
		        $.cookie("rmbUser", "false", { expires: -1 });
		        $.cookie("userName", '', { expires: -1 });
		        $.cookie("passWord", '', { expires: -1 });
		    }
			$.ajax({
				type:"post",
				data:$("#login").serialize(),
				url:"/login/userLogin",
				success:function(data){
					if(data.isLogin){
						var redirect='${redirectUrl}';
						if(redirect!=''){
							location.href=redirect;
							return;
						}
		        		location.href='/index';
		    		}else{
		    			var txt= data.message;
		    			alertNew(txt,false);
		    			$("#login input").val("");		                
		    		}
				},
				error:function(data){
					
				}
			})
		}
		
		//初始化页面时验证是否记住了密码
		$(document).ready(function() {
		    if ($.cookie("rmbUser") == "true") {
		        $("#rmbUser").attr("checked", true);
		        $("#userName").val($.cookie("userName"));
		        $("#userPwd").val($.cookie("passWord"));
		    }
		});
	</script>
</body>
</html>