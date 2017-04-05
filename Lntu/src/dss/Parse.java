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
			if (div != null && div.text().equals("�������������ϵͳ")) {
				result = true;
			}
			cookie = login.cookies();
		}
		return result;
	}

	// �����ɼ�ҳ�� ����ý��������ϵ�GPA
	public static List<Course> ParseCourses(String url) throws IOException {

		Document doc = Jsoup.connect(url).cookies(cookie).get();
		String str = doc.getElementsByTag("table").get(1).text();
		// System.out.println(str);
		int index1 = str.indexOf("ƽ��ѧ�ּ���");
		int index2 = str.indexOf("��");
		GPA = Float.valueOf(str.substring(index1 + 6, index2));

		List<Course> cj = new ArrayList<Course>();// �������пγ̳ɼ��ķ���
		// doc = Jsoup.parse(in, "gbk");

		Element table = doc.getElementsByTag("table").get(2);

		// Elements chengjidan = table.getElementsByClass("infolist_common");//
		// ��ȡ��ѧ�����пγ̳ɼ��ļ���
		Elements chengjidan = table.getElementsByAttributeValueContaining("style", "display");
		for (Element kecheng : chengjidan) {// �����γ̳ɼ�����
			String cname = kecheng.child(1).text().trim();
			String score = kecheng.child(3).text().replace(Jsoup.parse("&nbsp;").text(), "");
			if ("".equals(score))
				score = "0.0";
			float credit = Float.valueOf(kecheng.child(4).text().trim());
			String type = kecheng.child(8).text().trim();
			String time = kecheng.child(9).text().trim();
			Course course = new Course(cname, score, credit, type, time);
			// ȥ��һ����˫ѧλ�γ� �����ǽ���ϵͳ��������� �������������˫ѧλ�Ĳ����㡣 ��ҳԴ����˫ѧλ�Ŀγ�����ѧ�ֵġ�
			if (type.indexOf("˫ѧλ") >= 0) {
				// System.out.println(cname+" +++ "+credit);
				continue;
			}
			if (course.getPoint() == 0f && type.equals("��������") && !cname.endsWith("*"))
				continue;
			System.out.println(cname + "  " + credit);
			cj.add(course);
		}
		List<Course> courses = new ArrayList<Course>();
		// ȥ���ҿƿ�Ŀ

		// �γ�ѧϰʱ������ ��Ч��2000-2100���
		for (int year = 2000; year < 2100; year++) {

			for (Course course : cj) {
				if ((year + "��").equals(course.getTime()))
					courses.add(course);

			}
			for (Course course : cj) {
				if ((year + "��").equals(course.getTime()))
					courses.add(course);
			}
		}

		cj = null;

		return courses;
	}

	// ��ȡ���������ϵ�GPA
	public static float getGPA() {
		return GPA;
	}
}
