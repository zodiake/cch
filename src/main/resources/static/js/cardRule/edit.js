$(function() {
	$('button.confirm').click(function() {
		$('.success-modal,.fixed').show().delay(2000).fadeOut(500);
	})

	$('#validate').click(function() {
		var self = $(this);
		var id = self.attr('member');
		$.ajax({
			url : '/admin/member/' + id + '/validate',
			method : 'put',
			success : function(data) {
				if (data.status == 'success') {
					if (data.obj.valid == 'VALID') {
						self.val('valid');
					} else if (data.obj.valid == 'INVALID') {
						self.val('invalid');
					}
				}
			}
		});
	});

	var tmp;

	$('#ruleCategory').change(function(e) {
		var self = $(e.target);
		// 注册
		if (self.val() == 1) {
			tmp = $('#rate-div').children().remove();
		} else {
			console.log($('#rate-div').children().size());
			console.log(tmp);
			if ($('#rate-div').children().size() == 0 && tmp != null) {
				$('#rate-div').append(tmp);
			}
		}
	});

	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd',
	});
});