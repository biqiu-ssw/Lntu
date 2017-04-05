<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关于软件</title>
<link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
</head>
<body>
<input class="pure-button pure-button-active" type="button" value="返回" onclick="back()">
<div style="text-align: center;">
当前在线人数:<%=application.getAttribute("onLineCount") %>
历史访问人数:<%=application.getAttribute("count") %>
</div>
<div style="text-align: center;"><p> 工大加权成绩&GPA计算助手是辽工大软件14-3班学生董帅帅(http://www.cnblogs.com/startnow/)<br>和张涛(https://github.com/importtao)在课下写的一个软件用java语言写的。<br>
主要是为了帮助大家快速算出加权平均分和GPA的。数据来源是用学号密码登录后，<br>从教务在线上抓取的数据，然后筛选(主要把正考<60的成绩(除选修)不纳入计算)进行计算的。</p></div>
<script language="javascript">
function back(){
	window.history.back(-1);
}

</script>
</body>
</html>