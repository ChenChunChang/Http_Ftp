/*配置警示框和弹出确认框*/
toastr.options = {
	"closeButton": true, //是否显示关闭按钮
	"debug": false, //是否使用debug模式
	"positionClass": "toast-top-center", //弹出窗的位置
	"showDuration": "300", //显示的动画时间
	"hideDuration": "100", //消失的动画时间
	"timeOut": "2000", //展现时间
	"showEasing": "swing", //显示时的动画缓冲方式
	"hideEasing": "linear", //消失时的动画缓冲方式
	"showMethod": "fadeIn", //显示时的动画方式
	"hideMethod": "fadeOut", //消失时的动画方式
	"progressBar": true
};

function alertinfo(msg, issuccess) {
	if(issuccess) {
		toastr.success(msg);
	} else {
		toastr.warning(msg);
	}
}

function alertinfo(msg, issuccess, action) {
	alertNew(msg, issuccess);
	if(action != null) {
		setTimeout(action, 1000);
	}
}

function confirminfo(title, msg, action) {
	return art.dialog({
		id: 'Confirm',
		icon: 'question',
		fixed: true,
		lock: true,
		opacity: 0.5,
		content: title,
		ok: function(here) {
			action();
		},
		cancel: function(here) {
			art.dialog.close();
		}
	});
}
/*警示框效果*/
function alert_t() {
	alertinfo("你点击了搜索按钮！", false);
}

/*点击上边表格显示下面表格*/
$('#top_table tbody tr').click(function() {
	//var index=$(this).index();
	var inner = $(this).find('td:eq(1)').html();
	$(this).addClass('tr_active').siblings().removeClass('tr_active');
	$(this).parents('.ibox-content').siblings('.table_bot').css('display', 'block');
	$(this).parents('.ibox-content').siblings('.table_bot').find('tr').find('td:eq(1)').html(inner);
})
