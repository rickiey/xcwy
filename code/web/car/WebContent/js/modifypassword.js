$(function() {
	$("#modifypasswordform").validate({
		rules : {
			oldpassword : {
				required : true
			},
			password : {
				required : true
			},
			confirmpassword : {
				required : true,
				equalTo : "#password"
			},
		},
		messages : {
			oldpassword : {
				required : "请输入旧密码",
			},
			password : {
				required : "请输入新密码",
			},
			confirmpassword : {
				required : "请再次输入新密码",
				equalTo : "两次输入密码不一致"
			}
		},
		submitHandler : function(form) {
			$.ajax({
				type : "POST",
				url : "ModifyPasswordServlet",
				// async:false,
				data : {
					oldpassword : $("#oldpassword").val(),
					password : $("#password").val()
				},
				success : function(data) {
					if (data == "1") {
						bootbox.alert("修改成功", function() {
						});
					} else if (data == "0") {
						bootbox.alert("旧密码输入错误", function() {
						});
					} else if (data == "-1") {
						bootbox.alert("新密码必须与旧密码不同", function() {
						});
					} else {
						bootbox.alert("error", function() {
						});
					}
				}
			});
		}
	});

	$.validator.addMethod("password", function(value, element) {
		var password = /^[a-zA-Z]\w{5,17}$/; // 字母开头,长度6-18,只能包含字母、数字和下划线
		return this.optional(element) || (password.test(value));
	}, "字母开头,长度6-18,只能包含字母、数字和下划线");

});