$(function() {
	$('#confirm').click(function() {
		var password = $('#password').val();
		var repeat = $('#repeat').val();
		$.ajax({
			url : global.context + '/admin/password',
			type : 'put',
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify({
				password : password,
				repeat : repeat
			}),
			success : function(data) {
				console.log(data);
			}
		});
		return false;
	});

	$.validator.addMethod('equal', function(value, element, params) {
		var password = $('#password').val();
		return password == value;
	}, $.validator.format('asdf'));

	$('#form').validate({
		onkeyup : false,
		rules : {
			password : {
				required : true,
				minlength : 6
			},
			repeat : {
				required : true,
				equal : true,
				minlength : 6
			}
		},
		messages : {
			password : {
				required : '请输入密码',
				minlength : '长度需大于6'
			},
			repeat : {
				required : '请确认密码',
				equal : '两次输入密码不一致',
				minlength : '长度需大于6'
			}
		}
	});
});
