$(function() {
	$('.send').each(function() {
		var index = $('.send').index(this);
		$('.send').eq(index).click(function() {
			bootbox.confirm({
				message : "派遣该员工信息",
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
							url : "SendStaffServlet",
							data : {
								mobile : $('.phone').eq(index).text(),
								order_id : $('.order_id').text(),
							},
							success : function(data) {
								if (data == "1") {
									bootbox.alert({
										message : "已派遣",
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