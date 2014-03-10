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
		String taOpenID = (String) request.getParameter("taOpenID");
		String ifSelf = (String) request.getParameter("ifSelf");
	%>
	
	<body style="font-family:'Microsoft YaHei';text-align:center">
		<textarea id='taOpenID' style='visibility:hidden;display:none' ><%=taOpenID%></textarea>
		<textarea id='ifSelf' style='visibility:hidden;display:none' ><%=ifSelf%></textarea>
		
       <div class="container">
       	  <div class="row" style="margin-top:1em">
       	  	<header>
       	  	    <div class="row">
       	  	    	<div class="col-xs-12" style="padding:0;text-align:center">
       	  	    		<img id="img_icon" src="" alt="头像" class="img-circle img-responsive" style="margin:auto;height:120px;width:120px;box-shadow: 0px 0px 2px 6px rgba(255, 255, 255, 0.7),0px 0px 30px 2px rgba(0, 0, 0, 0.25);">   
 					      <!-- 更换现有头像属性头像属性 -->
       	  	        </div>
       	  	    </div>
       	  	 	<div class="row">
       	  	 		<div class="col-xs-12" style="margin-top:0.6em">
						<h4  style="color:#9FB6CD"><span id="sex_icon" class="glyphicon glyphicon-user" style="color:#d9534f"></span>&nbsp;&nbsp;<span id="text_nickname"></span></input></h4>	 
                	</div>	
       	  	 	</div> 
       	  	</header>
			<section style="margin-top:2em">
				<div class="row" id="wantTo">
					<div class="col-xs-6">
					  	<h4 style="text-align:right"  class="yahei_font"><span class="label label-danger"><span  class="glyphicon glyphicon-heart"></span>&nbsp;想和Ta</span></h4>
				 	</div>
				 	<div class="col-xs-6">
				 		<div class="btn-group" style="float:left">
						  <h3 style="margin-top:0.25em"><span id="text_hobby" class="label label-primary"></span></h3>
						</div>
				 	</div>
				</div>
				<div class="row" id="school" style="margin-top:15px">
				    <div class="col-xs-1"></div>
					<div class="col-xs-10">
						<div class="input-group">
						  <span class="input-group-addon" style="color:#d9534f">学校<span class="glyphicon glyphicon-book"></span></span>
						  <h4 id="input_school" class="form-control"></h4>
						</div>
						<div class="input-group" style="margin-top:1em">
							<span class="input-group-addon" style="color:#d9534f">说说<span class="glyphicon glyphicon-comment"></span></span>
							<h4 id="input_shuoshuo" class="form-control"></h4>
						</div>
						<div class="input-group" style="margin-top:1em">
							<span class="input-group-addon" style="color:#d9534f">微信号</span>
							<h4 id="input_weixinNum" class="form-control"></h4>
						</div>
					</div>
					<div class="col-xs-1"></div>
				</div>
			</section>
			<footer class="row" style="margin-top:2em">
				<div class="col-xs-6">
					<button id="btn_back" type="button" class="btn btn-warning" style="float:right">返回</button>
				</div> 
				<div class="col-xs-6">
					<button id="btn_edit" type="button" class="btn btn-success" style="float:left">修改</button>
				</div>
			</footer>
       	  </div>
       </div>
       
       
       <!-- 头像原图弹出框- -->
		<div id="img_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			    <h4 class="modal-title" id="myModalLabel">预览</h4>
			  </div>
			  <div class="modal-body" style="padding-bottom:10px">
			     <img id="icon_modal" src="" class="img-responsive" style="border-radius:10px"></img>
			  </div>
			</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
       
       
		<script src="https://code.jquery.com/jquery.js"></script>   <!-- import JQuery library -->
		<script src="js/bootstrap.min.js"></script>
		
		<script>
			
			var iconURL;
			
			// 点击头像查看原图事件
			$("#img_icon").click(function(){
			    $('#img_modal').modal('show');
			    $('#icon_modal').attr("src",iconURL);
			});
			
			// 点击头像查看原图事件
			$("#btn_edit").click(function(){
			    var currentID = document.getElementById('taOpenID').innerHTML;
			    window.location.href="http://2.cmwechatweb.duapp.com/mypage_edit.jsp?currentID="+currentID+"";
			});
			
			
			$("#btn_back").click(function(){
			    window.history.go(-1);
			});
			
			
			// 首先获取个人资料
			$.getJSON("http://2.cmwechatweb.duapp.com/QueryUserInfo",{ currentID:<%=taOpenID%> },function(data){
				if( data == 'error' ){
					alert("获取资料失败");
				}else{
					$.each(data,function(idx,item){
						iconURL = item.iconURL
						$('#img_icon').attr("src",iconURL);
						document.getElementById('text_nickname').innerHTML = item.weixinID;
						document.getElementById('input_school').innerHTML = item.school;
						document.getElementById('input_shuoshuo').innerHTML = item.shuoshuo;
						document.getElementById('input_weixinNum').innerHTML = item.weixinNum;
						var textHobby = "没想好噢";
						if( item.hobby == "0" ){
							textHobby = "没想好噢";
						}else if( item.hobby == "1" ){
							textHobby = "看电影";
						}else if( item.hobby == "2" ){
							textHobby = "吃美食";
						}else if( item.hobby == "3" ){
							textHobby = "逛街";
						}else if( item.hobby == "4" ){
							textHobby = "上自习";
						}else if( item.hobby == "5" ){
							textHobby = "约会";
						}
						document.getElementById('text_hobby').innerHTML = textHobby;
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
			
			if ( document.getElementById('ifSelf').innerHTML == "false" ){
				document.getElementById('btn_edit').style.visibility = 'hidden';
			}
			
		
		</script>
       
	   
	</body>	
</html>


