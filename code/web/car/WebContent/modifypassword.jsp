<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css">
<style type="text/css">
body {
	padding-top: 50px;
}
</style>
<script src="js/jquery.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/messages_zh.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.min.js"></script>
<script src="js/modifypassword.js"></script>
<script src="js/logout.js"></script>
<title>修改密码|行程无忧</title>
</head>
<body>
	<%
if(request.getSession().getAttribute("id") == null)
	response.sendRedirect("index.jsp");
%>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a href="home.jsp" class="navbar-brand">行程无忧</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a>您好,<%=request.getSession().getAttribute("id")%></a></li>
				<li><a href="history.jsp">历史订单</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">设置 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="information.jsp">商家信息</a></li>
						<li><a href="service.jsp">服务信息</a></li>
						<li><a href="staff.jsp">员工信息</a></li>
						<li><a href="modifypassword.jsp">修改密码</a></li>
					</ul></li>
				<li><a id="logout" name="logout">注销</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div class="container">
		<div class="page-header">
			<h1>修改密码</h1>
		</div>
		<form class="form-signin" id="modifypasswordform">
			<label for="oldpassword">旧密码</label>
			<div class="form-group">
				<input type="password" id="oldpassword" name="oldpassword"
					maxlength="18" class="form-control" placeholder="请输入旧密码" />
			</div>
			<label for="password">新密码</label>
			<div class="form-group">
				<input type="password" id="password" name="password" maxlength="18"
					class="form-control" placeholder="请输入新密码" />
			</div>
			<label for="confirmpassword">确认密码</label>
			<div class="form-group">
				<input type="password" id="confirmpassword" name="confirmpassword"
					maxlength="18" class="form-control" placeholder="请再次输入新密码" />
			</div>
			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" type="submit"
					value="提交" />
			</div>
		</form>
	</div>
</body>
</html>