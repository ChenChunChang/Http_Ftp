<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="css">
<style>
table tr th {
	text-align: center;
}

table tr td {
	vertical-align: middle !important;
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
			<div class="col-sm-12">
					<h3 style="float: left;">后台用户管理</h3>
					<px:authorize setting="用户-管理员管理添加">
						<button onclick="addAdminUser();"
							style="float: right; margin-left: 10px;" type="button"
							class="btn btn-warning">
							<i class="fa fa-plus"></i> 新建
						</button>
					</px:authorize>	
				</div>
			<div class="right_box" id="right_box" style="margin-top: 40px; padding: 15px 0;border-top: 1px solid #ddd">
				<div class="form-horizontal">
						<div class="form-group">
						<label for="" class="col-lg-1 col-md-2 col-sm-2 control-label">
							用户名：</label>
						<div class="col-lg-2 col-md-2 col-sm-2">
							<input type="text" class="form-control" name="userName" id="name"
								value="${userName }" />
						</div>
						<label for="" class="col-lg-1 col-md-2 col-sm-2 control-label">
							真实姓名：</label>
						<div class="col-lg-2 col-md-2 col-sm-2">
							<input type="text" class="form-control" name="realName"
								id="zsName" value="${realName }" />
						</div>
						<label class="col-lg-1 col-md-1 col-sm-1 control-label" for="">
							角色：</label>
						<div
							class="col-lg-2 col-md-2 col-sm-2 chosen-container chosen-container-single">
							<select class="select2_demo_1 form-control" name="roleName"
								id="rName">
								<option value="">不限</option>
								<c:forEach items="${role }" var="item">
									<option value="${item.roleName }"
										${roleName==item.roleName?'selected':'' }>${item.roleName }</option>
								</c:forEach>
							</select>
						</div>
					</div> 
						
						
					</div>
					<div style="margin-top: 1%;padding-bottom: 10px">
						<button id="search" style="margin-left: 10px;" type="button"
							class="btn btn-success">
							<i class="fa fa-search" style="margin-left: 5px;"></i> 搜索
						</button>
						<button onclick="clearbt();" style="margin-left: 10px;"
							type="button" class="btn btn-success">重置</button>
						<%-- <px:authorize setting="配件-批量删除配件信息"><button onclick="batchDeletes();" style="margin-left:10px;" type="button" class="btn btn-success">批量删除</button></px:authorize> --%>
					</div>
			
			
					 
			<!--<button onclick="add_user()" style="margin-left:10px;" type="button" class="btn btn-success"> <i class="fa fa-plus"></i> 添加</button>-->
			<!--表格部分-->
			<div class="ibox-content">
				<div class="row">
					<div class="col-sm-12">
						<table
							class="table table-striped table-bordered table-hover dataTables" >
							<thead>
								<tr>
									<th>序号</th>
									<th>用户名</th>
									<th>真实姓名</th>
									<th>邮箱</th>
									<th>出生日期</th>
									<th>家庭地址</th>
									<th>注册日期</th>
									<th>所属角色</th>
									<th>操作</th>
								</tr>
							</thead>
							
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	</div>
	</m:Content>
	<m:Content contentPlaceHolderId="js">
	<iframe id="ifr" src="" frameborder="no" scrolling="no" width="780px"
		height="100%" allowtransparency="true"></iframe>
	<script src="/admin/js/templet_form.js"></script>
	<script>
	var datatable;

	$.fn.dataTable.ext.errMode = 'none'; //不显示任何错误信息
	$(function(){
	
		$("#search").bind("click",function(){
			datatable.draw();
		});
		
		$("li[data-status='${status}']").addClass("select_tab");
		$("#clientStatus").val("${status}");
		$("li[data-status]").on("click",function(){
			$("li.select_tab").removeClass("select_tab");
			$(this).addClass("select_tab");
			$("#clientStatus").val($(this).data("status"));
			$("#detailPanel").css("display","none");
			tabIndex=1;
			datatable.draw();
		});

		datatable = $(".dataTables").DataTable({
			"processing": true,
            "serverSide": true,
            "filter": false,
            "dom": '<"top">rt<"bottom"iflp>',
            "ordering":false,
            "iDisplayLength":10,
            "aLengthMenu": [[5,10,20, 50, 100], [5,10,20, 50, 100]],
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
	        	$('td:eq(0)', row).attr("data-id",data.Id);
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
                    "targets": 6,
                    "data": null,
                    "render": function (data, type, row) {
                    	return parseTime(row.registrationDate);
                    }
                }, 
	            {
	                "targets": 8,
	                "data": null,
	                "render": function (data, type, row) {
	                	var html = '<px:authorize setting="用户-管理员管理修改"><a href="javascript:void(0)" onclick="editUser('+row.userId+')">编辑</a></px:authorize>&nbsp;&nbsp;&nbsp;';
	                	html+='<px:authorize setting="用户-管理员管理删除"><a href="javascript:void(0)" onclick="delAdminUser('+row.userId+')">删除</a></px:authorize>&nbsp;&nbsp;&nbsp';
	                    return html;
	                }
	            }
	        ],
	        "columns": [
	            { "data": null, "orderable": false },
				{ "data": "userName" },
				{ "data": "realname" },
				{ "data": "email" },
				{ "data": "birthDate" },
				{ "data": "address" },
				{ "data": "registrationDate" },
				{ "data": "roleName" },
	        ],
	        
	        "ajax": {
	            "url": "/system/user/adminlistData",
	            "method": "post",
	            "data": function (obj) {
	            	obj.name=$("#name").val();
	            	obj.zsName=$("#zsName").val();
	            	obj.roleName=$("#rName").val();
	               
	            }
	    	},
	    });
	});
	
	//重置搜索框
	function clearbt() {
		$("#name").val("") // 清空并获得焦点
		$("#zsName").val("");
		$("select[name='roleName']").val("");//清空下拉框
		refreshData();
	}
	function refreshData() {
		datatable.draw();
	}
	//添加后台用户
	 function addAdminUser() {
	 	$.dialog({
	 		title: '添加后台用户',
	 		width: 700,
	 		height: 680,
	 		lock:true,
	 		content: 'url:/system/user/adds',
	 		init: function() {
	 			var iframe = this.iframe.contentWindow;
	 			var ul = iframe.document.getElementById('right_list');
	 		},
	 		button: [{
	 			name: '保存',
	 			callback: function() {
	 				var iframe = this.iframe.contentWindow;
	 				var btn= iframe.document.getElementById('btnn').click();
	 				if(btn==true){
	 					
	 				}else{
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
	//修改后台用户
	 function editUser(userId) {
	 	$.dialog({
	 		title: '修改后台用户',
	 		width: 700,
	 		height: 680,
	 		lock:true,
	 		content: 'url:/system/user/adminUpdate?userId='+userId,
	 		init: function() {
	 			var iframe = this.iframe.contentWindow;
	 			var ul = iframe.document.getElementById('right_list');
	 		},
	 		button: [{
	 			name: '保存',
	 			callback: function() {
	 				var iframe = this.iframe.contentWindow;
	 				var btn= iframe.document.getElementById('btnn').click();
	 				if(btn==true){
	 					
	 				}else{
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
	function delAdminUser(userId){
		confirm("确定要删除吗？", ok);
		function ok() {
  			$.post("delAdminUser",{"userId":userId},
  			function(data){
  				if (data.success) {
					alertNew(data.msg, true);
				} else {
					alertNew(data.msg, false);
				}
				setTimeout(function() {
					datatable.draw();
				}, 2000)
  			});
		}	
	}
</script>
	</m:Content>
</m:ContentPage>
