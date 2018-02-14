<%@ page language="java"
	import="java.io.IOException,java.sql.Connection,java.sql.DriverManager,
java.sql.PreparedStatement,java.sql.ResultSet,javax.servlet.ServletException,javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,com.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="css/bootstrap.min.css">
<style type="text/css">
html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
	font-size: 14px;
}

body {
	padding-top: 50px;
}

#l-map {
	height: 800px;
	width: 100%;
}

#r-result {
	width: 100%;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=lLuokpU2dhZcx0eXOHnlGbc2VXsHpGKp"></script>
<script src="js/jquery.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/messages_zh.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.min.js"></script>
<script src="js/logout.js"></script>
<script src="js/information.js"></script>
<script>
	function PreviewImage(imgFile) {
		var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
		if (!pattern.test(imgFile.value)) {
			alert("系统仅支持jpg/jpeg/png/gif/bmp格式的照片！");
			imgFile.focus();
		} else {
			var path;
			if (document.all)//IE
			{
				imgFile.select();
				path = document.selection.createRange().text;
				document.getElementById("imgPreview").innerHTML = "";
				document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
						+ path + "\")";//使用滤镜效果
			} else//FF
			{
				path = URL.createObjectURL(imgFile.files[0]);
				document.getElementById("imgPreview").innerHTML = "<img src='"+path+"' width='600px' height='400px'/>";
			}
		}
	}
</script>
<title>商家信息|行程无忧</title>
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
	<%
		Connection conn = null; //当前的数据库连接
		PreparedStatement pst = null;//向数据库发送sql语句
		ResultSet rs = null;//结果集  

		String id = (String) request.getSession().getAttribute("id");

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			String sql = "select * from businessinfo_table where id='" + id + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next()) {
	%>
	<div class="container">
		<div class="page-header">
			<h1>商家信息</h1>
		</div>

		<div id="l-map"></div>
		<div id="r-result">
			定位不准,手动输入:<input type="text" id="suggestId" size="20"
				style="width: 150px;" />
		</div>
		<div id="searchResultPanel"
			style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>

		<form method="post" action="FileUp" enctype="multipart/form-data">
			<input type="file" name="file" onchange='PreviewImage(this)' />
			<div id="imgPreview">
				<img src="">
			</div>
			<input type="submit" value="提交图片"><br /> <img
				src=<%="upload/" + rs.getString(6)%> width="600px" height="400px">
		</form>

		<form class="form-signin" id="informationform" class="form-signin">
			<label for="name">名称</label>
			<div class="form-group">
				<input type="text" id="name" name="name" class="form-control"
					placeholder="请输入商家名称" value="<%=rs.getString(2)%>" />
			</div>
			<label for="phone">电话</label>
			<div class="form-group">
				<input type="text" id="phone" name="phone" class="form-control"
					maxlength="20" placeholder="请输入商家电话" value="<%=rs.getString(3)%>" />
			</div>
			<label for="address">地址</label>
			<div class="form-group">
				<input type="text" id="address" name="address" class="form-control"
					placeholder="请输入商家地址" value="<%=rs.getString(4)%>" />
			</div>
			<label for="description">具体描述</label>
			<div class="form-group">
				<textarea id="description" class="form-control" rows="3"
					placeholder="请输入服务描述"><%=rs.getString(5)%></textarea>
			</div>
			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" type="submit"
					value="提交" />
			</div>
		</form>

		<%
			} else {
		%>
		<div class="container">
			<div class="page-header">
				<h1>商家信息</h1>
			</div>

			<div id="l-map"></div>
			<div id="r-result">
				定位不准,手动输入:<input type="text" id="suggestId" size="20"
					style="width: 150px;" />
			</div>
			<div id="searchResultPanel"
				style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>

			<form method="post" action="FileUp" enctype="multipart/form-data"
				class="form-signin">
				<input type="file" name="file" onchange='PreviewImage(this)' />
				<div id="imgPreview">
					<img src="">
				</div>
				<input type="submit" value="提交图片"><br /> <img src=""
					width="600px" height="400px">
			</form>

			<form class="form-signin" id="informationform">
				<label for="name">名称</label>
				<div class="form-group">
					<input type="text" id="name" name="name" class="form-control"
						placeholder="请输入商家名称" />
				</div>
				<label for="phone">电话</label>
				<div class="form-group">
					<input type="text" id="phone" name="phone" class="form-control"
						placeholder="请输入商家电话" />
				</div>
				<label for="address">地址</label>
				<div class="form-group">
					<input type="text" id="address" name="address" class="form-control"
						placeholder="请输入商家地址" />
				</div>
				<label for="description">具体描述</label>
				<div class="form-group">
					<textarea id="description" class="form-control" rows="3"
						placeholder="请输入服务描述"></textarea>
				</div>

				<div class="form-group">
					<input class="btn btn-lg btn-primary btn-block" type="submit"
						value="提交" />
				</div>
			</form>

			<%
				}
			%>
		</div>
	</div>
	<%
		} catch (Exception e) {
			e.printStackTrace();
		} finally//资源清理
		{
			try {
				rs.close(); //关闭ResultSet结果集
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
<script type="text/javascript">
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}

	var map = new BMap.Map("l-map");
	map.centerAndZoom("南京", 12); // 初始化地图,设置城市和地图级别。
	map.enableScrollWheelZoom();

	var measuring_scale = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_BOTTOM_LEFT
	});
	map.addControl(measuring_scale);

	var geolocationControl = new BMap.GeolocationControl();
	geolocationControl.addEventListener("locationSuccess", function(e) {
		// 定位成功事件
		//alert("test"+e.location.lng);
		var address = '';
		address += e.addressComponent.province;
		address += e.addressComponent.city;
		address += e.addressComponent.district;
		address += e.addressComponent.street;
		address += e.addressComponent.streetNumber;
		alert("当前定位地址为：" + address);
	});
	geolocationControl.addEventListener("locationError", function(e) {
		// 定位失败事件
		alert(e.message);
	});
	map.addControl(geolocationControl);

	var ac = new BMap.Autocomplete( //建立一个自动完成的对象
	{
		"input" : "suggestId",
		"location" : map
	});

	ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
		var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province + _value.city + _value.district
					+ _value.street + _value.business;
		}
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = "
				+ value;

		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province + _value.city + _value.district
					+ _value.street + _value.business;
		}
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = "
				+ value;
		G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
		var _value = e.item.value;
		myValue = _value.province + _value.city + _value.district
				+ _value.street + _value.business;
		G("searchResultPanel").innerHTML = "onconfirm<br />index = "
				+ e.item.index + "<br />myValue = " + myValue;

		setPlace();
	});

	function setPlace() {
		map.clearOverlays(); //清除地图上所有覆盖物
		function myFun() {
			var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMap.Marker(pp)); //添加标注
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
			onSearchComplete : myFun
		});
		local.search(myValue);
	}
</script>