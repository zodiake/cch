$(function() {
	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/authorities/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						},
						id:function(){
							return $('#form').attr('data-id');
						}
					}
				}
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				remote : '名称不能重复'
			}
		}
	});
});