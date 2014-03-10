<%@ page language="java" import="java.util.*,java.net.URLEncoder,java.util.ArrayList" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>厘米墙_我要发布主页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<base href="<%=basePath%>">
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	<link href="css/redactor.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="css/mypage_detail.css">
	<link rel="stylesheet" href="css/gradients.css">
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="js/json.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
	<%
		String currentID = (String)request.getParameter("currentID");
	%>
	
	<!-- 执行上传文件操作的函数 -->
        <script type="text/javascript"> 
        	// 图片上传
        	function fileupload(){  
	            if($("#filePath").val()==""){  
	                var picRandom=Math.floor(Math.random()*90+1);
					imgURL="http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/systemPicSmall%2F"+picRandom+".jpg";
					
					// 将imgURL与获取到的输入内容一起插入数据库
					var description =  $.trim( $('#editor1').text() );
					var typeText = $.trim( $('#select_now').text() );
					var type;
					if( typeText=="表白" ){
						type = 1;
					}else if( typeText=="许愿" ){
						type = 2;
					}else if( typeText=="留言" ){
						type = 3;
					}
					
					$.post("http://2.cmwechatweb.duapp.com/MyLuanchServlet",{currentID:<%=currentID%> , content:imgURL , description:description , type:type},function(data){
						if( data == 'error' ){
							alert("噢欧，网络不给力");
						}else{
							alert("发布成功");
							window.history.go(-1);
						}
					});
					
	            }else{
		            $.ajaxFileUpload({  
	                    url:"http://2.cmwechatweb.duapp.com/servlet/ImgUploadServlet",  
	                    secureuri:false,  
	                    fileElementId:'filePath',  
	                    dataType: 'text/html',             
	                    success: function (data, status) { 
	                    	// 返回的内容：formatError fileTooBig error 或 imgURL
	                    	var reg = /\<[^\>]*\>([^\<]*)\<[^\>]*\>/g;
	                    	var resultStr = data.replace(reg,function($0,$1){return $1;});
	                    	
	                    	var imgURL = "";
	                    	if( resultStr == "formatError" ){
			              		alert("文件格式错误");
			              		return;
			              	}else if( resultStr == "fileTooBig" ){
			              		alert("图片过大，请处理后再做上传");
			              		return;
			              	}else if( resultStr == "error" ){
			              		alert("图片上传失败");
			              		return;
			              	}else {
			              		imgURL = resultStr;
			              	}
			               	
			        		// 将imgURL与获取到的输入内容一起插入数据库
							var description =  $.trim( $('#editor1').text() );
							var typeText = $.trim( $('#select_now').text() );
							var type;
							if( typeText=="表白" ){
								type = 1;
							}else if( typeText=="许愿" ){
								type = 2;
							}else if( typeText=="留言" ){
								type = 3;
							}
							$.post("http://2.cmwechatweb.duapp.com/MyLuanchServlet",{currentID:<%=currentID%> , content:imgURL , description:description , type:type},function(data){
								if( data == 'error' ){
									alert("噢欧，网络不给力");
								}else{
									alert("发布成功");
									window.history.go(-1);
								}
							});
        		
	                    },
	                    error: function (data, status, e){ 
						    var reg = /\<[^\>]*\>([^\<]*)\<[^\>]*\>/g;
	                    	var resultStr = data.replace(reg,function($0,$1){return $1;});
	                    	
	                    	var imgURL = "";
	                    	if( resultStr == "formatError" ){
			              		alert("文件格式错误");
			              		return;
			              	}else if( resultStr == "fileTooBig" ){
			              		alert("图片过大，请处理后再做上传");
			              		return;
			              	}else if( resultStr == "error" ){
			              		alert("图片上传失败");
			              		return;
			              	}else {
			              		imgURL = resultStr;
			              	}
			               	
			        		// 将imgURL与获取到的输入内容一起插入数据库
							var description =  $.trim( $('#editor1').text() );
							var typeText = $.trim( $('#select_now').text() );
							var type;
							if( typeText=="表白" ){
								type = 1;
							}else if( typeText=="许愿" ){
								type = 2;
							}else if( typeText=="留言" ){
								type = 3;
							}
							$.post("http://2.cmwechatweb.duapp.com/MyLuanchServlet",{currentID:<%=currentID%> , content:imgURL , description:description , type:type},function(data){
								if( data == 'error' ){
									alert("噢欧，网络不给力");
								}else{
									alert("发布成功");
									window.history.go(-1);
								}
							});
	                    }  
		           });
		           
	           }  
	        } 
        
        	// 发布按钮点击事件函数
        	function luanch(){
        		// 首先验证输入的文本内容是否有效
        		var description = $.trim( $('#editor1').text() );
        		if( description == "" ){
        			alert("文字内容不能为空");
        			return;
        		}
        		
        		var typeText = $.trim( $('#select_now').text() );
        		if( typeText == "请选择" ){
        			alert("请选择类型");
        			return;
        		}
        		
        		fileupload();
        	}
        	
        	
        	
    	</script>  
</head>



	<body style="font-family:'Microsoft YaHei'">
	
	   <br>
	
	   <div class="container">
	   
	   	  <div id="content-edit" class="row">
	   	  	<div class="col-xs-2">
	   	  		<span class="glyphicon glyphicon-edit btn-lg" style="color:#330099"></span>
	   	  	</div>
	   	  	<div class="col-xs-10">
	   	  	  <div class="redactor_box">
	   	  		<ul class="redactor_toolbar" data-role="editor-toolbar" data-target="#editor1">
	   	  		   <li><a title="Bold" class="redactor_btn_bold" data-edit="bold" data-original-title="Bold (Ctrl/Cmd+B)"></a></li>
	   	  		   <li><a title="Italic" class="redactor_btn_italic" data-edit="italic" ></a></li>
	               <li class="redactor_separator"></li>
	               <li><a title="Alignment-left" class="redactor_btn_alignleft" data-edit="justifyleft"></a></li>
	               <li><a title="Alignment-center" class="redactor_btn_aligncenter" data-edit="justifycenter"></a></li>
	               <li><a title="Alignment-right" class="redactor_btn_alignright" data-edit="justifyright"></a></li>
	               <li class="redactor_separator"></li>
	               <li><a title="Delete" class="redactor_btn_deleted" data-edit="delete"></a></li>
	   	  		</ul>
	   	  		   <div id="editor1" class="redactor_commentRedactor redactor_editor" contenteditable="true" dir="ltr">
	   	  		   <p><br></p></div>
	   	  		   <textarea id="redactor_content" class="commentRedactor" name="add_comment" style="display: none;"></textarea></div>
	   	  	 </div>
	   	   </div>
	   	   
	   	   
	   	   <br><br>
	   	   
	   	   <div id="pic_upgrade" class="row">
	   	      <div class="col-xs-3"></div>
	   	   	  <div class="col-xs-9">
	   	   	    <form  name="form_uploadImg" action="" method="POST" enctype="multipart/form-data" style="margin:0 auto">  
					<input type="file" name="filePath" id="filePath" />  
                </form> 
	   	   	  </div>
	   	   </div>
	   	   <div class="row">
	   	   		<div class="col-xs-12" style="text-align:center"><font color="#8A2BE2" size="1px">*若未选择图片,将使用随机精美封面</font></div>	 
	   	   </div>
	   	   
	   	   <br>
	   	   
	   	   <div class="row" id="wantTo">
				<div class="col-xs-6">
				  	<h4 style="text-align:right" class="yahei_font"><span class="label label-primary"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;类型</span></h4>
			 	</div>
			 	<div class="col-xs-6">
			 		<div class="btn-group" style="margin-top:3px;float:left">
					  <button id="select_now" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					    请选择 <span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu pull-right" role="menu">
					    <li><a href="" onclick="$('#select_now').html('表白 <span class=\'caret\'></span>');return false;">表白</a></li>
					    <li><a href="" onclick="$('#select_now').html('许愿 <span class=\'caret\'></span>');return false;">许愿</a></li>
					    <li><a href="" onclick="$('#select_now').html('留言 <span class=\'caret\'></span>');return false;">留言</a></li>
					    <li class="divider"></li>
					  </ul>
					</div>
			 	</div>
		   </div>
	   	   
	   	   <br><br>
	   	   
	   	   <div class="row" style="margin-top:20px">
	   	   	  <div class="col-xs-6">
	   	   	  	 <button id="btn_back" type="button" style="float:right" class="btn btn-warning">返回</button>
	   	   	  </div>
	   	   	  <div class="col-xs-6">
	   	   	  	 <input name="fileLoad" id="fileLoad"  type="button" class="btn btn-success" value="发布" onClick="luanch()"/>
	   	   	  </div>
	   	   </div>
	   	   
	   </div>
	   
	   
	   <script src="js/bootstrap-wysiwyg.js"></script>
	   <script src="js/jquery.hotkeys.js"></script>
	   <script>
	   
	   	  $('#btn_back').click(function(){
	   	  	window.history.go(-1);
	   	  });
	   						    
		  $(document).ready(function(){
		    $('#editor1').wysiwyg();
		  });
	      $('.glyphicon').hover(function(){
	        $(this).css("color","#CC0000");
	      });
	      $('.glyphicon').mouseleave(function(){
	        $(this).css("color","#666666");
	      });
	   </script>
	</body>
</html>