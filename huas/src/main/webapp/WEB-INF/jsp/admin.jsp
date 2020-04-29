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
<link href="${ctx }/res/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${ctx }/res/bootstrap/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${ctx }/res/bootstrap/js/bootstrap.min.js"></script>

<!-- 引入自定义的css -->
<link href="${ctx }/res/css/myCss.css" rel="stylesheet">
<link href="${ctx }/res/css/pager.css" rel="stylesheet">

<link href="${ctx }/res/txjsandcss/head/sitelogo.css" rel="stylesheet">

<script>

$(function(){
	
	$("#addLoginNameSpan").click(function(){
		
		 
		// 打开模态框excel_file
			$('#createLogin').modal(
					'show')		
				
	})
	
	//添加账号数量 加加
	$("#add").click(function (){
		var value=$("#num").val();
		 if(value<5){
			 value++;
			 $("#num").val(value);
			 
		 }else{
			 
			 alert("最多只能生成5个账号");
			 
		 }
				
	})
	//添加账号数量 减减
	$("#cut").click(function (){
		var value=$("#num").val();
		if(value>1){
			
			 value--;
			 $("#num").val(value);
			 
		 }else{
			 
			 alert("到底啦");
			 
		 }
		
	})
	
		$("#num").blur(function(){
			var value=$("#num").val();
			// 判断是否为空
			if(!value){
				alert("不能为空");
				 $("#num").val(1);
				
			}
			
			
			//判断是否为数字
			if(isNaN(value)){
				 
				 alert("请输入数字");
				 $("#num").val(1)
				 
			 }
			
			//校验范围
			
			if(value<1){
				 alert("请您输入1到5之间的数");
				 $("#num").val(1)
			}
			if(value>5){
				 alert("请您输入1到5之间的数");
				 $("#num").val(1)
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


		</div>

	</header>
	<div class="container ">



		<!-- 1，页脚部分 -->

		<div class="row" style="margin-top: 85px;">

			<div class="col-md-2 col-md-offset-1 ">

				<div class="row">
					<div class="list-group">
						<a href="${ctx }/user/manager/index" class="list-group-item ">个人资料</a>



						<a href="${ctx }/user/admin/index"
							class="list-group-item  list-group-item-success ">管理员</a>
						<!--没有此功能的用disabled-->
            
                   <!-- <a href="${ctx }/user/admin/index/dep" 
							class="list-group-item  list-group-item-success ">学院管理</a>  -->   



					</div>

				</div>




			</div>

			<div class="row col-md-8 ">

				<div class="panel panel-default ">


					<!-- 1，面板开始 -->
					<div class="panel-body" style="margin-bottom: 100px;">
						<!--第一行个人资料-->
						<div class="row">
							<div class=" col-md-3">

								<span style="font-size: 25px;">个人资料</span>

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
		<!--第三行头像-->
	<!--个人信息页面开始-->
	<div class="row">
	<div class="row" align="right" style="margin-right:20px;" >
     <span id="addLoginNameSpan" style="cursor: pointer;color:#3399EA;font-size:13px;">添加账号</span>
	</div>
	
	
	
	 	<div class="col-md-12 "  >
	
		
	<!--表格开始-->
	<div class="row col-md-11 " style="margin-left:40px;"  >  
	  <table class="table table-hover">

  <thead>
    <tr>
	
      <th>姓名</th>
      <th>账号</th>
	  <th>密码</th>
	  <th>学院</th>
	  <th>级别</th>
      <th>管理者</th>

	 
      <th>操作</th>
	</tr>
  </thead>
  <tbody>
	
	
	                 <c:forEach items="${lists}" var="ItemList">
							<tr style='cursor: pointer'>



								<td>${ItemList.name }</td>
								<td>${ItemList.loginName }</td>
								<td>${ItemList.pwd}</td>
						        <td>马克思主义学院</td>
						       <c:choose>
									<c:when test="${ItemList.level==1 }">
										<td>一级管理员</td>
									</c:when>
									<c:otherwise>

										<td>二级管理员</td>
									</c:otherwise>
								</c:choose>
						          <td>${ItemList.managerName }</td>
								<td><a
									href="${ctx }/user/admin/delete?id=${ItemList.userId}"
									style="color: #3399EA; font-size: 13px;" onclick="return confirm('确定要删除 该用户吗  ？')">删除</a></td>
							</tr>
						</c:forEach>

	
	
	
	
	 </tbody>





</table>
	
	</div>
	<!--表格结束-->
	 
	 <div class="row">
					<!-- 注意在分页的时候一定要把 条件搜索 和模糊查询的 相关的信息带到页面上去，否则会出错！！！！ -->
					<div style="margin-left: -380px">
						<z:pager pageIndex="${pageIndex}"
							pageSize="${pageModel.pageSize }"
							totalNum="${pageModel.totalNum }"
							submitUrl="index?pageIndex={0}&keywords=${keywords}&universityDepId=${universityDepId}&majorId=${majorId }&gradeClassId=${gradeClassId}&clazzId=${clazzId }&stuId=${stuId}&courseId=${ courseId}"
							pageStyle="quotes"></z:pager>
					</div>



					




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


<!--绑定操作模态框开始-->

<!-- Modal -->
<div class="modal fade " id="myModal" tabindex="-1" role="dialog"  data-target="#identifier"  aria-labelledby="myModalLabel" data-backdrop="false" aria-hidden="true">
	<div class="modal-dialog" style="width:280px;">
		<div class="modal-content" >
				<div class="modal-body" >	
				    <form class="form-horizontal" method="post"
			action="/taobao/identity/login.action"role="form"  style="margin-left:40px; ">
			
			
			<div class="form-group" style="margin-bottom:20px;margin-left:20px;">
				<div class="col-md-12">
					<span id="loginTip" style="font-size:20px;">绑定操作</span>
				</div>
	
			</div>

			
			
			<div class="form-group">
				<div class="col-md-10">
					<input class="form-control" value="" placeholder="手机号/邮箱"
						type="text" id="loginName" name="loginName" />
				</div>
	
			</div>
			<div class="form-group" style="margin-top:-20px;">
				<div class="col-md-8">
					<span id="loginTip" style="font-size:12px;color:#F66495;">*账号不存在呢！</span>
				</div>
	
			</div>

	
	


			<div class="form-group">
				<div class="col-md-10">
				<div class="input-group">
            <input type="text" class="form-control" placeholder="请输入验证码">
            <span class="input-group-btn">
                <button class="btn btn-default" type="button">发送</button>
             </span>
         </div><!-- /input-group -->
				</div>
			</div>
			
					<div class="form-group" style="margin-top:-20px;">
				<div class="col-md-8">
					<span id="passwordTip" style="font-size:12px;color:#F66495;">*您的密码呢？</span>
				</div>
	
			</div>
			
			<div class="form-group">
				<div class="col-md-8">
					<span style="color: red;"></span>
				</div>
			</div>

	
		
                                <input type="submit" class="btn btn-success btn-default" value="确&nbsp定" />
                              <input type="button" style="margin-left:40px" class="btn btn-danger btn-default" data-dismiss="modal" value="取&nbsp消" />
	
		</form>
					
		        </div>
			
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal 




  


<!--账号生成模态框开始-->
	<!-- 按钮触发模态框 -->
<div class="modal fade" id="createLogin">
    <div class="modal-dialog" style="width:300px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">账号生成</h4>
            </div>
            <div class="modal-body ">
                <div align="center">
                    <span style="color:red;"></span>
                    <form name="articleform" class="form-horizontal" action="${ctx}/user/admin/addLogin" method="post">
					 <div class="form-group">
                            <label class="col-sm-3 control-label">级别：</label>
                             <div class="col-sm-8">
                            <select class="form-control" name="level" id="addTypeCode">
                                 <!--当登陆级别不是超级管理员时,select只能是二级管理员-->
                                        
                                        
                                <c:choose>
									<c:when test="${session_user.level==1 }">
										  <option value="2" >二级管理员</option>
									</c:when>
									<c:otherwise>
									  <option value="1">一级管理员</option>
								
                                       <option value="2" >二级管理员</option>
    	
									</c:otherwise>
								</c:choose>        
                                        
                                        
                                   
                                </select> 
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账号：</label>
                             <div class="col-sm-8">
                                <input type="text" class="form-control" name="loginName" size="50">
                            </div>
                        </div>
						      <div class="form-group">
                            <label class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-8">
                                 <input type="text" class="form-control" name="pwd" size="50"> 
                            </div>
                        </div>
                          <input  name="id"   type="hidden" class="form-control"  value="${session_user.userId}" >
						      <div class="form-group">
                            <label class="col-sm-3 control-label">数量：</label>
                            <div class="col-sm-8">
                             <div class="input-group">
         <span id="cut" style="cursor: pointer;"  class="input-group-addon"><span class="glyphicon glyphicon-minus"></span></span>
        <input  name="num"  id="num" type="text" class="form-control"  value="1" aria-label="Amount (to the nearest dollar)">
           <span id="add"  style="cursor: pointer;"  class="input-group-addon"><span class="glyphicon glyphicon-plus"></span></span>
      </div>
                            </div>
                        </div>
				

               
                
                                <input type="button" style="margin-left:30px" class="btn btn-danger btn-default" data-dismiss="modal" value="取&nbsp消" />
     
                                <input type="submit" style="margin-left:40px" class="btn btn-success btn-default" value="确&nbsp定" />
                            
                          
                      
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!--账号生成模态框结束-->

 




  </body>
</html>
