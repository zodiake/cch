/**
 * Created by yagamai on 16-1-12.
 */
$(function () {
	$('textarea').ckeditor();
    $('div.imgUp').change(function (event) {
        var source = $(event.target);
        var id = source.attr('id');
        var img = source.parent().parent().find('img');
        var input = source.siblings().first();
        $.ajaxFileUpload({
                url: global.context + '/admin/upload',
                secureuri: false,
                fileElementId: source.attr('id'),
                dataType: 'json',
                success: function (data, status) {
                    if (status == 'success') {
                        img.attr('src', global.url + data.responseText);
                        input.val(global.url + data.responseText);
                    }
                    console.log(data);
                },
                error: function (data, status, e) {
                    alert(e);
                }
            }
        );
        return false;
    });

    $('.datepicker').datepicker({
        dateFormat: 'yy-mm-dd'
    });

    $('#form').validate({
        onkeyup: false,
        rules: {
            name: {
                required: true,
                remote: {
                    url: global.context + '/admin/parkingCoupons/name/duplicate',
                    data: {
                        name: function () {
                            return $('#name').val();
                        }
                    }
                }
            }
        },
        messages: {
            name: {
                required: '名称不能为空',
                remote:'名称不能为空'
            }
        }
    });
});
