$(function() {
	var url = "http://cchdev.b0.upaiyun.com/test/";
	$('div.col-md-2 input').change(
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
							$('div.col-md-3 img').attr('src',
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
		onkeyup : false,
		rules : {
			name : {
				required : true,
				maxlength : 10,
				remote : {
					url : global.context + '/admin/shops/name/duplicate',
					data : {
						name : function() {
							return $('#name').val()
						},
						id : $('#form').attr('data-id')
					}
				}
			},
			shopKey : {
				required : true,
				maxlength : 255,
				remote : {
					url : global.context + '/admin/shops/key/duplicate',
					data : {
						shopKey : function() {
							return $('#shopKey').val()
						},
						id : $('#form').attr('data-id')
					}
				}
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				maxlength : '名称最长不能超过10',
				remote : '名称重复'
			},
			shopKey : {
				required : 'Pos-Key不能为空',
				maxlength : 'Pos-Key最长不能超过255',
				remote : 'Pos-Key已存在'
			}
		}
	});
})