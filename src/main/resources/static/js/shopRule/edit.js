$(function () {
    $('button.confirm').click(function () {
        $('.success-modal,.fixed').show().delay(2000).fadeOut(500);
    })

    $('#valid-button').click(function (e) {
        var self = $(e.target);
        var id = self.attr('data-id');
        $("#dialog-confirm").dialog({
            resizable: false,
            height: 140,
            modal: true,
            buttons: {
                "取消": function () {
                    $(this).dialog("close");
                },
                "确认": function () {
                    $.ajax({
                        url: global.context + '/admin/shopRules/' + id + '/valid',
                        type: 'put',
                        success: function (data) {
                            if (data.status == 'success') {
                                $('#state-text').html('132')
                            }
                        }
                    });
                }
            }
        });
    });

    $('.datepicker').datepicker({
        dateFormat: 'yy-mm-dd'
    });

});