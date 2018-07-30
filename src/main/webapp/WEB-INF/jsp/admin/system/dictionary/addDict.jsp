<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tlds/authorizetag.tld" prefix="px"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="css">
		<!-- 选择框 -->
		<link href="/admin/css/plugins/iCheck/custom.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css"
			href="/admin/js/plugins/jquery-ui/jquery-ui.css" />
		<link rel="stylesheet" type="text/css"
			href="/admin/css/plugins/tagsinput/bootstrap-tagsinput.css" />
<style>
/*弹窗样式*/
.control-label{
	line-height:34px;
	float:left;
	text-align:right;
	width:20%;
}
.col-lg-6{
	width:75%;
	float:left;
}
#tb_info .form-control{
	border:none;
}
</style>
	</m:Content>
	<m:Content contentPlaceHolderId="content">
		<div id="wrapper" class="container">
			<div class="wrapper wrapper-content">
				<div class="right_box">
					<div style="border: none; box-shadow: none;" class="right_one row">
						<div class="col-md-12 col-sm-12 col-xs-12" style="float: left;">
							<form action="" class="form-horizontal"
								method="post" id="saveClient">
								<div class="form-group">
									<label class="col-lg-2 col-md-2 col-sm-3 control-label"><span style="color:red;">*</span>项目名称:</label>
									<div class="col-md-6 col-lg-6 col-sm-6">
										<input type="text" name="dictionaryName" class="form-control required"
											id="dictionaryName"  />
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-2 control-label">描述:</label>
									<div class="col-lg-6">
										<textarea name="dictionaryDescription" class="form-control"
											></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-2 control-label">类型:</label>
									<div class="col-lg-6">
										<select name="dictionaryType" class="form-control">
											<option value="">无</option>
											<c:forEach items="${typeList}" var="item">
												<option value="${item.dictionaryType}">${item.dictionaryType}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<input type="hidden" id="checkTF" value="0">
								<input type="button" onclick="closeModel()" style="display: none;" id="closeModelBtn">
								<px:authorize setting="字典-添加字典项">
									<input type="button" onclick="check()" style="display: none;"
										id="checkBtn" />
								</px:authorize>
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
			//验证
			function check() {
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