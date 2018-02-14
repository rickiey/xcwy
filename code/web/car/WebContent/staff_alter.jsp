<%@ page language="java"
	import="java.io.IOException,java.sql.Connection,java.sql.DriverManager,
java.sql.PreparedStatement,java.sql.ResultSet,javax.servlet.ServletException,javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,com.constants.Constants"%>
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
<script src="js/logout.js"></script>
<script src="js/staffalter.js"></script>
<title>修改员工信息|行程无忧</title>
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

	<%
Connection conn = null; //当前的数据库连接
PreparedStatement pst = null;//向数据库发送sql语句
ResultSet rs = null;//结果集  

String id = (String)request.getSession().getAttribute("id");
String mobile = request.getParameter("mobile");

try
{
	Class.forName(Constants.DRIVER);
	conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
			Constants.DB_PASSWORD);
	String sql = "select * from staff_table where mobile='"+mobile+"' and business_id='"+id+"'";
	pst = conn.prepareStatement(sql); //创建Statement对象
	rs = pst.executeQuery(sql);
%>
	<div class="container">
		<div class="page-header">
			<h1>员工
				<small id="mobile"><%=request.getParameter("mobile")%></small>
			</h1>
		</div>
		<%
	if(rs.next())
	{
%>
		<form class="form-signin" id="staffalterform">
			<%-- <label for="newmobile">新手机号码</label>
			<div class="form-group">
				<input type="text" id="mobile" name="mobile"
					class="form-control" placeholder="请输入新手机号码"
					value="<%=rs.getString(1)%>" />
			</div> --%>
			<label for="password">密码</label>
			<div class="form-group">
				<input type="password" id="password" name="password"
					class="form-control" placeholder="请输入新密码"
					value="<%=rs.getString(2)%>" />
			</div>
			<label for="confirmpassword">确认密码</label>
			<div class="form-group">
				<input type="password" id="confirmpassword" name="confirmpassword"
					class="form-control" placeholder="请再次输入密码"
					value="<%=rs.getString(2)%>" />
			</div>
			<label for="name">姓名</label>
			<div class="form-group">
				<input type="text" id="name" name="name" class="form-control"
					placeholder="请输入员工姓名" value="<%=rs.getString(3)%>" />
			</div>
			<!-- <label for="photo">照片</label>
			<div class="form-group">
				<input type="file" id="photo" name="photo" />
			</div> -->
			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" type="submit"
					value="提交" />
			</div>
		</form>
		<% 	
	}
%>
	</div>
	<%
}
catch(Exception e)
{
	e.printStackTrace();
}
finally//资源清理
{
	 try
	 {
		 rs.close(); //关闭ResultSet结果集
	 }catch(Exception e2){}
	 try
	 {
		 pst.close();//关闭Statement对象
	 }catch(Exception e3){}
	 try
	 {
		 conn.close();//关闭数据库连接
	 }catch(Exception e4){}
}
%>
</body>
</html>