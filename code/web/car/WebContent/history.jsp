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
<meta http-equiv="refresh" content="5">
<link rel="stylesheet" href="css/bootstrap.min.css">
<style type="text/css">
body {
	padding-top: 50px;
}
</style>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.min.js"></script>
<script src="js/logout.js"></script>
<title>历史订单|行程无忧</title>
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

	<!-- <div class="container"> -->
	<div class="page-header">
		<h1>历史订单</h1>
	</div>
	<!-- </div> -->
	<%
Connection conn = null; //当前的数据库连接
PreparedStatement pst = null;//向数据库发送sql语句
ResultSet rs = null;//结果集  

String id = (String)request.getSession().getAttribute("id");

try
{
	Class.forName(Constants.DRIVER);
	conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
			Constants.DB_PASSWORD);
	String sql ="select * from order_table where business_id='"+id+"' and (state=2 or state=-1)";
	pst = conn.prepareStatement(sql);
	rs = pst.executeQuery(sql);
%>
	<div class="panel panel-default">
		<!-- <div class="panel-heading">订单信息</div> -->
		<table class="table">
			<tr>
				<th>订单编号</th>
				<th>下单时间</th>
				<th>结单时间</th>
				<th>车主姓名</th>
				<th>车主手机</th>
				<th>车牌</th>
				<th>车型</th>
				<th>类型</th>
				<th>地点</th>
				<th>描述</th>
				<th>金额</th>
				<th>状态</th>
			</tr>
			<% 
	while(rs.next())
	{
%>
			<tr class="<%=rs.getString(13).equals("-1")?"danger":"success"%>">
				<td><%=rs.getString(1)%></td>
				<td><%=rs.getString(2)%></td>
				<td><%=rs.getString(3)%></td>
				<td><%=rs.getString(8)%></td>
				<td><%=rs.getString(7)%></td>
				<td><%=rs.getString(10)%></td>
				<td><%=rs.getString(9)%></td>
				<td><%=rs.getString(14)%></td>
				<td><%=rs.getString(4)%></td>
				<td><%=rs.getString(5)%></td>
				<td><%=rs.getString(6)%></td>
				<td><%=rs.getString(13).equals("-1")?"拒单":"结单"%></td>
			</tr>
			<% 
	}
%>
		</table>
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