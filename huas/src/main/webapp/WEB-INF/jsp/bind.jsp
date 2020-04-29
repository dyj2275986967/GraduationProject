<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="z" uri="/huas/system"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>绑定操作</title>


<!--springboot改图标-->
<link href="${ctx }/res/img/favicon.ico" type="image/x-icon" rel="icon">
    <!-- Bootstrap -->
    <link href="${ctx }/res/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${ctx }/res/bootstrap/js/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="${ctx }/res/bootstrap/js/bootstrap.min.js"></script>

 <!-- 引入自定义的css -->
	 <link href="${ctx }/res/css/myCss.css" rel="stylesheet">
<!-- 引入验证码的css -->
	<link rel="stylesheet" type="text/css" href="${ctx }/res/css/verify.css">


<script>
$(function (){
	

    var emailPhonestand=false;
	　var emailStandard =/^([1-9][0-9]{4,}\@qq\.com)|((\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+)$/;
		var phoneStandard = /^1[3456789]\d{9}$/;

		
		$("#emailPhone").blur(function(){
				if (!this.value) {

					$("#emailPhoneTips").text("*请输入邮箱号或手机号");
					
					//输入的校验方式 不等于手机号格式并且不等于邮箱格式
			 	} else if (!phoneStandard.test(this.value)&&!emailStandard.test(this.value)) {
			    	$("#emailPhoneTips").html ("*请输入正确的邮箱号或者手机号");
				
				}else{
					
					emailPhonestand=true;
				}	
			
			
			
		}).focus(function (){
			
			$("#emailPhoneTips").text("");
			
			
		})
		
	//倒计时

	   var wait =60;
	    function time(o) {
	        if (wait == 0) {
	            o.removeAttribute("disabled");
	            o.value = "发送";
	            wait = 60;
	   //如果时间过完 销毁session里的验证码
		//发短信
		var check="";
		//邮箱或者手机
	   var emailPhone=$("#emailPhone").val()
		if(phoneStandard.test(emailPhone)){
			check="2";
		//发邮箱	
		}else{
			
			check="1";
		
		}
		
		 $.ajax({
			   //指定请求地址的url
			   url:"${ctx}/user/api/destroy",
		      //指定
			   type:"post",
			   data:"check="+check,
			  //预期服务器返回的数据类型
			   dateType:"text",
			   //服务器响应成功时候的回调函数
			   success:function(result){
	            
	           //    alert(result);
		    			
		    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
	 	    			alert(xhr);
		    			alert(textStatus);
		    			alert(err)
		    			
				   
			   }
			  	   
		   })
		

	  

	        } else {
	            o.setAttribute("disabled", true);
	            o.value = "重新获取(" + wait + ")";
	            wait--;
	            setTimeout(function() {
	                time(o);
	            }, 1000);
	        };
	    }
	    document.getElementById("btn").onclick = function() {
	      
	           var value=$("#emailPhone").val();
	 
    if(!value){
 	   $("#emailPhoneTips").html ("*请输入正确的邮箱号或者手机号"); 
    }
	          //置空验证码
	          $("#yzm").val("");
	        //如果校验成功
	        if(emailPhonestand){
	        	
	        	//发短信
	        	if(phoneStandard.test(value)){
	        		
	        		
	        		 $.ajax({
						   //指定请求地址的url
						   url:"${ctx}/user/api/sendPhone",
					      //指定
						   type:"post",
						   data:"phone="+value,
						  //预期服务器返回的数据类型
						   dateType:"text",
						   //服务器响应成功时候的回调函数
						   success:function(result){
			                 
			                //    alert(result);
					    			
					    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
				  	    			alert(xhr);
					    			alert(textStatus);
					    			alert(err)
					    			
							   
						   }
						  	   
					   })
	        		
	        
	        	//发邮箱	
	        	}else{
	        	
	        	
	        		 $.ajax({
						   //指定请求地址的url
						   url:"${ctx}/user/api/sendEmail",
					      //指定
						   type:"post",
						   data:"email="+value,
						  //预期服务器返回的数据类型
						   dateType:"text",
						   //服务器响应成功时候的回调函数
						   success:function(result){
			                 
			                //    alert(result);
					    			
					    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
				  	    			alert(xhr);
					    			alert(textStatus);
					    			alert(err)
					    			
							   
						   }
						  	   
					   })
					  
	        	
	        	}
	        	
	        	
	        	time(this);	
	        	
	        }

	          
	    };

	//登陆开始
	$("#yzBtn").click(function(){
		  //邮箱或者手机
		   var emailPhone=$("#emailPhone").val()
		   //验证码
		    var yzm=$("#yzm").val()
		var check="";
		  //手机
		if(phoneStandard.test(emailPhone)){
			check="2";
		//发邮箱	
		}else{
			
			check="1";
		
		}
	 
		 $.ajax({
			   //指定请求地址的url
			   url:"${ctx}/user/manager/bind/insert",
		      //指定
			   type:"post",
			   data:"check="+check+"&emailOrPhone="+emailPhone+"&yzm="+yzm,
			  //预期服务器返回的数据类型
			   dateType:"text",
			   //服务器响应成功时候的回调函数
			   success:function(result){
	                if(result=="登陆成功"){
	                	
	                	window.location = "${ctx}/user/manager/index";
	                }else{
	                	$("#emailOrPhoneTip").text("*"+result);
	                	
	                }
	           
		    			
		    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
	 	    			alert(xhr);
		    			alert(textStatus);
		    			alert(err)
		    			
				   
			   }
			  	   
		   })
		
		
		
		
	})

$("#back").click(function(){
	
	window.location.href="/user/manager/index"
})
	
	
	
	
})



</script>


  </head>
  <body>

<!-- 1，页眉部分 -->
 <header class="container-fluid">

   <!-- 第一行 -->
  <div class="row">
  
  
<!-- 导航栏 -->
 <div class="row">
<nav class="navbar  navbar-fixed-top navbar-inverse"
					role="navigation">
					<div class="container">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed"
								data-toggle="collapse" data-target="#navbar"
								aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">显示导航条</span> <span class="icon-bar"></span>
								<span class="icon-bar"></span> <span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${ctx }/student/msg/list">学生综合素质管理系统</a>
						</div>
						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse"
							id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li><a href="${ctx }/student/msg/list">信息管理</a></li>
								<li><a href="${ctx }/student/grade/list">成绩管理</a></li>
								<li><a href="${ctx }/student/AwardOrPunish/index">奖罚管理</a></li>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown" role="button" aria-haspopup="true"
									aria-expanded="false"> <img
										src="/image/${session_user.img}" height="22"
										width="22" class="img-circle "><span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="${ctx }/user/manager/index">个人中心</a></li>
										<li role="separator" class="divider"></li>
										<li><a  href="${ctx }/user/manager/music" target="_Blank">放松一下</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="${ctx }/user/loginOut">退出</a></li>
									</ul></li>
							</ul>
						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->

				</nav>
</div>




 </div>
 
 


 </header>
 <div  class="container " >
  <div class="row  "  style="margin-top:150px;margin-left:440px;">
 <div class="row  col-md-7 " >
 <form class="form-horizontal" method="post"
			action="/taobao/identity/login.action"role="form" >
			
			
			<div class="form-group" style="margin-bottom:30px;margin-left:43px;">
				<div class="col-sm-8">
					<span id="loginTip" style="font-size:28px;">绑定操作</span>
				</div>
	
			</div>
    <div class="form-group">
										<div class="col-sm-8">
											<input class="form-control" value="" placeholder="手机号/邮箱"
												type="text" id="emailPhone" name="loginName" />
										</div>

									</div>
									<div class="form-group" style="margin-top: -20px;">
										<div class="col-sm-8">
											<span id="emailPhoneTips" style="font-size: 12px; color: #F66495;"></span>
										</div>

									</div>





									<div class="form-group">
										<div class="col-sm-8">
											<div class="input-group">
												<input type="text" id="yzm" class="form-control" placeholder="请输入验证码">
												<span class="input-group-btn">
													<input  id="btn" class="btn btn-default" type="button" value="发送">
												</span>
											</div>
											<!-- /input-group -->
										</div>
									</div>

									<div class="form-group" style="margin-top: -20px;">
										<div class="col-sm-8">
											<span id="emailOrPhoneTip"
												style="font-size: 12px; color: #F66495;"></span>
										</div>

									</div>

									<div class="form-group">
										<div class="col-sm-8">
											<span style="color: red;"></span>
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-8">
											<div class="btn-group btn-group-justified" role="group"
												aria-label="...">
												<div class="btn-group" role="group">
													<button id="yzBtn"type="button" class="btn btn-info">
														<span class="glyphicon glyphicon-check"></span>&nbsp;修改
													</button>
												</div>
												<div class="btn-group" role="group">
													<button id="back" type="button" class="btn btn btn-success">
														<span class="glyphicon glyphicon-share-alt"></span>返回
													</button>
												</div>
											</div>
										</div>
									</div>
		</form>
			
 </div>
  </div>
 
 
 
 


  
 



			
			
	<!--主体结束-->
  </div>			
<!-- 1，页脚部分 -->
<div  class="container-fluid"  style="margin-top:194px">




</div>
	

 




  </body>
</html>
