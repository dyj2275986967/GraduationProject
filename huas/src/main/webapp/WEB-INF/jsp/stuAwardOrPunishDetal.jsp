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
    <title>学生奖罚详情</title>

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
	 	<link rel="stylesheet" href="${ctx }/res/css/index.css">

<script>
$(function(){

	
	
	
	
	
	


	
	
	
	
	
$("#tab tr").dblclick(function(){
	
	$('#CreateStuMsgModal').modal(
	'show')
	
             var stuMsgs = new Array(11);

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

     

		if (stuMsgs[2] == "奖") {

			$("#j").prop("checked", "checked");

		} else {

			$("#f").prop("checked", "checked");
		}
	 
      
		$("#test1").val(stuMsgs[1]);
		hiddenid
	   $("#hiddenid").val(stuMsgs[0]);
		$("#description").val(stuMsgs[3])
	
})

  $("#insertBtn").click(function (){
	  //String id,String dataT,String reason
		
  	var dataT=$("#test1").val();
	  var reason=$("#description").val();
	  var id=$("#hiddenid").val();
	$.ajax({
		//指定请求地址的url
		url : "${ctx}/student/AwardOrPunish/update",
		//指定
		type : "post",

		data : "dataT=" + dataT
				+ "&reason=" + reason
				+ "&id=" + id,
		//预期服务器返回的数据类型
		dateType : "text",
		//服务器响应成功时候的回调函数
		success : function(result) {
     
			//隐藏模态框
			$('#CreateStuMsgModal').modal(
					'hide')
			window.location.href = "/student/AwardOrPunish/details?stuId=${stuId}&stuName=${stuName}"

		},
		error : function(xhr, textStatus,
				err) {//服务器响应失败时候的回调函数

			alert("更新失败，请仔细检查添加的信息");

		}

	})
	  
	   
	  
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
								<li class="active"><a href="${ctx }/student/AwardOrPunish/index">奖罚管理</a></li>
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
 <div  class="container " >
  
  <div class="row mymarginDetial">
   <!--修改个人信息页开始-->		
   <legend>Details:</legend>
  </div>

 <div class="row"> 
 <span><font color="red" size="3">姓名：${stuName } 学号：${stuId}</font></span>
 </div>
 

  				<div class="row" style="margin-top:3px">
				<table class="table table-hover table-bordered table-striped" id="tab">
				
					<thead>
						<tr>
						    <th style="width: 1%">奖罚号</th>
							<th style="width: 1%">时间</th>
							<th style="width: 1%">类型</th>
							<th style="width: 15%">描述</th>							
							<th style="width: 1%">操作</th>
						</tr>
					</thead>
					<tbody>
				
						<c:forEach items="${list}" var="ItemList">
						
							<tr style='cursor: pointer' >
							
							  <td>${ItemList.id}</td>
								<td><fmt:formatDate value="${ItemList.dataT}" pattern="yyyy-MM-dd" /></td>
								
							   <c:choose>
									<c:when test="${ItemList.awadrs=='奖' }">
										<td>${ItemList.awadrs}</td>
									</c:when>
									<c:otherwise>

										<td><font color="red">${ItemList.awadrs}</font></td>
									</c:otherwise>
								</c:choose>
								
								
								<td>${ItemList.reason}</td>
								
								

                     
								
							   <c:choose>
									<c:when test="${ItemList.awadrs=='奖' }">
									    <td><span id="create" style="color: #3399EA; font-size: 13px;">修改</span></td>
									</c:when>
									<c:otherwise>

										    <td><span id="create" style="color: #3399EA; font-size: 13px;">修改</span>&nbsp<a href="${ctx}/student/AwardOrPunish/delete?stuId=${ItemList.stuId}&stuName=${ItemList.stuName}&id=${ItemList.id}" style="color: #3399EA; font-size: 13px;">撤销</a></td>
									</c:otherwise>
								</c:choose>
							
							</tr>
						</c:forEach>

						
						
						
					</tbody>

					


				</table>
			</div>
  </div>			
<!-- 1，页脚部分 -->

	
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
								<label class="col-sm-3 control-label">学号：</label>
								<div class="col-sm-9">
									
								<input type="text" class="form-control" value="${stuId }" id="stuNames" size="50"
										disabled>
								</div>
							</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">姓名：</label>
								<div class="col-sm-9">
								<input type="text" class="form-control" value="${stuName }" id="stuNames" size="50"
										disabled>
								<input type="hidden" class="form-control"  id="hiddenid" size="50"
										disabled>
								</div>
							</div>
 
 						<div class="form-group">
								<label class="col-sm-3 control-label">类型：</label>
								<div class="col-sm-9">

									<input type="radio" name="unit"  checked="checked" id="j"
										style="margin-left: 10px" value="奖" /><span style="margin-left: 12px">奖</span>
									<input type="radio" name="unit"   id="f"
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

							<input type="button" id="insertBtn" class="btn btn-success btn-default"
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



 




  </body>
</html>
