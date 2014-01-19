<%@ page language="java" import="java.util.*,java.net.URLEncoder,java.util.ArrayList" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8" />
		<base href="<%=basePath%>">
	    <title>问答详情</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="厘米墙,大学生表白,附近的人">
		<meta http-equiv="description" content="厘米墙">
		<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	</head>
	
	
	<%
		String currentID = (String)request.getParameter("currentID");
		String openID = (String)request.getParameter("openID");
		String topicID = (String)request.getParameter("topicID");
		String distance = (String)request.getParameter("distance");
		String content = (String)request.getParameter("content");
	%>
	
	
	<body style="font-family:'Microsoft YaHei';text-align:center">
		
		<textarea id='topic_openID' style='visibility:hidden;display:none' ><%=openID%></textarea>
		<div class="container">
       		<header class="row" style="margin-top:2em">
       			<div class="col-xs-5">
       				<img id="img_headicon" src="" alt="头像" class="img-circle img-responsive" style="margin:auto;height:60px;width:60px;box-shadow: 0px 0px 2px 6px rgba(255, 255, 255, 0.7),0px 0px 30px 2px rgba(0, 0, 0, 0.25);float:right">
       			</div>
       			<div class="col-xs-7">
       				<div class="row">
       					<h4 id='weixinID_sex_span' value='' class="btn" style="text-align:center;padding:2px;float:left;margin-top:0.5em;background-color:#66CCFF;color:#FFFFFF"><span class="glyphicon glyphicon-user"></span>&nbsp;<span id='text_weixinID'></span></h4>
       				</div>
       				<div class="row">
       					<p  style="float:left;margin-top:5px">在 <%=distance%> <small>KM</small> 处问:</p>
       				</div>		
       			</div>
       		</header>
       	   <header class="row" style="margin-top:10px">
       			<div class="col-xs-12">
       				<h1 style='display:inline'>&nbsp;&nbsp;“</h1><p class='text-left' style='height:100px;display:inline'><%=content%></p><h1 style='display:inline'>”</h1>
       			</div>
       	   </header>
       	   <section class="row" style="margin-top:20px">
       	   		<div class="col-xs-12">
       	   			<ul id='ans_container' class="nav nav-pills nav-stacked">
						
					</ul>				
       	   		</div>
       	   </section>
       	   
       	   <input id='ip_replycontent' placeholder="期待你的答案" style="margin-top:40px"></input>
       	   <button class='btn btn-success' id='btn_reply_submit' style="margin-top:0px">发布</button>
       	   
       	   <section id='reply_container' class="row" style="margin-top:0px" >
				
       	   </section>
       	   
       	   <button id='btn_back' class='btn btn-warning' style='margin-top:30px' >返回</button>
       </div>
       
       <br>
       
	</body>
	
	
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>   <!-- import JQuery library -->
	<script>
		
		// 先查询话题发起人详细信息
		$.getJSON("http://2.cmwechatweb.duapp.com/QueryUserInfo",{currentID:<%=openID%>},function(data){
			if( data == 'error' ){
				$('#img_headicon').attr("src","http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/randomIcon%2F1.jpeg");
				document.getElementById('text_weixinID').innerHTML = "萱。";
				document.getElementById('weixinID_sex_span').style.backgroundColor = "#FFCCFF";
			}
			else{
				// 更新前端的该话题发起人的头像等信息
				$.each(data,function(idx,item){
					$('#img_headicon').attr("src",item.iconURL);
					document.getElementById('text_weixinID').innerHTML = item.weixinID;
					// 性别颜色
					var sex_color;
					if( item.sex == "1" ){
						sex_color = "#66CCFF";
					}else{
						sex_color = "#FFCCFF";
					}
					document.getElementById('weixinID_sex_span').style.backgroundColor = sex_color;
				});
			}
		});
		
		
		
		// 加载话题备选答案
		$.getJSON("http://2.cmwechatweb.duapp.com/QueryAnsServlet",{topicID:<%=topicID%>},function(data){
			if( data == 'error' ){
			}
			else{
				$.each(data,function(idx,item){
					// 循环添加备选答案
					var temp = 
					"<li id='ans_selection' class='ans_selection btn' style='border-radius:5px;padding:5px 10px;margin-top:10px;color:#FFFFFF;background-color:#0099CC;text-align:left'>"+
					 	"<span class='pull-right' style='background-color:#0099CC'><b id='text_voteCount'>+"+item.voteCount+" </b></span>"+
				        item.content+
				        "<button class='answerID' value='"+item.answerID+"' style='visibility:hidden;display:none'>a</button>"+
					"</li>";
					
					$('#ans_container').append(temp);
				});
			}
		});
		
		
		
		// 加载回复列表
		$.getJSON("http://2.cmwechatweb.duapp.com/QueryTopicReplyServlet",{topicID:<%=topicID%>},function(data){
			$.each(data,function(idx,item){
				var sex_color;
				if( item.sex == "1" ){
					sex_color = "#66CCFF";
				}else{
					sex_color = "#FFCCFF";
				}
				var i = idx+1;
				// 循环添加回复
				var temp = 
				"<div class='media media-feed' style='margin:1em;border:2px solid #E8E8E8;box-shadow:0 10px 10px 0 gray;padding:0.5em'>"+
					"<span class='pull-left'>"+
						"<img id='replylist_headicon' value="+item.openID+" class='media-object' src="+item.iconURL+" style='border-radius:5px;width:50px;height:50px'>"+
						"<small class='text-left' style='color:gray;float:left;margin-top:8px'>"+i+"楼</small>"+
					"</span>"+
					"<div class='media-body'>"+
						"<h5 class='media-heading text-left' style='color:"+sex_color+"'>"+item.weixinID+"</h5>"+
						"<p class='excerpt text-left' style='margin-bottom:0px'>"+item.content+"</p>"+
						"<h6 style='float:right;margin-top:1px;color:#CC6699'>"+item.createTime+"</h6>"+
					"</div>"+
				"</div>";
				
				$('#reply_container').append(temp);
			});
		});
		
		
		
		$(document).ready(function(){
			// 点击发布回复
			$('#btn_reply_submit').click(function(){
				var content = $.trim( $('#ip_replycontent').val() );
				if( content == "" ){
					alert("回复不能为空");
					return;
				}
				
				$.post("http://2.cmwechatweb.duapp.com/AddTopicReplyServlet",{currentID:<%=currentID%>,topicID:<%=topicID%>,content:content},function(data){
					if( data =='error' ){
						alert("噢欧，网络不给力");
					}else{
						alert("回复成功");
						history.go(0);
					}
				});
			});
		
			// 点击用户名进入用户详情页面
			$('#weixinID_sex_span').click(function(){
				var topicOpenID = document.getElementById('topic_openID').value;
				window.location.href="http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID="+topicOpenID+"&ifSelf=false";
			});
			
			// 点击答案进行投票
			$("#ans_selection").live("click",function(){
				var $this = $(this);
				
			    if (!$this.data('isClick')){
			        $this.data('isClick', 1);
			        // 得票数+1
					var count=$(this).find('#text_voteCount').html().replace(/\D+/g,"");
					count++;
					$(this).find('#text_voteCount').html("");
					$(this).find('#text_voteCount').html("+"+count+" ");
					
					// 数据库中对应投票数+1
					var answerID = $(this).find('.answerID').val();
					$.post("http://2.cmwechatweb.duapp.com/VoteAnsServlet",{answerID:answerID,topicID:<%=topicID%>},function(data){
						if( data == 'error' ){
						}else{
							
						}
					});
				}
			});
			
			
			
			// 点击回复列表头像查看个人主页
			$("#replylist_headicon").live("click",function(){
				var taOpenID = $(this).attr("value");
				window.location.href="http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+taOpenID+"'&ifSelf=false";
			});
			
			
			// 返回按钮
			$("#btn_back").click(function(){
				history.go(-1);
			});
		});
		
	
	</script>
	
	
</html>