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
<script src="js/service.js"></script>
<title>服务信息|行程无忧</title>
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

try
{
	Class.forName(Constants.DRIVER);
	conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
			Constants.DB_PASSWORD);
	String sql ="select * from businessservice_table where id='"+id+"'";
	pst = conn.prepareStatement(sql);
	rs = pst.executeQuery(sql);
%>
	<div class="container">
		<div class="page-header">
			<h1>
				服务信息<small>(0代表不提供该服务)</small>
			</h1>
		</div>
		<%
	if(rs.next())
	{
%>
		<form class="form-signin" id="serviceform">
			<label for="tyre">换胎</label>
			<div class="form-group">
				<input type="text" class="form-control" id="tyre" name="tyre"
					maxlength="10" placeholder="价格" value="<%=rs.getString(2)%>" />
			</div>
			<label for="unlocking">开锁</label>
			<div class="form-group">
				<input type="text" class="form-control" id="unlocking"
					name="unlocking" maxlength="10" placeholder="价格"
					value="<%=rs.getString(3)%>" />
			</div>
			<label for="water">送水</label>
			<div class="form-group">
				<input type="text" class="form-control" id="water" name="water"
					maxlength="10" placeholder="价格" value="<%=rs.getString(4)%>" />
			</div>
			<label for="electricity">搭电</label>
			<div class="form-group">
				<input type="text" class="form-control" id="electricity"
					name="electricity" maxlength="10" placeholder="价格"
					value="<%=rs.getString(5)%>" />
			</div>
			<label for="gasoline">送油</label>
			<div class="form-group">
				<input type="text" class="form-control" id="gasoline"
					name="gasoline" maxlength="10" placeholder="价格"
					value="<%=rs.getString(6)%>" />
			</div>
			<label for="trailer">拖车</label>
			<div class="form-group">
				<input type="text" class="form-control" id="trailer" name="trailer"
					maxlength="10" placeholder="价格" value="<%=rs.getString(7)%>" />
			</div>
			<label class="checkbox-inline"> <input type="checkbox"
				id="repair" name="service" />汽车维修
			</label> <label class="checkbox-inline"> <input type="checkbox"
				id="beauty" name="service" />汽车美容
			</label>

			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" type="submit"
					value="提交" />
			</div>
		</form>
		<% 
	}
	else{
%>
		<form class="form-signin" id="serviceform">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">换胎</span> <input type="text"
						class="form-control" id="tyre" name="tyre" placeholder="价格" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">开锁</span> <input type="text"
						class="form-control" id="unlocking" name="unlocking"
						placeholder="价格" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">送水</span> <input type="text"
						class="form-control" id="water" name="water" placeholder="价格" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">搭电</span> <input type="text"
						class="form-control" id="electricity" name="electricity"
						placeholder="价格" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">送油</span> <input type="text"
						class="form-control" id="gasoline" name="gasoline"
						placeholder="价格" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">拖车</span> <input type="text"
						class="form-control" id="trailer" name="trailer" placeholder="价格" />
				</div>
			</div>
			<label class="checkbox-inline"> <input type="checkbox"
				id="repair" name="service" />汽车维修
			</label> <label class="checkbox-inline"> <input type="checkbox"
				id="beauty" name="service" />汽车美容
			</label>
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