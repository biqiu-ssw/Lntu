<%@ page language="java" import="java.util.*" import="dss.Course" pageEncoding="utf-8" errorPage="Exception.jsp"%>
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
<link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
  </head>
  <body>
<style type="text/css">
h3{
	text-align: center;

}
</style>
<input class="pure-button pure-button-active"  type="button" value="返回" onclick="back()">
  <h3>最终成绩单</h3>
  <div style="text-align: center;">
  	<table class="pure-table pure-table-bordered" style="margin: auto;">
  		<thead>
  		<tr>
  			<th>ID</th>
  			<th>课程</th>
  			<th>学习时间</th>
  			<th>成绩</th>
  		</tr>
  		</thead>
  		<%
  			int i=1;
  			List<Course> fc =(List)session.getAttribute("fc");
  			for(Course course:fc){
  				out.print("<tr>");
  				out.print("<td>"+i+++"</td>");
  				out.print("<td>"+course.getCname()+"</td>");
  				out.print("<td>"+course.getTime()+"</td>");
  				out.print("<td>"+course.getScore()+"</td>");
  				out.print("</tr>");
  				
  			}
  		 %>
  	</table></div>
  	<h3>补考通过成绩单</h3><div style="text-align: center;">
  	<table class="pure-table pure-table-bordered" style="margin: auto;">
  		<thead>
  		<tr>
  			<th>ID</th>
  			<th>课程</th>
  			<th>学习时间</th>
  			<th>成绩</th>
  		</tr>
  		</thead>
  		<%
  			int j=1;
  			List<Course> pc =(List)session.getAttribute("pc");
  			for(Course course:pc){
  				out.print("<tr>");
  				out.print("<td>"+j+++"</td>");
  				out.print("<td>"+course.getCname()+"</td>");
  				out.print("<td>"+course.getTime()+"</td>");
  				out.print("<td>"+course.getScore()+"</td>");
  				out.print("</tr>");
  				
  			}
  		 %>
  	</table></div>
  	<h3>补考未通过成绩单</h3><div style="text-align: center;">&nbsp;
  	<table class="pure-table pure-table-bordered" style="margin: auto;">
  		<thead>
  		<tr>
  			<th>ID</th>
  			<th>课程</th>
  			<th>学习时间</th>
  			<th>成绩</th>
  		</tr>
  		</thead>
  		<%
  			int k=1;
  			List<Course> nc =(List)session.getAttribute("nc");
  			for(Course course:nc){
  				out.print("<tr>");
  				out.print("<td>"+k+++"</td>");
  				out.print("<td>"+course.getCname()+"</td>");
  				out.print("<td>"+course.getTime()+"</td>");
  				out.print("<td>"+course.getScore()+"</td>");
  				out.print("</tr>");
  				
  			}
  		 %>
  	</table></div>
  	<div style="text-align: center;margin-top: 40px;">
  	<input class="pure-button pure-button-active" type="button" value="复制成绩">&nbsp;&nbsp;&nbsp;
  	<input  class="pure-button pure-button-active" type="button" value="关于软件" onclick="link()">
     <script type="text/javascript">
     function link(){
     	window.location.href="About.jsp";
     }
     </script>
     
     </div>
<script language="javascript">
function back(){
	window.history.back(-1);
}
</script>
  	<ABLE>
  </body>
</html>
