<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>辽工大加权成绩&GPA计算助手 仅支持本科生</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript"> 
		onload=function(){ //实现倒计时跳转
			setInterval(go, 1000); 
		}; 
		var x=3;
		function go(){ 
			x--; 
			if(x>0){ 
			document.getElementById("ji").innerHTML=x; 
			}else{ 
				location.href='index.jsp';  
			} 
		} 
</script> 
  <body>
  <div style="text-align: ;background-color:rgba(227, 241, 240, 0.55);">
  <div style="text-align: center;margin-top: 150px;margin-right: auto;"><br><br><br>
   <h1>用户名和密码不匹配！</h1><br>
   <h2><label id="ji">3</label>秒后自动跳转登录页面</h2>
   <!-- <input  class="pure-button pure-button-active" type="button" value="返回" onclick="back()"> --><br><br><br>
   </div></div>
<!-- <script language="javascript">
function back(){
	window.history.back(-1);
} -->
  </body>
</html>
