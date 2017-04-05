package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import dss.Course;

public class Chengji extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Chengji() {
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
		try{
		List<Course> courses  = (List<Course>) request.getSession().getAttribute("courses");
		List<Course> bukao = new ArrayList<Course>();
		List<Course> chongxue = new ArrayList<Course>();
		/*File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();

		String desktopPath = desktopDir.getAbsolutePath();

		File dir = new File(desktopPath + "\\" + id);
		if (!dir.exists()) {
			dir.mkdirs();
		}*/
		// ɸѡ������������������(ѧ)
		for (Course course : courses) {
			// �����ɼ�
			if ("����".equals(course.getType()))
				bukao.add(course);
			// ���޳ɼ�
			if ("��ѧ".equals(course.getType()))
				chongxue.add(course);

		}
		/*PrintStream passExam = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "����ͨ��.xls")));
		PrintStream notPassExam = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "����δͨ��.xls")));
		PrintStream finalReport = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "���ճɼ���.xls")));*/
		List<Course> pc = new ArrayList<Course>();//����ͨ��
		List<Course> nc = new ArrayList<Course>();//����δͨ��
		List<Course> fc = new ArrayList<Course>();//���ճɼ���

		// ��������ͨ�� ����δͨ��
		int i = 1, j = 1;

		for (Course course : bukao) {
			// System.out.println(course.getCname() + " " + course.getScore());
			String score = course.getScore();
			Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
			boolean isfloat =  pattern.matcher(score).matches();
			if ((isfloat && Float.valueOf(score) < 60) || "������".equals(score) || "���ϸ�".equals(score)) {
				//notPassExam
						//.println(i++ + "\t" + course.getCname() + "\t" + course.getTime() + "\t" + course.getScore());
				nc.add(course);
			} else {
				//passExam.println(j++ + "\t" + course.getCname() + "\t" + course.getTime() + "\t" + course.getScore());
				pc.add(course);
			}
		}

		//notPassExam.close();
		//passExam.close();
		i = 1;
		for (Course course : courses) {
			fc.add(course);
			//finalReport.println(i++ + "\t" + course.getCname() + "\t" + course.getTime() + "\t" + course.getScore());
		}
		//finalReport.close();
		HttpSession session =  request.getSession();
		session.setAttribute("pc", pc);
		session.setAttribute("nc", nc);
		session.setAttribute("fc", fc);
		System.out.println("�ɼ������ɳɹ�");
		request.getRequestDispatcher("../Chengji.jsp").forward(request, response);
	}catch(java.lang.NullPointerException e){
			response.sendRedirect("../Exception.jsp");
		}
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
		this.doGet(request, response);
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
