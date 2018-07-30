<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<%@ taglib uri="/WEB-INF/tlds/authorizetag.tld" prefix="px"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="css">
	<link rel="stylesheet" href="/admin/css/templet_form.css" />
		<style>
th.prev {
	background: url(../img/dateleft.png) -3px !important;
}

th.next {
	background: url(../img/dateright.png) -10px !important;
}
th,td{
	text-align: center;
}
.dataTables tr {
	cursor: default !important;
}
</style>
	</m:Content>
	
	<m:Content contentPlaceHolderId="content">
		<div id="wrapper" class="container">
		<!--标编辑-->
		<div id="biaobj" class=" wrapper wrapper-content animated fadeInRight">
			<h3 style="float:left;display:inline-block;">后台用户管理</h3>
			<button id="gaoJi_se" data-target="gaoJiSouSuo"
				style="margin-left: 10px; float: right;margin-top:-8px;" type="button"
				class="btn btn-warning" onclick="addRole(${identify});">
				<i class="fa fa-plus" style="margin-left: 5px;"></i>	添加
			</button>
			<div class="right_box" id="right_box"  style="height:100%;margin-top:36px">
			
			<input type="hidden" name="identify" id="identify" value="${identify }" />
			</div>
			<!--表格部分-->
			<div class="ibox-content">
				<div class="row">
					<div class="col-sm-12">
						<table
							class="table table-striped table-bordered table-hover dataTables-example dataTables">
							<thead>
								<tr>
									<th style="width:10%;">序号</th>
									<th style="width:25%;">角色名称</th>
									<th style="width:25%;">所属角色</th>
									<th style="width:10%;">是否是固定角色配置</th>
									<th style="width:25%;">操作</th>
								</tr>
							</thead>
							<%-- <tbody>
								<c:forEach items="${list }" var="role">
									<tr>
										<td>${role.roleid }</td>
										<td>${role.roleName }</td>
										<td>
											<c:if test="${role.isFixation eq 0}">是</c:if>
											<c:if test="${role.isFixation eq 1}">否</c:if>
										</td>
										<td>
											
												<a 
												class="btn btn-success btn-xs"><i class="fa fa-edit"></i>角色权限</a>
											
											
											
									</tr>
								</c:forEach>
							</tbody> --%>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	</m:Content>
	<m:Content contentPlaceHolderId="js">
	<script>
	$.fn.dataTable.ext.errMode = 'none'; //不显示任何错误信息
	$(function(){
		var identify = $("#identify").val();
		datatable = $(".dataTables").DataTable({
            "processing": true,
            "serverSide": true,
            "filter": false,
            "dom": '<"toolbar">frtip',
            "ordering":false,
            "iDisplayLength":5,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "rowCallback":function(row, data, index){
            	$('td:eq(0)', row).attr("data-id",data.roleid);
            },
            "columnDefs": [
            	{
                    "targets": 0,
                    "data": null,
                    "render": function (data, type, row,meta) {
                    	var page= datatable.page();
                    	
                        return page*5 + meta.row+1;
                    }
                },
                {
                    "targets": 2,
                    "data": null,
                    "render": function (data, type, row) {
                    	if(row.parentId == 0){
                    		return "无";
                    	}else{
                    		return row.parentName;
                    	}
                    }
                },
                {
                    "targets": 3,
                    "data": null,
                    "render": function (data, type, row) {
                    	if(row.isFixation == 0){
                    		return "是";
                    	}
                    	if(row.isFixation == 1){
                    		return "否";
                    	}
                    }
                },
                {
                    "targets": 4,
                    "data": null,
                    "render": function (data, type, row) {
                    	var html = '';
                    	html += '<px:authorize setting="角色-角色权限"><a href="/system/role/roleInfo?_op=limits&roleId='+row.roleid+'">角色权限</a>&nbsp;&nbsp;&nbsp;</px:authorize>';
                    	html += '<px:authorize setting="角色-修改角色"><a onclick="editRole('+row.roleid+');">修改</a>&nbsp;&nbsp;&nbsp;</px:authorize>';
                    	if(row.isFixation == 1){
                    		html += '<px:authorize setting="角色-删除角色"><a onclick="del(\''+row.roleid+'\',\''+identify+'\',\''+row.isFixation+'\')";>删除</a></px:authorize>';
                    	}
                        return html;
                    }
                }
            ],
            "columns": [
                { "data": null, "orderable": false },
                { "data": "roleName",defaultContent:"" },
                { "data": "parentId",defaultContent:"" },
                { "data": "isFixation",defaultContent:"" },
            ],
            
            "ajax": {
                "url": "/system/role/list/dataTable",
                "method": "post",
                "data": function (obj) {
                	obj.identify= $("#identify").val();
                }
            }
            
        });
		
		
		
		
	});
	
	function editRole(pn) {
		var a = 0;//为了限制多次提交
		$.dialog({
			title: '修改角色',
			width: 600,
			height: 380,
			lock : true,
			content: 'url:/system/role/roleInfo?_op=update&roleId='+pn,
			init: function() {
				var iframe = this.iframe.contentWindow;
			},
			button: [{
				name: '保存',
				callback: function() {
					//iframe
				 	var iframe = this.iframe.contentWindow;
					//获取子页面的form表单
					var saveClient = $(iframe.document.body).find('#saveClient');
					 //为了限制多次提交
					if(a==0){
						//获取点击事件
						var btn = iframe.document.getElementById('checkBtn').click();
					}
					 if (btn == true) {
						
					} else {
						  if(iframe.document.getElementById('checkTF').value==1){
							 //为了限制多次提交
							 a = 1;
							 iframe.document.getElementById('checkTF').value = 0;
							 //ajax提交(文件请求需要换)
							 $.ajax({
						        type:"post",
						        data:saveClient.serialize(),
						        dataType:"json",
						        url:"/system/role/roleUpdate",
						        success:function(data){
						        	if(data.success){
						        		parent.alertNew(data.msg,true)
						        		setTimeout(function(){
						        			datatable.draw();
						        			iframe.document.getElementById('closeModelBtn').click();
								  		},2000)
						        	}else{
						        		parent.alertNew(data.msg,false)
						        		setTimeout(function(){
						        			datatable.draw();
						        			iframe.document.getElementById('closeModelBtn').click();
								  		},2000)
						        	}
						        	
						        },
						        error:function(data){
						        	parent.alertNew(data.msg,false)
						        	setTimeout(function(){
						        		datatable.draw();
						        		iframe.document.getElementById('closeModelBtn').click();
							  		},2000)
						        }
						    });
						}  
						return false;
					} 
				},
				focus: true

			},{
				name: '取消',
				callback: function() {

				}
			}]
		});
	}
	
	
	//添加
	function addRole(pn) {
		var a = 0;//为了限制多次提交
		$.dialog({
			title: '添加角色',
			width: 600,
			height: 380,
			lock : true,
			content: 'url:/system/role/roleInfo?_op=add&identify='+pn,
			init: function() {
				var iframe = this.iframe.contentWindow;
			},
			button: [{
				name: '保存',
				callback: function() {
					//iframe
				 	var iframe = this.iframe.contentWindow;
					//获取子页面的form表单
					var saveClient = $(iframe.document.body).find('#saveClient');
					 //为了限制多次提交
					if(a==0){
						//获取点击事件
						var btn = iframe.document.getElementById('checkBtn').click();
					}
					 if (btn == true) {
						
					} else {
						  if(iframe.document.getElementById('checkTF').value==1){
							 //为了限制多次提交
							 a = 1;
							 iframe.document.getElementById('checkTF').value = 0;
							 //ajax提交(文件请求需要换)
							 $.ajax({
						        type:"post",
						        data:saveClient.serialize(),
						        dataType:"json",
						        url:"/system/role/roleAdd",
						        success:function(data){
						        	if(data.success){
						        		parent.alertNew(data.msg,true)
						        		setTimeout(function(){
						        			datatable.draw();
						        			iframe.document.getElementById('closeModelBtn').click();
								  		},2000)
						        	}else{
						        		parent.alertNew(data.msg,false)
						        		setTimeout(function(){
						        			datatable.draw();
						        			iframe.document.getElementById('closeModelBtn').click();
								  		},2000)
						        	}
						        	
						        },
						        error:function(data){
						        	parent.alertNew(data.msg,false)
						        	setTimeout(function(){
						        		datatable.draw();
						        		iframe.document.getElementById('closeModelBtn').click();
							  		},2000)
						        }
						    });
						}  
						return false;
					} 
				},
				focus: true

			}, {
				name: '取消',
				callback: function() {

				}
			}]
		});
	}
	
	
	//删除
	function del(id,identify,isFixation) {
		if(isFixation == 0){
			alertNew("固定角色不允许被删除！");
			return false;
		}
		 confirm("确定删除所选项目？",function(){ 
			    $.ajax({
			        type:"post",
			        url:"roleDelete",
			        data:{"roleId":id},
			        dataType:"json",
			        success:function(data){
			        	alertNew("删除成功");
			        	window.location.href="list?identify="+identify;
			        },
			        error:function(){
			        	alert("删除失败!");
			        }
			    });
		 }
		)
			 
	}
</script>
	</m:Content>
</m:ContentPage>

