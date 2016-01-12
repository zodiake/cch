$(function() {
	$('div.col-md-5 input').change(
			function(event) {
				var source = $(event.target);
				var id = source.attr('id');
				$.ajaxFileUpload({
					url : global.context + '/admin/upload',
					secureuri : false,
					fileElementId : source.attr('id'),
					dataType : 'json',
					success : function(data, status) {
						if (status == 'success') {
							$('div.col-md-5 img').attr('src',
									global.url + data.responseText);
							$('#imgHref').val(global.url + data.responseText);
						}
						console.log(data);
					},
					error : function(data, status, e) {
						alert(e);
					}
				});
				return false;
			});

	$('#form').validate({
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/cards/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						}
					}
				}
			}
		},
		messages : {
			name : {
				required : '会员卡名称不能为空',
				remote : '会员卡名称重复'
			}
		}
	});
})