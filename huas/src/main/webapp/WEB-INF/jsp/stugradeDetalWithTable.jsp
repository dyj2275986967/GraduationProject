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
<title>学生成绩管理详情展示</title>

<!--springboot改图标-->
<link href="${ctx }/res/img/favicon.ico" type="image/x-icon" rel="icon">
<!-- Bootstrap -->
<link href="${ctx }/res/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${ctx }/res/bootstrap/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${ctx }/res/bootstrap/js/bootstrap.min.js"></script>

<link href="${ctx }/res/css/pager.css" rel="stylesheet">
<!--引入下拉框插件要用的css文件-->

<!-- 引入自定义的css -->
<link href="${ctx }/res/css/myCss.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx }/res/css/index.css">
<script>
 $(function (){
	 
		var depMsg = null;
		var gradeClassMsg = null;
	

		//**********************************
		//给添加模态框  和 条件搜索模态框 注入数据开始
		//*********************************
		//页面初始化时发ajax请求 
		$.ajax({
					//指定请求地址的url
					url : "${ctx}/student/msg/addShow",
					//指定
					type : "post",

					//预期服务器返回的数据类型
					dateType : "json",
					//服务器响应成功时候的回调函数
					success : function(result) {

						//为Select追加一个Option(下拉项) 
                        var dataArray= JSON.parse(result);
						depMsg = dataArray.msg;
						gradeClassMsg = dataArray.gradeClassmsg;
						
						//显示所有的院系信息   
						for (var i = 0; i < depMsg.length; i++) {
							
							//条件搜索模态框 
							$("#search_condition_dep").append(
									"<option value='"+depMsg[i].majors[0].university.depId+"'>"
											+ depMsg[i].depName
											+ "</option>");
		

						}

						for (var i = 0; i < gradeClassMsg.length; i++) {

							
							//条件搜索模态框
							$("#search_condition_grade")
									.append(
											"<option value='"+gradeClassMsg[i].clazzs[0].gradeClass.gradeClassId+"'>"
													+ gradeClassMsg[i].gradeClassName
													+ "</option>");

						}

					},
					error : function(xhr, textStatus, err) {//服务器响应失败时候的回调函数
						console.log(xhr);
						alert(xhr);
						alert(textStatus);
						alert(err)

					}

				})

		//**********************************
		//给添加模态框  和 条件搜索模态框 注入数据结束
		//*********************************
	 //**********************************
				//搜索模态框  动态选择数据开始 
				//*********************************

				//当院系选择状态改变时给专业动态的注入值
				$("#search_condition_dep")
						.change(
								function() {

									//移除专业系option的text
									$("#search_condition_major").find("option")
											.remove();
									$("#search_condition_clazz").find("option")
											.remove();
									//获取选中option的院系text的值
									var selectValue = $("#search_condition_dep")
											.find("option:selected").text();
									//加默认值 option 不选择
									$("#search_condition_clazz").append(
											"<option value=''>不选择</option>");

							

									if (selectValue != "不选择") {
										for (var i = 0; i < depMsg.length; i++) {

											if (selectValue == depMsg[i].depName) {

												for (var j = 0; j < depMsg[i].majors.length; j++) {

													//  console.log(depMsg[i].majors[j].majorName);
													//动态改变专业信息depMsg[0].majors[i].clazzs[i].major.majorId+

													//  console.log(depMsg[i].majors[j].clazzs);

													//判断专业下面是否有班级信息 没有就没必要拼接 ，没有就没必要搜索
													if (depMsg[i].majors[j].clazzs.length != 0) {

														$(
																"#search_condition_major")
																.append(
																		"<option value='"+depMsg[i].majors[j].clazzs[0].major.majorId+"'>"
																				+ depMsg[i].majors[j].majorName
																				+ "</option>");

													}

												}

											}

										}
									}else{
										
										//加默认值 option 不选择
										$("#search_condition_major").append(
												"<option value=''>不选择</option>");
									}

								});

				//当专业选择状态改变时给班级动态的注入值
				$("#search_condition_major")
						.change(
								function() {
									//移除班级option的text
									$("#search_condition_clazz").find("option")
											.remove();
									//获取当前选择的院系value值 即数组值	  
									var selectDepValue = $(
											"#search_condition_dep").find(
											"option:selected").text();
									var selectMajorValue = $(
											"#search_condition_major").find(
											"option:selected").text();

									//加默认值 option 不选择
									$("#search_condition_clazz").append(
											"<option value=''>不选择</option>");

									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#search_condition_grade").find(
											"option:selected").text();

									//如果年级等于不选择  将该专业下的所有 班级信息注入进去
							

										//循环遍历年级信息
										for (var i = 0; i < depMsg.length; i++) {

											//选择的院系和当前的院系一致
											if (selectDepValue == depMsg[i].depName) {
												//循环遍历专业信息
												for (var j = 0; j < depMsg[i].majors.length; j++) {
													//当前选择的专业和遍历的专业信息相同时 
													if (selectMajorValue == depMsg[i].majors[j].majorName) {
														//拼接该专业下所有的班级信息

														//遍历班级信息
														for (var z = 0; z < depMsg[i].majors[j].clazzs.length; z++) {

															//   console.log(depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName);

															//判断 选择的年级与遍历的年级一致 ，就加信息
															if (gradeClassVlue == depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName) {

																$(
																		"#search_condition_clazz")
																		.append(
																				"<option value='"+depMsg[i].majors[j].clazzs[z].classId+"'>"
																						+ depMsg[i].majors[j].clazzs[z].className
																						+ "</option>");

															}

														}

													

												}

											}

										}

									}

								});
				//当 年级选择状态改变时给班级动态的注入值
				$("#search_condition_grade")
						.change(
								function() {

									$("#search_condition_clazz").find("option")
											.remove();
									//获取当前选择的院系value值 即数组值	  
									var selectDepValue = $(
											"#search_condition_dep").find(
											"option:selected").text();
									var selectMajorValue = $(
											"#search_condition_major").find(
											"option:selected").text();

									//加默认值 option 不选择
									$("#search_condition_clazz").append(
											"<option value=''>不选择</option>");

									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#search_condition_grade").find(
											"option:selected").text();

									//循环遍历年级信息
									for (var i = 0; i < depMsg.length; i++) {

										//选择的院系和当前的院系一致
										if (selectDepValue == depMsg[i].depName) {
											//循环遍历专业信息
											for (var j = 0; j < depMsg[i].majors.length; j++) {
												//当前选择的专业和遍历的专业信息相同时 
												if (selectMajorValue == depMsg[i].majors[j].majorName) {
													//拼接该专业下所有的班级信息

													//遍历班级信息
													for (var z = 0; z < depMsg[i].majors[j].clazzs.length; z++) {

														//	   console.log(depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName);

														//判断 选择的年级与遍历的年级一致 ，就加信息
														if (gradeClassVlue == depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName) {

															$(
																	"#search_condition_clazz")
																	.append(
																			"<option value='"+depMsg[i].majors[j].clazzs[z].classId+"'>"
																					+ depMsg[i].majors[j].clazzs[z].className
																					+ "</option>");

														}

													}

												}

											}

										}

									}

								});

				//**********************************
				//搜索模态框  动态选择数据结束
				//*********************************

				
	//**********************************
	//名人名言开始
   //*********************************				

   
   $.ajax({
					//指定请求地址的url
					url : "https://v1.alapi.cn/api/mingyan?typeid=7",
					//指定
					type : "post",
                  //  data: "typeid=1",
					//预期服务器返回的数据类型
					dateType : "json",
					//服务器响应成功时候的回调函数
					success : function(result) {
					
						
       $("#pText").text(result.data.content);
       $("#pName").text(result.data.author);
                   console.log(result);

					},
					error : function(xhr, textStatus, err) {//服务器响应失败时候的回调函数
						
						   $("#pText").text("服务器延迟，刷新页面显示名人名言");
					}

						})
   
						
						//导出excel
						$("#exportExcelBtn").click(function(){
							
							
							window.location.href="/student/grade/detail/stuMsg_export?&majorId=${majorId}&gradeClassId=${gradeClassId}&clazzId=${clazzId }";
							
						})
						
						
   
   
   
	//**********************************
	//名人名言结束
   //*********************************			
				
	//查询开始
	$("#findMsg").click(function(){
		var depNum = $("#search_condition_dep").val();
		var majorId =$("#search_condition_major").val();
		var gradeClassId = $("#search_condition_grade").val();
		var clazzId = $("#search_condition_clazz").val();
		if(depNum){
			
			window.location.href = "/student/grade/detail/detailsWithTable?&majorId="+majorId+"&gradeClassId="+gradeClassId+"&clazzId="+clazzId;
			
		}else{
			

			   $("#tipSpan").html("<font color='red' size='20'>请选择一个专业</font>");
		
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
								<li class="active"><a href="${ctx }/student/grade/list">成绩管理</a></li>
								<li><a href="${ctx }/student/AwardOrPunish/index">奖罚管理</a></li>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown" role="button" aria-haspopup="true"
									aria-expanded="false"> <img
										src="${ctx }/res/img/UserImg/${session_user.img}" height="22"
										width="22" class="img-circle "><span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="${ctx }/user/manager/index">个人中心</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="#">放松一下</a></li>
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


		<div class="row mymarginDetial">
			<!--修改个人信息页开始-->
			<legend>Details:</legend>
		</div>


		<!--第一行开始-->
		<div class="row mymarginTop">
			<div class="col-md-1">
				<span style="font-size: 16px">院&nbsp系:</span>


			</div>


			<div class="col-md-2" style="margin-left: -20px">
				<select class="form-control" style="width: 190px"
					id="search_condition_dep">
					<option value="">不选择</option>

				</select>
			</div>
			<div class="col-md-1" style="margin-left: 20px">
				<span style="font-size: 16px">专&nbsp业:</span>


			</div>


			<div class="col-md-2" style="margin-left: -20px">

				<select class="form-control" id="search_condition_major">
					<option value="">不选择</option>

				</select>
			</div>


			<div class="col-md-1 ">

				<span style="font-size: 16px">年级：</span>


			</div>


			<div class="col-md-1 " style="margin-left: -30px">
				<select class="form-control" style="width: 100px"
					id="search_condition_grade">
					<option value="">不选择</option>

				</select>
			</div>

			<div class="col-md-1" style="margin-left: 20px">
				<span style="font-size: 16px">班&nbsp级:</span>


			</div>

			<div class="col-md-2">


				<select class="form-control" style="margin-left: -20px"
					id="search_condition_clazz">>
					<option value="">不选择</option>

				</select>
			</div>




			<div class="col-md-1 " style="margin-left: 20px">

				<button type="button" class="btn btn-info btn-sm" id="findMsg">查询</button>


			</div>

		</div>







		<div class="row " style="margin-top: 50px">


			<div class="col-md-12">

				<c:choose>
					<c:when test="${not empty courseName }">

						<table class="table table-hover" id="tab">
							<thead>

								<tr>
									<th>学号</th>
									<th>姓名</th>
									<c:forEach items="${courseName}" var="ItemList">
										<th>${ItemList }</th>
									</c:forEach>
									<th>班级</th>
								</tr>

							</thead>
							<tbody>

								<c:forEach items="${lists}" var="ItemList">
									<tr>
										<td>${ItemList.stuId}</td>
										<td>${ItemList.stuName}</td>
										<c:forEach items="${letter}" var="Item">

											<c:choose>
												<c:when test="${ItemList[Item]>=60}">
													<td>${ ItemList[Item]}</td>
												</c:when>
												<c:otherwise>
													<td><font color="red">${ ItemList[Item]}</font></td>

												</c:otherwise>
											</c:choose>
										</c:forEach>
										<td>${ItemList.className}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
					<div style="margin-left: -800px">
						<z:pager pageIndex="${pageIndex}"
							pageSize="${pageModel.pageSize }"
							totalNum="${pageModel.totalNum }"
							submitUrl="detailsWithTable?pageIndex={0}&majorId=${majorId}&gradeClassId=${gradeClassId}&clazzId=${clazzId }"
							pageStyle="quotes"></z:pager>

					</div>
						
							<div style="margin-left: 1065px">
						<button class="btn btn-info" id="exportExcelBtn">导出</button>

					</div>
					</c:when>
					<c:otherwise>
						<center>
							<div class="row" style="margin-top: 150px; cursor: pointer">
								<p id="pText" style="font-size: 20px; font-family: sans-serif"></p>
								<p id="pName" style="margin-top: 50px"></p>
							</div>
						</center>
					</c:otherwise>
				</c:choose>

				<center>

					<span id="tipSpan"></span>
				</center>



			</div>
		</div>





	</div>
	<!--主体结束-->
	</div>










</body>
</html>
