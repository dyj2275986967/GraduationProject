<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>系统登录</title>


<!-- Bootstrap -->
<!--springboot改图标-->
<link href="${ctx }/res/img/favicon.ico" type="image/x-icon" rel="icon">
<link href="${ctx }/res/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${ctx }/res/bootstrap/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${ctx }/res/bootstrap/js/bootstrap.min.js"></script>

<!-- 引入自定义的css -->
<link href="${ctx }/res/css/myCss.css" rel="stylesheet">



<!-- 引入切换面板的css -->
<link type="text/css" href="${ctx }/res/css/stylePlane.css"
	rel="stylesheet" />

<script type="text/javascript">
	var loginFlag = true;
	var pwdFlag = true;
	$(function() {

		//校验账号是否匹配正确的格式
		$("#loginName").blur(function() {

			var standard = /^([a-zA-Z0-9]|[-]){5,12}$/;
			if (!this.value) {
				$("#loginSpanTips").text("*用户名不能为空");

				loginFlag = false;
			} else if (!standard.test(this.value)) {
				$("#loginSpanTips").text("*用户名格式不正确");
				//校验成功
			} else {

				loginFlag = true;

			}
			//聚焦事件，聚焦时将提示清掉  
		}).focus(function() {
			$("#loginSpanTips").html("");
		})

		$("#passWord").blur(function() {

			var standard = /^([a-zA-Z0-9]|[-]){5,12}$/;
			if (!this.value) {
				$("#pwdSpanTips").text("*密码不能为空");
				pwdFlag = false;
			} else if (!standard.test(this.value)) {

				$("#pwdSpanTips").html("*密码格式不正确");

				//校验成功
			} else {

				pwdFlag = true;

			}
			//聚焦事件，聚焦时将提示清掉       
		}).focus(function() {
			$("#pwdSpanTips").html("");
		})
		
		// 登录
		   $("#loginBtn").click(function (){

				  //获取账号信息
				  var loginNameVal=$("#loginName").val();
				  var passWordVal=$("#passWord").val(); 
				  
				  //如果前端校验成功就进行AJAX请求
				  
				  if(loginFlag&&pwdFlag){
					  $.ajax({
						   //指定请求地址的url
						   url:"${ctx}/user/api/login",
					      //指定
						   type:"post",
						   data:"loginName="+loginNameVal+"&password="+passWordVal,
						  //预期服务器返回的数据类型
						   dateType:"text",
						   //服务器响应成功时候的回调函数
						   success:function(result){
			                 
			                     if(result.message== "1"){
						               
						            	
					            	 $("#loginSpanTips").html( "*账号不存在！");
					              }else if(result.message == "2"){
					            
					            	 $("#pwdSpanTips").html( "*密码输入错误");
					              }else{
					            	  //跳转至首页
					            	  window.location = "${ctx}/student/msg/list";
					              }
					          
					    			
					    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
				  	    			alert(xhr);
					    			alert(textStatus);
					    			alert(err)
					    			
							   
						   }
						  	   
					   })
					  
					  
				  }

			  })   
//邮件短信校验开始

//邮箱手机号校验开始


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
		   url:"${ctx}/user/api/yzLogin",
	      //指定
		   type:"post",
		   data:"check="+check+"&emailOrPhone="+emailPhone+"&yzm="+yzm,
		  //预期服务器返回的数据类型
		   dateType:"text",
		   //服务器响应成功时候的回调函数
		   success:function(result){
                if(result=="登陆成功"){
                	
                	window.location = "${ctx}/student/msg/list";
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


//进入忘记密码的界面
$("#forgetT").click(function(){
	
	
	window.location="${ctx}/user/createPwd";
	
})
$("#forgetO").click(function(){
	
	
	window.location="${ctx}/user/createPwd";
	
})

		
		
	})

	
</script>






<style>
.target {
	width: 600px;
	height: 350px;
	overflow: hidden;
	position: relative;
}

.target img {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}
</style>


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
							<a class="navbar-brand" href="shop/index.action">学生综合素质管理系统</a>
						</div>
						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse"
							id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">


								<li><a href="stuMessageM.html">信息管理</a></li>
								<li><a href="#">成绩管理</a></li>
								<li><a href="#">奖罚管理</a></li>

							</ul>

							<ul class="nav navbar-nav navbar-right">
								<li><a href="#"> <span style='color: red;'>管理员</span>
										,先生,欢迎您!

								</a></li>
							</ul>
						</div>
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>

		</div>


		</div>

	</header>
	<div class="container ">





		<div class="row mymarginLogin">
			<div class="col-md-8">
				<div class="row " style="margin-top: 60px;">


					<div class="target" id="target-2">
						<div>
							<div>
								<img src="${ctx }/res/img/lbtImg/06.jpg">
							</div>
							<div>
								<img src="${ctx }/res/img/lbtImg/02.jpg">
							</div>
							<div>
								<img src="${ctx }/res/img/lbtImg/05.jpg">
							</div>
							<div>
								<img src="${ctx }/res/img/lbtImg/07.jpg">
							</div>

						</div>
					</div>
				</div>
			</div>

			<div class="col-md-4">

				<div class="mt-tabpage" js-tab="3">
					<div class="mt-tabpage-title">
						<a href="javascript:;" class="mt-tabpage-item">密码登录</a> <a
							href="javascript:;" class="mt-tabpage-item">验证登录</a>
					</div>
					<div class="mt-tabpage-count">
						<ul class="mt-tabpage-cont__wrap">



							<!-- 面板一开始-->
							<li class="mt-tabpage-item">

								<form class="form-horizontal" method="post"
									action="/taobao/identity/login.action" role="form">
									<div class="form-group">
										<div class="col-sm-8">
											<input class="form-control" placeholder="用户名" type="text"
												id="loginName" name="loginName" />
										</div>

									</div>
									<div class="form-group" style="margin-top: -20px;">
										<div class="col-sm-8">
											<span style="font-size: 12px; color: #F66495;"
												id="loginSpanTips"></span>
										</div>

									</div>



									<div class="form-group">
										<div class="col-sm-8">
											<input class="form-control" placeholder="密码" id="passWord"
												type="password" name="passWord" />
										</div>
									</div>

									<div class="form-group" style="margin-top: -20px;">
										<div class="col-sm-8">
											<span style="font-size: 12px; color: #F66495;"
												id="pwdSpanTips"></span>
										</div>

									</div>


									<div class="form-group">
										<div class="col-sm-8">
											<div class="btn-group btn-group-justified" role="group"
												aria-label="...">
												<div class="btn-group" role="group">
													<button type="button" id="loginBtn" id="loginBtn"
														class="btn btn-info">
														<span class="glyphicon glyphicon-log-in"></span>&nbsp;登录
													</button>
												</div>
												<div class="btn-group" role="group">
													<button id="forgetO" type="button" class="btn btn btn-danger">
														<span class="glyphicon glyphicon-edit"></span>忘记密码
													</button>
												</div>
											</div>
										</div>
									</div>
								</form>



							</li>
							<!-- 面板一结束-->
							<!-- 面板二开始-->
							<li class="mt-tabpage-item">


								<form class="form-horizontal" method="post"
									action="/taobao/identity/login.action" role="form">
									<div class="form-group">
										<div class="col-sm-8">
											<input class="form-control" value="" placeholder="手机号/邮箱"
												type="text" id="emailPhone" name="loginName" />
										</div>

									</div>
									<div class="form-group" style="margin-top: -20px;">
										<div class="col-sm-8">
											<span id="emailPhoneTips"
												style="font-size: 12px; color: #F66495;"></span>
										</div>

									</div>





									<div class="form-group">
										<div class="col-sm-8">
											<div class="input-group">
												<input type="text" id="yzm" class="form-control"
													placeholder="请输入验证码"> <span class="input-group-btn">
													<input id="btn" class="btn btn-default" type="button"
													value="发送">
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
													<button id="yzBtn" type="button" class="btn btn-info">
														<span class="glyphicon glyphicon-log-in"></span>&nbsp;登录
													</button>
												</div>
												<div class="btn-group" role="group">
													<button id="forgetT" type="button" class="btn btn btn-danger">
														<span class="glyphicon glyphicon-edit"></span>忘记密码
													</button>
												</div>
											</div>
										</div>
									</div>
								</form>
							</li>

							<!-- 面板二结束-->

						</ul>
					</div>
				</div>


			</div>








		</div>

	</div>


	<!--主体



	<!--轮播图所用的插件开始-->
	<script src="${ctx }/res/js/jquery.min.js"></script>
	<script src="${ctx }/res/js/HappyImage.min.js"></script>
	<!--轮播图所用的插件结束-->
	<script>
		$("#target-2").HappyImage({
			effect : "fade"
		});
	</script>

	<!--引入切换面版-->
	<script type="text/javascript" src="${ctx }/res/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/res/js/mt-tabpage.js"></script>
	<script type="text/javascript">
		$(function() {

			$('[js-tab=3]').tab({
				curDisplay : 1,
				changeMethod : 'horizontal'
			});

		});
	</script>

</body>
</html>
