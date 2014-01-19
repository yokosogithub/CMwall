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
      <title>问答发布</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">    
	  <meta http-equiv="keywords" content="厘米墙,大学生表白,附近的人">
	  <meta http-equiv="description" content="厘米墙">
	  <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
	  <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	  <link rel="stylesheet" href="css/mypage_detail.css">
	</head>
	
	
	<%
	// 获得当前用户（浏览人）的坐标
	String locationx = (String)request.getParameter("locationx");
	String locationy = (String)request.getParameter("locationy");
	// 获取当前用户的openid
	String currentID = (String)request.getParameter("currentID");
	%>
	
	
	<body style="font-family:'Microsoft YaHei';text-align:center">
		<textarea id='currentID' style='visibility:hidden;display:none' ><%=currentID%></textarea>
		<textarea id='locationx' style='visibility:hidden;display:none' ><%=locationx%></textarea>
		<textarea id='locationy' style='visibility:hidden;display:none' ><%=locationy%></textarea>
       <div class="container">
       	  <div class="row">
			<section style="margin-top:2em">
				<div class="row" id="school" style="margin-top:15px">
				    <div class="col-xs-1"></div>
					<div class="col-xs-10">
						<div class="input-group">
						  <span class="input-group-addon" style="color:#0099FF"><span class="glyphicon glyphicon-pencil"></span></span>
						  <textarea id='ta_content' class="form-control" rows="4" placeholder="输入话题内容"></textarea>
						</div>
						<div class="input-group" style="margin-top:1em">
							<span class="input-group-addon" style="color:#0099FF"><span>1</span></span>
							<input id='ta_ans1' type="text" class="form-control" placeholder="投票项">
						</div>
						<div class="input-group" style="margin-top:0.5em">
							<span class="input-group-addon" style="color:#0099FF"><span>2</span></span>
							<input id='ta_ans2' type="text" class="form-control" placeholder="">
						</div>
						<div class="input-group" style="margin-top:0.5em">
							<span class="input-group-addon" style="color:#0099FF"><span>3</span></span>
							<input id='ta_ans3' type="text" class="form-control" placeholder="">
						</div>
						<div class="input-group" style="margin-top:0.5em">
							<span class="input-group-addon" style="color:#0099FF"><span>4</span></span>
							<input id='ta_ans4' type="text" class="form-control" placeholder="">
						</div>
						<div class="input-group" style="margin-top:0.5em">
							<span class="input-group-addon" style="color:#0099FF"><span>5</span></span>
							<input id='ta_ans5' type="text" class="form-control" placeholder="">
						</div>
						<div style="margin-top:0.5em;float:left">
							<span class="label label-info">*以上备选答案将作为投票项供附近的同学投票,最多5项</span>
						</div>
					</div>
					<div class="col-xs-1"></div>
				</div>
			</section>
			<footer class="row" style="margin-top:2em">
				<div class="col-xs-6">
					<button id='btn_cancel' type="button" class="btn btn-warning" style="float:right">取消</button>
				</div>
				<div class="col-xs-6">
					<button id='btn_submit' type="button" class="btn btn-success" style="float:left">发布</button>
				</div>
			</footer><br>
       	  </div>
       </div>
       
	</body>
	
	
	<script src="https://code.jquery.com/jquery.js"></script>   <!-- import JQuery library -->
	<script src="js/bootstrap.min.js"></script>
	<script>
		$('#btn_cancel').click(function(){
			window.history.go(-1);
		});
	
		// 点击发布
		$('#btn_submit').click(function(){
			var locationx = document.getElementById('locationx').value;
	 		var locationy = document.getElementById('locationy').value;
			var currentID = document.getElementById('currentID').value;
			var content = $.trim( $('#ta_content').val() );
			var ansArr = new Array();
			ansArr[0] = $.trim( $('#ta_ans1').val() );
			ansArr[1] = $.trim( $('#ta_ans2').val() );
			ansArr[2] = $.trim( $('#ta_ans3').val() );
			ansArr[3] = $.trim( $('#ta_ans4').val() );
			ansArr[4] = $.trim( $('#ta_ans5').val() );
			
			if( content == "" ){
				alert("话题内容不能为空噢");
				return;
			}
			var answers = "";
			for( var i = 0; i < 5; i++ ){
				if( ansArr[i] == ""){
					continue;
				}else{
					answers = answers + ansArr[i] + "sptTk";
				}
			}
		
 			// 发布话题
			$.post("http://2.cmwechatweb.duapp.com/TopicLuanchServlet",{currentID:<%=currentID%>,content:content,answers:answers},function(data){
				if( data == 'error' ){
					alert('噢欧，网络错误');
				}else{
					alert('发布成功');
					window.location.href="http://2.cmwechatweb.duapp.com/topic_waterfall.jsp?locationx="+locationx+"&locationy="+locationy+"&currentID="+currentID;
				}
			});
		});
		
	</script>
		
</html>


