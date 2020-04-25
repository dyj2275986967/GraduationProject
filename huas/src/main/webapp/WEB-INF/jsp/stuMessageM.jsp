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
<title>学生信息管理</title>
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

<link href="${ctx }/res/css/pager.css" rel="stylesheet">



<script type="text/javascript" src="${ctx }/res/js/tools.js"></script>

<script>
	$(
			function() {
				//***************
				//操作select属性开始
				//****************
				//存学院信息的对象
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
									//添加模态框
									$("#select_dep_id").append(
											"<option value='"+i+"'>"
													+ depMsg[i].depName
													+ "</option>");
									//条件搜索模态框 
									$("#search_condition_dep").append(
											"<option value='"+depMsg[i].majors[0].university.depId+"'>"
													+ depMsg[i].depName
													+ "</option>");
									//修改模态框  
									$("#select_updata_dep_id").append(
											"<option value='"+depMsg[i].majors[0].university.depId+"'>"
													+ depMsg[i].depName
													+ "</option>");

								}

								//写死 显示第一次进入的学院 的专业信息
								for (var i = 0; i < depMsg[0].majors.length; i++) {
									//添加模态框
									$("#select_major_id")
											.append(
													"<option value='"+i+"'>"
															+ depMsg[0].majors[i].majorName
															+ "</option>");
									//条件搜索模态框 
									//条件搜索模态框 初始化页面时 都默认是不选择  所以 不用拼装 专业信息
									//  $("#search_condition_major").append("<option value='"+depMsg[0].majors[i].clazzs[i].major.majorId+"'>"+depMsg[0].majors[i].majorName+"</option>");  
									//修改模态框
									$("#select_updata_major_id")
											.append(
													"<option value='"+depMsg[0].majors[i].clazzs[i].major.majorId+"'>"
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

										//	  $("#search_condition_clazz").append("<option value='"+depMsg[0].majors[0].clazzs[i].classId+"'>"+depMsg[0].majors[0].clazzs[i].className+"</option>");  
										$("#select_updata_clazz_id")
												.append(
														"<option value='"+depMsg[0].majors[0].clazzs[i].classId+"'>"
																+ depMsg[0].majors[0].clazzs[i].className
																+ "</option>");
									}

								}
								//写死 显示第一次进入的学院 的年级信息"

								for (var i = 0; i < gradeClassMsg.length; i++) {

									//添加模态框
									$("#select_grade_clazz_id")
											.append(
													"<option value='"+i+"'>"
															+ gradeClassMsg[i].gradeClassName
															+ "</option>");

									//条件搜索模态框
									$("#search_condition_grade")
											.append(
													"<option value='"+gradeClassMsg[i].clazzs[0].gradeClass.gradeClassId+"'>"
															+ gradeClassMsg[i].gradeClassName
															+ "</option>");

									$("#select_updata_grade_clazz_id")
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
				//添加模态框  动态选择数据开始
				//*********************************
				//当院系选择状态改变时给专业动态的注入值
				$("#select_dep_id")
						.change(
								function() {

									//移除专业系option的text
									$("#select_major_id").find("option")
											.remove();
									$("#select_clazz_id").find("option")
											.remove();
									//获取选中option的院系text的值
									var selectValue = $("#select_dep_id").find(
											"option:selected").text();
									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#select_grade_clazz_id").find(
											"option:selected").text();

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
																	"<option value='"+depMsg[i].majors[0].clazzs[k].classId+"'>"
																			+ depMsg[i].majors[0].clazzs[k].className
																			+ "</option>");

												}

											}

										}

									}

								});
				//当专业选择状态改变时给院系动态的注入值
				$("#select_major_id")
						.change(
								function() {
									//移除专业系option的text
									$("#select_clazz_id").find("option")
											.remove();
									//获取当前选择的院系value值 即数组值	  
									var depNum = parseInt($("#select_dep_id")
											.val());
									var majorNum = parseInt($(
											"#select_major_id").val());
									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#select_grade_clazz_id").find(
											"option:selected").text();
						
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
										}
									}

								});

				//当 年级选择状态改变时给院系动态的注入值
				$("#select_grade_clazz_id")
						.change(
								function() {
									//移除专业系option的text
									$("#select_clazz_id").find("option")
											.remove();
									//获取当前选择的院系value值 即数组值	  
									var depNum = parseInt($("#select_dep_id")
											.val());
									var majorNum = parseInt($(
											"#select_major_id").val());
									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#select_grade_clazz_id").find(
											"option:selected").text();

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

										}

									}
								});

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

				//**********************************
				//修改模态框  动态选择数据开始 
				//*********************************
				//当院系选择状态改变时给专业动态的注入值
				$("#select_updata_dep_id")
						.change(
								function() {

									//移除专业系option的text
									$("#select_updata_major_id").find("option")
											.remove();
									$("#select_updata_clazz_id").find("option")
											.remove();
									//获取选中option的院系text的值
									var selectValue = $("#select_updata_dep_id")
											.find("option:selected").text();

									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#select_updata_grade_clazz_id")
											.find("option:selected").text();

									for (var i = 0; i < depMsg.length; i++) {

										if (selectValue == depMsg[i].depName) {

											for (var j = 0; j < depMsg[i].majors.length; j++) {

												//判断专业下面是否有班级信息 没有就没必要拼接
												if (depMsg[i].majors[j].clazzs.length != 0) {

													$("#select_updata_major_id")
															.append(
																	"<option value='"+depMsg[i].majors[j].clazzs[0].major.majorId+"'>"
																			+ depMsg[i].majors[j].majorName
																			+ "</option>");

												}
												//拼接班级信息			  		 			

												//遍历班级信息
												for (var z = 0; z < depMsg[i].majors[j].clazzs.length; z++) {

													//   console.log(depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName);

													//判断 选择的年级与遍历的年级一致 ，就加信息
													if (gradeClassVlue == depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName) {

														$(
																"#select_updata_clazz_id")
																.append(
																		"<option value='"+depMsg[i].majors[j].clazzs[z].classId+"'>"
																				+ depMsg[i].majors[j].clazzs[z].className
																				+ "</option>");

													}

												}

											}

										}

									}

								});

				//当专业选择状态改变时给班级动态的注入值
				$("#select_updata_major_id")
						.change(
								function() {
									//移除班级option的text
									$("#select_updata_clazz_id").find("option")
											.remove();
									//获取当前选择的院系value值 即数组值	  
									var selectDepValue = $(
											"#select_updata_dep_id").find(
											"option:selected").text();
									var selectMajorValue = $(
											"#select_updata_major_id").find(
											"option:selected").text();

									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#select_updata_grade_clazz_id")
											.find("option:selected").text();

									//alert(gradeClassVlue);
									//循环遍历年级信息
									for (var i = 0; i < depMsg.length; i++) {

										//选择的院系和当前的院系一致
										if (selectDepValue == depMsg[i].depName) {
											//循环遍历专业信息
											for (var j = 0; j < depMsg[i].majors.length; j++) {
												//当前选择的专业和遍历的专业信息相同时 
												if (selectMajorValue == depMsg[i].majors[j].majorName) {
													//遍历班级信息
													for (var z = 0; z < depMsg[i].majors[j].clazzs.length; z++) {

														//   console.log(depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName);

														//判断 选择的年级与遍历的年级一致 ，就加信息
														if (gradeClassVlue == depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName) {

															$(
																	"#select_updata_clazz_id")
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
				//当年级选择状态改变时给班级动态的注入值
				$("#select_updata_grade_clazz_id")
						.change(
								function() {

									//移除班级option的text
									$("#select_updata_clazz_id").find("option")
											.remove();
									//获取当前选择的院系value值 即数组值	  
									var selectDepValue = $(
											"#select_updata_dep_id").find(
											"option:selected").text();
									var selectMajorValue = $(
											"#select_updata_major_id").find(
											"option:selected").text();

									//获取选中option的年级text的值
									var gradeClassVlue = $(
											"#select_updata_grade_clazz_id")
											.find("option:selected").text();

									//alert(gradeClassVlue);
									//循环遍历年级信息
									for (var i = 0; i < depMsg.length; i++) {

										//选择的院系和当前的院系一致
										if (selectDepValue == depMsg[i].depName) {
											//循环遍历专业信息
											for (var j = 0; j < depMsg[i].majors.length; j++) {
												//当前选择的专业和遍历的专业信息相同时 
												if (selectMajorValue == depMsg[i].majors[j].majorName) {
													//遍历班级信息
													for (var z = 0; z < depMsg[i].majors[j].clazzs.length; z++) {

														//   console.log(depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName);

														//判断 选择的年级与遍历的年级一致 ，就加信息
														if (gradeClassVlue == depMsg[i].majors[j].clazzs[z].gradeClass.gradeClassName) {

															$("#select_updata_clazz_id")
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

								})

				//**********************************
				//修改模态框  动态选择数据结束
				//*********************************

				//************************
				//操作select属性结束
				//**********************

				//************************
				//插入数据开始
				//**********************

				$("#insertBtn").click(
						
						function() {
							
					
							//isNaN(stuId)
							//获取当前的班级id
							var classId = parseInt($("#select_clazz_id").val())
							var stuId = $("#stuId").val();
							var stuName = $("#stuName").val();
							var nation = $("#nation").val();
							var phone = $("#phone").val();
							var sex = $('input:radio:checked').val();						
							if(stuId==""){alert("学号不能为空")
													
							}else{
								if(isNaN(stuId)){
									alert("学号只能是数字")
								}else{
									
									
									$.ajax({
										//指定请求地址的url
										url : "${ctx}/student/msg/insertStudent",
										//指定
										type : "post",
										
										data :"stuId=" + stuId + "&stuName=" + stuName
											+ "&nation=" + nation + "&sex=" + sex
											+ "&phone=" + phone + "&classId="
											+ classId,
										//预期服务器返回的数据类型
										dateType : "text",
										//服务器响应成功时候的回调函数
										success : function(result) {
											
										
											//隐藏模态框
											$('#createStuMsgModal').modal('hide')
											window.location.href="/student/msg/list?keywords=${keywords}&universityDepId=${universityDepId}&majorId=${majorId }&gradeClassId=${gradeClassId}&clazzId=${clazzId }";	

										},
										error : function(xhr, textStatus, err) {//服务器响应失败时候的回调函数

											alert("更新失败，请仔细检查添加的信息");

										}

									})
									
									
									
									
									
									
									
							
                                        }	
								
								
								
							}
						
							
							
						

						})

				//************************
				//插入数据结束
				//**********************		

				//************************
				//插入数据结束searchBtn
				//**********************	

				//************************
				//修改数据开始
				//**********************	

				var stuMsg = new Array(11);
				//给模态框注入数据
				$("#tab tr").dblclick(function() {
					//获取选中表格行的下标
					var index = $(this).closest("tr").index() + 1;
					//准备存放数据的数组

					$('#tab tr').each(function(i) { // 遍历 tr
						$(this).children('td').each(function(j) { // 遍历 tr 的各个 td		    	
							if (i == index) {
								//将数据存放在数组里面
								stuMsg[j] = $(this).text();
							}

						});
					});
					//给模态框的输入框注入值
					//$("#select_updata_dep_id").val(stuMsg[8]);
					//$("#select_updata_major_id").val(stuMsg[7]);
				//	$("#select_updata_grade_id").val(stuMsg[9]);
					//$("#select_updata_clazz_id").val(stuMsg[6]);
					$("#stuIds").val(stuMsg[0]);
					$("#stuNames").val(stuMsg[1]);
					$("#nations").val(stuMsg[3]);
					$("#phones").val(stuMsg[4]);
			
					if (stuMsg[2] == "男") {
					
						
						$("#man").prop("checked", "checked");

					} else {
					
						
						$("#woman").prop("checked", "checked");
					}				 
																												
            
			var dep = "option:contains(" + stuMsg[7] + ")";
			var major = "option:contains(" + stuMsg[6] + ")";
			var  grade = "option:contains(" + stuMsg[8] + ")";
			var clzaa = "option:contains(" + stuMsg[5] + ")";
		
			$("#select_updata_dep_id").find(dep).attr("selected", true);
			$("#select_updata_dep_id").trigger("change")
  			$("#select_updata_major_id").find(major).attr("selected", true);
			$("#select_updata_major_id").trigger("change")
			
			$("#select_updata_grade_clazz_id").find(grade).attr("selected", true);
			$("#select_updata_grade_clazz_id").trigger("change")
	
			
      		$("#select_updata_clazz_id").find(test).attr("selected", true);
			$("#select_updata_clazz_id").trigger("change")
			
			
			
			//	$("#select_updata_dep_id").val("数理学院");
			//打开修改模态框
			$("#createStuMsgModal").modal('show');

		});

		//发送ajax请求
		//更新数据
		$("#updateBtn").click(

				function() {
				
					//获取当前的班级id
					var classId = parseInt($("#select_updata_clazz_id").val());

					var stuId = $("#stuIds").val();
					var stuName = $("#stuNames").val();
					var nation = $("#nations").val();
					var phone = $("#phones").val();
					var oldStuId = stuMsg[0];
					var sex = $("input[name='updateRadio']:checked").val();
				
					
					if(stuId==""){alert("学号不能为空")
						
					}else{
						if(isNaN(stuId)){
							alert("学号只能是数字")
						}else{
							
							$.ajax({
								//指定请求地址的url
								url : "${ctx}/student/msg/updataStudent",
								//指定
								type : "post",
								//+"&nation="+nation+"&sex="+sex+"&phone="+phone+"&classId="+classId
								data : "stuId=" + stuId + "&stuName=" + stuName
										+ "&nation=" + nation + "&sex=" + sex
										+ "&phone=" + phone + "&classId=" + classId
										+ "&oldStuId=" + oldStuId,
								//预期服务器返回的数据类型
								dateType : "text",
								//服务器响应成功时候的回调函数
								success : function(result) {
									
								
									//隐藏模态框
									$('#createStuMsgModal').modal('hide')
									window.location.href="/student/msg/list?keywords=${keywords}&universityDepId=${universityDepId}&majorId=${majorId }&gradeClassId=${gradeClassId}&clazzId=${clazzId }"

								},
								error : function(xhr, textStatus, err) {//服务器响应失败时候的回调函数

									alert("更新失败，请仔细检查添加的信息");

								}

							})

							
						}
						
						
					}
					
					
					

					//$("#select_updata_dep_id option[value='数理学院']").prop("selected","selected");

					
				})

		//************************
		//修改数据结束
		//**********************	
		//搜索表单提交
		$("#search").click(function() {
			$("#searchForm").submit();
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
							window.location.href = "/student/msg/stuMsg_export?keywords="
									+ keywords
									+ "&universityDepId="
									+ dep
									+ "&majorId="
									+ major
									+ "&gradeClassId="
									+ gradeClass + "&clazzId=" + clazz;

						})

		//文件上传			
		$("#Excel_To_Lead").click(function() {

			$("#QueryForm").submit();

		})


		
		
		//监控添加按钮模态框的隐藏事件
		$('#CreateStuMsgModal').on('hidden.bs.modal', function() {
			//移除的全部信息防止再次打开时再次拼接
			// $("#select_dep_id").find("option").remove(); 
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
								<li class="active"><a href="#">信息管理</a></li>
								<li><a href="${ctx }/student/grade/list">成绩管理</a></li>
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



	<!--页眉结束-->
	<div class="container ">
		<!--表格上的搜索条件框-->


		<!--轮播图开始-->




		<div class="row " style="margin-top: 100px">


			<!-- 第一行 搜索框 -->
			<!-- 注意在模糊查询的时候一定要把 条件搜索 相关的信息带到页面上去，否则会出错！！！！ -->
			<div class="row  paddtop ">

				<form action="${ctx }/student/msg/list" method="get" id="searchForm">
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
				<img src="${ctx }/res/img/icons/star.jpg"> <span>学生信息管理</span>
			</div>


	


			<div class="row " style="margin-top: 20px;">



				<div class="col-md-1">

					<button class="btn btn-info" id="add" data-toggle="modal"
						data-target="#CreateStuMsgModal">添加</button>

				</div>

				<div class="col-md-1">

					<button class="btn btn-info" type="submit" data-toggle="modal"
						data-target="#searchStuMsg">条件搜索</button>

				</div>


				<div class="col-md-4" style="margin-left: 50px;"></div>



				<div class="col-md-3" style="margin-left: 150px;">


					<form id="QueryForm" action="${ctx}/student/msg/stuMsg_To_Lead"
						method="post" enctype="multipart/form-data">
						<div class="row">
							<div class="col-sm-3" style="width: 78%;">
								<div class="box box-primary">
									<div class="box-header with-border">

										<div class="box-body">
											<input id="excel_file" class="form-control" type="file"
												name="filename" accept="xlsx" size="80" />
										</div>
									</div>



								</div>
							</div>

						</div>
					</form>

				</div>

				<div class="col-md-1" style="margin-left: -80px;">


					<button class="btn btn-info" id="Excel_To_Lead">导入</button>


				</div>


			</div>
			<!--结束-->

			<!--第二行开始-->


			<!--结束-->
		</div>
		<!--第二行-->
		<div class="row " style="margin-top: 30px;">
			<div class="col-md-12 ">
				<table class="table table-hover" id="tab">
					<thead>
						<tr>

							<th>学号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>民族</th>
							<th>手机号</th>
							<th>班级</th>

							<th>专业</th>
							<th>院系</th>
							<th>年级</th>

							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="ItemList">
							<tr style='cursor: pointer' id="${ItemList.stuId }">



								<td>${ItemList.stuId }</td>
								<td>${ItemList.stuName }</td>
								<td>${ItemList.sex}</td>
								<td>${ItemList.nation }</td>
								<td>${ItemList.phone}</td>
								<td>${ItemList.className}</td>

								<td>${ItemList.majorName}</td>
								<td>${ItemList.depName}</td>
								<td>${ItemList.gradeClassName}</td>
								<td><a
									href="${ctx }/student/msg/delete?stuId=${ItemList.stuId }"
									style="color: #3399EA; font-size: 13px;">删除</a></td>
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
						submitUrl="list?pageIndex={0}&keywords=${keywords}&universityDepId=${universityDepId}&majorId=${majorId }&gradeClassId=${gradeClassId}&clazzId=${clazzId }"
						pageStyle="quotes"></z:pager>

				</div>



				<div style="margin-left: 1044px">
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
					<h4 class="modal-title" id="exampleModalLabel">添加学生信息</h4>
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
									<input type="text" class="form-control" id="stuId" size="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">姓名：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="stuName" size="50">
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-3 control-label">性别：</label>
								<div class="col-sm-9">

									<input type="radio" name="aa" value="男" checked="checked"
										style="margin-left: 10px" /><span style="margin-left: 12px">男</span>
									<input type="radio" name="aa" value="女"
										style="margin-left: 20px" /> <span style="margin-left: 10px;">女</span>

								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">民族：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="nation" size="50">
								</div>
							</div>
							<div class="form-group" style="margin-bottom: 20px">
								<label class="col-sm-3 control-label">手机：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="phone" size="50">
								</div>
							</div>



							<input type="button" class="btn btn-success btn-default"
								value="提&nbsp交" id="insertBtn" /> <input type="button"
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
							action="${ctx}/student/msg/list" enctype="multipart/form-data"
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
	<!-- 修改学生信息模态框开始 -->

	<div class="modal fade" id="createStuMsgModal">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">修改学生信息</h4>
				</div>
				<div class="modal-body ">
					<div align="center">
						<span style="color: red;"></span>
						<form name="articleform" class="form-horizontal" action=""
							enctype="multipart/form-data" method="post">

							<div class="form-group">
								<label class="col-sm-3 control-label">院系：</label>
								<div class="col-sm-9">
									<select class="form-control" name="code"
										id="select_updata_dep_id">
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">专业：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_updata_major_id">


									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年级：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_updata_grade_clazz_id">



									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">班级：</label>
								<div class="col-sm-9">
									<select class="form-control" id="select_updata_clazz_id">


									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">学号：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="stuIds" size="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">姓名：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="stuNames" size="50">
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-3 control-label">性别：</label>
								<div class="col-sm-9">

									<input type="radio" name="updateRadio" id="man" value="男"
										checked="checked" style="margin-left: 10px" /><span
										style="margin-left: 12px">男</span> <input type="radio"
										name="updateRadio" id="woman" value="女"
										style="margin-left: 20px" /> <span style="margin-left: 10px;">女</span>

								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">民族：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="nations" size="50">
								</div>
							</div>
							<div class="form-group" style="margin-bottom: 20px">
								<label class="col-sm-3 control-label">手机：</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="phones" size="50">
								</div>
							</div>



							<input type="button" class="btn btn-success btn-default"
								value="提&nbsp交" id="updateBtn" /> <input type="button"
								style="margin-left: 30px" class="btn btn-danger btn-default"
								data-dismiss="modal" value="取&nbsp消" />

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改学生信息模态框开始 -->

</body>
</html>
