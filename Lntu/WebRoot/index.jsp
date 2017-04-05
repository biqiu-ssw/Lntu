<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <%
   
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>辽工大加权成绩&GPA计算助手 仅支持本科生</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
<script type="text/javascript" src="js/script.js"></script>
</head>
<body>
<div class="them" >辽工大加权成绩&GPA计算助手<br>（仅支持本科生）</div>
<form action="servlet/Check" name="loginform" method="post" class="pure-form">
	学号:<input type="text" id="xh" name="j_username" onblur="xuehao()"><br>
	<span id="s1">学号不能为空</span><br>
	密码:<input type="password" id="ps" name="j_password" onblur="password()"><br>
	<span id="s2">密码不能为空</span><br>
	<input type="radio" name="port" value="1" checked="checked">端口一
	<input type="radio" name="port" value="2">端口二<br>
	<input type="button" value="登录" style="margin-top: 30px;" onclick="login()" class="pure-button pure-button-active">
</form>
<div style="text-align: center;margin-top: 50px;">
<input class="pure-button pure-button-active" type="button" value="关于软件" onclick="link()"></div>
     <script type="text/javascript">
     function link(){
     	window.location.href="About.jsp";
     }
     </script>
</body>
</html>