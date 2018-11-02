package qisi.utils;

import qisi.bean.course.Chapter;
import qisi.bean.course.Course;
import qisi.bean.course.Lesson;
import qisi.bean.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author : ddv
 * @date : 2018/10/25 下午3:18
 */

public class Mock {
	private final static int COUNT = 100;
	private static String[] courseNames = {"Golang", "CSS", "HTML", "JAVA", "C++", "JS"};
	private static String[] courseIds = {"b6a9860c86624a938c7494155f29ae0d", "ec885ae819144bffbbfa1252d13c91ec", "3c62c0215a034195a6e2a9605151f31f"
			, "9452a0c962ce4f83843de9d7f349da1e", "7f40b8df2a9a4a50bf7166b146f21574", "b32aeaa329c346e2b866e960dd343f79"};
	private static String[] chatperIds = {"61ecece3a5084c728b26f02e564a2fc6", "86d511e2ead547daa2103fa702512021", "81bed5cadc734923a362e991aba60b40", "1033596990ca4f9dbf837bb98f6aa79d"};

	public static List<User> mockUsers() {
		Random random = new Random();
		String[] jobs = {"学生", "工人", "老师", "人事", "售货员"};
		List<User> users = new ArrayList<>(COUNT);
		for (int i = 0; i < COUNT; i++) {
			String index = String.valueOf(i);
			String sex = (i & 1) == 0 ? "男" : "女";
			String job = jobs[random.nextInt(jobs.length)];
			User user = new User();
			user.setUsername(index);
			user.setPassword(Utils.encode(index));
			user.setCreatedAt(new Date());
			user.setAge(index);
			user.setEmail("email_" + index);
			user.setPhone("phone_" + index);
			user.setSex(sex);
			user.setJob(job);
			user.setRole("普通用户");
			users.add(user);
			user = null;
		}
		return users;
	}

	public static List<Course> mockCourses() {

		List<Course> courses = new ArrayList<>(courseNames.length);
		for (int i = 0; i < courseNames.length; i++) {
			Course course = new Course();
			course.setCourseId(courseIds[i]);
			course.setCourseName(courseNames[i]);
			course.setIntroduction(courseNames[i] + " 快速入门");
			courses.add(course);
			course.setCreatedAt(new Date());
			course = null;
		}
		return courses;
	}

	public static List<Chapter> mockChapters() {
		List<Chapter> chapters = new ArrayList<>();
		for (int i = 0; i < courseNames.length; i++) {
			for (int j = 0; j < 2; j++) {
				Chapter chapter = new Chapter();
				chapter.setCourseId(courseIds[i]);
				chapter.setChapterId(Utils.getUUID());
				chapter.setChapterName(courseNames[i] + "-" + (j + 1));
				chapter.setChapterIndex(j + 1);
				chapter.setCreatedAt(new Date());
				chapters.add(chapter);
				chapter = null;
			}
		}
		return chapters;
	}

//	public static List<Lesson> mockLessons() {
//		List<Lesson> lessons = new ArrayList<>();
//		for (int i = 0; i < 2; i++) {
//			for (int j = 0; j < chatperIds.length; j++) {
//				Lesson lesson = new Lesson();
//				lesson.setLessonId(Utils.getUUID());
//				lesson.setChapterId(chatperIds[j]);
//
//			}
//		}
//
//		return lessons;
//	}
}
