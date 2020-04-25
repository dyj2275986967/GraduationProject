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
<title>学生奖罚管理</title>
<!-- Bootstrap -->
<!--引入轮播图的css文件-->

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

<link href="${ctx }/res/css/pager.css" rel="stylesheet">


<script type="text/javascript" src="${ctx }/res/js/tools.js"></script>

<script>
$(function (){
	
	//************************
	//操作select属性开始
	//**********************

	//***************
	//操作select属性开始
	//****************
	//存学院信息的对象
	var depMsg = null;
	var gradeClassMsg = null;
	var stuMsg = null;

	//**********************************
	//给添加模态框  和 条件搜索模态框 注入数据开始
	//*********************************
	//页面初始化时发ajax请求 
	$.ajax({
				//指定请求地址的url
				url : "${ctx}/student/grade/addShow",
				//指定
				type : "post",

				//预期服务器返回的数据类型
				dateType : "json",
				//服务器响应成功时候的回调函数
				success : function(result) {

					//为Select追加一个Option(下拉项) 

					var dataArray = JSON.parse(result);
					console.log(dataArray);
					depMsg = dataArray.msg;
					gradeClassMsg = dataArray.gradeClassmsg;
					stuMsg = dataArray.stuMsg;

					$("#stuIdSpan")
							.text(
									depMsg[0].majors[0].clazzs[2].stus[0].stuId);
					
					
					//显示所有的院系信息   
					for (var i = 0; i < depMsg.length; i++) {
						//添加模态框
						$("#select_dep_id").append(
								"<option value='"+i+"'>"
										+ depMsg[i].depName + "</option>");
						//条件搜索模态框 
						$("#search_condition_dep").append(
								"<option value='"+depMsg[i].majors[0].university.depId+"'>"
										+ depMsg[i].depName + "</option>");

					}

					//写死 显示第一次进入的学院 的专业信息
					for (var i = 0; i < depMsg[0].majors.length; i++) {
						//添加模态框
						$("#select_major_id").append(
								"<option value='"+i+"'>"
										+ depMsg[0].majors[i].majorName
										+ "</option>");

					}

					//获取第一个年级信息
					var gradeClassValue = gradeClassMsg[0].gradeClassName;
					//写死 显示第一次进入的学院 的班级信息
					for (var i = 0; i < depMsg[0].majors[0].clazzs.length; i++) {
						//获取当前年级下的年级信息
						var gradeClassNowValue = depMsg[0].majors[0].clazzs[i].gradeClass.gradeClassName;

						if (gradeClassValue == gradeClassNowValue) {

							//添加模态框
							$("#select_clazz_id")
									.append(
											"<option value='"+depMsg[0].majors[0].clazzs[i].classId+"'>"
													+ depMsg[0].majors[0].clazzs[i].className
													+ "</option>");

							//条件搜索模态框 初始化页面时 都默认是不选择  所以 不用拼装 班级信息

							//这里写死 2 时因为毛泽东思想的 16级是2号
							for (var s = 0; s < depMsg[0].majors[0].clazzs[i].stus.length; s++) {

								//	console.log(depMsg[0].majors[0].clazzs[i].stus[s].);
								$("#select_stuName_id")
										.append(
												"<option value='"+depMsg[0].majors[0].clazzs[2].stus[s].stuId+"'>"
														+ depMsg[0].majors[0].clazzs[2].stus[s].stuName
														+ "</option>");

							}

						}

					}
					//写死 显示第一次进入的学院 的年级信息"

					for (var i = 0; i < gradeClassMsg.length; i++) {

						//添加模态框
						$("#select_grade_clazz_id").append(
								"<option value='"+i+"'>"
										+ gradeClassMsg[i].gradeClassName
										+ "</option>");

						//条件搜索模态框
						$("#search_condition_grade").append(
								"<option value='"+gradeClassMsg[i].clazzs[0].gradeClass.gradeClassId+"'>"
										+ gradeClassMsg[i].gradeClassName
										+ "</option>");

					}

					//写死第一次进入学院的 课程信息
					for (var i = 0; i < depMsg[0].majors[0].courses.length; i++) {

						$("#select_coruse_id")
								.append(
										"<option value='"+depMsg[0].majors[0].courses[i].courseId+"'>"
												+ depMsg[0].majors[0].courses[i].courseName
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
	//添加模态框  动态选择数据开始
	//*********************************
		//当院系选择状态改变时给专业动态的注入值
		$("#select_dep_id")
				.change(
						function() {

							//移除专业系option的text
							$("#select_major_id").find("option").remove();
							$("#select_clazz_id").find("option").remove();
							$("#select_coruse_id").find("option").remove();
							$("#select_stuName_id").find("option").remove();
							$("#stuIdSpan").text("");

							//获取选中option的院系text的值
							var selectValue = $("#select_dep_id").find(
									"option:selected").text();
							//获取选中option的年级text的值
							var gradeClassVlue = $("#select_grade_clazz_id")
									.find("option:selected").text();

							for (var i = 0; i < depMsg.length; i++) {

								if (selectValue == depMsg[i].depName) {

									for (var j = 0; j < depMsg[i].majors.length; j++) {
										//动态改变专业信息
										$("#select_major_id")
												.append(
														"<option value='"+j+"'>"
																+ depMsg[i].majors[j].majorName
																+ "</option>");

									}

									for (var k = 0; k < depMsg[i].majors[0].clazzs.length; k++) {

										//获取当前年级下的年级信息
										var gradeClassNowValue = depMsg[i].majors[0].clazzs[k].gradeClass.gradeClassName;
										//	 alert(depMsg[i].majors[j].clazzs[k].className);
										//保证选中的年级信息要和当前的年级信息一致,保证选中的专业要与当前专业一致
										if (gradeClassVlue == gradeClassNowValue) {

											$("#select_clazz_id")
													.append(
															"<option value='"+depMsg[0].majors[0].clazzs[k].classId+"'>"
																	+ depMsg[i].majors[0].clazzs[k].className
																	+ "</option>");

											//拼接学生信息 ,遍历学生信息													

											for (var s = 0; s < depMsg[i].majors[0].clazzs[k].stus.length; s++) {

												$("#select_stuName_id")
														.append(
																"<option value='"+depMsg[i].majors[0].clazzs[k].stus[s].stuId+"'>"
																		+ depMsg[i].majors[0].clazzs[k].stus[s].stuName
																		+ "</option>");

											}
											//动态改变学生id信息
											if (depMsg[i].majors[0].clazzs[k].stus.length) {

												$("#stuIdSpan")
														.text(
																depMsg[i].majors[0].clazzs[k].stus[0].stuId);
											} else {

												$("#stuIdSpan").text("");

											}

										}

									}
									//动态改变课程信息select_coruse_id
									for (var k = 0; k < depMsg[i].majors[0].courses.length; k++) {
										$("#select_coruse_id")
												.append(
														"<option value='"+depMsg[i].majors[0].courses[k].courseId+"'>"
																+ depMsg[i].majors[0].courses[k].courseName
																+ "</option>");

										console
												.log(depMsg[i].majors[0].courses[k].length);

									}

								}

							}

						});
		//当专业选择状态改变时给院系动态的注入值
		$("#select_major_id")
				.change(
						function() {
							//移除专业系option的text
							$("#select_clazz_id").find("option").remove();
							//移除课程option的text
							$("#select_coruse_id").find("option").remove();
							$("#select_stuName_id").find("option").remove();
												$("#stuIdSpan").text("");
							//获取当前选择的院系value值 即数组值	  
							var depNum = parseInt($("#select_dep_id").val());
							var majorNum = parseInt($("#select_major_id").val());
							//获取选中option的年级text的值
							var gradeClassVlue = $("#select_grade_clazz_id")
									.find("option:selected").text();

							for (var k = 0; k < depMsg[depNum].majors[majorNum].clazzs.length; k++) {
								//当前所在院系专业 班级下的年级级别
								var gradeClassNowValue = depMsg[depNum].majors[majorNum].clazzs[k].gradeClass.gradeClassName;

								//获取当前年级下的年级信息

								if (gradeClassVlue == gradeClassNowValue) {

									$("#select_clazz_id")
											.append(
													"<option value='"+depMsg[depNum].majors[majorNum].clazzs[k].classId+"'>"
															+ depMsg[depNum].majors[majorNum].clazzs[k].className
															+ "</option>");

									for (var s = 0; s < depMsg[depNum].majors[majorNum].clazzs[k].stus.length; s++) {

										$("#select_stuName_id")
												.append(
														"<option value='"+depMsg[depNum].majors[majorNum].clazzs[k].stus[s].stuId+"'>"
																+ depMsg[depNum].majors[majorNum].clazzs[k].stus[s].stuName
																+ "</option>");

									}

									//动态改变学生id信息
									if (depMsg[depNum].majors[majorNum].clazzs[k].stus.length) {

										$("#stuIdSpan")
												.text(
														depMsg[depNum].majors[majorNum].clazzs[k].stus[0].stuId);
									} else {

										$("#stuIdSpan").text("");

									}

								}

							}

							//动态改变课程信息select_coruse_id
							for (var k = 0; k < depMsg[depNum].majors[majorNum].courses.length; k++) {
								$("#select_coruse_id")
										.append(
												"<option value='"+depMsg[depNum].majors[majorNum].courses[k].courseId+"'>"
														+ depMsg[depNum].majors[majorNum].courses[k].courseName
														+ "</option>");

							}

						});

		//当 年级选择状态改变时给院系动态的注入值
		$("#select_grade_clazz_id")
				.change(
						function() {
							//移除专业系option的text
							$("#select_clazz_id").find("option").remove();
							$("#select_stuName_id").find("option").remove();
							$("#stuIdSpan").text("");
							//获取当前选择的院系value值 即数组值	  
							var depNum = parseInt($("#select_dep_id").val());
							var majorNum = parseInt($("#select_major_id").val());
							//获取选中option的年级text的值
							var gradeClassVlue = $("#select_grade_clazz_id")
									.find("option:selected").text();

							for (var k = 0; k < depMsg[depNum].majors[majorNum].clazzs.length; k++) {
								//当前所在院系专业 班级下的年级级别
								var gradeClassNowValue = depMsg[depNum].majors[majorNum].clazzs[k].gradeClass.gradeClassName;

								//获取当前年级下的年级信息

								if (gradeClassVlue == gradeClassNowValue) {

									$("#select_clazz_id")
											.append(
													"<option value='"+depMsg[depNum].majors[majorNum].clazzs[k].classId+"'>"
															+ depMsg[depNum].majors[majorNum].clazzs[k].className
															+ "</option>");

									//改变学生信息
									for (var s = 0; s < depMsg[depNum].majors[majorNum].clazzs[k].stus.length; s++) {

										$("#select_stuName_id")
												.append(
														"<option value='"+depMsg[depNum].majors[majorNum].clazzs[k].stus[s].stuId+"'>"
																+ depMsg[depNum].majors[majorNum].clazzs[k].stus[s].stuName
																+ "</option>");

									}

									//动态改变学生id信息
									if (depMsg[depNum].majors[majorNum].clazzs[k].stus.length) {

										$("#stuIdSpan")
												.text(
														depMsg[depNum].majors[majorNum].clazzs[k].stus[0].stuId);
									} else {

										$("#stuIdSpan").text("");

									}

								}

							}
						});

		//当学生姓名改变时改变学生id
		$("#select_stuName_id").change(function() {

			$("#stuIdSpan").text($("#select_stuName_id").val());

		})

		

		//**********************************
		//添加模态框  动态选择数据结束
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

									//加默认值 option 不选择
									$("#search_condition_major").append(
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
				
		
		$("#insert").click(function (){
			
			var stuId=$("#select_stuName_id").val();
		
			var awadrs = $("input[name='unit']:checked").val();
			var reason=$("#description").val();
			
	      
			var dataT=$("#test1").val();
		    var stand=/^\d{4}[-]\d{2}[-]\d{2}$/;
		    
		    if(stand.test(dataT)){
		    	
		    	
				$.ajax({
					//指定请求地址的url
					url : "${ctx}/student/AwardOrPunish/insert",
					
					data:"stuId="+stuId+"&awadrs="+awadrs+"&reason="+reason+"&dataT="+dataT,
					type : "post",

					//预期服务器返回的数据类型
					dateType : "json",
					//服务器响应成功时候的回调函数
					success : function(result) {
						//隐藏模态框
						$('#CreateStuMsgModal').modal(
								'hide')
						 
								window.location.href="/student/AwardOrPunish/index?keywords=${keywords}&universityDepId=${universityDepId}&majorId=${majorId }&gradeClassId=${gradeClassId}&clazzId=${clazzId }"
					},
					error : function(xhr, textStatus, err) {//服务器响应失败时候的回调函数
						console.log(xhr);
						alert(xhr);
						alert(textStatus);
						alert(err)

					}

				})
				
		    	
		    }else{
		    	alert("日期格式不正确");
		    }
			 
		
			
			
			
		})
			//导出excel
		$("#exportExcelBtn")
				.click(
						function() {
							//获取数据
							var keywords = $("#keyword").val();
							var dep = $("#dep").val();
							var major = $("#major").val();
							var gradeClass = $("#gradeClass").val();
							var clazz = $("#clazz").val();
							//带数据到controller
							window.location.href = "/student/AwardOrPunish/stuMsg_export?keywords="
									+ keywords
									+ "&universityDepId="
									+ dep
									+ "&majorId="
									+ major
									+ "&gradeClassId="
									+ gradeClass + "&clazzId=" + clazz;

						})

	$("#search").click(function() {
		$("#searchForm").submit();
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
								<li ><a href="${ctx }/student/grade/list">成绩管理</a></li>
								<li class="active"><a href="#">奖罚管理</a></li>
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






	<!--页眉结束-->
	<div class="container ">
		<!--表格上的搜索条件框-->

	


		<div class="row " style="margin-top: 100px">


			<!-- 第一行 搜索框 -->
			<div class="row  paddtop ">

		     <form action="${ctx }/student/AwardOrPunish/index" method="get" id="searchForm">
					<input type="hidden" id="dep" name="universityDepId"
						value="${universityDepId}" /> <input type="hidden" id="major"
						name="majorId" value="${majorId}" /> <input type="hidden"
						id="gradeClass" name="gradeClassId" value="${gradeClassId}" /> <input
						type="hidden" id="clazz" name="clazzId" value="${clazzId}" /> <input
						id="keyword" type="text" name="keywords" value="${keywords}"
						class="search-input" placeholder="请输入要搜索的信息" /><span id="search"
						class="search-btn" style='cursor: pointer'>搜&nbsp索</span>

				</form>



			</div>


			<!-- 第二行 主体 -->
			<div class="row jx">
				<img src="${ctx }/res/img/icons/star.jpg"> <span>学生奖罚管理<span>
			</div>




			<div class="row " style="margin-top: 20px;">



				<div class="col-md-1">

					<button class="btn btn-info" type="submit" data-toggle="modal"
						data-target="#CreateStuMsgModal">添加</button>

				</div>

				<div class="col-md-5">

					<button class="btn btn-info" type="submit" data-toggle="modal"
						data-target="#searchStuMsg">条件搜索</button>

				</div>



	





			</div>







			<!--结束-->

			<!--第二行开始-->


			<!--结束-->
		</div>
		<!--第二行-->
		<div class="row " style="margin-top: 30px;">
			<div class="col-md-12 ">
				<table class="table table-hover">
					<thead>
						<tr>
						
							<th>学号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>院系</th>							
							<th>专业</th>
							<th>班级</th>
							<th>年级</th>
							<th>奖罚数量</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="ItemList">
							<tr style='cursor: pointer' id="${ItemList.stuId }">
								<td>${ItemList.stuId }</td>
								<td>${ItemList.stuName}</td>
								<td>${ItemList.sex }</td>
								<td>${ItemList.depName }</td>
								<td>${ItemList.majorName }</td>
								<td>${ItemList.className }</td>
								<td>${ItemList.gradeClassName }</td>
						        <td>${ItemList.num }</td>
						        <td><a
									href="${ctx}/student/AwardOrPunish/details?stuId=${ItemList.stuId}&stuName=${ItemList.stuName}"
									style="color: #3399EA; font-size: 13px;">详情</a></td>
							</tr>
						</c:forEach>
					</tbody>
			</table>
			</div>
				<div class="row">
				<!-- 注意在分页的时候一定要把 条件搜索 和模糊查询的 相关的信息带到页面上去，否则会出错！！！！ -->
				<div style="margin-left: -800px">
					<z:pager pageIndex="${pageIndex}" pageSize="${pageModel.pageSize }"
						totalNum="${pageModel.totalNum }"
						submitUrl="index?pageIndex={0}&keywords=${keywords}&universityDepId=${universityDepId}&majorId=${majorId }&gradeClassId=${gradeClassId}&clazzId=${clazzId }"
						pageStyle="quotes"></z:pager>

				</div>



				<div style="margin-left: 1019px">
					<button class="btn btn-info" id="exportExcelBtn">导出</button>

				</div>
			</div>
		</div>
		<!--引入按钮第三行-->



	</div>

	


	<!-- 添加学生信息模态框 -->

	<div class="modal fade" id="CreateStuMsgModal">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">添加奖罚信息</h4>
				</div>
				<div class="modal-body ">
					<div align="center">
						<span style="color: red;"></span>
						<form name="articleform" class="form-horizontal" action=""
							enctype="multipart/form-data" method="post">

                            <div class="form-group">
								<label class="col-sm-3 control-label">院系：</label>
								<div class="col-sm-9">
									<select class="form-control" name="code" id="select_dep_id">
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">专业：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_major_id">


									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年级：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_grade_clazz_id">



									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">班级：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_clazz_id">


									</select>
								</div>
							</div>

						


							<div class="form-group">
								<label class="col-sm-3 control-label">学号：</label>
								<div class="col-sm-9">
									
									<span  id="stuIdSpan"></span>
								</div>
							</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">姓名：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_stuName_id">

									</select>
								</div>
							</div>



							<div class="form-group">
								<label class="col-sm-3 control-label">类型：</label>
								<div class="col-sm-9">

									<input type="radio" name="unit"  checked="checked"
										style="margin-left: 10px" value="奖" /><span style="margin-left: 12px">奖</span>
									<input type="radio" name="unit" 
										style="margin-left: 20px"  value="罚"/> <span style="margin-left: 10px;">罚</span>

								</div>
							</div>
							<div class="form-group" style="margin-bottom: 20px">
								<label class="col-sm-3 control-label">日期：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" name="storage"
										size="50" id="test1" placeholder="请选择日期">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">详情：</label>
								<div class="col-sm-9">
									<textarea rows="5" cols="60" class="form-control"
										name="description" id="description"></textarea>
								</div>
							</div>

							<input type="button" id="insert" class="btn btn-success btn-default"
								value="提&nbsp交" /> <input type="button"
								style="margin-left: 30px" class="btn btn-danger btn-default"
								data-dismiss="modal" value="取&nbsp消" />

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--添加学生信息模态框结束-->
<!--条件搜索学生信息模态框开始-->
	<div class="modal fade" id="searchStuMsg">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">条件搜索</h4>
				</div>
				<div class="modal-body ">
					<div align="center">
						<span style="color: red;"></span>
						<form name="articleform" class="form-horizontal"
							action="${ctx}/student/AwardOrPunish/index" enctype="multipart/form-data"
							method="get">
							<input type="hidden" name="keywords" value="${keywords }">
							<div class="form-group">
								<label class="col-sm-3 control-label">院系：</label>
								<div class="col-sm-9">
									<select class="form-control" name="universityDepId"
										id="search_condition_dep">

										<option value="">不选择</option>

									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">专业：</label>
								<div class="col-sm-9">
									<select class="form-control" name="majorId"
										id="search_condition_major">
										<option value="">不选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年级：</label>
								<div class="col-sm-9">
									<select class="form-control" name="gradeClassId"
										id="search_condition_grade">
										<option value="">不选择</option>
									</select>
								</div>
							</div>
							<div class="form-group" style="margin-bottom: 20px">
								<label class="col-sm-3 control-label">班级：</label>
								<div class="col-sm-9">
									<select class="form-control" name="clazzId"
										id="search_condition_clazz">
										<option value="">不选择</option>
									</select>
								</div>
							</div>
							<input type="submit" class="btn btn-success btn-default"
								value="搜&nbsp索" /> <input type="button"
								style="margin-left: 30px" class="btn btn-danger btn-default"
								data-dismiss="modal" value="关&nbsp闭" />


						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--条件搜索学生信息模态框结束-->


	<!--引入输入框弹出日历件要用的js-->
	<script src="${ctx }/res/laydate/laydate.js"></script>
	<!-- 改成你的路径 -->
	<script>
lay('#version').html('-v'+ laydate.v);

//执行一个laydate实例
laydate.render({
  elem: '#test1' //指定元素
});
</script>

</body>
</html>
