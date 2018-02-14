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
<script src="js/logout.js"></script>
<script src="js/sendstaff.js"></script>
<script src="js/withdrawstaff.js"></script>
<title>派遣员工|行程无忧</title>
</head>
<body>
	<%
		if (request.getSession().getAttribute("id") == null)
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
			<h1>
				派遣员工<small>订单号:</small><small class="order_id"><%=request.getParameter("order_id")%></small>
			</h1>
		</div>
	</div>

	<%
		Connection conn = null; //当前的数据库连接
		PreparedStatement pst = null;//向数据库发送sql语句
		ResultSet rs = null, rs2 = null;//结果集  

		String id = (String) request.getSession().getAttribute("id");

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			String sql = "select * from staff_table where business_id='" + id + "' and state =1";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			sql = "select * from staff_table where business_id='" + id + "' and state =0";
			pst = conn.prepareStatement(sql);
			rs2 = pst.executeQuery(sql);
	%>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">空闲员工</div>
			<table class="table table-hover">
				<tr>
					<th>手机号码</th>
					<th>姓名</th>
					<th>操作</th>
				</tr>
				<%
					while (rs.next()) {
				%>
				<tr>
					<td class="phone"><%=rs.getString(1)%></td>
					<td><%=rs.getString(3)%></td>
					<td><a class="send">派遣</a></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">繁忙员工</div>
			<table class="table table-hover">
				<tr>
					<th>手机号码</th>
					<th>姓名</th>
					<th>处理订单</th>
					<th>操作</th>
				</tr>
				<%
					while (rs2.next()) {
				%>
				<tr>
					<td class="withdrawphone"><%=rs2.getString(1)%></td>
					<td><%=rs2.getString(3)%></td>
					<th><%=rs2.getString(6)%></th>
					<td><a class="withdraw">召回</a></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
	<%
		} catch (Exception e) {
			e.printStackTrace();
		} finally//资源清理
		{
			try {
				rs.close(); //关闭ResultSet结果集
				rs2.close();
			} catch (Exception e2) {
			}
			try {
				pst.close();//关闭Statement对象
			} catch (Exception e3) {
			}
			try {
				conn.close();//关闭数据库连接
			} catch (Exception e4) {
			}
		}
	%>
</body>
</html>