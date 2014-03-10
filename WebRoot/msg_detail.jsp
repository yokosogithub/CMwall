<%@ page language="java" import="java.util.*,java.net.URLEncoder" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
	<head>
	  <meta charset="UTF-8" />
	  <base href="<%=basePath%>">
      <title>厘米墙 - 那些年的青涩与感动</title> 
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
	  <meta http-equiv="keywords" content="厘米墙,大学生表白,附近的人">
	  <meta http-equiv="description" content="厘米墙">
	  <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
	  <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	  <link href="css/cmwall.css" rel="stylesheet" type="text/css">
	  <link href="css/widgets.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	  <script src="js/bootstrap.min.js"></script> 
	</head>
	
	<%
	String taOpenID = (String) request.getParameter("taOpenID");
	String sex = (String) request.getParameter("sex");
	String sex_color;
	if(sex.equals("1")){
		sex_color = "#66CCFF";
	}else{
		sex_color = "#FFCCFF";
	}
	// 当前用户（浏览人）openid
	String currentID = (String) request.getParameter("currentID");
	// 该状态的状态ID
	String msgID = (String) request.getParameter("msgID");
	// 该状态的表白人昵称
	String weixinID = (String) request.getParameter("weixinID");
	// 该状态的封面图片url
	String content = (String) request.getParameter("content");
	// 该状态的文字内容
	String description = (String) request.getParameter("description");
	// 该状态的创建时间
	String createTime = (String) request.getParameter("createTime");
	// 该状态计算出来的与当前用户（浏览者）的距离
	String distance = (String) request.getParameter("distance");
	// 该状态发布人的头像
	String iconURL = (String) request.getParameter("iconURL");
	%>
	
	
	<body style="font-family:'Microsoft YaHei';text-align:center">
		<!-- 保存当前状态发送人的openid -->
		<textarea id='taOpenID' style='visibility:hidden;display:none' ><%=taOpenID%></textarea>
	
       <div class="container">
       	  <div class="row" style="margin-top:1em"> 
       	  	<header>
       	  	    <div class="row">
       	  	    	<div class="col-xs-12" style="padding:0;text-align:center">
       	  	    		<img id="head_icon" alt="http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Ficon_out_of_date.jpg" src=<%=iconURL%> class="img-circle img-responsive" style="width:150px;height:150px;margin:auto;box-shadow: 0px 0px 2px 6px rgba(255, 255, 255, 0.7),0px 0px 30px 2px rgba(0, 0, 0, 0.25);">
       	  	        </div>
       	  	        <i style='color:<%=sex_color%>;margin-top:20px' class='glyphicon glyphicon-user'  ></i>
       	  	    </div>
       	  	 	<div class="row">
       	  	 		<div class="col-xs-12">
                		<h3 id="btn_weixinID" style="margin-top:0px;text-align:center;padding:2px" class="btn btn-primary btn-lg"><%=weixinID%></h3>
                	</div>	
       	  	 	</div> 
       	  	</header>
			<section>
				<div id="distance" class="row">
					<div class="col-xs-12">
						<h3><span class="glyphicon glyphicon-map-marker" style="color:#9933CC"></span>&nbsp;&nbsp;&nbsp;<%=distance%> <small>KM</small></h3>
					</div>
				</div>
				<div id="content" class="row">
				    <div class="col-xs-1"></div>
					<div class="col-xs-10">
						<p class="lead" style="font-size:1.5em;border:2px solid rgba(20, 11, 243, 0.3)">“ <%=description%> ”</p>
					</div>
					<div class="col-xs-1"></div>
				</div>
				<div class="row" id="features">
					<div class="col-xs-5">
						<button type="button" class="btn btn-info" style="float:right;padding:2px" data-toggle="popover" data-placement="bottom" id="btn_back">
							<span class="glyphicon glyphicon-heart-empty" ><br>厘米墙</span>
						</button>
					</div>
					<div class="col-xs-2" style="padding-left:0;padding-right:0">
						<button type="button" class="btn btn-info" style="padding:2px" id="reply_btn">
							<span class="glyphicon glyphicon-comment" ><br>回复</span>
						</button>
					</div>
					<div class="col-xs-5">
						<button type="button" id="zan_btn" class="btn btn-info" style="float:left;padding:2px">
							<span class="glyphicon glyphicon-thumbs-up" ><br>赞(1)</span>
						</button>
					</div>
				</div>
			</section>
			
			<section id="reply" class="moredetail">
				<div class="row">
				    <div class="col-xs-1"></div>
					<div id="reply_maincontent" class="col-xs-10" style="margin-top:10px">
						<div id="reply_list" class="well widget">
							<!-- widget header -->
							<div class="widget-header">
								<h3 class="title" style="float:left;margin-top:5px">回复列表</h3>
							</div>
							<!-- ./ widget header -->
							<div class="row">
								<div class="col-xs-9" style="padding:0 0 0 10px">
									<input class="form-control"  type="text" id="your_reply_content" placeholder="说点什么吧:">
								</div>
								<div class="col-xs-3" style="padding:0 0 0 10px;margin-top:10px">
									<button id="father_reply" class="btn btn-warning btn-xs">发布</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-1"></div>
				</div>
			</section>
			
			<br><br>
			
       	  </div>
       </div>
       
       <div style="font-size:13px;color:#8E388E" class="find_center">
	   		<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="80%" color=#987cb9 SIZE=3>
	      	<b>厘米</b>  &nbsp;&nbsp;那些年的欢乐与感动
 		</div><br>
       
       <!--子回复（回复ta）的弹出框-->
	 	<div id="son_reply_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header" style="background-color:#34495E">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" style="text-align:left;color:#FFFFFF">回复Ta</h4>
			  </div>
			  <div class="modal-body" style="padding-bottom:10px">
			     <textarea class="form-control" id="son_reply_content" type="text" placeholder="内容："></textarea>
			  </div>
			  <div class="modal-footer">
			    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			    <button type="button" id="son_reply_fabu" class="btn btn-primary" data-dismiss="modal">发布</button>
			  </div>
			</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		
		<!-- 头像原图弹出框- -->
		<div id="img_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			    <h4 class="modal-title" id="myModalLabel">预览</h4>
			  </div>
			  <div class="modal-body" style="padding-bottom:10px">
			     <img src=<%=iconURL%> class="img-responsive" style="border-radius:10px"></img>
			  </div>
			</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		
<script>

	// 点击进入个人主页
	$('#btn_weixinID').click(function(){
		var taOpenID = $.trim( $('#taOpenID').val() );
		window.location.href="http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+taOpenID+"'&ifSelf=false";
	});
	

	$('#reply').hide();

	// 如果头像载入失败，将其换成，默认图片
    var img = document.getElementById('head_icon');
    // 头像载入成功后改变宽高比例
  	img.onload=function(){
	  	img.onload = null;
		//控制头像长与宽
	    $('#head_icon').css('width','40%');
	    var width=$('#head_icon').css('width');
		$('#head_icon').css("height",width); 
    };
	// 自定义首次错误标识。
  	img.firstError = true;
  	img.onerror = function()
  	{
    	if (this.firstError)
    	{
        	this.firstError = false;
        	this.src = "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Ficon_out_of_date.jpg";
    	}
    	else
    	{
        	this.alt = "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Ficon_out_of_date.jpg";
    	}
  	};
  	
  	
  	$(document).ready(function(){
  		// 查询赞的数量并更新前端
		$.post("http://2.cmwechatweb.duapp.com/QueryZanCountServlet",{ msgID:<%=msgID%> },function(data){
			if( data!="error" ){
		 		$('#zan_btn>span').html("<br>赞("+data+")");
		 	}
		});
		
		
		// 查询回复的数量并更新前端
	 	$.get("http://2.cmwechatweb.duapp.com/QueryReplyCountServlet",{msgID:<%=msgID%>},
	 	function(data){
		 	// 改变回复数量前端
		 	$('#reply_btn>span').html("<br>回复("+data+")");
	 	});
	 	
	 	
	 	// 点击显示回复按钮后，加载回复列表
	 	$('#reply_btn').one('click',function(){
		  $.getJSON("http://2.cmwechatweb.duapp.com/QueryReplyServlet", { msgID:<%=msgID%>, button_type: "show_reply" },
	  		function(data){  //加载success后的回调函数
	   		 $.each(data,function(idx,item) 
	   		 {
	   		   $('#reply_list').append("<div class='media media-feed'>"+
					"<div class='pull-left'>"+
						"<img class='media-object' src="+item.iconURL+">"+
						"<br>"+
						"<button type='submit' class='btn btn-success btn-xs' id='son_reply' style='margin-top:10px;margin-bottom:10px' value='"+item.replyID+"'>回复Ta</button>"+
					    "</div>"+
					    "<div class='media-body'>"+
						"<h4 class='media-heading text-left' style='color:#FF2408'>"+item.weixinID+"</h4>"+
						"<p class='excerpt text-left'>"+item.content+"</p>"+
						"<h6 style='float:right'>"+item.createTime+"</h6>"+
					"</div>"+
				"</div>");   //循环增加回复列表
	   		  });
	   		  son_reply_click();
	  		});
		});
		
		
		// 点击显示回复列表
		$('#reply_btn').click(function(){
			$('#reply').toggle(600);  //控制回复列表的收缩效果，speed=600ms
		});
		
		
		// 点击父回复
		$('#father_reply').click(function(){
			// 首先获取回复内容
			var replyContent = document.getElementById('your_reply_content').value;
			// 如果回复为空
			if( !replyContent ){
				alert("回复不能为空噢");
			}
			else{
				// 请求后台插入新回复
				$.post("http://2.cmwechatweb.duapp.com/AddReplyServlet",{msgID:<%=msgID%>, fatherReplyID:0, currentID:<%=currentID%>, content:replyContent },function(data){
					if(data=='error'){
						alert('网络不给力噢~');
					}else{
						// 数据库插入回复成功
						alert("回复成功");
	  					// 清空回复输入框
	  					document.getElementById('your_reply_content').value = '';
	  					
	  					// 插入成功后重新载入新回复
						$.getJSON("http://2.cmwechatweb.duapp.com/QueryReplyServlet",{msgID:<%=msgID%>, currentID:<%=currentID%>, button_type: "father_reply" },function(data){
							// 父回复成功后回复数量+1
		  					var count=$('#reply_btn>span').html().replace(/\D+/g,"");
		  					count++;
		  					$('#reply_btn>span').html("");
		  					$('#reply_btn>span').html("<br>回复("+count+")");
		  					
		   		 			$.each(data,function(idx,item) 
		   					{
		   						// 后台获取成功,重新加载新的回复至前端
		   		 				$('#reply_list').append("<div class='media media-feed'>"+
								"<div class='pull-left'>"+
								"<img class='media-object' src="+item.iconURL+">"+
								"<br>"+
								"<button type='submit' class='btn btn-success btn-xs' id='son_reply' style='margin-top:10px;margin-bottom:10px' value='"+item.replyID+"'>回复Ta</button>"+
								"</div>"+
								"<div class='media-body'>"+
								"<h4 class='media-heading text-left' style='color:#FF2408'>"+item.weixinID+"</h4>"+
								"<p class='excerpt text-left'>"+item.content+"</p>"+
								"<h6 style='float:right'>"+item.createTime+"</h6>"+
								"</div>"+
								"</div>");   //循环增加回复列表
							});
		   		            son_reply_click();
						});  
					}
				});
			}
		});
		
		
		
		// 绑定子回复点击事件方法
		function son_reply_click(){
			$('[id=son_reply]').bind("click",function(){
				// 先获取被点击的父回复的replyID
				var fatherReplyID;
				fatherReplyID=$(this).attr('value');
				// 弹出输入回复框
				$('#son_reply_modal').modal('toggle');
				// 将父回复的ID告知输入框的发布按钮
				document.getElementById('son_reply_fabu').value=fatherReplyID;
		    });
	    }
	
		
		
		// 点击子回复后弹出的回复框的发布按钮的点击事件
		$('#son_reply_fabu').click(function(){
			// 首先获取文字内容
			var sonReplyContent = document.getElementById('son_reply_content').value;
			// 获取被回复的父回复replyID
			var fatherReplyID = document.getElementById('son_reply_fabu').value;
			
			if( !sonReplyContent ){
				alert("回复不能为空");
			}else{
				
				// 插入一条子回复
				$.post("http://2.cmwechatweb.duapp.com/AddReplyServlet",{msgID:<%=msgID%>, fatherReplyID:fatherReplyID, currentID:<%=currentID%>, content:sonReplyContent},
				function(data1){
					if(data1=='error'){
						alert('网络不给力噢~');
					}else{
						// 数据库插入回复成功
						alert("回复成功");
						// 清空回复输入框
	  					document.getElementById('son_reply_content').value = '';
						
						// 插入成功后重新载入新回复
						$.getJSON("http://2.cmwechatweb.duapp.com/QueryReplyServlet", {msgID:<%=msgID%>, currentID:<%=currentID%>, button_type: "son_reply" },
		  				function(data){  
		  				   	
			  				// 子回复成功后回复数量+1
		  					var count=$('#reply_btn>span').html().replace(/\D+/g,"");
		  					count++;
		  					$('#reply_btn>span').html("");
		  					$('#reply_btn>span').html("<br>回复("+count+")");
		  					
		   		 			$.each(data,function(idx,item) 
		   					{
		   						// 后台获取成功,重新加载新的回复至前端
		   		 				$('#reply_list').append("<div class='media media-feed'>"+
								"<div class='pull-left'>"+
								"<img class='media-object' src="+item.iconURL+">"+
								"<br>"+
								"<button type='submit'  class='btn btn-success btn-xs' id='son_reply' style='margin-top:10px;margin-bottom:10px' value='"+item.replyID+"'>回复Ta</button>"+
								"</div>"+
								"<div class='media-body'>"+
								"<h4 class='media-heading text-left' style='color:#FF2408'>"+item.weixinID+"</h4>"+
								"<p class='excerpt text-left'>"+item.content+"</p>"+
								"<h6 style='float:right'>"+item.createTime+"</h6>"+						
								"</div>"+
								"</div>");   //循环增加回复列表 
						   });
						   
					   });
					}
				});
			}
		});
		
		
		
		// 点击返回的响应事件
		$('#btn_back').click(function(){
		    window.history.go(-1);
		});
		
		// 点击赞的响应事件
		$('#zan_btn').one("click",function(){
			$.post("http://2.cmwechatweb.duapp.com/DianZanServlet",{msgID:<%=msgID%>},function(data){
				if( data != "error" ){
					var count=$('#zan_btn>span').html().replace(/\D+/g,"");
			  		count++;
			  		$('#zan_btn>span').html("");
			  	    $('#zan_btn>span').html("<br>赞("+count+")");
					// 更新赞次数
				}else{
					alert("噢欧，网速不给力噢");
				}
				
			});
		});
		
		// 点击头像查看原图事件
		$("#head_icon").click(function(){
		   $('#img_modal').modal('show');
		});
	
  		
  	});

</script>
	