<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>error</title>
<!--springboot改图标-->
<link href="${ctx }/res/img/favicon.ico"  type="image/x-icon" rel="icon">
<style>
*{margin:0;padding:0;outline:none;font-family:\5FAE\8F6F\96C5\9ED1,å®ä½;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;-khtml-user-select:none;user-select:none;cursor:default;font-weight:lighter;}
.center{margin:0 auto;}
.whole{width:100%;height:100%;line-height:100%;position:fixed;bottom:0;left:0;z-index:-1000;overflow:hidden;}
.whole img{width:100%;height:100%;}
.mask{width:100%;height:100%;position:absolute;top:0;left:0;background:#000;opacity:0.6;filter:alpha(opacity=60);}
.b{width:100%;text-align:center;height:400px;position:absolute;top:50%;margin-top:-230px}.a{width:150px;height:50px;margin-top:30px}.a a{display:block;float:left;width:150px;height:50px;background:#fff;text-align:center;line-height:50px;font-size:18px;border-radius:25px;color:#333}.a a:hover{color:#000;box-shadow:#fff 0 0 20px}
p{color:#fff;margin-top:40px;font-size:24px; line-height:56px;}
#num{margin:0 5px;font-weight:bold;}
</style>

<script type="text/javascript">
	var num=6;
	function redirect(){
		num--;
		document.getElementById("num").innerHTML=num;
		if(num<0){
			document.getElementById("num").innerHTML=0;
			window.location.href = "/student/msg/list";
			}
		}
	setInterval("redirect()", 1000);
</script>
</head>

<body onLoad="redirect();">
<div class="whole">

	<img src="${ctx}/res/img/errorPageImg/back.jpg" />
    <div class="mask"></div>
</div>
<div class="b">
<span class="center"  style="font-size:100px;color:white;font-weightï¼bold">500</span>
		
	  <p>
			服务器响应错误<br>
			错误原因：${errorMsg}
			 <br>
            <span id="num"></span>秒后自动跳转到主页
		</p>
	</div>

</body>
</html>
