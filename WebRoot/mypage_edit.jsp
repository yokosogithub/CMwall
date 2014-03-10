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
		String currentID = (String) request.getParameter("currentID");
	%>
	
	<body style="font-family:'Microsoft YaHei';text-align:center">
       <div class="container">
       	  <div class="row" style="margin-top:1em">
       	  	<header>
       	  	    <div class="row">
       	  	    	<div class="col-xs-12" style="padding:0;text-align:center">
       	  	    		<img id="img_icon" src="" alt="头像" class="img-circle img-responsive" style="margin:auto;height:150px;width:150px;box-shadow: 0px 0px 2px 6px rgba(255, 255, 255, 0.7),0px 0px 30px 2px rgba(0, 0, 0, 0.25);">   
 					      <!-- 更换现有头像属性头像属性 -->
       	  	        </div>
       	  	    </div>
       	  	 	<div class="row">
       	  	 		<div class="col-xs-2"></div>
       	  	 		<div class="col-xs-8" style="margin-top:0.6em">
       	  	 		    <div class="input-group" style="margin-top:1em">
							<span class="input-group-addon" id="sex_icon" style="color:#d9534f"><span class="glyphicon glyphicon-user">昵称</span></span>
							<input id="input_nickname" type="text" class="form-control" size="15" value="unknown"></input>
						</div> 
                	</div>
                	<div class="col-xs-2"></div>	
       	  	 	</div> 
       	  	</header>
			<section style="margin-top:2em">
				<div class="row" id="wantTo">
					<div class="col-xs-6">
					  	<h4 style="text-align:right" class="yahei_font"><span class="label label-danger"><span class="glyphicon glyphicon-heart"></span>&nbsp;想和Ta</span></h4>
				 	</div>
				 	<div class="col-xs-6">
				 		<div class="btn-group" style="margin-top:3px;float:left">
						  <button id="select_now" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
						    请选择 <span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu pull-right" role="menu">
						    <li><a href="" onclick="$('#select_now').html('看电影 <span class=\'caret\'></span>');return false;">看电影</a></li>
						    <li><a href="" onclick="$('#select_now').html('吃美食 <span class=\'caret\'></span>');return false;">吃美食</a></li>
						    <li><a href="" onclick="$('#select_now').html('逛街 <span class=\'caret\'></span>');return false;">逛街</a></li>
						    <li><a href="" onclick="$('#select_now').html('上自习 <span class=\'caret\'></span>');return false;">上自习</a></li>
						    <li><a href="" onclick="$('#select_now').html('约会 <span class=\'caret\'></span>');return false;">约会</a></li>
						    <li class="divider"></li>
						  </ul>
						</div>
				 	</div>
				</div>
				<div class="row" id="school" style="margin-top:15px">
				    <div class="col-xs-1"></div>
					<div class="col-xs-10">
						<div class="input-group">
						  <span class="input-group-addon" style="color:#d9534f"><span class="glyphicon glyphicon-book"></span> 学校</span>
						  <input id="input_school" type="text" class="form-control" placeholder="你的大学"></input>
						</div>
						<div class="input-group" style="margin-top:1em">
							<span class="input-group-addon" style="color:#d9534f"><span class="glyphicon glyphicon-comment"></span> 说说</span>
							<textarea  id="textarea_shuoshuo" class="form-control" rows="3" placeholder="你的个性签名"></textarea>
						</div>
						<div class="input-group" style="margin-top:1em">
							<span class="input-group-addon" style="color:#d9534f">微信号</span>
							<input  id="input_weixinNum" type="text" class="form-control" placeholder="勾搭必备"></input>
						</div>
					</div>
					<div class="col-xs-1"></div>
				</div>
			</section>
			<footer class="row" style="margin-top:2em">
				<div class="col-xs-6">
					<button type="button" id="btn_cancel" class="btn btn-warning" style="float:right">返回</button>
				</div>
				<div class="col-xs-6">
					<button type="button" id="btn_save" class="btn btn-success" style="float:left">保存</button>
				</div>
			</footer>
       	  </div>
       </div>
       
       <script src="https://code.jquery.com/jquery.js"></script>   <!-- import JQuery library -->
	   <script src="js/bootstrap.min.js"></script>
	   		
	   <script>
	   		
	   		// 首先获取个人资料
			$.getJSON("http://2.cmwechatweb.duapp.com/QueryUserInfo",{ currentID:<%=currentID%> },function(data){
				if( data == 'error' ){
					alert("获取资料失败");
				}else{
					$.each(data,function(idx,item){
						$('#img_icon').attr("src",item.iconURL);
						document.getElementById('input_nickname').value = item.weixinID;
						document.getElementById('input_school').value = item.school;
						document.getElementById('textarea_shuoshuo').innerHTML = item.shuoshuo;
						document.getElementById('input_weixinNum').value = item.weixinNum;
						var sex_color;
						if( item.sex == "1" ){
							sex_color = "#66CCFF";
						}else{
							sex_color = "#FFCCFF";
						}
						document.getElementById('sex_icon').style.color = sex_color;
					});
				}
			});
			
			
			// 保存按钮点击事件
			$('#btn_save').click(function(){
				// 首先获取各个元素的内容
				var weixinID = $.trim( $('#input_nickname').val() );
				var hobbytext = $.trim( $('#select_now').text() );
				var hobby = 0;
				if( hobbytext == "请选择" ){
					hobby = 0;
				}else if( hobbytext == "看电影" ){
					hobby = 1;
				}else if( hobbytext == "吃美食" ){
					hobby = 2;
				}else if( hobbytext == "逛街" ){
					hobby = 3;
				}else if( hobbytext == "上自习" ){
					hobby = 4;
				}else if( hobbytext == "约会" ){
					hobby = 5;
				}
				var school = $.trim( $('#input_school').val() );
				var shuoshuo = $.trim( $('#textarea_shuoshuo').val() );
				var weixinNum = $.trim( $('#input_weixinNum').val() );
				
				// 验证是否内容为空
				if( weixinID=="" ){
					alert('昵称不能为空');
					return;
				}
				if( school=="" ){
					school = '未填写';
				}
				if( shuoshuo=="" ){
					shuoshuo = '未填写';
				}
				if( weixinNum=="" ){
					weixinNum = '保密';
				}
				
	
				// 上传服务器保存
				$.post("http://2.cmwechatweb.duapp.com/UpdateUserInfo",{currentID:<%=currentID%>, weixinID:weixinID, hobby:hobby, school:school, shuoshuo:shuoshuo, weixinNum:weixinNum},function(data){
					if( data == 'error' ){
						alert('网络不给力噢');
					}else{
						alert('修改成功，请稍后查看');
						window.history.go(-1);
					}
	
				});
			});
			
			$('#btn_cancel').click(function(){
				window.history.go(-1);
			});
	   		
	   </script>
	   
	</body>	
</html>

