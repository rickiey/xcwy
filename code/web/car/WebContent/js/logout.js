$(function() {
	$("#logout").click(function() {
		bootbox.confirm({
			message : "注销",
			buttons : {
				confirm : {
					label : '确定',
				},
				cancel : {
					label : '取消',
				}
			},
			callback : function(result) {
				if (result) {
					location.href = "LogoutServlet";
				}
			}
		});
	});

});