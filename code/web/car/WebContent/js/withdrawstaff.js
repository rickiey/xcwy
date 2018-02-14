$(function() {
	$('.withdraw').each(function() {
		var index = $('.withdraw').index(this);
		$('.withdraw').eq(index).click(function() {
			bootbox.confirm({
				message : "召回该员工信息",
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
							url : "WithdrawStaffServlet",
							data : {
								mobile : $('.withdrawphone').eq(index).text()
							},
							success : function(data) {
								if (data == "1") {
									bootbox.alert({
										message : "已召回",
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