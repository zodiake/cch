$(function() {
	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : '规则名称不能为空'
			}
		}
	});
});