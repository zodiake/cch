$(function () {
    $('button.confirm').click(function () {
        $('.success-modal,.fixed').show().delay(2000).fadeOut(500);
    })
    
    $('#validate').click(function () {
        var self = $(this);
        var id = self.attr('member');
        $.ajax({
            url: global.context + '/admin/member/' + id + '/validate',
            method: 'put',
            success: function (data) {
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

    $('#ruleCategory').change(function (e) {
        var self = $(e.target);
        // 注册
        if (self.val() == 1) {
            tmp = $('#rate-div').children().remove();
        } else {
            if ($('#rate-div').children().size() == 0 && tmp != null) {
                $('#rate-div').append(tmp);
            }
        }
    });

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
                        url: global.context + '/admin/cardRules/' + id + '/valid',
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