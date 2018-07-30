var _placeMyStands;
//var api = frameElement.api,W=api.opener;
var parent = window.parent || window,
dialogProxy = parent.DrawPositionDialogProxy,
$wrapper = null,
selectedStyle = {
    fill: '#79ba1e',
    stroke: 'yellow'
},
zoom = 1;
    /*
     ********************
     * 加载页面
     ********************
     */

    function render(data) {
    	
        // 加载画布
        renderPaper(data.img_url, function (paper) {
            var pos = data.pos;
            // 加载已有区域
            renderPolygons(paper, pos.diagramPosition);
            
            // 加载右键菜单
            renderContextMenu(paper, pos.roomTypePosition);

            // 加载重叠时弹出提示信息
            renderPopMessage(paper);
           
            // 加载鼠标滑过时提示信息
            renderTipMessage();

            // 保存编辑
            $('#checkBtn').on('click', function () {
            	//图片地址
            	var picUrl1 = $("#images").val();
            	var len = picUrl1.length;
            	if(len==0){
            		alertNew("请上传平面图！");
            		return false;
            	}
            	//仓储名称
            	var position =$("#imageName").val();
                var res = getPolygonData(paper),
                    param = {
                        pos: res.data
                    };
                var len = param.pos;
                var x;
                var y;
                var name;
                var pid;
                var jsonstr = "";
                for(var i=0;i<len.length;i++){
                	var coord=len[i].coordinate;
                	pid=len[i].pid;//货位id
                	name=len[i].text;
                	jsonstr += "{" + "\"" + "Id" + "\""+ ":" + "\"" + (i+1) + "\"," + "\"" + "position_name" + "\""+ ":" + "\"" + name + "\"," + "\"" + "position_id" + "\""+ ":" + "\"" + pid + "\"," + "\"" + "coordinate" + "\""+ ":" + "[" ;
                	var str ="";
                	for(var k=0;k<coord.length;k++){
                		for(var z in coord[k]){
                			x = coord[k][0];
                			y = coord[k][1];
                		}
                		str += "[" + "\"" + x + "\"" + "," + "\"" + y + "\"],";
                	}
                	str = str.substring(0,str.lastIndexOf(','));
                	str = str + "]},";
                	jsonstr += str;
                }
                jsonstr = "[" + jsonstr.substring(0,jsonstr.lastIndexOf(',')) + "]";
                console.log(jsonstr);
                // 校验是否所有区域都已经设置部位
                if (res.err) {
                    return showMessage(res.err);
                }

                // 保存数据
               $.ajax({
                    type: 'post',
                    url: "/stock/manage/addPicUrl",
                    data: {"pointCoordinate":jsonstr,"position":position,"picUrl1":picUrl1,"parentId":0},
                    success: function (data) {
                    	alertNew(data.msg,true);
                    	setTimeout(function(){
    			       		api.close();
    			       		W.location.reload();
    			       		exit();
                    	},2000)
                        
                    }
                });
            });
        });
        // 取消编辑
        $('#btn_cancel').on('click', exit);
    }


    /*
     ********************
     * 加载画布
     ********************
     */

    function renderPaper(url, callback) {
    	
    	$wrapper = $('#drawWrapper');
        var img = new Image(),        
            w =  $wrapper.width(),
            h = $wrapper.height();

        img.onload = function () {
           /* var p = img.width / img.height;

            w / h < p ? (h = w / p) : (w = h * p);

            zoom = w / img.width;
            img.width = w;
            img.height = h;
            h = h < 450 ? 450 : h;
            $('#drawPaper').height(h);
            $wrapper.width(w).height(450).append(img);*/
        	$wrapper.width('1500px').height('762px');
            img.width = $wrapper.width();
            img.height = $wrapper.height();
            $wrapper.append(img);
            callback(new Paper('drawPaper'));
        };
        
        img.onerror = function () {
            showMessage('加载仓库平面图失败，请稍后再试');
        };

        img.style.float = 'left';
        img.src = url;
    }


    // 加载已有区域
    function renderPolygons(paper, items) {
    	
        if (items != null && items != undefined){
        	
            $.each(items, function (i, v) {
            	
                var c = v.coordinate,
                    mx, my,
                    el, p = [];
                if (c.x && $.isNumeric(c.x)) {

                    // 转换数据格式
                    c.width = parseFloat(c.width);
                    c.height = parseFloat(c.height);

                    // 小于10px的区域不再绘制
                    if (c.width < 10 || c.height < 10) {
                        return;
                    }

                    mx = parseFloat(c.x) + c.width;
                    my = parseFloat(c.y) + c.height;
                    c = [
                        [c.x, c.y],
                        [mx, c.y],
                        [mx, my],
                        [c.x, my]
                    ];
                }

                if (!c || !c.length) {
                    return;
                }

                $.each(c, function (i, v) {
                    p.push([v[0] * zoom, v[1] * zoom]);
                });
                
                el = paper.polygon(p, true);
                el.uid = v.Id;
                el.pid = v.position_id;
                el.node.setAttribute('data-tips', v.position_name);
                paper.style(selectedStyle, el);
                $(el.node).click(function(){
                	//货位id
                	var stockId = el.pid;
                	$.dialog({
    					title: '货位信息',
    					width: 700,
    					height:400,
    					lock : true,
    					content : 'url:/stock/manage/stockMessage?id='+stockId,
    				});
                })
            });

            paper.blur();
        }
    }


    // 加载右键菜单
    function renderContextMenu(paper, items) {
    	
        var $menu = $('#contextMenu'),
            code = createContextMenuCode(items),
            stopPropagation = true,
            actPolygon = null,
            maxY = 0;

        // 监听弹出右键菜单事件
        paper.on('showmenu', function (m) {
        	
            actPolygon = paper.activePolygon;
            
            var pid = parseInt(actPolygon.pid),
                top = maxY < m.y ? maxY : m.y;
            
            $menu.find('.ht-li').each(function (i, el) {
                var $el = $(el),
                    id = $el.data('id');
                
                id === pid ? $el.addClass('act') : $el.removeClass('act');
                
            });
            
            $menu.css({left: m.x, top: top}).show();
        });

        // 退出右键菜单
        paper.on('hidemenu', function () {
            //actPolygon = null;
            $menu.hide();
        });


        // 设置菜单样式
        $wrapper.css('position', 'relative');
        $menu
            .css('position', 'absolute')
            .html(code)
            .on('contextmenu', function (e) {
                e.preventDefault();
            })
            .on('mousedown', function (e) {
                stopPropagation && e.stopPropagation();
                stopPropagation = true;
            })
            .on('mousedown', '.ht-remove', function () {
                actPolygon && paper.removePolygon(actPolygon);
                stopPropagation = false;
            })
            .on('mousedown', '.ht-li', function () {
            	var api = frameElement.api,W=api.opener;
            	//显示库位信息
            	W.$.dialog({
					title: '关联货位',
					width: 700,
					height:400,
					lock : true,
					content : 'url:/stock/manage/toaddLocation',
					button : [
						{
							name : '保存',
							callback : function() {
								var iframe = this.iframe.contentWindow;
								var saveBtn = $(iframe.document.body).find('#btnn');
								var id = $(iframe.document.body).find('#parentId').val();
								var name = $(iframe.document.body).find('#parentIdShow').val();
								if(id.length==0 && name.length==0){
									alertNew("请选择货位！");
									return false;
								}
								if (actPolygon) {
				                    actPolygon.node.setAttribute('data-tips', name);
				                    actPolygon.node.setAttribute('data-pid', id);
				                    paper.style(selectedStyle, actPolygon);
				                }
								stopPropagation = false;
								iframe.closeModel();
								return false;
							},
							focus : true
						}, {
							name : '取消'
					}]
				});
            });

        maxY = $wrapper.height() - $menu.height();
        return $menu;
    }

    // 生成菜单内容
    function createContextMenuCode(items) {
       /* var id = getQueryStringByName("id");
        var projectid = getQueryStringByName("projectid");
        var html = '<h4 class="ht-tit">选择部位</h4>';
        $.get("/StandardCom/GetPlaceMyStand", { projectid: projectid }, function (data) {
            if (data != '') {                
                for (var i = 0; i < data.length; i++) {
                    if (_placeMyStands == null || _placeMyStands.indexOf(data[i].id + '') != -1 || _placeMyStands == data[i].id) {
                        html += '<li class="ht-li" data-id="' + data[i].id + '">' + data[i].name + '</li>';
                    }
                }
                html += '</ul><h4 class="ht-tit ht-remove"> 删除</h4>';
                $("#contextMenu").html(html);
            }
        })*/
        var html = '<h4 class="ht-tit">选择部位</h4>';
        var html = '<h4 class="ht-li" data-id="0">关联货位</h4>';
        html += '</ul><h4 class="ht-tit ht-remove"> 删除</h4>';
        $("#contextMenu").html(html);
    }


    // 加载重叠错误提示
    function renderPopMessage(paper) {
        var $pop = $('#popMessage'),
            timeStamp = null;

        paper.on('cross', function () {

            // 显示提示
            $pop.fadeIn(500);

            // 3秒后自动隐藏
            timeStamp && clearTimeout(timeStamp);
            timeStamp = setTimeout(function () {
                $pop.fadeOut(300);
            }, 3000);
        });
    }


    // 加载鼠标滑过时提示
    function renderTipMessage() {
    	
        var $tipMessage = $('#tipMessage').hide();
        
        // 监听鼠标移入事件
        $wrapper.on('mousemove', '[data-tips]', function (e) {
        	
            var title = this.getAttribute('data-tips'),
                rect, x, y;
            
            if (title) {
            	
                // 获取位置信息
                rect = $wrapper.offset();
                x = e.clientX - rect.left;
                y = e.clientY - rect.top;
                
                $tipMessage
                    .text(title)
                    .css({'left': x + 5, 'top': y})
                    .show();
            }
        });

        // 监听鼠标移出事件
        $wrapper.on('mouseleave', '[data-tips]', function () {
            $tipMessage.hide();
        });
    }


    // 获取区域数据
    function getPolygonData(paper) {
        var data = [],
            err = null;

        paper.each(function (points) {
        	
            if (!$(this.node).data('pid')) {
                err = '请在红框内鼠标右键选择部位';
                return false;
            }

            var p = [];

            $.each(points, function (i, v) {
                var x = Math.round(100 * v[0] / zoom) / 100,
                    y = Math.round(100 * v[1] / zoom) / 100;

                p.push([x, y]);
            });

            data.push({               
                pid: $(this.node).data('pid'),
                text: $(this.node).data('tips'),
                coordinate: p,
            });
        });

        return {
            err: err,
            data: data
        };
    }


    // 关闭窗口
    function exit() {
        art.dialog.close();
    }

    /*
     ********************
     * 工具方法
     ********************
     */

    // 提示信息
    function showMessage(message, isNormal) {        
        alert(message);
    }
