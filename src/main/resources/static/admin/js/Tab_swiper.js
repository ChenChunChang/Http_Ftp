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
/*轮播*/
$(function() {

	var $banner = $('.banner');

	var $banner_ul = $('.banner-img');

	var $btn = $('.banner-btn');

	var $btn_a = $btn.find('a')

	var v_width = $banner.width();

	var page = 1;

	var timer = null;

	var btnClass = null;

	var page_count = $banner_ul.find('li').length; //把这个值赋给小圆点的个数

	var banner_cir = "<li class='selected' href='#'><a></a></li>";

	for(var i = 1; i < page_count; i++) {

		//动态添加小圆点

		banner_cir += "<li><a href='#'></a></li>";

	}

	$('.banner-circle').append(banner_cir);

	var cirLeft = $('.banner-circle').width() * (-0.5);

	$('.banner-circle').css({
		'marginLeft': cirLeft
	});

	$banner_ul.width(page_count * v_width);

	function move(obj, classname) {

		//手动及自动播放

		if(!$banner_ul.is(':animated')) {

			if(classname == 'prevBtn') {

				if(page == 1) {

					$banner_ul.animate({
						left: -v_width * (page_count - 1)
					});

					page = page_count;

					cirMove();

				} else {

					$banner_ul.animate({
						left: '+=' + v_width
					}, "slow");

					page--;

					cirMove();

				}

			} else {

				if(page == page_count) {

					$banner_ul.animate({
						left: 0
					});

					page = 1;

					cirMove();

				} else {

					$banner_ul.animate({
						left: '-=' + v_width
					}, "slow");

					page++;

					cirMove();

				}

			}

		}

	}

	function cirMove() {

		//检测page的值，使当前的page与selected的小圆点一致

		$('.banner-circle li').eq(page - 1).addClass('selected')

		.siblings().removeClass('selected');

	}

	$banner.mouseover(function() {

		$btn.css({
			'display': 'block'
		});

		clearInterval(timer);

	}).mouseout(function() {

		$btn.css({
			'display': 'none'
		});

		clearInterval(timer);

		timer = setInterval(move, 3000);

	}).trigger("mouseout"); //激活自动播放

	$btn_a.mouseover(function() {

		//实现透明渐变，阻止冒泡

		$(this).animate({
			opacity: 0.6
		}, 'fast');

		$btn.css({
			'display': 'block'
		});

		return false;

	}).mouseleave(function() {

		$(this).animate({
			opacity: 0.3
		}, 'fast');

		$btn.css({
			'display': 'none'
		});

		return false;

	}).click(function() {

		//手动点击清除计时器

		btnClass = this.className;

		clearInterval(timer);

		timer = setInterval(move, 3000);

		move($(this), this.className);

	});

	$('.banner-circle li').on('click', function() {

		var index = $('.banner-circle li').index(this);

		$banner_ul.animate({
			left: -v_width * index
		}, 'slow');

		page = index + 1;

		cirMove();

	});

});
/*****选择人员*********/
function add_user() {
	$.dialog({
		title: '添加人员',
		width: 520,
		height: 420,
		content: 'url:add_user_tree.html',
		init: function() {
			//var iframe = this.iframe.contentWindow;
			//var ul = iframe.document.getElementById('right_list');
		},
		button: [{
			name: '保存',
			callback: function() {
				var iframe = this.iframe.contentWindow;
				if(!iframe.document.body) {
					alert('iframe还没加载完毕呢')
					return false;
				};
			},
			focus: true
			}, {
			name: '取消',
			callback: function() {
			}
		}]
	})
}