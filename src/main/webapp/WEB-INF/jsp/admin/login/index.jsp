<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="px"%>
<px:ContentPage materPageId="master">
<px:Content contentPlaceHolderId="css">
<link rel="stylesheet" href="/admin/css/plugins/toastr/toastr.min.css" />
<link title="" href="/admin/css/index.css" rel="stylesheet" type="text/css" />
<style>
.left-full .navContent li .sublist-icon{
	margin-left: 10px;
}
.left-off {
    width: 48px;
    display: block;
    overflow:inherit !important;
}
/* .right-product {
    z-index: 101;
} */
</style>
</px:Content>
	<px:Content contentPlaceHolderId="content">

	<div class="nav-style">
		<div class="">
			<div style="float: left;width:200px;overflow:hidden"><img alt="" src="/admin/img/logo2.png" width="150px" style="margin-left: 10px;"></div>
			<ul id="cz_ul" class="nav_right_box pull-right" style="margin-right:30px;">
				
				<li><a id="drop_menu"
					href="javascript:void(0)">${username }<span class="caret"></span></a>
					<div class="user-menu" style="position:absolute;right:10px;">
						<span class="jiao"></span>
						<ul>
							<li id="xiuGai_pwd" data-toggle="modal" data-target="#myModal1"><a
								href="javascript:void(0)"> 修改密码</a></li>
							<li><a href="/login/loginOut">退出</a></li>
						</ul>
					</div></li>
			</ul>
			<ul id="a1" class="nav_guid pull-right">
				<c:forEach items="${treenodes }" var="item">
					<li ${item.treeName==moduleName?"class='active'":'' }><a
						href="/index?moduleName=${item.treeName }">
							<span class="title_top"></span> ${item.treeName }
					</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="modal inmodal fade" id="myModal1" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="" class="col-lg-3 col-md-3 col-sm-3 control-label">
							输入原密码：</label>
						<div class="col-lg-9 col-md-9 col-sm-9">
							<input type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-lg-3 col-md-3 col-sm-3 control-label">
							输入新密码：</label>
						<div class="col-lg-9 col-md-9 col-sm-9">
							<input type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-lg-3 col-md-3 col-sm-3 control-label">
							确认新密码：</label>
						<div class="col-lg-9 col-md-9 col-sm-9">
							<input type="text" class="form-control" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">
						关闭</button>
					<button type="button" class="btn btn-primary">确认修改</button>
				</div>
			</div>
		</div>
	</div>
	<div class="down-main">
		<div class="left-main left-full" style="overflow-y:auto;">
			<div class="sidebar-fold">
				<span style="font-size: 15px;" class="fa fa-dedent"></span>
			</div>
			<div id="menu_box">
				<div id="grzm" class="subNavBox">
					<c:forEach items="${erList }" var="item">
						<div class="sBox">
							<div class="subNav sublist-down">
								<span class="title-icon fa fa-caret-right"></span><span
									class="sublist-title">${item.treeName }</span>
							</div>
							<ul class="navContent" style="display: none">
								<c:forEach items="${item.chList }" var="items">
										<li>
											<div class="showtitle" style="width:100px;">
												<img src="/admin/img/leftimg.png" />${items.treeName}
											</div>
											<a href="${items.url}"><span
												class="${items.icon }"></span><span
												class="sub-title">${items.treeName}</span> </a>
										</li>
								
								</c:forEach>
							</ul>
						</div>
					</c:forEach>

				</div>
			</div>
		</div>
		<div class="right-product my-index right-full">
			<iframe id="ifr" src="${url }" frameborder="no" scrolling="auto"
				width="100%" style="min-width: 1280px" height="100%" allowtransparency="true"></iframe>
		</div>
	</div>
	
   </px:Content>
   <px:Content contentPlaceHolderId="js">
   <script src="/admin/js/plugins/toastr/toastr.min.js"></script>
   <script	src="/admin/js/plugins/layer/layer.js"></script>
   <script src="/admin/js/jquery.nicescroll.js"></script>
   <script src="/admin/js/index.js"></script>
   <script>
   $(function(){
	 //滚动条美化
		$('.left-main').niceScroll({
	        cursorcolor: "#ccc",//#CC0071 光标颜色
	        cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
	        touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
	        cursorwidth: "5px", //像素光标的宽度
	        cursorborder: "0", // 	游标边框css定义
	        cursorborderradius: "5px",//以像素为光标边界半径
	        autohidemode: false //是否隐藏滚动条
	    });
	 
	   	$("body").css("overflow-y","hidden");
	 //修改密码 退出的显示问题
		$('#cz_ul>li:last-child').mouseover(function() {
			$(this).find('.user-menu').css('display', 'block');
		});
		$('#cz_ul>li:last-child').mouseleave(function() {
			$(this).find('.user-menu').css('display', 'none');
		});
		
		$('#xiuGai_pwd').click(function() {
			$(this).parent().parent().css('display', 'none');
		});
		//$(".down-main .left-main left-off").css('overflow','inherit');
		
   });
   function confirm(msg,ok,c){
	   layer.confirm(msg, {icon: 3, title:'提示'}, function(index){
		   if(ok!=null){
				ok();
			}
		   layer.close(index);
		 },function(index){
			 if(c!=null){
					c();
				}
			   layer.close(index);
		 });
	}
   function loading(t){
	   if(t){
			layer.load();
		}else{
			layer.closeAll('loading');
		}
   }
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
   function alertNew(msg,flag){
	   if(flag) {
			toastr.success(msg);
		} else {
			toastr.warning(msg);
		}
   }
	</script>
   </px:Content>
</px:ContentPage>