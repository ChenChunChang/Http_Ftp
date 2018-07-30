<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<%@ taglib uri="/WEB-INF/tlds/authorizetag.tld" prefix="px"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="css">
	<link rel="stylesheet" href="/admin/css/keHuGuanXi.css" />
		<style>
th.prev {
	background: url(../img/dateleft.png) -3px !important;
}

th.next {
	background: url(../img/dateright.png) -10px !important;
}

th, td {
	text-align: center;
}
</style>
	</m:Content>

	<m:Content contentPlaceHolderId="content">
		<div id="wrapper" class="container">
			<div id="list" class="wrapper wrapper-content animated fadeInRight">
				<h3 style="float:left;display:inline-block;">数据字典配置</h3>
				<button type="button" onclick="fh();" style="float: right;margin-top:-8px;margin-left:10px" class="btn btn-success">
				<i class="fa fa-step-backward"></i>  
				返回</button>
				<px:authorize setting="字典-添加字典详细">
						<button type="button" onclick="addOrUpItem('0');"
							class="btn btn-warning" style="float: right;margin-top:-8px;"><i class="fa fa-plus" style="margin-left: 5px;margin-top:-8px"></i>	新增</button>
					</px:authorize>
					
				<div class="right_box" style="margin-top:36px;">
					
					<input type="hidden" style="width: 180px" id="dictId" name="dictId"
						value="${dictId}" />
					<!--表格部分-->
					<div class="ibox-content" style="margin-top: 20px;">
						<div class="row">
							<div class="col-sm-12">
								<table
									class="table table-striped table-bordered table-hover dataTables-example dataTables">
									<thead>
										<tr>
											<th width="11%">序号</th>
											<th width="12%">名称</th>
											<th width="11%">值</th>
											<th width="12%">排序号</th>
											<th width="11%">常规操作</th>
										</tr>
									</thead>
									<%-- <tbody>
									<c:forEach items="${list }" var="item">
										<tr>
											<td>${item.itemValue}</td>
											<td>${item.itemName}</td>
											<td><a
												href="updateItem?itemId=${item.itemId}&dictId=${dictId}">修改</a>
												<a onclick="delDictInfo('${item.itemId}','${dictId}')">删除</a>
											</td>
										</tr>
									</c:forEach>
								</tbody> --%>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!--</div>-->
			</div>
		</div>
	</m:Content>
	<m:Content contentPlaceHolderId="js">
		<script>
		//$.fn.dataTable.ext.errMode = 'none'; //不显示任何错误信息
		$(function(){
			datatable = $(".dataTables").DataTable({
	            "processing": true,
	            "serverSide": true,
	            "filter": false,
	            "dom": '<"toolbar">frtip',
	            "ordering":false,
	            "iDisplayLength":10000,
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
	                    "targets": 4,
	                    "data": null,
	                    "render": function (data, type, row) {
	                    	var html = '';
	                    	//
	                    	html += '<px:authorize setting="字典-修改字典详细"><a onclick="addOrUpItem('+row.itemId+')">修改</a>&nbsp;&nbsp;&nbsp;</px:authorize>';
	                    	html += '<px:authorize setting="字典-删除字典详细"><a onclick="delItem('+row.itemId+')">删除</a></px:authorize>';
	                        return html;
	                    }
	                }
	            ],
	            "columns": [
	                { "data": null, "orderable": false },
	                { "data": "itemName",defaultContent:"" },
	                { "data": "itemValue",defaultContent:"" },
	                { "data": "orderIndex",defaultContent:"" },
	            ],
	            
	            "ajax": {
	                "url": "/system/dictionary/showDictInfo/dataTable",
	                "method": "post",
	                "data": function (obj) {
	                	obj.dictId=$("#dictId").val();
	                }
	            }
	            
	        });
			
		});
		
		//返回
		function fh() {
			window.location.href = "/system/dictionary/list";
		}
		
		//添加或者修改
		function addOrUpItem(itemId){
			var dictId = $("#dictId").val();
			var a = 0;//为了限制多次提交
			var info = '';//弹窗标题
			if(itemId==0){
				info = '添加字典详细';
			}else{
				info = '修改字典详细';
			}
			$.dialog({
				title: info,
				width: 600,
				height: 380,
				lock : true,
				content: 'url:/system/dictionary/addOrUpItemModel?itemId='+itemId+'&dictId='+dictId,
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
							        url:"/system/dictionary/addOrUpItem",
							        success:function(data){
							        	if(data.success){
							        		parent.alertNew(data.msg,true);
						        			datatable.draw();
						        			iframe.document.getElementById('closeModelBtn').click();
							        	}else{
							        		parent.alertNew(data.msg,false);
						        			datatable.draw();
						        			iframe.document.getElementById('closeModelBtn').click();
							        	}
							        	
							        },
							        error:function(data){
							        	parent.alertNew(data.msg,false)
						        		datatable.draw();
						        		iframe.document.getElementById('closeModelBtn').click();
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
		function delItem(itemId){
			var dictId = $("#dictId").val();
			$.dialog.confirm("确定要删除吗？", function(){
				$.post("/system/dictionary/delDictInfo",{"itemId":itemId,"dictId":dictId},
				function(data){
					if(data.success){
						alertNew(data.msg,true);
					}else{
						alertNew(data.msg,false);
					}
					datatable.draw();
				});
						    
			});
		}
		
		

		</script>
	</m:Content>
</m:ContentPage>