$(function() {
	$("#serviceform").validate({
		rules : {
			tyre : {
				required : true,
				isPrice : "#tyre"
			},
			unlocking : {
				required : true,
				isPrice : "#unlocking"
			},
			water : {
				required : true,
				isPrice : "#water"
			},
			electricity : {
				required : true,
				isPrice : "#electricity"
			},
			gasoline : {
				required : true,
				isPrice : "#gasoline"
			},
			trailer : {
				required : true,
				isPrice : "#trailer"
			},
		},
		messages : {
			tyre : {
				required : "请输入价格"
			},
			unlocking : {
				required : "请输入价格"
			},
			water : {
				required : "请输入价格"
			},
			electricity : {
				required : "请输入价格"
			},
			gasoline : {
				required : "请输入价格"
			},
			trailer : {
				required : "请输入价格"
			},
		},
		submitHandler : function(form) {
			$.ajax({
				type : "POST",
				url : "ServiceServlet",
				data : {
					tyre : $("#tyre").val(),
					unlocking : $("#unlocking").val(),
					water : $("#water").val(),
					electricity : $("#electricity").val(),
					gasoline : $("#gasoline").val(),
					trailer : $("#trailer").val(),
					repair : $("#repair").is(":checked") ? "面议" : "0",
					beauty : $("#beauty").is(":checked") ? "面议" : "0",
				},
				success : function(data) {
					if (data == "1") {
						bootbox.alert("修改成功", function() {
						});
					} else {
						bootbox.alert("error", function() {
						});
					}
				}
			});
		}
	});

	$.validator.addMethod("isPrice", function(value, element) {
		var price = /^\d{1,5}(\.\d{1,2})?$/; // 小数点前最多5位,小数点后最多2位的非负数
		return this.optional(element) || (price.test(value));
	}, "小数点前最多5位,小数点后最多2位的非负数");
});