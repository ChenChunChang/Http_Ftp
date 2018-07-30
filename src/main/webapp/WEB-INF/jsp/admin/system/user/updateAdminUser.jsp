<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<link href="/admin/public/bootstrap.min.css" title="" rel="stylesheet" />
<link href="/admin/public/inspinia.css" rel="stylesheet" />
<link href="/admin/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="/admin/public/animate.css" rel="stylesheet" />
<link
	href="/admin/css/plugins/datetimepicker/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen" />
<link href="/admin/public/zTree/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<link href="/admin/js/multipleselect/multiple-select.css"
	rel="stylesheet" />
<link rel="stylesheet" href="/admin/css/Tab_swiper.css" />
<link rel="stylesheet" href="/admin/css/new_page_if.css" />
<link rel="stylesheet" type="text/css"
	href="${request.getContext }/admin/css/xcConfirm.css" />
<script src="/admin/My97/WdatePicker.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="/admin/public/html5shiv.min.js"></script>
	<script src="/admin/public/respond.min.js"></script>
<![endif]-->
<style>
th.prev {
	background: url(img/dateleft.png) -3px !important;
}

th.next {
	background: url(img/dateright.png) -10px !important;
}
</style>
</head>
<body>
	<div
		style="width: 20px; height: 100%; position: relative; left: 16px; box-shadow: -5px 0 20px #8e9092;">
	</div>
	<div class="container-frame-detail" id="containerDetail"
		style="display: block; right: 0px; width: 780px; position: relative; bottom: 100%; left: 16px; height: 100%;">
		<div class="detail-inner" id="detailInner">
			<div class="slid-panel-header clearfix">
				<span class="panel-title">后台用户</span> <span class="form-close-icon">X</span>
			</div>
			<div class="detail_left">
				<div class="detail_left_inner">

					<!-- <div class="cus_simple_info">
                        <div class="tagSelector-warp">
                            <div class="tag-item-list">
                                <span class="tag-inputContainer"><span class="span-tag">+标签</span> </span>
                                <div class="tag-pullDown-container-down">
                                </div>
                            </div>
                        </div>
                    </div>-->
					<div class="customer_tabs fn-clear"
						style="margin-right: 30px; margin-left: 10px;">
						<ul class="detail_main_nav">
							<li><a class="slide-panel-tab slide_nav_hover"
								href="javascript:void(0)">后台用户</a></li>
						</ul>
						<div class="detail_panels_main_body">
							<div id="renWu_msg" class="active">
								<form id="editAdminUser" method="post" class="form-horizontal">
									<input type="hidden" name="userId" id="userId"
										value="${list.userId }">
										<div style="overflow: hidden;">
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													用户名：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control" id="userName"
														name="userName" value="${list.userName }">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">真实姓名：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control" data-val="true"
														id="realname" name="realname" value="${list.realname }">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													密码：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control"
														placeholder="输入要修改的密码" id="userPwd" name="userPwd"
														value="">
												</div>
											</div>
											
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">所属部门：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<select class="select2_demo_1 form-control required" name="departmentId" id="departmentId">
														<option value="">--请选择--</option>
														<c:forEach items="${deptlist }" var="li">
															<option value="${li.departmentId }" ${list.departmentId==li.departmentId?'selected':'' }>${li.departmentName }</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													邮箱：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control" id="email"
														name="email" value="${list.email }">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													电话：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control" id="telenumber"
														name="telenumber" value="${list.telenumber }">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													身份证号：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control" id="identitynumber"
														name="identitynumber" value="${list.identitynumber }">
												</div>
											</div>
											<div class="form-group">
												<label for=""
													class="col-lg-2 col-md-2 col-sm-3 control-label">
													出生日期：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<div class="date input-append form_date ymd_date"
														id="addtimeCalendar"
														style="float: left; margin-right: 5px;">
														<div class="input-group">
															<input class="form-control input-sm" type="text"
																value="<fmt:formatDate type="time" value="${list.birthDate }" pattern="yyyy-MM-dd" />"
																name="birthDate" id="birthDate"> <span
																class="add-on input-group-addon"><i
																	class="fa fa-calendar" style="color: black;"> </i></span>
														</div>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													家庭地址：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<input type="text" class="form-control" id="address"
														name="address" value="${list.address }">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 col-md-2 col-sm-3 control-label">
													所属角色：</label>
												<div class="col-md-6 col-lg-6 col-sm-6">
													<c:forEach items="${role }" var="item" varStatus="vs">
														<input type="checkbox" style="width: 60px"
															value="${item.roleid }" name="roleId" />
														<span>${item.roleName }</span>
													</c:forEach>
												</div>
												<input type="hidden" value="${list.roleId }" id="roleHidden" />
											</div>
										</div>
							</div>
							<a class="show-more-info">查看更多</a>
							<div class="hr-line-dashed"></div>
							<input type="button" onclick="editReceipt();"
								style="width: 82px; height: 34px; margin-left: 12px" value="保存"
								class="col-lg-3 btn btn-success" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script src="/admin/public/jquery-1.11.3.js"></script>
	<script src="/admin/js/plugins/lhgdialog/lhgdialog.js"></script>
	<!-- 时间控件样式 -->
	<script
		src="/admin/js/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script
		src="/admin/js/plugins/datetimepicker/bootstrap-datetimepicker-update.min.js"></script>
	<script
		src="/admin/js/plugins/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
	<!-- 弹出框 -->
	<script src="/admin/js/multipleselect/multiple-select.js"></script>
	<script src="${request.getContext }/admin/js/xcConfirm.js"
		type="text/javascript" charset="utf-8"></script>
	<!--    <script src="/admin/js/Tab_swiper.js"></script>-->
	<script src="/admin/js/new_page_if.js"></script>
	<script>
		$(function() {
			window.onload = function() {
				var checkeds = $("#roleHidden").val();
				if (checkeds.length > 0) {
					var roleArr = checkeds.split(',');
					$.each(roleArr, function(index, roleid) {
						$("input[type=checkbox][name='roleId']").each(
								function() {
									if ($(this).val() == roleid) {
										$(this).attr("checked", true);
									}
								});
					});
				}
			};
		});

		function check() {
			var userName = $("#userName").val();
			var realname = $("#realname").val();
			var userPwd = $("#userPwd").val();
			var departmentId= $('#departmentId option:selected') .val();
			//var email=$("#email").val();
			var telenumber = $("#telenumber").val();
			//var identitynumber=$("#identitynumber").val();
			var roleIdlength = $("input[name='roleId']:checked").length;
			if (userName.length == 0) {
				alert('请输入用户名！');
				return false;
			}
			if (realname.length == 0) {
				alert('请输入真实姓名！');
				return false;
			}
			if (departmentId == "") {
				alertNew('请选择所属部门！', false);
				return false;
			}
			if (telenumber.length != 11) {
				alert('请输入有效的手机号码位数！');
				return false;
			}
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if (!myreg.test(telenumber)) {
				alert('请输入有效的手机号码！');
				return false;
			}
			if (roleIdlength == 0) {
				alert('请选择角色！');
				return false;
			}
			return true;
		}
		function editReceipt() {
			if (check()) {
				$.ajax({
							type : "post",
							data : $("#editAdminUser").serialize(),
							dataType : "json",
							url : "/system/user/editAdminUser",
							success : function(data) {
								window.wxc
										.xcConfirm(
												data.msg,
												window.wxc.xcConfirm.typeEnum.info,
												{
													onOk : function() {
														if (data.success) {
															window.parent.location.href = "/system/user/adminlist";
														}
													}
												})
							},
							error : function(data) {
								alert("修改失败");
							}
						});
			}
		}
	</script>
</body>
</html>
