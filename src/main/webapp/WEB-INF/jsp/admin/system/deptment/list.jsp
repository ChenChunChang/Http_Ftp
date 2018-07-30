<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<m:ContentPage materPageId="master">
	<m:Content contentPlaceHolderId="css">
		<link rel="stylesheet" href="/admin/css/zTreeStyle/metro.css" type="text/css"/>
		<style>
	    	.icheckbox_square-green{
	    		float:left;
	    	}
	   </style>
	</m:Content>
	<m:Content contentPlaceHolderId="js">
		<script type="text/javascript" src="/admin/js/plugins/ztree/jquery.ztree.all.min.js"></script>
		<script type="text/javascript">	
		var IDMark_A = "_a";
		var zTree;  
    	var demoIframe;
    	var zNodes ; 
		var setting = {
				/* treeId:"applicationTree",
				async: {
					enable: true,//开启异步加载模式
				}, */
				view: {
					addDiyDom: addDiyDom,
					dblClickExpand: false,
					selectedMulti: false
				},
				
				edit: {
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				data: {
					keep:{
						leaf:true, // 子节点属性锁,true就是不能将子节点变为父节点
						parent:true // 父节点属性锁,true就是不能将父节点变为子节点
						},
					simpleData: {
						enable: true
					}
				},
				callback: {
					
					beforeDrag: beforeDrag,
					beforeDrop: beforeDrop,
					onDrag: onDrag //捕获节点被拖拽的事件回调函数  
			        //onDrop: onDrop //捕获节点拖拽操作结束的事件回调函数 
				}
			};	
		
		function beforeDrag(treeId, treeNodes) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		
			return targetNode ? targetNode.drop !== false : true;
		}
		 function onDrag(event, treeId, treeNodes) {  
			        className = (className === "dark" ? "":"dark");  
			    }  
		 
		   /*  function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {  
		        //拖拽结束之后  
//		      alert("拖拽节点的id:"+treeNodes[0].id);  
//		      alert("拖拽节点的新父节点的id:"+targetNode.id);  
		        //更新拖拽节点的父级目录id  
		        $.ajax({
					cache:false,
					type:"post",
					data:{
						"moveType":moveType,
						"parentId":targetNode.id,
						"catId":treeNodes[0].id
					},
					dataType:"json",
					url:"updateParentId",
					async:true,
					success:function(data){
						alert(data.message);
					},
					error:function(data){
						//alert("移动失败");
						
					}
				}); 
		    }  */ 
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("depTee"),
			isCopy = $("#copy").attr("checked"),
			isMove = $("#move").attr("checked"),
			prev = $("#prev").attr("checked"),
			inner = $("#inner").attr("checked"),
			next = $("#next").attr("checked");
			zTree.setting.edit.drag.isCopy = isCopy;
			zTree.setting.edit.drag.isMove = isMove;
			showCode(1, ['setting.edit.drag.isCopy = ' + isCopy, 'setting.edit.drag.isMove = ' + isMove]);

			zTree.setting.edit.drag.prev = prev;
			zTree.setting.edit.drag.inner = inner;
			zTree.setting.edit.drag.next = next;
			showCode(2, ['setting.edit.drag.prev = ' + prev, 'setting.edit.drag.inner = ' + inner, 'setting.edit.drag.next = ' + next]);
		}
		function showCode(id, str) {
			var code = $("#code" + id);
			code.empty();
			for (var i=0, l=str.length; i<l; i++) {
				code.append("<li>"+str[i]+"</li>");
			}
		}
		
/* 		
		function beforeClick(treeId, treeNode) {
			return !treeNode.isCur;
		}
		 */
		//删除
		function del(treeNode){
			 
			 confirm("确定要删除吗？",ok);
			 function ok(){
				 if(treeNode.id!=1){//总公司不能删除
	                $.ajax({
	                    type: "POST",
	                    url: "/system/department/deleteDept",
	                    async: false,
	                    data:{"deptId":treeNode.id},
	                    success: function (data) {
	                    	if(data.success){
								alertNew(data.msg,true);
							}else{
								alertNew(data.msg,false);
							}
	                    	setTimeout(function(){
								window.location.href="/system/department/allDept";
					  		},2000)
	                    },
	                    error:function(data){
	                    	alert("删除失败");
	                    	window.location.reload();
	                    }
	                });
				 }
			 }	 
		}
	/* 	//添加<暂时不用>
		function add(treeNode){
			var treeInfo = treeNode.id;
			window.location.href = "showContent?catId="+treeInfo+"&type=add";
		}
		 */
		
		function addDiyDom(treeId, treeNode) {
			if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
			var aObj = $("#" + treeNode.tId + IDMark_A);
			 var treeInfo = treeNode.id;
				var editStr = "<a id='diyBtn2_" +treeNode.id+ "' onclick='editDept("+JSON.stringify(treeNode)+");' style='color: #0000CC;'>[修改]</a>"+
					"<a id='diyBtn3_" +treeNode.id+ "' onclick='del("+JSON.stringify(treeNode)+");' style='color: #0000CC;'>[删除]</a>";
				aObj.after(editStr);
		}
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("depTee");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		var curStatus = "init", curAsyncCount = 0, goAsync = false; 
		function expandAll() {  
            if (!check()) {
                return;  
            }  
            var zTree = $.fn.zTree.getZTreeObj("depTee");  
            expandNodes(zTree.getNodes());  
            if (!goAsync) {  
                curStatus = "";  
            }  
        }  
        function expandNodes(nodes) {  
            if (!nodes) return;  
            curStatus = "expand";  
            var zTree = $.fn.zTree.getZTreeObj("depTee");  
            for (var i=0, l=nodes.length; i<l; i++) {  
                zTree.expandNode(nodes[i], true, false, false);//展开节点就会调用后台查询子节点  
                if (nodes[i].isParent && nodes[i].zAsync) {  
                    expandNodes(nodes[i].children);//递归  
                } else {  
                    goAsync = true;  
                }  
            }  
        }
        function check() {  
            if (curAsyncCount > 0) {  
                return false;  
            }  
            return true;  
        }  
		
		$(document).ready(function(){
			$.ajax({
				type:'post',
				url:'/system/department/treeDept',
				cache:false,
				dataType:"json",
				async:true,
				success:function(data){
					var arr = [];
					$.each(data.dlist,function(n, item){
						arr.push({
							id:item.departmentId,
							pId:item.parentId,
							name:item.departmentName
						});
						
					});
					$.fn.zTree.init($("#depTee"), setting, arr);
					$("#prev").bind("change", {},setCheck);
					$("#inner").bind("change", {},setCheck);
					$("#next").bind("change", {},setCheck);
					$("#selectAll").bind("click", selectAll);
					 setTimeout(function(){
			                expandAll("depTee");  
			            },0);//延迟加载  
				},
				error:function(data){
					
				}
			});	
		});
		function addDepartment() {
			$.dialog({
				title: '添加部门信息',
				width: 600,
				height: 380,
				lock:true,
				content: 'url:/system/department/addDepartment',
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

		//修改部门信息
		function editDept(treeNode) {
			$.dialog({
				title: '修改部门信息',
				width: 600,
				height: 380,
				lock:true,
				content: 'url:/system/department/eidtDepartment?departmentId='+treeNode.id,
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
	</script>
	</m:Content>
	<m:Content contentPlaceHolderId="content">
		<div class="content_wrap">
			<div class="jklr wrapper wrapper-content animated fadeInRight">
				<p style="font-size: 14px; margin-left: 10px"><img src="/admin/css/zTreeStyle/img/gen.png"/>公司架构<a onclick="addDepartment();" style="cursor: pointer;">[添加]</a></p>
				<div class="zdepTeeBackground left">
					<ul id="depTee" class="ztree"></ul>
				</div>
			</div>
			</div>
	</m:Content>
</m:ContentPage>
