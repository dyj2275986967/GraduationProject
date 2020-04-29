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
<title>学生成绩管理图表展示</title>
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
<link rel="stylesheet" href="${ctx }/res/css/index.css">
<script>
	$(function() {

		//存及格 学院名  及及格人数
		var nameData = new Array();
		var passValueData = new Array();
		var passNValueData = new Array();

		//存不及格 学院名  及不及格人数 饼状图
		var bztJsonDate = null;
		var notPassValueData = new Array();
		var notPassNameData = new Array();
		$
				.ajax({
					//指定请求地址的url
					url : "${ctx}/student/grade/detail/showMsg",
					//指定
					type : "post",

					//预期服务器返回的数据类型
					dateType : "json",
					//服务器响应成功时候的回调函数
					success : function(result) {
						var dataArray = JSON.parse(result);

						bztJsonDate = dataArray.notPass;
						console.log(dataArray);
						//遍历及格和不及格的成绩
						for (var i = 0; i < dataArray.passOrNotPass.length; i++) {

							nameData[i] = dataArray.passOrNotPass[i].name;
							passValueData[i] = dataArray.passOrNotPass[i].pass;
							passNValueData[i] = dataArray.passOrNotPass[i].notpass;

						}
						//遍历不及格成绩
						for (var i = 0; i < dataArray.notPass.length; i++) {

							notPassNameData[i] = dataArray.notPass[i].name;
							notPassValueData[i] = dataArray.notPass[i].value;

						}

						console.log(notPassValueData);

						/* ===*/
						/* ===*/
						//学院名
						var depName = nameData;
						//及格
						var pass = passValueData;
						//不及格
						var notPass = passNValueData;

						var juniorservice = echarts.init(document
								.getElementById('juniorservice'));

						option = {
							tooltip : {
								trigger : 'axis',
								axisPointer : { // 坐标轴指示器，坐标轴触发有效
									type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
								}
							},
							color : [ '#22ac38', 'red' ],
							legend : {
								right : '0',
								data : [ '及格', '不及格' ],
								textStyle : {
									color : 'black'
								}
							},
							grid : {
								left : '8%',
								right : '4%',
								bottom : '3%',
								top : '10%',
								containLabel : true
							},
							xAxis : {
								type : 'value',
								splitLine : {
									show : true,
									lineStyle : {
										color : '#1e2b43'
									}
								},
								axisLine : {
									show : false,
									lineStyle : {
										color : '#115372'
									}
								},
								axisTick : {
									show : false
								},
								axisLabel : {
									textStyle : {
										color : "#fff"
									},
									alignWithLabel : true,
									interval : 0

								}
							},
							dataZoom : [ {
								type : 'slider',
								yAxisIndex : 0,
								filterMode : 'empty',
								start : 0,
								x : '0',
								end : 60,
								handleStyle : {
									color : "#519cff",
									backgroundColor : '#519cff'
								},
								textStyle : {
									color : "#fff"
								},
								borderColor : "#519cff"
							} ],
							yAxis : {
								type : 'category',
								data : depName,
								splitLine : {
									show : false,
									lineStyle : {
										color : '#1e2b43'
									}
								},

								axisTick : {
									show : false
								},
								axisLine : {
									show : true,
									lineStyle : {
										color : '#115372'
									}
								},
								axisLabel : {
									textStyle : {
										color : "black"
									},
									lineStyle : {
										color : '#519cff'
									},
									alignWithLabel : true,
									interval : 0
								}
							},
							series : [ {
								name : '及格',
								type : 'bar',
								stack : '比例',
								label : {
									normal : {
										show : true,
										position : 'insideRight',
										textStyle : {
											color : '#333'
										}
									}

								},
								data : pass
							}, {
								name : '不及格',
								type : 'bar',
								stack : '比例',
								label : {
									normal : {
										show : true,
										position : 'right',
										textStyle : {
											color : 'black'
										}

									}
								},
								data : notPass
							} ]
						};
						juniorservice.setOption(option);

						//统计图调用函数结束

						//饼状图调用函数开始

						var professionrate = echarts.init(document
								.getElementById('professionrate'));
						option = {
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} ({d}%)"
							},
							legend : {
								orient : 'vertical',
								right : '0',
								y : 'middle',
								textStyle : {
									color : "#fff"
								},
								//学院名
								data : notPassNameData,
								formatter : function(name) {
									var oa = option.series[0].data;
									var num = oa[0].value + oa[1].value
											+ oa[2].value;
									for (var i = 0; i < option.series[0].data.length; i++) {
										if (name == oa[i].name) {
											return name + ' ' + oa[i].value;
										}
									}
								}
							},
							series : [ {
								name : '不及格人数对比图',
								type : 'pie',
								radius : '50%',
								center : [ '35%', '50%' ],
								//放数据
								data : bztJsonDate,
								itemStyle : {
									emphasis : {
										shadowBlur : 10,
										shadowOffsetX : 0,
										shadowColor : 'rgba(0, 0, 0, 0.5)'
									}
								},
								itemStyle : {
									normal : {
										label : {
											show : true,
											position : 'outside',
											formatter : '  {b}'
										}
									},
									labelLine : {
										show : true
									}
								}
							} ]
						};
						professionrate.setOption(option);
					},
					error : function(xhr, textStatus, err) {//服务器响应失败时候的回调函数

						alert("更新失败，请仔细检查添加的信息");

					}

				})

	});
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


		</div>

	</header>
	<div class="container ">


		<div class="row mymarginDetial">
			<!--修改个人信息页开始-->
			<legend>Details:</legend>
		</div>


		<!--第一行开始-->








		<div class="row mymarginTop">


			<div class="col-md-6">

				<div class="border-container">
					<div class="name-title">&nbsp</div>
					<div id="juniorservice"></div>
					<span class="top-left border-span"></span> <span
						class="top-right border-span"></span> <span
						class="bottom-left border-span"></span> <span
						class="bottom-right border-span"></span>



				</div>
			</div>

			<div class="col-md-6">



				<div class="border-container">

					<div class="name-titleT">&nbsp</div>

					<div id="professionrate"></div>



					<span class="top-left border-span"></span> <span
						class="top-right border-span"></span> <span
						class="bottom-left border-span"></span> <span
						class="bottom-right border-span"></span>


				</div>



			</div>
		</div>







		<!--主体结束-->
	</div>


	<script src="${ctx }/res/js/jquery.js"></script>
	<script src="${ctx }/res/js/jquery.fancyspinbox.js"></script>



	<!--引入统计图插件要用的js-->
	<script src="${ctx }/res/js/echarts.min.js"></script>
	<script src="${ctx }/res/js/index.js"></script>







</body>
</html>
