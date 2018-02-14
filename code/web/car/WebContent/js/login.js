$(function() {
	$("#loginform").validate({
		rules : {
			id : {
				required : true
			},
			password : {
				required : true
			},
		},
		messages : {
			id : {
				required : "请输入组织机构代码"
			},
			password : {
				required : "请输入密码"
			}
		},
		submitHandler : function(form) {
			$.ajax({
				type : "POST",
				url : "LoginServlet",
				// async:false,
				data : {
					id : $("#id").val(),
					password : $("#password").val()
				},
				success : function(data) {
					if (data == "0") {
						bootbox.alert("组织机构代码不存在", function() {
						});
					} else if (data == "-1") {
						bootbox.alert("密码错误", function() {
						});
					} else if (data == "1") {
						location.href = "home.jsp";
					} else {
						bootbox.alert("error", function() {
						});
					}
				}
			});
		}
	});
});