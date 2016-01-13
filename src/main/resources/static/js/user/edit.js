$(function() {
	$('#validate-button').click(
			function() {
				var self = $(this);
				$.ajax({
					url : global.context + '/admin/user/'
							+ self.attr('data-id') + '/validate',
					type : 'put',
					success : function(data) {
						console.log(data);
					}
				})
			});
});