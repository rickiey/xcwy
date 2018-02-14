$(function() {
	$("#informationform").validate({
		submitHandler : function(form) {
			$.ajax({
				type : "POST",
				url : "InformationServlet",
				data : {
					name : $("#name").val(),
					phone : $("#phone").val(),
					address : $("#address").val(),
					description : $("#description").val(),
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

});