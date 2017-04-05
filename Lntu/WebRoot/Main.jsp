<%@page import="java.util.ArrayList"%>
<%@ page language="java" import="dss.Student" import="java.util.List" import="dss.Parse" import="dss.Course" pageEncoding="utf-8" errorPage="Exception.jsp"%>
<%
	Student student =(Student)session.getAttribute("student");
	String  id = student.getId();
	List<Course> courses  = (List)session.getAttribute("courses");
	session.setAttribute("ave", "");
	session.setAttribute("courses", courses);
	session.setAttribute("student", student);
	float GPA = Parse.getGPA();//获取教务在线绩点
	Course.save(courses, student.getId());//保存成绩单
	List<String> terms = Course.getDistinctTerm(courses);//获取学期集合
	String over = terms.get(0);
			for (String term : terms) {//获取到学期
				
				over = term;
			}
	float GPA1 = Course.calGPA(courses, terms.get(0), over);
	
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
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
 <script type="text/javascript"> 
		
			document.getElementById("ji").innerHTML=id; 
			
</script> 
<style type="text/css">
span{
	font-size: 30px;

}
</style>
  </head>
  
  <body>
   <div class="uname" style="text-align: right;"><h1>欢迎使用:<%= id%></h1></div>
   <div style="text-align: center;margin-top: 50px;background-color:whitesmoke;"><br>
    你的教务在线学分绩为:<span><%=GPA %></span><br><br>
    通过本系统计算所得学分绩为:<span><%=GPA1 %></span><br><br>
    <form name="jisuan" action="Chengji" method="post" class="pure-form">
    <input class="pure-button pure-button-active" type="submit" value="生成成绩单">
    </form><br>
    </div>
    <div style="text-align: center;padding-top: 20px;background-color:whitesmoke;">
    <form name="jisuan" action="Jisuan" method="post" class="pure-form">
   起始时间 <select name="begin">
    	<% 
    	for(String term:terms){
    		
			out.print("<option value=\""+term+"\">"+term+"</option>");
		}
    	
    	%> </select><br><br>
    结束时间 <select name="end">
    	<% 
    	for(String term:terms){
			out.print("<option value=\""+term+"\">"+term+"</option>");
			System.out.println(term);
		}
    	
    	%>
    </select>
    <br><br>
    <input class="pure-button pure-button-active" type="submit" value="计算加权">
    </form>
    <br><label><%
    		String ave = request.getAttribute("ave").toString();
    		if(!ave.equals(" ")){
    			out.print("你"+request.getAttribute("begin")+"--"+request.getAttribute("end")+"的加权平均分为:<span>"+ ave+"</span>");
    		}
    	%>
    	</label>
    	<br><br>
    	</div>
    	<div style="text-align: center;padding-top: 20px;background-color:whitesmoke;">
    	<input class="pure-button pure-button-active" type="button" value="关于软件" onclick="link()">
    	<br><br><br><br><br><br>
    	</div>
   <%--  <%
    	out.print("<div>教务在线学分绩为</div>");
    	out.print("<selection></selection")
    
     %>> --%>
     
     <script type="text/javascript">
     function link(){
     	window.location.href="../About.jsp";
     }
     </script>
  </body>
</html>
