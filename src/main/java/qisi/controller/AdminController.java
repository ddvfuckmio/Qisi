package qisi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.*;
import qisi.bean.json.ApiResult;
import qisi.bean.user.MockUser;
import qisi.bean.user.User;
import qisi.utils.*;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.service.UserService;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 管理员及测试API
 *
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */

@RestController
@RequestMapping("/admin")
public class AdminController {
	protected static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private final String receive = "receive";

	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@GetMapping("/mockCourses")
	public String mockCourses() {
		List<Course> courses = Mock.mockCourses();
		courseService.saveCourses(courses);
		return "done";
	}

	@GetMapping("/mockChapters")
	public String mockChapters() {
		List<Chapter> chapters = Mock.mockChapters();
		courseService.saveChapters(chapters);
		return "done";
	}

	@GetMapping("/mockLessons")
	public String mockLessons() {
		List<Lesson> lessons = Mock.mockLessons();
		courseService.saveLessons(lessons);
		return "done";
	}

	@GetMapping("/mockTasks")
	public String mockTasks() {
		List<Task> tasks = Mock.mockTasks();
		courseService.saveTasks(tasks);
		return "done";
	}

	@GetMapping("/mockCases")
	public String mockCases() {
		List<Case> cases = Mock.mockCases();
		courseService.saveCases(cases);
		return "done";
	}

	@GetMapping("/mockProgress")
	public String mockProgress() {
		Progress progress = new Progress();
		progress.setProgressId(Utils.getUUID());
		progress.setCourseId("codeId...");
		progress.setChapterId("chapterId...");
		progress.setLessonId("lessonId...");
		progress.setTaskId("taskId...");
		progress.setUsername("username...");
		progress.setCreatedAt(new Date());
		courseService.saveProgress(progress);
		return "done...";
	}

	@GetMapping("/mockUsers")
	public String mockUsers(@RequestParam("start") int start) {
		List<User> users = Mock.mockUsers(start);
		System.out.println("mock 完毕...");
		userService.mockUsers(users);
		return "done";
	}

	@GetMapping("/mockJudge")
	public String mockJudge(@RequestParam String codeId, @RequestParam Boolean pass) {
		System.out.println(codeId + pass);
		try {
			Jms.produce(receive, codeId, pass);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "judge job done...";
	}

	@GetMapping("/redis/set")
	public String setKey() {
		stringRedisTemplate.opsForValue().set("user", "张杰", 60 * 5, TimeUnit.SECONDS);
		return "set key done...";
	}

	@GetMapping("/redis/get")
	public String setKey(@RequestParam("key") String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	@GetMapping("/removeSession")
	public String removeSession(HttpSession session) {
		session.removeAttribute("username");
		return "done";
	}


	/**
	 * 获取所有用户信息
	 *
	 * @return 用户列表
	 */
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	/**
	 * 根据用户名查询用户
	 *
	 * @param username 用户名
	 * @return 用户信息
	 */
	@GetMapping(value = "/user/{username}")
	public MockUser findUserByUserName(@PathVariable("username") String username) {
		User user = userService.findUserByUsername(username);
		return user == null ? null : Dozer.getBean(user, MockUser.class);
	}

	/**
	 * 添加课程
	 *
	 * @param courses course列表
	 * @return ApiResult
	 */
	@PostMapping("/addCourses")
	public ApiResult addCourses(@RequestBody List<Course> courses) {

		for (int i = 0; i < courses.size(); i++) {
			courses.get(i).setCourseId(Utils.getUUID());
			courses.get(i).setCreatedAt(new Date());
		}
		try {
			courseService.saveCourses(courses);
		} catch (Exception e) {
			return ApiResult.ERROR();
		}
		return ApiResult.SUCCESS();
	}

	/**
	 * 添加目录
	 *
	 * @param chapters chapters列表
	 * @return ApiResult
	 */
	@PostMapping("/addChapters")
	public ApiResult addChapters(@RequestBody List<Chapter> chapters) {
		for (int i = 0; i < chapters.size(); i++) {
			chapters.get(i).setChapterId(Utils.getUUID());
			chapters.get(i).setCreatedAt(new Date());
		}
		try {
			courseService.saveChapters(chapters);
		} catch (Exception e) {
			return ApiResult.ERROR();
		}
		return ApiResult.SUCCESS();
	}

	/**
	 * 添加训练
	 *
	 * @param lessons lessons列表
	 * @return ApiResult
	 */
	@PostMapping("/addLessons")
	public ApiResult addLessons(@RequestBody List<Lesson> lessons) {
		for (int i = 0; i < lessons.size(); i++) {
			lessons.get(i).setLessonId(Utils.getUUID());
			lessons.get(i).setCreatedAt(new Date());
		}
		try {
			courseService.saveLessons(lessons);
		} catch (Exception e) {
			return ApiResult.ERROR();
		}
		return ApiResult.SUCCESS();
	}

	/**
	 * 添加任务
	 *
	 * @param tasks tasks列表
	 * @return ApiResult
	 */
	@PostMapping("/addTasks")
	public ApiResult addTasks(@RequestBody List<Task> tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setTaskId(Utils.getUUID());
			tasks.get(i).setCreatedAt(new Date());
		}
		try {
			courseService.saveTasks(tasks);
		} catch (Exception e) {
			return ApiResult.ERROR();
		}
		return ApiResult.SUCCESS();
	}

	/**
	 * 添加测试用例
	 *
	 * @param cases cases列表
	 * @return ApiResult
	 */
	@PostMapping("/addCases")
	public ApiResult addCases(@RequestBody List<Case> cases) {
		for (int i = 0; i < cases.size(); i++) {
			cases.get(i).setCaseId(Utils.getUUID());
			cases.get(i).setCreatedAt(new Date());
		}
		try {
			courseService.saveCases(cases);
		} catch (Exception e) {
			return ApiResult.ERROR();
		}
		return ApiResult.SUCCESS();
	}


	@GetMapping("/getCourses")
	public List<Course> getCourses() {
		return courseService.findAllCourses();
	}

	@GetMapping("/getChapters")
	public List<Chapter> getChapters() {
		return courseService.findAllChapters();
	}

	@GetMapping("/getLessons")
	public List<Lesson> getLessons() {
		return courseService.findAllLessons();
	}

	@GetMapping("/getTasks")
	public List<Task> getTasks() {
		return courseService.findAllTasks();
	}

	@GetMapping("/getCases")
	public List<Case> getCases() {
		return courseService.findAllCases();
	}

}
