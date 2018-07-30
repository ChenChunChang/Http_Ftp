<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<%@ taglib uri="/WEB-INF/tlds/authorizetag.tld" prefix="px"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="css">
		<link href="/admin/css/plugins/iCheck/custom.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css"
			href="/admin/js/plugins/jquery-ui/jquery-ui.css" />
		<link rel="stylesheet" type="text/css"
			href="/admin/css/plugins/tagsinput/bootstrap-tagsinput.css" />
	</m:Content>
	<m:Content contentPlaceHolderId="content">
		<div id="wrapper" class="container">
		<div class="wrapper wrapper-content">
			<div class="right_box">
				<div style="border: none; box-shadow: none;" class="right_one row">
					<div class="col-md-12 col-sm-12 col-xs-12" style="float: left;">
						<form class="form-horizontal" method="post" id="saveClient" >
							<c:if test="${identify=='1' }">
								<input type="hidden" name="identify" id="identify" value="1" />
							</c:if>
							<c:if test="${identify=='0' }">
								<input type="hidden" name="identify" id="identify" value="0" />
							</c:if>
							<input type="hidden" name="roleid" id="roleid" value="${roleid }" />
							<div class="form-group">
								<label class="col-lg-2 control-label"><span style="color:red;">*</span>分组名称:</label>
								<div class="col-lg-6">
									<input type="text" class="form-control required" id="roleName"
										name="roleName" value="${roleName}">
								</div>
							</div>
							<c:if test="${parentId ne 0}">
								<div class="form-group">
									<label class="col-lg-2 control-label"><span style="color:red;">*</span>上级角色:</label>
									<div class="col-lg-6">
										<select id="parentId" name="parentId" class="form-control">
											<c:forEach items="${roleList }" var="item">
												<c:if test="${item.roleid ne 1}">
													<option value="${item.roleid }" <c:if test="${item.roleid eq parentId}"> selected="selected"</c:if>>${item.roleName }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
							<%-- <div class="form-group">
								<label class="col-lg-2 control-label"><span style="color: red;"></span>是否是固定角色配置:</label>
								<div class="col-lg-6">
									<div class="i-checks">
										<label> 
											<input type="radio"  name="isFixation" <c:if test="${isFixation eq 0}">checked="checked"</c:if> value="0" />是
										</label>
										<label> 
											<input type="radio"  name="isFixation" <c:if test="${empty(isFixation)}">checked="checked"</c:if><c:if test="${isFixation eq 1}">checked="checked"</c:if> value="1" />否
		                            	</label>
									</div>
								</div>
							</div> --%>
							<div class="form-group">
								<label class="col-lg-2 control-label"></label>
								<div class="col-lg-6">
									<input type="hidden" id="checkTF" value="0">
									<input type="button" onclick="closeModel()" style="display: none;" id="closeModelBtn">
									<input type="button" onclick="check();" id="checkBtn"
									style="width: 82px; height: 34px; display: none; margin-left: 12px"
									value="保存" class="col-lg-3 btn btn-success" />
								</div>
							</div>
							<div></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</m:Content>
	<m:Content contentPlaceHolderId="js">
		<script src="/admin/js/plugins/jqform/jquery.form.min.js"></script>
		<script src="/admin/js/plugins/validate/jquery.validate.min.js"></script>
		<script
			src="/admin/js/plugins/validate/localization/messages_zh.min.js"></script>
		<script src="/admin/js/plugins/iCheck/icheck.min.js"></script>
		<!-- 滚动条 -->
		<script src="/admin/js/jquery.nicescroll.js"></script>
	<script>
		//选择框样式
		$('.i-checks').iCheck({
			checkboxClass : 'icheckbox_square-green',
			radioClass : 'iradio_square-green',
		});
		//滚动条美化
		$('body').niceScroll({
			cursorcolor : "#ccc",//#CC0071 光标颜色
			cursoropacitymax : 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
			touchbehavior : false, //使光标拖动滚动像在台式电脑触摸设备
			cursorwidth : "5px", //像素光标的宽度
			cursorborder : "0", // 	游标边框css定义
			cursorborderradius : "5px",//以像素为光标边界半径
			autohidemode : false
		//是否隐藏滚动条
		});
		
		
		function check(){
			if (!$("#saveClient").validate().form()) {
				return;
			}
			$("#checkTF").val("1");
			return true;
		}
		//关闭弹窗
		function closeModel(){
			frameElement.api.close();
		}
	</script>
	</m:Content>
</m:ContentPage>

