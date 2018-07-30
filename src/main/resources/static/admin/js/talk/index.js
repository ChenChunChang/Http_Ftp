/*显示回复输入框*/
function show_reply_input(e,id,c){
    $(e).parents('.item_main').siblings().toggleClass('active');
    //加载回复列表
    $.get("/shcrm/ContactLog/loglistdata",{parentId:id,clientId:c},function(html){
    	$("#replylist"+id).html(html);
    });
}
/*显示回复后回复输入框*/
function show_rtr(e){
    $(e).css('display','none');
    $(e).siblings().css('display','block');
    parent.setFrameHeight();
}
/*隐藏回复后回复输入框*/
function hideInput(e){
    $(e).parents('.input_show_box').css('display','none');
    $(e).parents('.input_show_box').siblings().css('display','block');
    parent.setFrameHeight();
}
/*回复后回复*/
function reply_content(e){
	hideInput(e)//回复后隐藏回复输入框
	var input=$(e).parent().siblings('.input-wrapper').find('textarea');
    var con=input.val();
    var parentid=input.data("parentid");
    var clientid=input.data("clientid");
    var parentusername=input.data("parentusername");
    if(con!==''){
    	$.post("/shcrm/ContactLog/addcontactlog",{parentId:parentid,remarks:con,clientId:clientid,parentUserName:parentusername},function(data){
    		if(data.result){
    			var replycountspan = $("#replycount"+data.rootid);
    			if(replycountspan.length>0){
    				var count=Number(replycountspan.find("font").html());
    				replycountspan.find("font").html(count+1);
    				replycountspan.css("display","inline-block");
    			}
    			var dom='<li class="reply_item">' +
                '       <div class="reply_touXiang">' +
                '           <a href="javascript:void(0)">' +
                '               <img src="/admin/css/talk/img/user.png" alt="">' +
                '           </a>' +
                '       </div>' +
                '       <div class="reply_content_inner">' +
                '           <div class="reply_content_text">' +
                '               <a href="javascript:void(0)">'+data.userName+'</a>' +
                '               <span>' +
                '                   :回复' +
                '                   <a href="javascript:void(0)">'+parentusername+'</a>:' +con+
                '               </span>' +
                '           </div>' +
                '           <div class="reply_time">' +
                '               <span>'+data.inputDate+'</span>' +
                '                   <span class="reply-btn">' +
                '                   <span title="赞" class="islike-btn">' +
                '                       <b></b>' +
                '                   </span>' +
                '                   <i class="S_txt3">|</i>' +
                '                   <a onclick="show_last_reply(this)" href="javascript:void(0);" class="open-reply-btn">回复</a>' +
                '               </span>' +
                '           </div>' +
                '       </div>' +
                '       <div class="reply-to-reply-wrapper">' +
                '           <div class="input-box">' +
                '               <div class="r-to-r-img">' +
                '                   <img src="/admin/css/talk/img/user.png" alt="">' +
                '               </div>' +
                '               <div class="box_r">' +
                '                   <div class="input-wrapper">' +
                '                       <fieldset>' +
                '                           <textarea data-clientid="'+clientid+'" data-parentusername="'+data.userName+'" data-parentid="'+data.id+'" placeholder="添加回复..." ></textarea>' +
                '                        </fieldset>' +
                '                   </div>' +
                '                   <div class="media">' +
               
                '                   </div>' +
                '                   <div class="f-actions">' +
                '                       <button onclick="last_reply(this)" type="button" class="huiFu">回复</button>' +
                '                       <button onclick="hideThis(this)" type="button" class="quXiao">取消</button>' +
                '                   </div>' +
                '               </div>' +
                '           </div>' +
                '       </div>' +
                '   </li>';
    	        $(e).parents('.reply_header_wrapper').siblings('.reply_body').find('.reply_list').prepend(dom);
    	        $(e).parent().siblings('.input-wrapper').find('textarea').val('');
    	        parent.setFrameHeight();
    		}else{
    			alertNew(data.msg);
    		}
    	});
        
    }else{
    	alertNew('回复内容不能为空！');
    }
}
function show_last_reply(e){
    $(e).parents('.reply_content_inner').siblings('.reply-to-reply-wrapper').addClass('active');
}
function hideThis(e){
    $(e).parents('.reply-to-reply-wrapper').removeClass('active');
}
function last_reply(e){
    var input=$(e).parent().siblings('.input-wrapper').find('textarea');
    var con=input.val();
    var parentid=input.data("parentid");
    var clientid=input.data("clientid");
    var parentusername=input.data("parentusername");
    if(con!==''){
    	$.post("/shcrm/ContactLog/addcontactlog",{parentId:parentid,remarks:con,clientId:clientid,parentUserName:parentusername},function(data){
    		if(data.result){
    			var replycountspan = $("#replycount"+data.rootid);
    			if(replycountspan.length>0){
    				var count=Number(replycountspan.find("font").html());
    				replycountspan.find("font").html(count+1);
    				replycountspan.css("display","inline-block");
    			}
    			var str='<li class="reply_item">' +
                '       <div class="reply_touXiang">' +
                '           <a href="javascript:void(0)">' +
                '               <img src="/admin/css/talk/img/user.png" alt="">' +
                '           </a>' +
                '       </div>' +
                '       <div class="reply_content_inner">' +
                '           <div class="reply_content_text">' +
                '               <a href="javascript:void(0)">'+data.userName+'</a>' +
                '               <span>' +
                '                   :回复' +
                '                   <a href="javascript:void(0)">'+parentusername+'</a>:' +con+
                '               </span>' +
                '           </div>' +
                '           <div class="reply_time">' +
                '               <span>'+data.inputDate+'</span>' +
                '               <span class="reply-btn">' +
               // '                   <a onclick="delete_last_reply(this)" href="javascript:;" class="open-reply-btn">删除</a>' +
                '                   <i class="S_txt3">|</i>' +
                '                   <span title="赞" class="islike-btn">' +
                '                       <b></b>' +
                '                   </span>' +
                '                   <i class="S_txt3">|</i>' +
                '                   <a onclick="show_last_reply(this)" href="javascript:;" class="open-reply-btn">回复</a>' +
                '               </span>' +

                '           </div>' +
                '       </div>' +
                '       <div class="reply-to-reply-wrapper">' +
                '           <div class="input-box">' +
                '               <div class="r-to-r-img">' +
                '                   <img src="/admin/css/talk/img/user.png" alt="">' +
                '               </div>' +
                '               <div class="box_r">' +
                '                   <div class="input-wrapper">' +
                '                       <fieldset>' +
                '                           <textarea data-clientid="'+clientid+'" data-parentusername="'+data.userName+'" data-parentid="'+data.id+'" placeholder="添加回复..." ></textarea>' +
                '                        </fieldset>' +
                '                   </div>' +
                '                   <div class="media">' +
              
                '                   </div>' +
                '                   <div class="f-actions">' +
                '                       <button onclick="last_reply(this)" type="button" class="huiFu">回复</button>' +
                '                       <button onclick="hideThis(this)" type="button" class="quXiao">取消</button>' +
                '                   </div>' +
                '               </div>' +
                '           </div>' +
                '       </div>' +
                '   </li>';
	            $(e).parents('.reply_list').prepend(str);
	            $(e).parent().siblings('.input-wrapper').find('textarea').val('');
    		}
    		else{
    			alertNew(data.msg)
    		}
    	});
        
    }else{
    	alertNew('请填写回复内容！');
    }
}


/*删除回复的回复*/
function delete_last_reply(e){
    $(e).parents('.reply_item').remove();
}


$(function(){
	/*顶部发送部分tab切换标签*/
	$('.ui-items li').click(function(){
	    var index=$(this).index();
	    $(this).addClass('sel').siblings().removeClass('sel');
	    var left=(index*55.0625);
	    $(this).parent().siblings().animate({ left: (13+left)}, "100" )
	})
	/*发送列表部分tab切换标签*/
	$('.w-tab li').click(function(){
	    $(this).addClass('state-active').siblings().removeClass('state-active');
	    var type=$(this).html();
	    if(type=="全部"){
	    	$("dd.target_item2,div[data-logtype]").css("display","block");
	    	$("dd.target_item2").attr("data-show",true);
	    }else{
	    	$("div[data-logtype]").each(function(){
	    		var logtype=$(this).data("logtype");
	    		var id=$(this).data("id");
	    		var showtype=logtype==type?"block":"none";
	    		$(this).css("display",showtype);
	    		$("dd[data-id='"+id+"']").css("display",showtype);
	    		$("dd[data-id='"+id+"']").attr("data-show",logtype==type);
	    	});
	    }
	    
	    $("dd.active").removeClass("active");
    	$("dd.target_item2[data-show='true']:first").addClass("active");
	})
	/*****显示顶部输入框*****/
	$('.ui-content .form-thumb').click(function(){
	    $(this).css('display','none');
	    $(this).siblings().css('display','block');
	    $('.ui-content .close').click(function(){
	        $(this).css('display','none');
	        $(this).siblings('.input_form').css('display','none');
	        $(this).siblings('.form-thumb').css('display','block');
	    })
	})
	/*****发布主题***/
	$('#faBu').click(function(){
		var publicinput=$(this).parents('.input_form').find('.publish-input');
		var clientid=publicinput.data("clientid");
	    var inner=publicinput.val();
	    if(inner!==''){
	    	//提交数据
	    	$.post("/shcrm/ContactLog/addContactLog",{clientId:clientid,remarks:inner},function(data){
	    		if(data.result){
	    			var html='<div id="item'+data.id+'" class="list_item">' +
	                '       <div class="item_main">' +
	                '           <div class="item_top">' +
	                '               <div class="item_face">' +
	                '                   <a href="javascript:void(0)">' +
	                '                       <img src="/admin/css/talk/img/user.png" alt="">' +
	                '                   </a>' +
	                '               </div>' +
	                '               <div class="item_info">' +
	                '                   <div class="item_name">' +
	                '                       <a href="javascript:void(0)">'+data.userName+'</a>' +
	                '                   </div>' +
	                '                   <div class="item_time">' +
	                '                       <span class="create_time">'+data.inputDate+'</span>' +
	                '                       <span>-</span>' +
	              //  '                       <span>全公司</span>' +
	                '                   </div>' +
	                '               </div>' +
	                '               <div class="clear"></div>' +
	                '           </div>' +
	                '           <div class="item_detail">' +
	                '               <div class="info_content">'+inner+'</div>' +
	                '               <div class="item-func">' +
	                '                   <div class="handle">' +
	               
	                '                       <div class="fl_btn islike">' +
	                '                           <i class="S_txt3">|</i>' +
	                '                           <a href="javascript:void(0);"><b></b></a>' +
	                '                       </div>' +
	                '                       <div class="fl_btn select-cur">' +
	                '                           <i class="S_txt3">|</i>' +
	                '                           <a onclick="show_reply_input(this)" href="javascript:void(0);">回复</a>' +
	                '                       </div>' +
	                '                   </div>' +
	                '               </div>' +
	                '           </div>' +
	                '       </div>' +
	                '       <!-- 底部回复部分 -->' +
	                '       <div class="bottom_wrapper">' +
	                '           <div class="list_repeat">' +
	                '               <div class="reply_header_wrapper">' +
	                '                   <div class="input_show_box">' +
	                '                       <div class="reply_user_img">' +
	                '                           <img src="/admin/css/talk/img/user.png" alt="">' +
	                '                       </div>' +
	                '                       <div class="box_r">' +
	                '                           <div class="input-wrapper">' +
	                '                               <fieldset>' +
	                '                                   <textarea data-parentusername="'+data.userName+'" data-clientid="'+data.clientId+'" data-parentid="'+data.id+'" placeholder="添加回复..."></textarea>' +
	                '                               </fieldset>' +
	                '                           </div>' +
	                '                           <div class="media">' +
	              
	                '                           </div>' +
	                '                           <div class="f-actions">' +
	                '                               <button onclick="reply_content(this)" type="button" class="huiFu">回复</button>' +
	                '                               <button onclick="hideInput(this)" type="button" class="quXiao">取消</button>' +
	                '                           </div>' +
	                '                       </div>' +
	                '                   </div>' +
	                '                   <div onclick="show_rtr(this)" class="input-box-hidden">' +
	                '                       <span>添加回复&nbsp;…</span>' +
	                '                   </div>' +
	                '                 </div>' +
	                '                 <div class="reply_body">' +
	                '                     <ul class="reply_list"></ul>' +
	                '                 </div>' +
	                '             </div>' +
	                '         </div>' +
	                '     </div>';
		            $('.feed-list-inner').prepend(html);
		            $(this).parents('.input_form').find('.publish-input').val('');
		            parent.setFrameHeight();
	    		}else{
	    			alertNew(data.msg);
	    		}
	    	});
	        
	        
	    }else{
	        alertNew('发布内容不能为空！');
	    }
	})
	
	/*筛选*/
	$('.btn-filter').click(function(){
	    $(this).parent().siblings().toggleClass('active');
	})
	/*筛选条件（时间）*/
	$('.date_select a').click(function(){
	    $(this).siblings().toggleClass('active');
	})
	$('.date_select .select_box ul li').click(function(){
	    $(this).addClass('selected').siblings().removeClass('selected');
	    var inner=$(this).find('.name').html();
	    $(this).parent().parent().siblings().html(inner);
	    $(this).parent().parent().removeClass('active');
	})
	$('.mn-select-box a').click(function(){
	    $(this).siblings().toggleClass('active');
	})
	$('.mn-select-box .select_box ul li').click(function(){
	    $(this).addClass('selected').siblings().removeClass('selected');
	    var inner=$(this).find('.name').html();
	    $(this).parent().parent().siblings().html(inner);
	    $(this).parent().parent().removeClass('active');
	})
	/*添加发送范围*/
	$('.select_wrapper').click(function(){
	    $(this).find('.dialog-wrapper').addClass('active');
	})
	$(document).click(function(e){
	    if($(e.target).closest('.select_wrapper').length==0){
	        $('.dialog-wrapper').removeClass('active');
	    }
	})

	$('.select_wrapper .range-tab-title li').click(function(){
	    var index=$(this).index();
	    $(this).addClass('state-active').siblings().removeClass('state-active');
	    $('.tab_con_wrapper>div:eq('+index+')').addClass('active').siblings().removeClass('active');
	})
	
    var scrollTop2 = 0;
    if (document.documentElement && document.documentElement.scrollTop)	{
        scrollTop2 = document.documentElement.scrollTop;
    }else if (document.body) {
        scrollTop2 = document.body.scrollTop;
    }

    $(window).scroll(function(){
        if($('.target_menu').attr('IsClick')=="1"){
            return false;
        }
        var divs=$('.feed-list-inner>.list_item');
        for(var i=0;i<divs.length;i++){
            if($(this).scrollTop()>=$(divs[i]).offset().top&&$(this).scrollTop()<=$(divs[i]).offset().top+$(divs[i]).height()){
                var id=$(divs[i]).attr('id');
                $('a[href="#'+id+'"]').parent().addClass('active');
                $('a[href="#'+id+'"]').parent().siblings().removeClass('active');
            }
        }
    });
    /*左侧时间轴关联内容*/
    $('.target_menu dl dd.target_item2').click(function(){
        $('.target_menu dl dd.target_item2').removeClass('active');
        $('.target_menu').attr('IsClick','1');
        $(this).addClass('active');
        var href=$(this).find('a').attr('href');
        var Height=$(href).offset().top;
        $("html,body").animate({scrollTop: Height}, "fast",function(){
            $('.target_menu').attr('IsClick','0');
        });
    })
    $('#popup').popup({
        ifDrag: true,
        dragLimit: true
    });

    $.datetimepicker.setLocale('ch');
    $('#datetimepicker').datetimepicker({
        inline:true,
        defaultDate:convertDateFromString($("#maincontent").data("currentdate")),
        onSelectDate:function(ct,$i){
        	var d = ct.format("yy年MM月dd日");
        	$("dd.target_item1 > a").each(function(){
        		var t=$(this).html();
        		if(t==d){
        			$(this).parent().next().find("a").click();
        		}
        	});
        	
        }
    });
});
function convertDateFromString(dateString) { 
	if (dateString) { 
		var arr1 = dateString.split(" "); 
		var sdate = arr1[0].split('-'); 
		var date = new Date(sdate[0], sdate[1]-1, sdate[2]); 
		return date;
	}else{
		return new Date();
	}
}
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