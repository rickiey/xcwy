$(function() {
	$("#registerform").validate({
		rules : {
			id : {
				required : true,
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
			id : {
				required : "请输入组织机构代码",
			},
			password : {
				required : "请输入密码"
			},
			confirmpassword : {
				required : "请输入密码",
				equalTo : "两次输入密码不一致"
			}
		},
		submitHandler : function(form) {
			$.ajax({
				type : "POST",
				url : "RegisterServlet",
				// async:false,
				data : {
					id : $("#id").val(),
					password : $("#password").val()
				},
				success : function(data) {
					if (data == "0") {
						bootbox.alert({
							message : "组织机构代码已注册",
							callback : function() {
							}
						})
					} else if (data == "1") {
						location.href = "home.jsp";
					}
				}
			});
		}
	});
	$.validator.addMethod("text", function(value, element) {
		var text = /^\d{9}$/; // 9位数字
		return this.optional(element) || (text.test(value));
	}, "9位数字");
	$.validator.addMethod("password", function(value, element) {
		var password = /^[a-zA-Z]\w{5,17}$/; // 字母开头,长度6-18,只能包含字母、数字和下划线
		return this.optional(element) || (password.test(value));
	}, "字母开头,长度6-18,只能包含字母、数字和下划线");
});