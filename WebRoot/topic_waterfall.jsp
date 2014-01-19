<%@ page language="java" import="java.util.*,java.net.URLEncoder,java.util.ArrayList" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<title>话题墙</title>
		<meta content="text/html; charset=utf-8" http-equiv="content-type">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name="description" content="list waterfall of reply" />
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<link rel="stylesheet" href="css/bootstrap-theme.min.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</head>
	
	
	<%
	// 获得当前用户（浏览人）的坐标
	String locationx = (String)request.getParameter("locationx");
	String locationy = (String)request.getParameter("locationy");
	// 获取当前用户的openid
	String currentID = (String)request.getParameter("currentID");
	%>
	
	
	<body style="font-family:'Microsoft YaHei'">
		<textarea id='currentID' style='visibility:hidden;display:none' ><%=currentID%></textarea>
		<textarea id='locationx' style='visibility:hidden;display:none' ><%=locationx%></textarea>
		<textarea id='locationy' style='visibility:hidden;display:none' ><%=locationy%></textarea>
		
		<h5 style='display:inline;margin-top:5px;margin:10px 10px 10px 10px'>趣味话题，生活困扰，有问必答</h5>
		<button style='margin:10px 10px 0px 0px;border-radius:20px;float:right' class='btn btn-success' id='btn_topic_luanch'>我要提问</button>
		
		<div id='topic_container' style='margin-top:50px'>	
			
		</div> 
	
		<!-- <script type="text/javascript" src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script> -->
		<script type="text/javascript">
			// 查询附近所有的话题
			$.getJSON("http://2.cmwechatweb.duapp.com/QueryTopicServlet",{locationx:<%=locationx%>,locationy:<%=locationy%>},function(data){
				if(jQuery.isEmptyObject(data)){
		  			alert("附近还没有话题噢，快去发起一个话题吧(例：附近的美食、校园最美一角投票、XX课选哪个老师比较好呢)");
	  			}else{
	  				$.each(data,function(idx,item){
	  					var sex_color;
			  		    if(item.sex==1){
				  			sex_color="#66CCFF";
			  		    }
			  		    else{
				  			sex_color="#FFCCFF";
			  		    }
	  				
	  					var temp = 
						"<div style=\"font-family:'Microsoft Yahei';border:1.5px solid gray;margin:10px 10px 10px 10px;border-radius:10px\">" +
							"<button class='btn_topic_title row btn-primary' style='margin:0;border-radius:10px 10px 0 0;width:100%'>"+
								"<div class='col-xs-12'>"+
								    "<h4 style='float:left;padding-left:0px'><span class='glyphicon glyphicon-map-marker' style='color:#9932CC'></span>&nbsp;"+item.distance+"<small style='color:white'> KM</small></h4>"+
									"<h4 style='float:left;padding-left:20px'><img src='img/glyphicons_022_fire2.png' style='width:1em;height:1em' ></img>&nbsp;"+item.heat+"℃</h4>"+
									"<span class='topicID' style='visibility:hidden;display:none'>"+item.topicID+"</span>"+
									"<span class='content' style='visibility:hidden;display:none'>"+item.content+"</span>"+
									"<span class='distance' style='visibility:hidden;display:none'>"+item.distance+"</span>"+
									"<span class='openID' style='visibility:hidden;display:none'>"+item.openID+"</span>"+
								"</div>"+
							"</button>"+
							"<section class='row' style='padding-top:15px;margin:0px 0px 0px 0px'>"+
							    "<div class='col-xs-12'>"+
							    	"<h1 style='display:inline'>&nbsp;&nbsp;“</h1><p class='text-left' style='border:0px solid purple;height:100px;display:inline'>"+item.content+"</p><h1 style='display:inline'>”</h1>"+
							    "</div>"+
							"</section>"+
							"<footer class='row'>"+
								"<div class='col-xs-7'>"+
									"<h4 style='float:left;padding-left:15px;padding-top:15px;font-size:0.7em'><span class='glyphicon glyphicon-user' style='color:"+sex_color+"'></span> "+item.weixinID+"</h4>"+
								"</div>"+
								"<div class='col-xs-5'>"+
									"<h5 style='margin-top:32px;float:right;color:gray;font-size:0.6em'>"+item.createTime+"</h5>"+
								"</div>"+
							"</footer>"+
						"</div>";
						
						
						$('#topic_container').append(temp);
	  				});
	  			}
			});
			
			
			
			
			// 页面加载完成后，绑定点击事件
	 		$(document).ready(function(){
	 			// 点击任意问答进入详情界面
	 			$(".btn_topic_title").live("click",function(){
	 				var topicID = $(this).find('span.topicID').html();
	 				var openID = $(this).find('span.openID').html();
	 				var content = $(this).find('span.content').html();
	 				var distance = $(this).find('span.distance').html();
	 				var currentID = document.getElementById('currentID').value;
	 				window.location.href="http://2.cmwechatweb.duapp.com/topic_detail.jsp?currentID="+currentID+"&openID='"+openID+"'&topicID="+topicID+"&distance="+distance+"&content="+content;
	 			});
	 			
	 			
	 			// 发布问答
	 			$('#btn_topic_luanch').click(function(){
	 				var currentID = document.getElementById('currentID').value;
	 				var locationx = document.getElementById('locationx').value;
	 				var locationy = document.getElementById('locationy').value;
	 				// 跳转至话题发布页面
	 				window.location.href="http://2.cmwechatweb.duapp.com/topic_submit.jsp?locationx="+locationx+"&locationy="+locationy+"&currentID="+currentID;
	 			});
	 		});
			
		</script>
		
	</body>
	
</html>