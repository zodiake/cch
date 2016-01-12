$(function() {
	$('#ruleCategory').change(function(e) {
		var self = $(e.target);
		if (self.val() == 1) {
			tmp = $('#rate-div').children().remove();
		} else {
			if ($('#rate-div').children().size() == 0 && tmp != null) {
				$('#rate-div').append(tmp);
			}
		}
	});

	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd'
	});

	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/cardRules/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						}
					}
				}
			},
			card : {
				required : true
			},
			ruleCategory : {
				required : true
			}
		},
		messages : {
			name : {
				required : '规则名称不能为空',
				remote : '名称不能重复'
			},
			card : {
				required : '所属会员卡不能为空'
			},
			ruleCategory : {
				required : '积分类型不能为空'
			}
		}
	})
});