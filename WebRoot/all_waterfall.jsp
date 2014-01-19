<%@ page language="java" import="java.util.*,java.net.URLEncoder,java.util.ArrayList" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<title>厘米墙</title>
		<meta content="text/html; charset=utf-8" http-equiv="content-type">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name="description" content="list waterfall of reply" />
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
	    <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	    <link href="css/waterfall_style.css" rel="stylesheet"/>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script src="js/jquery.appear.js"></script>
		<script type="text/javascript" src="js/freewall.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script type='text/javascript'>
			$(function() {
			  $(document.body).on('appear', '.card', function(e, $affected) {
			    // add class called “appeared” for each appeared element
			    $(this).addClass("appeared");
			  });
			  $('.card').appear({force_process: true});
			});      /*google-plus-app-tile-animation js implement function*/
        </script>
	</head>
	
	
	<%
	// 获得当前用户（浏览人）的坐标
	String locationx = (String)request.getParameter("locationx");
	//locationx = java.net.URLDecoder.decode(locationx, "UTF-8");
	String locationy = (String)request.getParameter("locationy");
	//locationy = java.net.URLDecoder.decode(locationy, "UTF-8");
	// 获取当前用户的openid
	String currentID = (String)request.getParameter("currentID");
	//currentID = java.net.URLDecoder.decode(currentID, "UTF-8");
	%>
	
	
	<body style="font-family:'Microsoft YaHei'">
	    <div>
	        <img id='title_img' alt="title" width=100%  src="img/all_waterfall_title.jpg">
	        <button type="btn" id="my_luanch_btn" class="btn btn-warning" style="position:absolute;left:72%;top:0.3%;z-index:1">+我要发布</button>
		</div>
	
		<textarea id='currentID' style='visibility:hidden;display:none' ><%=currentID%></textarea>
		<textarea id='locationx' style='visibility:hidden;display:none' ><%=locationx%></textarea>
		<textarea id='locationy' style='visibility:hidden;display:none' ><%=locationy%></textarea>
		
		<div class="container">
				<div id="freewall" class="free-wall" style="margin-top:15px"></div>
				
				<footer>
				    <div class="row" style="margin-top:10px">
				    	<div class="col-xs-12"><button type="btn" id="loadmore_btn" class="btn btn-success" style="width:100%">加载更多</button></div>
				    </div>
				</footer>
				
				<div style="font-size:13px;color:#8E388E;text-align:center;text-shadow:none"  class="find_center">
		      		<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="80%" color=#987cb9 SIZE=3>
		         	<b>厘米</b>  &nbsp;&nbsp;那些年的欢乐与感动
       			</div><br>
		</div>
		
		
		
		<script type="text/javascript">
		
		// my_luanch_btn点击事件
		$('#my_luanch_btn').click(function(){
			var currentID = document.getElementById('currentID').value;
			var locationx = document.getElementById('locationx').value;
			var locationy = document.getElementById('locationy').value;
			
			window.location.href="http://2.cmwechatweb.duapp.com/my_luanch.jsp?currentID="+currentID+"&locationx="+locationx+"&locationy="+locationy+"";
		});
		
		
		// 封装img对象
		var imgArr = new Array();
		// 用户刷新表白的次数
		var time = 0;
		// 装载附近状态的json对象
		var jArr;
		
		
		// 加载更多
		$("#loadmore_btn").click(function(){
			$.each(jArr,function(idx,item){
			
				var top = time*10+10;
				if( time*10+10 >= jArr.length ){
					top = jArr.length;
				}
				
				if( idx < top && idx >= time*10 ){
				
					  var msgType = ""; 
  		  			  if( item.type == 1 ){
					  	  msgType = "表白";
  		  			  }else if( item.type == 2 ){
  		  			  	  msgType = "许愿";
  		  			  }else{
  		  			      msgType = "留言";
  		  			  }
				
	     	    	  if(item.content=="undefine") {
			  		      var picRandom=Math.floor(Math.random()*90+1);
				  		  item.content="http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/systemPicSmall%2F"+picRandom+".jpg";
			  		  }
			  		  
			  		  var sex_color;
			  		  if(item.sex==1){
				  		 sex_color="#66CCFF";
			  		  }
			  		  else{
				  		 sex_color="#FFCCFF";
			  		  }
			  		  
			  		  // 截取图片描述
			  		  var description;
			  		  if( item.description.replace(/[^\x00-\xff]/g,"a").length > 50 ){
			  		  	description = item.description.substr(0,50) + "...";
			  		  }else{
			  		  	description = item.description;
			  		  }
			  		  
			  		  var locationLabel;
			  		  if( item.locationLabel.replace(/[^\x00-\xff]/g,"a").length > 8 ){
			  		  	locationLabel = item.locationLabel.substr(0,8) + "..";
			  		  }else{
			  		  	locationLabel = item.locationLabel;
			  		  }
			  		  
		
		             var temp =""+
					"<div class='cell' id='mycell"+idx+"' style='color:#000000;padding:10px 10px 10px 10px;border: 2px solid #DDD;border-radius: 4px;width:180px;height:450px;background-"+    
					  "color:#FFFFFFF'>"+
					 "<div id='cell_inside"+idx+"'>"+
					 "<div class='row' style='padding-top:0px'>"+
		              	"<div class='col-xs-12' style='padding-right:0px'>"+
							"<h3 style='margin:0px;text-shadow:none;display:inline'><span class='glyphicon glyphicon-map-marker' style='color:#9932CC'></span>&nbsp;"+item.distance+ "<small>KM&nbsp;&nbsp;&nbsp;&nbsp;<font style='color:#9932CC'>"+msgType+"</font></small></h3>"+
					  	"</div>"+
						"<div class='col-xs-12' style='margin-top:0.3em'>"+
						"<span class=\"label label-primary\">"+locationLabel+"</span>"+
						"</div>"+
					  	"<div class='col-xs-12' style='margin-top:0.7em'>"+
					  	    "<small><span class='glyphicon glyphicon-comment' style='text-shadow:none;padding-left:3px;color:#66CCFF;float:right' ><a style='color:#AAAAAA;font-size:0.8em'>"+item.replyCount+"</a></span><span class='glyphicon glyphicon-heart' style='text-shadow:none;float:right;padding-right:3px;color:#FFCCFF' font ><a style='color:#AAAAAA;font-size:0.8em'>"+item.zanCount+"</a></span></small>"+
					  	"</div>"+
		             "</div>"+
		               "<div id='img_content' class='row'>"+
		                  "<div class='col-xs-12 card' style='text-align:center'><img alt='加载中...' src='http://desandro.github.io/imagesloaded/assets/loading.gif'  id='img"+idx+"' class='img-responsive' style='margin-top:10px;border-radius:5px;background-color: black;'></div>"+
		               "</div>"+
		               "<div class='row'>"+
		                  "<div class='col-xs-12' style='padding-right:0px'><h3 style='text-shadow:none;font-size:0.6em;padding:0px;color:#696969'><span style='color:"+sex_color+"' class='glyphicon glyphicon-user'></span>&nbsp;&nbsp;"+item.weixinID+"</h3></div>"+
		               "</div>"+
		               "<div class='row' style='margin-top:0px'>"+
		                  "<div class='col-xs-12'><p class='lead' style='font-size:0.7em;border:1.5px solid rgba(20, 11, 243, 0.3);text-shadow:none;text-align:center;padding:5px'>"+description+"</p></div>"+
		                  "<div class='col-xs-12' style='padding-right:0px;margin-bottom:3px;background: none repeat scroll 0 0"+sex_color+";"+
		    "border-top: 1px solid #DFDFDF;color: #999999;line-height: 25px;padding: 0 5px;position: relative;text-overflow: ellipsis;white-space: nowrap;border-radius:5px'><h6 style='font-size:0.5em;color:#FFFFFF;text-shadow:none;margin-bottom:0px;margin-top:0px;text-align:center;'>"+item.createTime+"</h6></div>"+
		               "</div>"+
		            "<div style='clear:both;display:none'></div>"+
		            "<button class='msgID' value='"+item.msgID+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='weixinID' value='"+item.weixinID+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='content' value='"+item.content+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='description' value='"+item.description+"' style='visibility:hidden;display:none'>a</button>"+ 
		            "<button class='createTime' value='"+item.createTime+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='distance' value='"+item.distance+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='iconURL' value='"+item.iconURL+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='zanCount' value='"+item.zanCount+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='sex2' value='"+item.sex+"' style='visibility:hidden;display:none'>a</button>"+
		            "<button class='taOpenID' value='"+item.openID+"' style='visibility:hidden;display:none'>a</button>"+
		             "</div>"+
					"</div>";
					
					  $('#freewall').append(temp);
					  
			  		  imgArr[idx] = new Image();
			  		  imgArr[idx].onload=function(){
						  var imgID = 'img'+idx;
						  $('#'+imgID).attr("src",this.src);
						  
						  $('#mycell'+idx).css("height", "auto" );
					  };
					  
					  imgArr[idx].onerror=function(){
					  	  var my_idx = idx+1;
			  		  	  $('#'+my_idx+'-1').css("height",170);
					  };
					    
					  imgArr[idx].src = item.content;
	  			}
  			
  			});
  			time++;
  			// 调整格式
			$(window).trigger("resize");
		});
		
		
		
		// 第一次进入页面，后台获取附近的表白，并只往页面插入10条
		$.getJSON("http://2.cmwechatweb.duapp.com/QueryAllServlet", { locationx:<%=locationx%>,locationy:<%=locationy%>,currentID:<%=currentID%>},function(data){
			// 成功获取附近的状态
			
			if(jQuery.isEmptyObject(data)){
	  			// 后期插入假数据
		  		alert("附近还没有同学发状态噢，快去成为第一个表白的人吧~");
	  		}else{
	  			jArr = data;
	  	      	time++;
	  	      	
	  	      	$.each(data,function(idx,item){
	  	      		// 第一次刷新页面只加载10个
	  		  		if(idx < 10){
	  		  			var msgType = ""; 
	  		  			if( item.type == 1 ){
	  		  				msgType = "表白";
	  		  			}else if( item.type == 2 ){
	  		  				msgType = "许愿";
	  		  			}else{
	  		  				msgType = "留言";
	  		  			}
	  		  			
	  		  			if(item.content=="undefine")
			  		  	{
				  		    var picRandom=Math.floor(Math.random()*90+1);
					  		item.content="http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/systemPicSmall%2F"+picRandom+".jpg";
			  		  	}
			  		    var sex_color;
			  		    if(item.sex==1){
				  			sex_color="#66CCFF";
			  		    }
			  		    else{
				  			sex_color="#FFCCFF";
			  		    }
			  		    
			  		    // 截取图片描述
			  		    var description;
			  		    if( item.description.replace(/[^\x00-\xff]/g,"a").length > 50 ){
			  		  		description = item.description.substr(0,50) + "...";
			  		    }else{
			  		  		description = item.description;
			  		    }
			  		    
			  		    var locationLabel;
			  		    if( item.locationLabel.replace(/[^\x00-\xff]/g,"a").length > 8 ){
			  		  		locationLabel = item.locationLabel.substr(0,8) + "..";
			  		    }else{
			  		  		locationLabel = item.locationLabel;
			  		    }
			  		    
			  		    var temp =""+
						"<div class='cell' id='mycell"+idx+"' style='color:#000000;padding:10px 10px 10px 10px;border: 2px solid #DDD;border-radius: 4px;width:180px;height:450px;background-"+    
						  "color:#FFFFFFF'>"+
						 "<div id='cell_inside"+idx+"'>"+
						 "<div class='row' style='padding-top:0px'>"+
			              	"<div class='col-xs-12' style='padding-right:0px'>"+
								"<h3 style='margin:0px;text-shadow:none;display:inline'><span class='glyphicon glyphicon-map-marker' style='color:#9932CC'></span>&nbsp;"+item.distance+"<small>KM&nbsp;&nbsp;&nbsp;&nbsp;<font style='color:#9932CC'>"+msgType+"</font></small> </h3>"+
						  	"</div>"+
						  	"<div class='col-xs-12' style='margin-top:0.3em'>"+
						  	"<span class=\"label label-primary\">"+locationLabel+"</span>"+
						  	"</div>"+
						  	"<div class='col-xs-12' style='margin-top:0.7em'>"+
						  	    "<small><span class='glyphicon glyphicon-comment' style='text-shadow:none;padding-left:3px;color:#66CCFF;float:right' ><a style='color:#AAAAAA;font-size:0.8em'>"+item.replyCount+"</a></span><span class='glyphicon glyphicon-heart' style='text-shadow:none;float:right;padding-right:3px;color:#FFCCFF' font ><a style='color:#AAAAAA;font-size:0.8em'>"+item.zanCount+"</a></span></small>"+
						  	"</div>"+
			             "</div>"+
			               "<div id='img_content' class='row'>"+
			                  "<div class='col-xs-12 card' style='text-align:center'><img alt='加载中...' src='http://desandro.github.io/imagesloaded/assets/loading.gif'  id='img"+idx+"' class='img-responsive' style='margin-top:10px;border-radius:5px;background-color: black;'></div>"+
			               "</div>"+
			               "<div class='row'>"+
			                  "<div class='col-xs-12' style='padding-right:0px'><h3 style='text-shadow:none;font-size:0.6em;padding:0px;color:#696969'><span style='color:"+sex_color+"' class='glyphicon glyphicon-user'></span>&nbsp;&nbsp;"+item.weixinID+"</h3></div>"+
			               "</div>"+
			               "<div class='row' style='margin-top:0px'>"+
			                  "<div class='col-xs-12'><p class='lead' style='font-size:0.7em;border:1.5px solid rgba(20, 11, 243, 0.3);text-shadow:none;text-align:center;padding:5px'>"+description+"</p></div>"+
			                  "<div class='col-xs-12' style='padding-right:0px;margin-bottom:3px;background: none repeat scroll 0 0"+sex_color+";"+
			    "border-top: 1px solid #DFDFDF;color: #999999;line-height: 25px;padding: 0 5px;position: relative;text-overflow: ellipsis;white-space: nowrap;border-radius:5px'><h6 style='font-size:0.5em;color:#FFFFFF;text-shadow:none;margin-bottom:0px;margin-top:0px;text-align:center;'>"+item.createTime+"</h6></div>"+
			               "</div>"+
			            "<div style='clear:both;display:none'></div>"+
			            "<button class='msgID' value='"+item.msgID+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='weixinID' value='"+item.weixinID+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='content' value='"+item.content+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='description' value='"+item.description+"' style='visibility:hidden;display:none'>a</button>"+ 
			            "<button class='createTime' value='"+item.createTime+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='distance' value='"+item.distance+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='iconURL' value='"+item.iconURL+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='zanCount' value='"+item.zanCount+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='sex2' value='"+item.sex+"' style='visibility:hidden;display:none'>a</button>"+
			            "<button class='taOpenID' value='"+item.openID+"' style='visibility:hidden;display:none'>a</button>"+
			             "</div>"+
						"</div>";
						// dom加入网页
						$('#freewall').append(temp);
						
						// 加载图片，并且绑定点击事件
						imgArr[idx] = new Image();
				  		imgArr[idx].onload=function(){
							var imgID = 'img'+idx;
							$('#'+imgID).attr("src",this.src);
				  		  	$('#mycell'+idx).css("height", "auto" );
						};
						imgArr[idx].onerror=function(){
						  	var my_idx = idx+1;
				  		  	$('#'+my_idx+'-1').css("height",170);
						};  
						imgArr[idx].src = item.content;
						
	  		  		}
	  	      	});
	  	      	// 调整格式
				$(window).trigger("resize");
	  		}
		});
		
		
		// freewall 调整格式
		var ewall = new freewall("#freewall");
		ewall.reset({
			selector: '.cell',
			animate: true,
			cell: {
				width: 180,
				height: 20
			},
			onResize: function() {
				ewall.fitWidth();
			},
		});
		ewall.fitWidth();
		// for scroll bar appear;
	 	$(window).trigger("resize");
		
		
		// 页面加载完成后，为每个cell绑定点击事件
	 	$(document).ready(function(){
	 		$(".cell").live("click",function(){
	     		$(this).css("background","#BFEFFF");
	     		// 传参列表 currentID msgID weixinID content description createTime distance iconURL
		     	var currentID = document.getElementById('currentID').value;
		     	var msgID = $(this).find('.msgID').val();
		     	var weixinID = $(this).find('.weixinID').val();
		     	var content = $(this).find('.content').val(); 
		     	var description = $(this).find('.description').val();
		     	var createTime = $(this).find('.createTime').val();
		     	var distance = $(this).find('.distance').val();
		     	var iconURL = $(this).find('.iconURL').val();
		     	var sex = $(this).find('.sex2').val();
		     	var taOpenID = $(this).find('.taOpenID').val();
		     	
		     	// 跳转至msg_detail.jsp
		    	window.location.href="http://2.cmwechatweb.duapp.com/msg_detail.jsp?taOpenID="+taOpenID+"&sex="+sex+"&currentID="+currentID+"&msgID="+msgID+"&weixinID="+weixinID+"&content="+content+"&description="+description+"&createTime="+createTime+"&distance="+distance+"&iconURL="+iconURL+" ";
	 		});
	 	});
		
		</script>
		
		
	</body>
</html>