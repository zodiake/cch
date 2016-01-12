$(function() {
	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/users/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						}
					}
				}
			},
			password : {
				required : true,
				minlength : 6
			}
		},
		messages : {
			name : {
				required : '用户名不能为空',
				remote : '用户名不能重复'
			},
			password : {
				required : '密码不能为空',
				minlength : '长度应大于6'
			}
		}
	});
});