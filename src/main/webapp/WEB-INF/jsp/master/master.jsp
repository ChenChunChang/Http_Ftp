<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib  uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="px"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<px:MasterPage id="master">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/admin/public/bootstrap.min.css" title="" rel="stylesheet" />
<link href="/admin/font-awesome/css/font-awesome.min.css" rel="stylesheet">


<link href="/admin/css/plugins/datetimepicker/bootstrap-datetimepicker.min.css"	rel="stylesheet" media="screen">
<link href="/admin/public/animate.css" rel="stylesheet">
<link href="/admin/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="/admin/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<link href="/admin/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
<link href="/admin/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">
<link rel="stylesheet" href="/admin/css/plugins/dataTables/fixedColumns.dataTables.min.css" />
<link rel="stylesheet" href="/admin/public/inspinia.css" />
<style>
.table tbody tr.sel{
	background:#F4F7FD;
	color:#000;
}

.dataTables{
width:100% !important;
}
.dataTables a{
	display:inline-block;
}
.dataTables tr {
	cursor: pointer;
}
.dataTables_length{
margin-left:10px;
margin-top:4px;
}
.dataTables_length select{
margin-left:0px;
padding-top:3px;
}
#tb_info .form-group{
	margin-bottom:0;
}
#dialog{
    width:100%;
    height:100%;
    position: fixed;
    /*background:#000;
    opacity: 0.6;*/
   	background:url(/admin/img/b3.png) repeat 0 0;
   	z-index:1001;
    display: none;
}
#dialog_con{
    width: 400px;
    height:200px;
    position: relative;
    background:#fff;
    border-radius: 3px;
    padding-top: 50px;
    z-index:1002;
    display: none;
}
#dialog_body{
    text-align: center;
}
p.msgInfo{
    font-size:20px;
    color:#F16B6B;
}
.btn_box{
    width: 100%;
    position: absolute;
    bottom:0;
    padding:10px 0;
    text-align: center;
    border-top:1px solid #ddd;
}
.btn_box button{
    padding:5px 10px;
    margin:0 5px;
    border-radius:3px ;
}
.queDing_btn{
    background:#70CA10;
    border:1px solid #70CA10;
    color:#fff;
}
.quXiao_btn{
    background:#DCDDDC;
    border:1px solid #DCDDDC;
    color:#333;

}
p.msgInfo.confirm_style{
    color:#30BEED;
}
p.msgInfo.success_style{
    color:#70CA10;
}
</style>
<px:ContentPlaceHolder id="css"></px:ContentPlaceHolder>

<title>在线答题考试系统</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="/admin/public/html5shiv.js"></script>
	<script src="/admin/public/respond.min.js"></script>
<![endif]-->
</head>
<body style="overflow-y:auto">
<px:ContentPlaceHolder id="content"></px:ContentPlaceHolder>
<script src="/admin/public/jquery-1.11.3.js"></script>
<script src="/admin/public/bootstrap.min.js" type="text/javascript"></script>
<script src="/admin/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/admin/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/admin/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/admin/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
<script	src="/admin/js/plugins/dataTables/dataTables.fixedColumns.min.js"></script>
<script src="/admin/js/plugins/lhgdialog/lhgdialog.js"></script>

<script src="/admin/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script	src="/admin/js/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script	src="/admin/js/plugins/datetimepicker/bootstrap-datetimepicker-update.min.js"></script>
<script	src="/admin/js/plugins/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>


<script type="text/javascript">
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}

$.fn.dataTable.FixedColumns.prototype.destroy = function(){
    var nodes = ['body', 'footer', 'header'];

    //remove the cloned nodes
    for(var i = 0, l = nodes.length; i < l; i++){
        if(this.dom.clone.left[nodes[i]]){
            this.dom.clone.left[nodes[i]].parentNode.removeChild(this.dom.clone.left[nodes[i]]);
        }
        if(this.dom.clone.right[nodes[i]]){
            this.dom.clone.right[nodes[i]].parentNode.removeChild(this.dom.clone.right[nodes[i]]);
        }
    }

    //remove event handlers
    $(this.s.dt.nTable).off( 'column-sizing.dt.DTFC destroy.dt.DTFC draw.dt.DTFC' );

    $(this.dom.scroller).off( 'scroll.DTFC mouseover.DTFC' );
    $(window).off( 'resize.DTFC' );

    $(this.dom.grid.left.liner).off( 'scroll.DTFC wheel.DTFC mouseover.DTFC' );
    //$(this.dom.grid.left.wrapper).remove();

    $(this.dom.grid.right.liner).off( 'scroll.DTFC wheel.DTFC mouseover.DTFC' );
    //$(this.dom.grid.right.wrapper).remove();

    $(this.dom.body).off('mousedown.FC mouseup.FC mouseover.FC click.FC');

    //remove DOM elements
    var $scroller = $(this.dom.scroller).parent();
    var $wrapper = $(this.dom.scroller).closest('.DTFC_ScrollWrapper');
    $scroller.insertBefore($wrapper);
    $wrapper.remove();

    //cleanup variables for GC
    delete this.s;
    delete this.dom;
};

function parseTime(timestamp,f) {
	if(f==null){
		f="yyyy-MM-dd hh:mm:ss";
	}
	 var date = new Date(parseInt(timestamp));
	 return date.format(f);
}

function convertDateFromString(dateString) { 
	if (dateString) { 
		var arr1 = dateString.split(" "); 
		var sdate = arr1[0].split('-'); 
		if(arr1.length==2){
			var smin=arr1[1].split(":");
			var date = new Date(sdate[0], sdate[1]-1, sdate[2],smin[0],smin[1]); 
			return date;
		}else{
			var date = new Date(sdate[0], sdate[1]-1, sdate[2]); 
			return date;
		}
		
	} 
}

function loading(t){
	window.top.loading(t);	
}

function alertNew(msg,flag){
	window.top.alertNew(msg,flag);
}

$(function(){
    var height=$(window).height();
    var width=$(window).width();
    $('#dialog_con').css({
        "position":"absolute",
        "top":(height/2)-150,
        "left":(width/2)-200
    })
})
function confirm(msg,ok){
	window.top.confirm(msg,function(){
		if(ok!=null){
			ok();
		}
	});
}

</script>
<px:ContentPlaceHolder id="js"></px:ContentPlaceHolder>
</body>
</html>
</px:MasterPage>
