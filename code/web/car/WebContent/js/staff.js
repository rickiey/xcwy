$(function() {
	$("#staffform").validate({
		rules : {
			mobile : {
				required : true,
				isMobile : "#mobile"
			},
			password : {
				required : true
			},
			confirmpassword : {
				required : true,
				equalTo : "#password"
			},
			name : {
				required : true,
				isName : "#name"
			},
		},
		messages : {
			mobile : {
				required : "请输入手机号码",
			},
			password : {
				required : "请输入密码"
			},
			confirmpassword : {
				required : "请输入密码",
				equalTo : "两次输入密码不一致"
			},
			name : {
				required : "请输入员工姓名"
			}
		},
		submitHandler : function(form) {
			$.ajax({
				type : "POST",
				url : "StaffRegisterServlet",
				data : {
					mobile : $("#mobile").val(),
					password : $("#password").val(),
					name : $("#name").val()
				},
				success : function(data) {
					if (data == "0") {
						bootbox.alert({
							message : "该员工已注册",
							callback : function() {
								window.location.reload()
							}
						})
					} else if (data == "1") {
						bootbox.alert({
							message : "注册成功",
							callback : function() {
								window.location.reload()
							}
						})
					}
				}
			});
		}
	});

	$.validator
			.addMethod(
					"isMobile",
					function(value, element) {
						var mobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
						return this.optional(element) || (mobile.test(value));
					}, "手机号码格式不正确");

	$.validator.addMethod("password", function(value, element) {
		var password = /^[a-zA-Z]\w{5,17}$/; // 字母开头,长度6-18,只能包含字母、数字和下划线
		return this.optional(element) || (password.test(value));
	}, "字母开头,长度6-18,只能包含字母、数字和下划线");

	$.validator.addMethod("isName", function(value, element) {
		var name = /^[\u4e00-\u9fa5]{0,}$/;
		return this.optional(element) || (name.test(value));
	}, "姓名格式不正确");
});