<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="pxkj"%>
<pxkj:ContentPage materPageId="master">
<pxkj:Content contentPlaceHolderId="css">
<link rel="stylesheet" href="/admin/css/zTreeStyle/metro.css" type="text/css"/>
<style>
	.form-group>label{
		width:25%;
		float:left;
		padding-left:5%;
		line-height:34px;
		text-align:left;
	}
	.form-group>div{
		width:70%;
		float:left;
	}
	ul.ztree{
    margin-top: 10px;
    border: 1px solid #ddd;
    background: #F4F7FD;
    width: 100%;
    height: 360px;
    overflow-y: scroll;
    overflow-x: auto;
    }
</style>
</pxkj:Content>
<pxkj:Content contentPlaceHolderId="content">
	<div id="wrapper" class="container">
		<div class="wrapper wrapper-content">
			<div class="right_box">
				<div style="border: none; box-shadow: none;" class="right_one row">
					<div class="col-md-12 col-sm-12 col-xs-12" style="float: left;">
						<form class="form-horizontal" method="post" id="editDepartment"
							novalidate="novalidate">
							<div class="form-group">
								<label class="col-lg-2 control-label">所属部门<span
										style="color: red;">*</span>:</label>
								<div class="">
									<div class="input-group" style="width:100%;">
											<input type="hidden" name="parentId" id="parentId" value="">
											<input type="text" name="parentIdShow" value="${department.pdepartmentName }" onclick="showMenu()" disabled='disabled' class="form-control" id="parentIdShow"/>
											<span class="input-group-btn"> 
												<input type="button" value="选择" onclick="showMenu()" class="btn btn-primary"/>
											</span>
										</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">部门名称<span
										style="color: red;">*</span>:</label>
								<div class="col-lg-6">
								<input name="departmentId" id="departmentId" type="hidden"  class="form-control" value="${department.departmentId }" style="width:200px;"/>
									<input type="text" class="form-control required" id="departmentName" name="departmentName"
										value="${department.departmentName }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">部门状态<span
										style="color: red;">*</span>:</label>
								<div class="col-lg-6">
									<select class="select2_demo_1 form-control required" name="dstatus" id="dstatus">
										<option value="">--请选择--</option>
										<option value="0" ${department.dstatus==0?'selected':'' }>不开启</option>
										<option value="1" ${department.dstatus==1?'selected':'' }>开启</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">备注:</label>
								<div class="col-lg-6">
									<textarea class="form-control" name="dremark" id="dremark" rows="3">${department.dremark }</textarea>
								</div>
							</div>
							<input type="button" onclick="saveCustomer();" id="btnn"
								style="width: 82px; height: 34px; display: none; margin-left: 12px"
								value="保存" class="col-lg-3 btn btn-success" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="depTree" class="ztree" style="margin-top:0; min-width:200px; height: 300px;"></ul>
	</div>
	</pxkj:Content>
 <pxkj:Content contentPlaceHolderId="js">
	<script src="/admin/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="/admin/js/plugins/jqform/jquery.form.min.js"></script>
	<script src="/admin/js/plugins/iCheck/icheck.min.js"></script>
	<script src="/admin/js/plugins/validate/localization/messages_zh.min.js"></script>
	<script type="text/javascript" src="/admin/js/plugins/ztree/jquery.ztree.all.min.js"></script>
	<script>
	$(function() {
		var departmentId=$("#departmentId").val();
		if(departmentId==1){
			$("#parentIdShow").val("总公司");
			　$("#parentIdShow").attr("disabled", true);
		}
	});
		function check() {
			if(!$("#editDepartment").validate().form()){
				return false;
			}
			return true;
		}
		function saveCustomer() {
			if (check()) {
				$.ajax({
					type : "post",
					data : $("#editDepartment").serialize(),
					dataType : "json",
					url : "/system/department/upDepartment",
					success : function(data) {
						if (data.success) {
							alertNew(data.msg, true);
							$('#ifr', window.parent.document).attr('src',
									'/system/department/allDept');
						}
					},
					error : function(data) {
						alert("添加失败");
					}
				});
			}
		}
		$(function(){;
		var setting = {
				check: {
					enable: false,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData:{
						idKey:"departmentId",
						pIdKey:"parentId",
						enable:true,
						rootPId:0
					},key:{
						name:"departmentName"
					}
				},
				callback: {
					onDblClick: onDblClick
				}
				
			};

			var zNodes =${deptlist};

			function onDblClick(event, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("depTree");
				var nodes = zTree.getSelectedNodes();
				if(nodes.length>0){
					$("#parentId").val(nodes[0].departmentId);
					$("#parentIdShow").val(nodes[0].departmentName);
					hideMenu();
				}
				return false;
			}

			$.fn.zTree.init($("#depTree"), setting, zNodes);
			var treeObj = $.fn.zTree.getZTreeObj("depTree"); 
			treeObj.expandAll(true); 
		
	});
	function showMenu() {
		var cityObj = $("#parentIdShow");
		var cityOffset = $("#parentIdShow").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	</script>
</pxkj:Content>
</pxkj:ContentPage>