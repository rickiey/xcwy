$(function() {
	$('.acc')
			.each(
					function() {
						var index = $('.acc').index(this);
						$('.acc')
								.eq(index)
								.click(
										function() {
											if ($('.state').eq(index).text() == "未处理") {
												bootbox
														.confirm({
															message : "接受该订单",
															buttons : {
																confirm : {
																	label : '确定',
																},
																cancel : {
																	label : '取消',
																}
															},
															callback : function(
																	result) {
																if (result) {
																	$
																			.ajax({
																				type : "POST",
																				url : "AcceptServlet",
																				data : {
																					orderid : $(
																							'.orderid')
																							.eq(
																									index)
																							.text(),
																					service_condition : $(
																							'.service_condition')
																							.eq(
																									index)
																							.text(),
																				},
																				success : function(
																						data) {
																					if (data == "1") {
																						bootbox
																								.alert(
																										"已接受订单",
																										function() {
																											window.location
																													.reload();
																										});
																					} else {
																						bootbox
																								.alert(
																										"error",
																										function() {
																											window.location
																													.reload();
																										});
																					}
																				}
																			});
																}
															}
														});
											} else {
												window.location.href = "detail.jsp?order_id="
														+ $('.orderid').eq(
																index).text();
											}
										});
					});
});