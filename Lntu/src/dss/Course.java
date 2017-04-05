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
	private String cname;// �γ���
	private String score;// �ɼ�
	private float floatScore;// С���ɼ�
	private float credit;// ѧ��
	private String type;// ��������
	private String time;// ѧϰʱ��
	private float point;// ����

	// ����һ�ſγ�
	public Course(String cname, String score, float credit, String type, String time) {
		super();
		this.cname = cname;
		this.score = score;
		this.credit = credit;
		this.type = type;
		this.time = time;

		if (isFloat(score))// �ٷ���
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
		} else {// �弶���������
			if ("������".equals(score) || "���ϸ�".equals(score)) {
				point = 0f;
				floatScore = 50.0f;
			} else if ("����".equals(score)) {
				point = 1.0f;
				floatScore = 65.0f;
			} else if ("��".equals(score) || "�ϸ�".equals(score)) {
				point = 2.0f;
				floatScore = 75.0f;
			} else if ("��".equals(score)) {
				point = 3.0f;
				floatScore = 85.0f;
			} else if ("����".equals(score)) {
				point = 4.0f;
				floatScore = 95.0f;
			} else/* happyȥ�� */ {
				point = 10000.0f;// �����쳣
				floatScore = 666666.6f;// �����쳣
			}

		}

	}

	public Course() {
		// TODO Auto-generated constructor stub
	}

	// ���ִ���ʱ��ǰ���ϵ 2014����2014��С
	public static int getIntTime(String time) {
		if (time.substring(4).equals("��"))
			return Integer.valueOf(time.substring(0, 4) + "3");
		else
			return Integer.valueOf(time.substring(0, 4) + "9");
	}

	// �����Ȩƽ����
	public static float calAveScore(List<Course> courses, String start, String over) {
		float ave = 0f;

		float sumScore = 0f;// ���Գɼ�֮��
		float sumCredit = 0f;// ��Ŀѧ��֮��
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

	// ����ĳʱ�����GPA
	public static float calGPA(List<Course> courses, String start, String over) {
		float GPA = 0.0f;
		float sumPoint = 0f;// ��Ŀ����֮��
		float sumCredit = 0f;// ��Ŀѧ��֮��

		HashMap<String, Integer> hs = new HashMap<String, Integer>();

		for (Course course : courses) {
			if (!course.getType().equals("��ѧ"))
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
				if (course.getType().equals("����")) {
					point = point / 2.0f;
				} else if (course.getType().equals("��ѧ")) {
					point = point / hs.get(course.getCname());
				}
				if (point < 1.0f) {
					point = 1.0f;
				}
			}
			sumPoint += point * credit;
			if (!kechengs.contains(name) || course.getType().equals("��������")) {
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

	// �������ѧ��
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

	// �Ѳ���ͨ�� δͨ�� ���ճɼ������浽�ļ���
	public static void save(List<Course> courses, String id) throws FileNotFoundException {
		List<Course> bukao = new ArrayList<Course>();
		List<Course> chongxue = new ArrayList<Course>();
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();

		String desktopPath = desktopDir.getAbsolutePath();

		File dir = new File(desktopPath + "\\" + id);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// ɸѡ������������������(ѧ)
		for (Course course : courses) {
			// �����ɼ�
			if ("����".equals(course.getType()))
				bukao.add(course);
			// ���޳ɼ�
			if ("��ѧ".equals(course.getType()))
				chongxue.add(course);

		}
		PrintStream passExam = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "����ͨ��.xls")));
		PrintStream notPassExam = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "����δͨ��.xls")));
		PrintStream finalReport = new PrintStream(
				new FileOutputStream(new File(dir.getAbsolutePath() + "\\" + "���ճɼ���.xls")));

		// ��������ͨ�� ����δͨ��
		int i = 1, j = 1;

		for (Course course : bukao) {
			// System.out.println(course.getCname() + " " + course.getScore());
			String score = course.getScore();
			if ((isFloat(score) && Float.valueOf(score) < 60) || "������".equals(score) || "���ϸ�".equals(score)) {
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
