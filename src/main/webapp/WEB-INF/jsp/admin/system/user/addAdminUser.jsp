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
						<form class="form-horizontal" method="post" id="saveAdminUser"
							novalidate="novalidate">
							<div class="form-group">
								<label class="control-label">用户名:</label>
								<div class="">
									<input type="text" class="form-control required" id="userName"
										name="userName">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">真实姓名:</label>
								<div class="">
									<input type="text" class="form-control required" id="realname"
										name="realname">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">密码:</label>
								<div class="">
									<input type="password" class="form-control required" id="userPwd"
										name="userPwd">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">所属部门:</label>
								<div class="">
									<div class="input-group" style="width:100%;">
											<input type="hidden" name="departmentId" id="departmentId" value="">
											<input type="text" name="departmentIdShow" onclick="showMenu('department')" disabled='disabled' class="form-control" id="departmentIdShow"/>
											<span class="input-group-btn"> 
												<input type="button" value="选择" onclick="showMenu('department')" class="btn btn-primary"/>
											</span>
										</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">上级主管:</label>
								<div class="">
									<div class="input-group" style="width:100%;">
											<input type="hidden" name="leaderId" id="leaderId" value="">
											<input type="text" name="leaderIdShow" onclick="showMenu('leader')" disabled='disabled' class="form-control" id="leaderIdShow"/>
											<span class="input-group-btn"> 
												<input type="button" value="选择" onclick="showMenu('leader')" class="btn btn-primary"/>
											</span>
										</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">邮箱:</label>
								<div class="">
									<input type="text" class="form-control" id="email" name="email"
										value="">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">电话:</label>
								<div class="">
									<input type="text" class="form-control required" id="telenumber"
										name="telenumber" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">身份证号:</label>
								<div class="">
									<input type="text" class="form-control" id="identitynumber"
										name="identitynumber" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">出生日期:</label>
								<div class="">
									<input type="text" placeholder="出生日期" class="form-control date"  
											id="birthDate" name="birthDate" value=""/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">家庭地址:</label>
								<div class="">
									<input type="text" class="form-control" id="address"
										name="address" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">所属角色:</label>
								<div class="">
									<c:forEach items="${role }" var="item">
										<input type="checkbox" style="width: 60px" id="roleId"
											value="${item.roleid }" name="roleId" />
										<span>${item.roleName }</span>
									</c:forEach>
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
	<div id="menuContent" data-target="department" class="menuContent" style="display:none; position: absolute;z-index:999">
		<ul id="depTree" class="ztree" style="margin-top:0; min-width:200px; height: 300px;"></ul>
	</div>
	<div id="menuContent1" data-target="leader" class="menuContent" style="display:none; position: absolute;z-index:999">
		<ul id="userTree" class="ztree" style="margin-top:0; min-width:200px; height: 300px;"></ul>
	</div>
	</pxkj:Content>
 <pxkj:Content contentPlaceHolderId="js">
 <script src="/admin/js/plugins/validate/jquery.validate.min.js"></script>
 <script src="/admin/js/plugins/jqform/jquery.form.min.js"></script>
	<script src="/admin/js/plugins/iCheck/icheck.min.js"></script>
	<script src="/admin/js/plugins/validate/localization/messages_zh.min.js"></script>
		<script type="text/javascript" src="/admin/js/plugins/ztree/jquery.ztree.all.min.js"></script>
	<script>
$(function(){
		$(".date").datetimepicker({
            language: 'zh-CN',
            startView: 2,
            autoclose: 1,
            minView: 2,
            maxView: 4,
            format: "yyyy-mm-dd"
        }); 
		
	});
		function check() {
			if(!$("#saveAdminUser").validate().form()){
				return false;
			}
			var telenumber = $("#telenumber").val();
			var roleIdlength = $("input[name='roleId']:checked").length;
			
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if (!myreg.test(telenumber)) {
				alertNew('请输入有效的手机号码！', false);
				return false;
			}
			if (roleIdlength == 0) {
				alertNew('请选择角色！', false);
				return false;
			}
			return true;
		}
		function saveCustomer() {
			if (check()) {
				$.ajax({
					type : "post",
					data : $("#saveAdminUser").serialize(),
					dataType : "json",
					url : "/system/user/addAdmin",
					success : function(data) {
						if (data.success) {
							alertNew(data.msg, true);
							$('#ifr', window.parent.document).attr('src',
									'/system/user/adminlist');
						}
					},
					error : function(data) {
						alert("添加失败");
					}
				});
			}
		}
		$(function(){;
			initDepTree();
			initUserTree();
		});
		var currentShowMenu='';
		function initUserTree(){
			var setting = {
					check: {
						enable: false
					},
					view: {
						dblClickExpand: false
					},
					data:{
						 simpleData: {
					         enable: true,
					         idKey: "id",
					         pIdKey: "pId",
					         rootPId: "0"
					     }
					},
					callback: {
						onDblClick: onDblClick
					}
				};
				
				
				function onDblClick(event, treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("userTree");
					var nodes = zTree.getSelectedNodes();
					if(nodes.length>0 && Number(nodes[0].id)) {
						$("#leaderId").val(nodes[0].id);
						$("#leaderIdShow").val(nodes[0].name);
						hideMenu();
					}
					return false;
				}
				
				var zNodes=${deptuserlist};
				
				$.fn.zTree.init($("#userTree"), setting,zNodes);
				var treeObj = $.fn.zTree.getZTreeObj("userTree"); 
				treeObj.expandAll(true); 
		}
		
		function initDepTree(){
			var setting = {
					check: {
						enable: false
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
						$("#departmentId").val(nodes[0].departmentId);
						$("#departmentIdShow").val(nodes[0].departmentName);
						hideMenu();
					}
					return false;
				}

				$.fn.zTree.init($("#depTree"), setting, zNodes);
				var treeObj = $.fn.zTree.getZTreeObj("depTree"); 
				treeObj.expandAll(true); 
		}
		
		
		function showMenu(t) {
			currentShowMenu=t;
			
			var obj = $("#"+t+"IdShow");
			var objOffset = obj.offset();
			$("div[data-target='"+t+"']:first").css({left:objOffset.left + "px", top:objOffset.top + obj.outerHeight() + "px"}).slideDown("fast");
	
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("div[data-target='"+currentShowMenu+"']:first").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			var id = $("div[data-target='"+currentShowMenu+"']:first").attr("id");
			if (!(event.target.id == (currentShowMenu+"IdShow") || event.target.id == id || $(event.target).parents("#"+id).length>0)) {
				hideMenu();
			}
		}
	</script>
</pxkj:Content>
</pxkj:ContentPage>