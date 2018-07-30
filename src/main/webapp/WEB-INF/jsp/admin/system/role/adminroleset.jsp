<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<%@ taglib uri="/WEB-INF/tlds/authorizetag.tld" prefix="px"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="content">
		<div id="wrapper" class="container">
		<!--标编辑-->
		<div id="biaobj" class=" wrapper wrapper-content animated fadeInRight">
			<h3>角色权限</h3>
			<div class="right_box" id="right_box">
				<!--表格部分-->
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<form action="">
								<input type="hidden" value="${roleId }" id="roleId" />
								<c:forEach items="${li }" var="li">
									<input type="hidden" value="${li.AuthorityID }"
										class="AuthorityID" />
								</c:forEach>
								<table	class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th width="100px" style="text-align: right;">模块</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${settings }" var="sett">
											<tr>
												<td class="text-right w-150px">${sett.key }<input
													type="checkbox" name="allchecker" value="${sett.key }"></td>
												<td  class="pv-10px" style="text-align: left">
													<c:forEach items="${sett.value }" var="val">
														<input type="checkbox" name="actions-1" value="${val }"
															data-role="${sett.key }" />
														<span class="priv">${val }</span>
													</c:forEach>
												</td>
											</tr>
										</c:forEach>
										<tr>
											<td class="text-right w-150px">
												全选<input type="checkbox" id="ckall"/>
											</td>
										</tr>
									</tbody>
									
								</table>
								<px:authorize setting="角色-角色权限">
									<button type="button"
										style="width: 82px; height: 34px; margin-left: 12px"
										onclick="addRoleAuthority();"
										class="col-lg-3 btn btn-warning">
											<i class="fa fa-save"></i>   保存
										</button> 
								</px:authorize>
								<button type="button" onclick="fh();" style="margin-left:10px" class="btn btn-success">
									<i class="fa fa-step-backward"></i>  
									返回</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</m:Content>
	<m:Content contentPlaceHolderId="js">
		<script>
		$(function() {
			var AuthorityArr = $(".AuthorityID");
			if (AuthorityArr.length > 0) {
				for (var i = 0; i < AuthorityArr.length; i++) {
					$("input[type=checkbox][name='actions-1'][value='"+$(AuthorityArr[i]).val()+"']").attr("checked", true);
				}
			}
			
			$("tbody > tr").each(function(){
				if($(this).find("td:eq(1)").find("input").length==$(this).find("td:eq(1)").find("input:checked").length){
					$(this).find("td:eq(0)").find("input").attr("checked", true);
				}
			});
			
			
			$("#ckall").on("click",function(){
				$("input[data-role],input[name='allchecker']").prop("checked",$(this).is(":checked"));
			});
			
			$("input[data-role]").on("click",function(){
				var role=$(this).data("role");
				
				$("input[value='"+role+"']").prop("checked",$("input[data-role='"+role+"']").length==$("input[data-role='"+role+"']:checked").length);
			});
			
			$("input[name='allchecker']").on("click",function(){
				var v=$(this).val();
				$("input[data-role='"+v+"']").prop("checked",$(this).is(":checked"));
			});
		});
		function fh() {
			window.location.href = "/system/role/list?identify=0";
		}
		function addRoleAuthority() {
			var spCodesTemp = "";
			var spCodesTemp2 = "";
			$('input:checkbox[name=actions-1]:checked').each(function(i) {
				spCodesTemp2 = $(this).data("role");
				if (0 == i) {
					spCodesTemp = spCodesTemp2 + "-" + $(this).val();
				} else {
					spCodesTemp += ("," + spCodesTemp2 + "-" + $(this).val());
				}
			});
			var roleId = $("#roleId").val();
			$.ajax({
				type : "post",
				url : "/system/role/saveRoleAuthority",
				data : {
					"roleId" : roleId,
					"spCodesTemp" : spCodesTemp
				},
				dataType : "json",
				success : function(data) {
					alertNew("保存成功",true);
				},
				error : function() {
					alertNew("系统异常，请联系管理员！");
				}
			});
		}
	</script>
	</m:Content>
</m:ContentPage>
