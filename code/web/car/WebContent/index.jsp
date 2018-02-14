<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录|行程无忧</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/messages_zh.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.min.js"></script>
<script src="js/login.js"></script>
</head>

<body>
	<%
		if (request.getSession().getAttribute("id") != null)
			response.sendRedirect("home.jsp");
	%>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a href="" class="navbar-brand">行程无忧</a>
		</div>
	</div>
	</nav>

	<div style="position: absolute; width: 100%; height: 100%; z-index: -1">
		<img src="images/bg.jpg" height="100%" width="100%" />
	</div>

	<div class="container">
		<div style="padding-top:300px">
		<div style="margin:auto ;width:50%;">
			<form id="loginform" method="post">
				<label for="id">商家ID</label>
				<div class="form-group">
					<input type="text" placeholder="请输入商家ID" id="id" name="id"
						class="form-control" maxlength="9" />
				</div>
				<label for="password">密码</label>
				<div class="form-group">
					<input type="password" placeholder="请输入密码" id="password"
						name="password" maxlength="18"  class="form-control"/>
				</div>
				<div class="form-group">
					<input type="submit" value="登录"
						class="btn btn-lg btn-success btn-block" />
				</div>
				<a href="register.jsp" style="color:#222">注册</a>
			</form>
		</div>
		</div>
	</div>
	
</body>
</html>