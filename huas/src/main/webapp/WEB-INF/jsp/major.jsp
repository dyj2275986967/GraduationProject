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
    <title>管理员</title>
<!--springboot改图标-->
<link href="${ctx }/res/img/favicon.ico" type="image/x-icon" rel="icon">

    <!-- Bootstrap -->
    <link href="${ctx }/res/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${ctx }/res/bootstrap/js/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="${ctx }/res/bootstrap/js/bootstrap.min.js"></script>
<link href="${ctx }/res/css/pager.css" rel="stylesheet">
 <!-- 引入自定义的css -->
	 <link href="${ctx }/res/css/myCss.css" rel="stylesheet">

<script>
 $(function(){
	 
	 //添加
	 $("#add").click(function(){
		var value= $("#depName").val();
		if(!value){
		 alert("不能为空");	
		} else{
			
			// window.location="${ctx }/user/admin/index/major/add?majorName="+value+"&depId=${depId}";
		
		
			 $.ajax({
				   //指定请求地址的url
				   url:"${ctx }/user/admin/index/major/add",
			      //指定
				   type:"post",
				   data:"majorName="+value+"&depId=${depId}",
				  //预期服务器返回的数据类型
				   dateType:"text",
				   //服务器响应成功时候的回调函数?pageIndex=
				   success:function(result){
		            
					   window.location="${ctx }/user/admin/index/major?depId=${depId}&pageIndex=${pageIndex}";
			    			
			    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
		 	    			alert(xhr);
			    			alert(textStatus);
			    			alert(err)
			    			
					   
				   }
				  	   
			   })
		
		
		
		
		
		
		
		
		
		
		
		
		
		}

	 })
	 // 给修改框注入数据开始
	 var stuMsgs = new Array(11);

		//给模态框注入数据
		$("#tab tr").dblclick(function() {

			//获取选中表格行的下标
			var index = $(this).closest("tr").index() + 1;
			//准备存放数据的数组

			$('#tab tr').each(function(i) { // 遍历 tr
				$(this).children('td').each(function(j) { // 遍历 tr 的各个 td		    	
					if (i == index) {
						//将数据存放在数组里面

						stuMsgs[j] = $(this).text();
					}

				});
			});
			
			$("#creDepName").val(stuMsgs[1]);
			$("#creDepId").val(stuMsgs[0]);

		});
	 //修改
	 	 //添加
	 $("#createBtn").click(function(){
		var creDepName= $("#creDepName").val();
		var creDepId= $("#creDepId").val();
		
		if(!creDepName){
		 alert("不能为空");	
		} else{
			
		
		
  
			 $.ajax({
				   //指定请求地址的url
				   url:"${ctx }/user/admin/index/major/update",
			      //指定
				   type:"post",
				   data:"majorName="+creDepName+"&majorId="+creDepId,
				  //预期服务器返回的数据类型
				   dateType:"text",
				   //服务器响应成功时候的回调函数?pageIndex=
				   success:function(result){
		             
					   alert(result);
					   window.location="${ctx }/user/admin/index/major?depId=${depId}&pageIndex=${pageIndex}";
			    			
			    		},error:function(xhr, textStatus, err){//服务器响应失败时候的回调函数
		 	    			alert(xhr);
			    			alert(textStatus);
			    			alert(err)
			    			
					   
				   }
				  	   
			   })
		
		
		
		
		
		
		
		
		
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		

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
										src="/image/${session_user.img}" height="22" width="22"
										class="img-circle "><span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="${ctx }/user/manager/index">个人中心</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="${ctx }/user/manager/music" target="_Blank">放松一下</a></li>
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
  
 

<!-- 1，页脚部分 -->

<div class="row" style="margin-top:85px;">

<div class="col-md-2 col-md-offset-1 "  >

<div class="row">
<div class="list-group">
	 <a href="#" class="list-group-item ">个人资料</a>



<a href="#" class="list-group-item   ">管理员</a><!--没有此功能的用disabled-->
<a href="#" class="list-group-item  list-group-item-success ">学院管理</a><!--没有此功能的用disabled-->


				
				</div>

</div>




</div>

<div  class="row col-md-8 " >

<div class="panel panel-default " >

					<!-- 1，面板开始 -->
					<div class="panel-body" style="margin-bottom: 100px;">
						<!--第一行个人资料-->
						<div class="row">
							<div class=" col-md-3">

								<span style="font-size: 25px;">专业管理</span>

							</div>
						</div>
						<!--第一行个人资料-->
						<!--第二行 线-->
						<div class="row">
							<div class=" col-md-12">
								<hr>
							</div>

						</div>
						<!--第二行 线-->
						<!--第三行头像-->
						<div class=" row">
							<div class=" col-md-2">
								<div class="row" style="margin-left: 7px">
									<img src="/image/${session_user.img}" height="80" width="80"
										class="img-circle ">
								</div>

							</div>
							<div class=" col-md-10">
								<!--第一行-->
								<div class="row">
									<div class="col-md-4">
										<span style="color: #999999; font-size: 14px">ID:&nbsp${session_user.loginName}</span>
									</div>




								</div>
								<div class="row" style="margin-top: 8px;">
									<div class="col-md-4">

										<c:choose>
											<c:when test="${not empty session_user.phone}">
												<span style="color: #999999; font-size: 14px">手机号:<font>${session_user.phone}</font></span>
											</c:when>
											<c:otherwise>

												<span style="color: #999999; font-size: 14px">手机号:<font
													color="red">尚未绑定手机</font></span>

											</c:otherwise>
										</c:choose>



									</div>
									<div class="col-md-4">
										

										<c:choose>
											<c:when test="${not empty session_user.email}">
												<span style="color: #999999; font-size: 14px">邮箱:<font>${session_user.email}</font></span>
											</c:when>
											<c:otherwise>

											<span style="color: #999999; font-size: 14px">邮箱:<font
											color="red">尚未绑定邮箱</font></span>

											</c:otherwise>
										</c:choose>


									</div>
								</div>
								<div class="row">
									<div class="col-md-12" style="margin-top: 5px;">
										<span style="color: #999999; font-size: 14px">座右铭:&nbsp${session_user.signature}</span>
									</div>
								</div>
								<div class="row"
									style="margin-top:0px;">
	<div class="col-md-12" style="margin-top:30px;">
	<hr>
	</div>
	
	
	
	</div>
	
	
	</div>


	
		<div class="row" style="margin-left:100px">
	 <div class="col-md-2">
	  <a href="${ctx }/user/admin/index/dep" style="color:#3399EA;font-size:13px;">院系管理</a>
	  </div>
	  <div class="col-md-2"  style="margin-left:-40px">
	  <a href="#" style="color:#3399EA;font-size:13px;">专业管理</a>
	  </div>
	  <div class="col-md-2" style="margin-left:-40px">
	  <a href="#" style="color:#3399EA;font-size:13px;">班级管理</a>
	  </div>
	  <div class="col-md-2" style="margin-left:-40px">
	  <a href="#" style="color:#3399EA;font-size:13px;">年级管理</a>
	  </div>

	
	</div>
	
		<div class="row" style="margin-left:100px;margin-top:30px">
       
	  <div class="col-md-3">
  	  <input type="text" id="depName" class="form-control"  size="50">
	 
	 </div>
	  <div class="col-md-2">
   <button  class="btn btn-info" id="add">添加</button>
	 
	 </div>
	 
	        
	  <div class="col-md-3">
  	  <input type="text" class="form-control"  id="creDepName" size="50">
	   <input type="hidden" class="form-control" id="creDepId" size="50">
	 </div>
	  <div class="col-md-2">
   <button  class="btn btn-info" id="createBtn">修改</button>
	 
	 </div>
	 
	  
	</div>
	
	
	
	
	
	<!--个人信息页面开始-->
	
	
	<div class="row">

	

		
	<!--表格开始-->
	<div class="col-md-6 " style="margin-left:150px;margin-top:30px"   >  
	  <table class="table table-hover" id="tab">

  <thead>
    <tr>
	
      <th>学院id</th>
      <th> 学院名</th>
      <th>操作</th>
	</tr>
  </thead>
  <tbody>
 <c:forEach items="${list}" var="ItemList">
							<tr style='cursor: pointer'>
								<td>${ItemList.majorId }</td>
								<td>${ItemList.majorName }</td>
						
						       
								<td><a
									href="${ctx }/user/admin/index/dep/delete?id=${ItemList.depId }"
									style="color: #3399EA; font-size: 13px;" onclick="return confirm('确定要删除 该用户吗  ？')">删除</a></td>
							</tr>
						</c:forEach>
		
	
	
	
	 </tbody>





</table>
	
	</div>
	<!--表格结束-->
	 
	 <div class="row" style="margin-left:250px">
					<!-- 注意在分页的时候一定要把 条件搜索 和模糊查询的 相关的信息带到页面上去，否则会出错！！！！ -->
					<div style="margin-left: -380px">
						<z:pager pageIndex="${pageIndex}"
							pageSize="${pageModel.pageSize }"
							totalNum="${pageModel.totalNum }"
							submitUrl="major?pageIndex={0}&depId=${depId}"
							pageStyle="quotes"></z:pager>
					</div>



					




				</div>
	  
	
	

		
	</div>
		<!--个人信息页面结束-->
	
	
	
	</div>
	
	
	</div>
</div>

</div>
</div>

			
			
	<!--主体结束-->
  </div>		






  
<!--头像模态框开始-->
		
			


 




  </body>
</html>
