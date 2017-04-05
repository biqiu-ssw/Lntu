package dss;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

public class Course {
	private String cname;// 课程名
	private String score;// 成绩
	private float floatScore;// 小数成绩
	private float credit;// 学分
	private String type;// 考试类型
	private String time;// 学习时间
	private float point;// 绩点

	// 构造一门课程
	public Course(String cname, String score, float credit, String type, String time) {
		super();
		this.cname = cname;
		this.score = score;
		this.credit = credit;
		this.type = type;
		this.time = time;

		if (isFloat(score))// 百分制
		{
			floatScore = Float.valueOf(score);
			float s = Float.valueOf(score);
			if (s < 60)
				point = 0f;
			else if (s < 65)
				point = 1f;
			else if (s < 70)
				point = 1.5f;
			else if (s < 75)
				point = 2f;
			else if (s < 80)
				point = 2.5f;
			else if (s < 85)
				point = 3f;
			else if (s < 90)
				point = 3.5f;
			else if (s < 95)
				point = 4f;
			else
				point = 4.5f;
		} else {// 五级或二级分制
			if ("不及格".equals(score) || "不合格".equals(score)) {
				point = 0f;
				floatScore = 50.0f;
			} else if ("及格".equals(score)) {
				point = 1.0f;
				floatScore = 65.0f;
			} else if ("中".equals(score) || "合格".equals(score)) {
				point = 2.0f;
				floatScore = 75.0f;
			} else if ("良".equals(score)) {
				point = 3.0f;
				floatScore = 85.0f;
			} else if ("优秀".equals(score)) {
				point = 4.0f;
				floatScore = 95.0f;
			} else/* happy去吧 */ {
				point = 10000.0f;// 出现异常
				floatScore = 666666.6f;// 出现异常
			}

		}

	}

	public Course() {
		// TODO Auto-generated constructor stub
	}

	// 区分春秋时间前后关系 2014春比2014秋小
	public static int getIntTime(String time) {
		if (time.substring(4).equals("春"))
			return Integer.valueOf(time.substring(0, 4) + "3");
		else
			return Integer.valueOf(time.substring(0, 4) + "9");
	}

	// 计算加权平均分
	public static float calAveScore(List<Course> courses, String start, String over) {
		float ave = 0f;

		float sumScore = 0f;// 考试成绩之和
		float sumCredit = 0f;// 科目学分之和
		for (Course course : courses) {
			if (Course.getIntTime(course.getTime()) > Course.getIntTime(over))
				break;
			if (Course.getIntTime(course.getTime()) < Course.getIntTime(start))
				continue;
			// System.out.println(course.getCname()+" "+course.getFloatScore());
			float score = course.getFloatScore();
			float credit = course.getCredit();
			sumScore += score * credit;
			sumCredit += credit;
		}
		if (sumCredit > 0)
			ave = sumScore / sumCredit;
		// System.out.println(start+" ---- "+over);
		return ave;
	}

	// 计算某时间段内GPA
	public static float calGPA(List<Course> courses, String start, String over) {
		float GPA = 0.0f;
		float sumPoint = 0f;// 科目绩点之和
		float sumCredit = 0f;// 科目学分之和

		HashMap<String, Integer> hs = new HashMap<String, Integer>();

		for (Course course : courses) {
			if (!course.getType().equals("重学"))
				continue;
			Integer count = 3;
			String name = course.getCname();
			if (hs.get(name) != null) {
				count = hs.get(name) + 1;
			}
			hs.put(name, count);

		}
		List<String> kechengs = new ArrayList<String>();
		for (Course course : courses) {
			if (Course.getIntTime(course.getTime()) < Course.getIntTime(start))
				continue;
			if (Course.getIntTime(course.getTime()) > Course.getIntTime(over))
				break;
			String name = course.getCname();
			float point = course.getPoint();
			float credit = course.getCredit();
			if (point > 0f) {
				if (course.getType().equals("补考")) {
					point = point / 2.0f;
				} else if (course.getType().equals("重学")) {
					point = point / hs.get(course.getCname());
				}
				if (point < 1.0f) {
					point = 1.0f;
				}
			}
			sumPoint += point * credit;
			if (!kechengs.contains(name) || course.getType().equals("正常考试")) {
				sumCredit += credit;
				kechengs.add(name);
				// System.out.println(name + " " + course.getType() + ""
				// +point);
			}

		}

		if (sumCredit > 0)
			GPA = sumPoint / sumCredit;
		return GPA;
	}

	// 获得所有学期
	public static List<String> getDistinctTerm(List<Course> courses) {
		List<String> terms = new ArrayList<String>();
		for (Course course : courses) {
			String term = course.getTime();
			if (terms.contains(term))
				continue;
			terms.add(term);
		}

		return terms;
	}

	// 把补考通过 未通过 最终成绩单保存到文件中
	public static void save(List<Course> courses, String id) throws FileNotFoundException {
		List<Course> bukao = new ArrayList<Course>();
		List<Course> chongxue = new ArrayList<Course>();
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();

		String desktopPath = desktopDir.getAbsolutePath();

		File dir = new File(desktopPath + "\\" + id);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 筛选出正考，补考，重修(学)
		for (Course course : courses) {
			// 补考成绩
			if ("补考".equals(course.getType()))
				bukao.add(course);
			// 重修成绩
			if ("重学".equals(course.getType()))
				chongxue.add(course);

		}
		PrintStream passExam = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "补考通过.xls")));
		PrintStream notPassExam = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "补考未通过.xls")));
		PrintStream finalReport = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "最终成绩单.xls")));

		// 解析补考通过 补考未通过
		int i = 1, j = 1;

		for (Course course : bukao) {
			// System.out.println(course.getCname() + " " + course.getScore());
			String score = course.getScore();
			if ((isFloat(score) && Float.valueOf(score) < 60) || "不及格".equals(score) || "不合格".equals(score)) {
				notPassExam
						.println(i++ + "\t" + course.getCname() + "\t" + course.getTime() + "\t" + course.getScore());
			} else {
				passExam.println(j++ + "\t" + course.getCname() + "\t" + course.getTime() + "\t" + course.getScore());
			}
		}

		notPassExam.close();
		passExam.close();
		i = 1;
		for (Course course : courses) {
			finalReport.println(i++ + "\t" + course.getCname() + "\t" + course.getTime() + "\t" + course.getScore());
		}
		finalReport.close();

	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public static boolean isFloat(String str) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	public float getFloatScore() {
		return floatScore;
	}

	public void setFloatScore(float floatScore) {
		this.floatScore = floatScore;
	}
}
