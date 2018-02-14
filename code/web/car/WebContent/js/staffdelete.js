$(function() {
	$('.del').each(function() {
		var index = $('.del').index(this);
		$('.del').eq(index).click(function() {
			bootbox.confirm({
				message : "删除该员工信息",
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
						$.ajax({
							type : "POST",
							url : "StaffDeleteServlet",
							data : {
								mobile : $('.phone').eq(index).text()
							},
							success : function(data) {
								if (data == "1") {
									bootbox.alert({
										message : "删除成功",
										callback : function() {
											window.location.reload();
										}
									})
								} else {
									bootbox.alert({
										message : "error",
										callback : function() {
										}
									})
								}
							}
						});
					}
				}
			});
		});
	});
});