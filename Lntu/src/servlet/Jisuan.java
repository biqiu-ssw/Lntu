package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dss.Course;
import dss.Student;

public class Jisuan extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Jisuan() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	try{
		request.setCharacterEncoding("utf-8");
		List<Course> courses= (List<Course>) request.getSession().getAttribute("courses");
		Student stu = (Student) request.getSession().getAttribute("student");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		float ave = Course.calAveScore(courses, begin, end);
		request.setAttribute("ave", ave);
		request.setAttribute("begin", begin);
		System.out.println(begin);
		request.setAttribute("end", end);
		request.getRequestDispatcher("../Main.jsp").forward(request, response);
	}catch(java.lang.NullPointerException e){
		response.sendRedirect("../Exception.jsp");
	}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
