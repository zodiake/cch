$(function () {
    $('.aside-small .par-nav>li').mouseover(function () {
        $(this).find('a i').addClass('active')
    }).mouseout(function () {
        $(this).find('a i').removeClass('active')
    })
    $('.sidebar-toggle').click(function () {
        $(this).find('i.icon-toggle').toggleClass('active');
        $('.aside').toggleClass('aside-small');
        if ($('.aside').hasClass('aside-small')) {
            $('.mainpanel').css('width', '96%');
        } else {
            $('.mainpanel').css('width', '90%');
        }
    });

    var detail_h = $('.mainpanel').height();
    $('.card-detail-wrap').css('height', detail_h);
    
	var alert = $("#alert");
	if(alert){
		setTimeout('$("#alert").hide()',2000);
	}
});