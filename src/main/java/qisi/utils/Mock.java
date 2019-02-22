package qisi.utils;

import qisi.bean.admin.News;
import qisi.bean.course.*;
import qisi.bean.user.User;
import qisi.bean.work.Worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author : ddv
 * @date : 2018/10/25 下午3:18
 */

public class Mock {
	private final static int COUNT = 100000;
	private static String[] courseNames = {"Golang", "Java"};
	private static String[] courseIds = {"b6a9860c86624a938c7494155f29ae0d", "ec885ae819144bffbbfa1252d13c91ec"};
	private static String[] chapterIds = {"61ecece3a5084c728b26f02e564a2fc6", "86d511e2ead547daa2103fa702512021", "81bed5cadc734923a362e991aba60b40", "1033596990ca4f9dbf837bb98f6aa79d"};
	private static String[] lessonIds = {"8ce7a39ae74c44f1b2a4170798651283", "d2065579e875472693ad2a07067aa807", "92f34fb88b01429b8c59a50b017ff32d", "1faad2009e204a99bcea55ad5e3e5903"};
	private static String[] taskIds = {"2e866bdc6ab44089b395417bc7a59153", "6ea5e2b58c634d0c976ccf62461e6d5c", "701bf592860b4a00b53d6dd46eb2963f", "3dc5a41d151a41e09ceb802ccc36f47a"};
	private static String[] caseIds = {"ee43b6ba63d549b9ac1436683ddbcea0", "9aa62cf8f32c494ba1ace043db61c7c6", "1a81df1e35554e43bdc01f7384bd26cb", "68b266886028416ebfed3e5903cae899"};

	public static List<User> mockUsers(int start) {
		Random random = new Random();
		String[] jobs = {"学生", "工人", "老师", "人事", "售货员"};
		List<User> users = new ArrayList<>(COUNT);
		for (int i = start; i < COUNT; i++) {
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
			course.setType(courseNames[i]);
			course.setPublished(true);
			courses.add(course);
			course.setCreatedAt(new Date());
			course = null;
		}
		return courses;
	}

	public static List<Chapter> mockChapters() {
		List<Chapter> chapters = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < courseNames.length; i++) {
			for (int j = 0; j < 2; j++) {
				Chapter chapter = new Chapter();
				chapter.setCourseId(courseIds[i]);
				chapter.setChapterId(chapterIds[count++]);
				chapter.setChapterName(courseNames[i] + "-" + (j + 1));
				chapter.setChapterIndex(j + 1);
				chapter.setCreatedAt(new Date());
				chapters.add(chapter);
				chapter = null;
			}
		}
		return chapters;
	}

	public static List<Lesson> mockLessons() {
		List<Lesson> lessons = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < chapterIds.length; i++) {
			String lessonName = (i & 1) != 1 ? "环境搭建" : "快速上手";
			Lesson lesson = new Lesson();
			lesson.setChapterId(chapterIds[count]);
			lesson.setLessonId(lessonIds[count]);
			lesson.setLessonName(courseNames[i / 2] + lessonName);
			lesson.setOriginCode("code ....");
			lesson.setLessonIndex((i & 1) != 1 ? 1 : 2);
			lesson.setIntroduction("介绍:" + courseNames[i / 2] + lessonName);
			lesson.setCreatedAt(new Date());
			lessons.add(lesson);
			count++;
			lesson = null;
		}

		return lessons;
	}

	public static List<Task> mockTasks() {
		List<Task> tasks = new ArrayList<>();
		for (int i = 0; i < taskIds.length; i++) {
			Task task = new Task();
			task.setTaskId(taskIds[i]);
			task.setFirstCode("first code...");
			task.setSecondCode("seconde code...");
			task.setIntroduction("任务介绍...");
			task.setLessonId(lessonIds[i]);
			task.setTaskIndex(1);
			task.setMaxTime(1000 * (i + 1));
			task.setMaxMemory(1024 * i * 2);
			task.setCreatedAt(new Date());
			tasks.add(task);
			task = null;
		}

		return tasks;
	}


	public static List<Case> mockCases() {
		List<Case> list = new ArrayList<>();
		Case testCase = null;
		for (int i = 0; i < caseIds.length; i++) {
			testCase = new Case();
			testCase.setCaseId(caseIds[i]);
			testCase.setTaskId(taskIds[i]);
			testCase.setInput(i + "");
			testCase.setOutput(i % 2 + "");
			testCase.setCreatedAt(new Date());
			list.add(testCase);
		}
		return list;
	}

	public static List<Worker> mockWorkers() {
		Random random = new Random();
		List<Worker> workers = new ArrayList<>(COUNT);
		String[] departments = {"销售部", "技术部", "行政部", "采购部", "公关部", "政党部", "临时部门", "人事部"};
		for (int i = 0; i < COUNT; i++) {
			String index = String.valueOf(i);
			String sex = (i & 1) == 0 ? "男" : "女";
			String department = departments[random.nextInt(departments.length)];
			Worker worker = new Worker();
			worker.setUsername(index);
			worker.setPassword(Utils.encode(index));
			worker.setAge(i);
			worker.setRealName("张" + index);
			worker.setDepartment(department);
			worker.setEmail(index + "@qq.com");
			worker.setCreatedAt(new Date());
			worker.setSex(sex);
			worker.setPhone("phone" + index);
			workers.add(worker);
		}
		return workers;
	}

	public static List<News> mockNews() {
		List<News> newsList = new ArrayList<>();
		String[] msgs = {"所有员工,新年快乐!🐖阖家团结,幸福美满!",
				"紧急通知,所有技术部门的年底总结需要在2.19日之前上交.",
				"国家税务总局关于修改〈税务部门规章制定实施办法〉的决定》，已经2018年12月29日国家税务总局2018年度第3次局务会议审议通过，现予公布，自2019年3月1日起施行。",
				"10月受邀出席2018世界知识论坛，加强国际合作与交流，深化出海战略",
				"投资少儿绘本平台咔哒故事和儿童编程教育平台妙小程，进军互联网少儿教育领域"
		};

		for (int i = 0; i < 5; i++) {
			News news = new News();
			news.setId(i);
			news.setMsg(msgs[i]);
			news.setCreatedAt(new Date());
			newsList.add(news);
		}

		return newsList;
	}
}
