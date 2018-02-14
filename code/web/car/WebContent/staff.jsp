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
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.min.js"></script>
<script src="js/staff.js"></script>
<script src="js/logout.js"></script>
<script src="js/staffdelete.js"></script>
<title>员工信息|行程无忧</title>
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
			<h1>员工信息</h1>
		</div>
		<!-- <p style="color: red">注意!若手机号码已注册,旧信息会被覆盖!</p> -->
		<form class="form-signin" id="staffform" method="post">
			<label for="mobile">手机号码</label>
			<div class="form-group">
				<input type="text" id="mobile" name="mobile" maxlength="11"
					class="form-control" placeholder="请输入手机号码" />
			</div>
			<label for="password">密码</label>
			<div class="form-group">
				<input type="password" id="password" name="password" maxlength="18"
					class="form-control" placeholder="请输入密码" />
			</div>
			<label for="confirmpassword">确认密码</label>
			<div class="form-group">
				<input type="password" id="confirmpassword" name="confirmpassword"
					maxlength="18" class="form-control" placeholder="请再次输入密码" />
			</div>
			<label for="confirmpassword">姓名</label>
			<div class="form-group">
				<input type="text" id="name" name="name" maxlength="10"
					class="form-control" placeholder="请输入员工姓名" />
			</div>
			<!-- <label for="photo">照片</label>
			<div class="form-group">
				<input type="file" id="photo">
			</div> -->
			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" type="submit"
					value="添加员工" />
			</div>
		</form>
	</div>



	<%
Connection conn = null; //当前的数据库连接
PreparedStatement pst = null;//向数据库发送sql语句
ResultSet rs = null;//结果集  

String mobile = request.getParameter("mobile");
String password = request.getParameter("password");
String name = request.getParameter("name");
String id = (String)request.getSession().getAttribute("id");

try
{
	Class.forName(Constants.DRIVER);
	conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
			Constants.DB_PASSWORD);
	String sql ="select * from staff_table where business_id='"+id+"'";
	pst = conn.prepareStatement(sql);
	rs = pst.executeQuery(sql);
%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">员工信息</div>
			<table class="table">
				<tr>
					<!-- <th>照片</th> -->
					<th>手机号码</th>
					<th>姓名</th>
					<th>操作</th>
				</tr>
				<%
	while(rs.next())
	{
%>
				<tr>
					<!-- <td>photo</td> -->
					<td class="phone"><%=rs.getString(1)%></td>
					<td><%=rs.getString(3)%></td>
					<td><a href="staff_alter.jsp?mobile=<%=rs.getString(1)%>">修改</a>|<a
						class="del">删除</a></td>
				</tr>
				<% 
	}
%>
			</table>
		</div>
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