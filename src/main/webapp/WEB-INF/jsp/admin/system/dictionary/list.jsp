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
		<div id="list" class="wrapper wrapper-content animated fadeInRight">
			<h3 style="float:left;display:inline-block;">数据字典配置</h3>
			<button type="button" onclick="addDict();" style="margin-left: 10px; float: right;margin-top:-8px;"
					class="btn btn-warning"><i class="fa fa-plus" style="margin-left: 5px;"></i>	新增</button>
			
			<div class="right_box" style="height:100%;margin-top:36px">
				<!--表格部分-->
				<div class="ibox-content" style="margin-top: 20px;">
					<div class="row">
						<div class="col-sm-12">
							<table
								class="table table-striped table-bordered table-hover dataTables-example dataTables">
								<thead>
									<tr>
										<th width="5%">序号</th>
										<th width="5%">ID</th>
										<th width="10%">名称</th>
										<th width="15%">描述</th>
										<th width="7%">类型</th>
										<th width="15%">操作</th>
									</tr>
								</thead>
								<%-- <tbody>
									<a onclick="Deletes('${dictionary.dictionaryId}')">删除</a></td>
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
			datatable = $(".dataTables").DataTable({
	            "processing": true,
	            "serverSide": true,
	            "filter": false,
	            "dom": '<"toolbar">frtip',
	            "ordering":false,
	            "iDisplayLength":10,
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
	                    	var page = datatable.page.info();
			                 var pageno = page.page;
			                 var length = page.length;
							return pageno*length+meta.row+1;
	                    }
	                },
	                {
	                    "targets": 5,
	                    "data": null,
	                    "render": function (data, type, row) {
	                    	var html = '';
	                    	//
	                    	html += '<px:authorize setting="字典-查看字典详细"><a href="showDictInfo?dictId='+row.dictionaryId+'">数字字典项管理</a>&nbsp;&nbsp;&nbsp;</px:authorize>';
	                    	html += '<px:authorize setting="字典-修改字典项"><a onclick="upDict('+row.dictionaryId+')">修改</a>&nbsp;&nbsp;&nbsp;</px:authorize>';
	                    	//暂时屏蔽删除功能
	                    	/* html += '<px:authorize setting="字典-删除字典项"><a onclick="delDict('+row.dictionaryId+')">删除</a></px:authorize>'; */
	                        return html;
	                    }
	                }
	            ],
	            "columns": [
	                { "data": null, "orderable": false },
	                { "data": "dictionaryId",defaultContent:"" },
	                { "data": "dictionaryName",defaultContent:"" },
	                { "data": "dictionaryDescription",defaultContent:"" },
	                { "data": "dictionaryType",defaultContent:"" }
	            ],
	            
	            "ajax": {
	                "url": "/system/dictionary/dataTable",
	                "method": "post",
	                "data": function (obj) {
	                	
	                }
	            }
	            
	        });
			
			
		});
		
		//添加
		function addDict() {
			var a = 0;//为了限制多次提交
			$.dialog({
				title: '添加字典项',
				width: 600,
				height: 380,
				lock : true,
				content: 'url:/system/dictionary/addDictModel',
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
							        url:"/system/dictionary/addOrUpDictionary",
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
		
		//修改
		function upDict(dictId) {
			var a = 0;//为了限制多次提交
			$.dialog({
				title: '修改字典项',
				width: 600,
				height: 380,
				lock : true,
				content: 'url:/system/dictionary/updateDict?dictId='+dictId,
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
							        url:"/system/dictionary/addOrUpDictionary",
							        success:function(data){
							        	if(data.success){
							        		parent.alertNew(data.msg,true)
						        			datatable.draw(false);
						        			iframe.document.getElementById('closeModelBtn').click();
							        	}else{
							        		parent.alertNew(data.msg,false)
						        			datatable.draw(false);
						        			iframe.document.getElementById('closeModelBtn').click();
							        	}
							        	
							        },
							        error:function(data){
							        	parent.alertNew(data.msg,false)
						        		datatable.draw(false);
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
		function delDict(dictId){
			$.dialog.confirm("确定要删除吗？", function(){
				$.post("/system/dictionary/delDict",{"dictId":dictId},
				function(data){
					if(data.success){
						alertNew(data.msg,true);
					}else{
						alertNew(data.msg,false);
					}
					datatable.draw(false);
				});
						    
			});
			
		}

		</script>
	</m:Content>
</m:ContentPage>
