$(function() {
	$('.ref').each(function() {
		var index = $('.ref').index(this);
		$('.ref').eq(index).click(function() {
			bootbox.confirm({
				message : "拒绝该订单(自动召回员工)",
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
							url : "RejectServlet",
							data : {
								orderid : $('.orderid').eq(index).text(),
							},
							success : function(data) {
								if (data == "1") {
									bootbox.alert("已拒绝订单", function() {
										window.location.reload();
									});
								} else {
									bootbox.alert("error", function() {
										window.location.reload();
									});
								}
							}
						});

					}
				}
			});
		});
	});
});