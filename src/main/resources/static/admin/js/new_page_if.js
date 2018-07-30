$(function() {
	$('.ymd_date').datetimepicker({　
		minView: "month",
		format: "yyyy-mm-dd",
		language: 'zh-CN',
		autoclose: true,
		pickerPosition: "bottom-left",

	});
})
$('.detail_main_nav li a').click(function(event) {
	event.preventDefault();
	var index = $(this).parent().index();
	$(this).addClass('slide_nav_hover').parent().siblings().find('.slide_nav_hover').removeClass('slide_nav_hover');
	$('.detail_panels_main_body>div:eq(' + index + ')').addClass('active').siblings().removeClass('active');
});

/**点击查看更多*/
$('.show-more-info').click(function() {
	if($(this).siblings('.hide_box').hasClass('active')) {
		$(this).siblings('.hide_box').removeClass('active');
		$(this).html('查看更多');
	} else {
		$(this).siblings('.hide_box').addClass('active');
		$(this).html('收起');
	}
})
/*****点击折叠列表*****/
var lianxirenIcons = $('.accordion-row .accordion-tab');
for(var i = 0; i < lianxirenIcons.length; i++) {
	$(lianxirenIcons[i]).click(function() {
		if($(this).find('.li-opera-icons').find('i').hasClass('active')) {
			$(this).find('.li-opera-icons').find('i').removeClass('active');
			$(this).siblings().removeClass('active');
		} else {
			$('.accordion-row .accordion-tab .li-opera-icons i').removeClass('active');
			$(this).find('.li-opera-icons').find('i').addClass('active');
			$('.accordion-row .detail-modify-container').removeClass('active');
			$(this).siblings().addClass('active');
		}
	});
}