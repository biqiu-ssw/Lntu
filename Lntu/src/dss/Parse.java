package dss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parse {
	private static Map<String, String> cookie;

	private static float GPA;

	public static boolean Login(String url, Student stu) throws IOException {
		Connection con = Jsoup.connect(url);
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("j_username", stu.getId());
		datas.put("j_password", stu.getPassword());
		Response login;
		boolean result = false;
		login = con.method(Method.POST).data(datas).execute();

		if (login != null) {

			Element div = Jsoup.parse(login.body()).getElementsByTag("title").first();
			// System.out.println(div.text());
			if (div != null && div.text().equals("本科生教务管理系统")) {
				result = true;
			}
			cookie = login.cookies();
		}
		return result;
	}

	// 解析成绩页面 并获得教务在线上的GPA
	public static List<Course> ParseCourses(String url) throws IOException {

		Document doc = Jsoup.connect(url).cookies(cookie).get();
		String str = doc.getElementsByTag("table").get(1).text();
		// System.out.println(str);
		int index1 = str.indexOf("平均学分绩是");
		int index2 = str.indexOf("，");
		GPA = Float.valueOf(str.substring(index1 + 6, index2));

		List<Course> cj = new ArrayList<Course>();// 保存所有课程成绩的泛型
		// doc = Jsoup.parse(in, "gbk");

		Element table = doc.getElementsByTag("table").get(2);

		// Elements chengjidan = table.getElementsByClass("infolist_common");//
		// 获取到学生所有课程成绩的集合
		Elements chengjidan = table.getElementsByAttributeValueContaining("style", "display");
		for (Element kecheng : chengjidan) {// 遍历课程成绩集合
			String cname = kecheng.child(1).text().trim();
			String score = kecheng.child(3).text().replace(Jsoup.parse("&nbsp;").text(), "");
			if ("".equals(score))
				score = "0.0";
			float credit = Float.valueOf(kecheng.child(4).text().trim());
			String type = kecheng.child(8).text().trim();
			String time = kecheng.child(9).text().trim();
			Course course = new Course(cname, score, credit, type, time);
			// 去除一部分双学位课程 好像是教务系统计算的问题 考试性质里包含双学位的不计算。 网页源码中双学位的课程是有学分的。
			if (type.indexOf("双学位") >= 0) {
				// System.out.println(cname+" +++ "+credit);
				continue;
			}
			if (course.getPoint() == 0f && type.equals("正常考试") && !cname.endsWith("*"))
				continue;
			System.out.println(cname + "  " + credit);
			cj.add(course);
		}
		List<Course> courses = new ArrayList<Course>();
		// 去除挂科科目

		// 课程学习时间排序 有效期2000-2100年间
		for (int year = 2000; year < 2100; year++) {

			for (Course course : cj) {
				if ((year + "春").equals(course.getTime()))
					courses.add(course);

			}
			for (Course course : cj) {
				if ((year + "秋").equals(course.getTime()))
					courses.add(course);
			}
		}

		cj = null;

		return courses;
	}

	// 获取教务在线上的GPA
	public static float getGPA() {
		return GPA;
	}
}
