package qisi.controller;

import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.*;
import qisi.bean.json.AjaxResponse;
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
 * 测试API
 *
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */

@RestController
@RequestMapping("/admin")
public class AdminController {
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

	@GetMapping("/judge")
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


	/**
	 * 获取所有用户信息
	 *
	 * @return 用户json列表
	 */
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	/**
	 * 根据用户名获取用户信息
	 *
	 * @param username 用户名
	 * @return 用户个人信息
	 */
	@GetMapping(value = "/user/{username}")
	public MockUser findUserByUserName(@PathVariable("username") String username) {
		User user = userService.findUserByUsername(username);
		return user == null ? null : Dozer.getBean(user, MockUser.class);
	}

	@PostMapping("/addCourses")
	public AjaxResponse addCourses(@RequestBody List<Course> courses) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		for (int i = 0; i < courses.size(); i++) {
			courses.get(i).setCourseId(Utils.getUUID());
			courses.get(i).setCreatedAt(new Date());
		}
		try {
			courseService.saveCourses(courses);
		} catch (Exception e) {
			ajaxResponse.setStatus(400);
			ajaxResponse.setMsg("添加失败!");
			return ajaxResponse;
		}

		ajaxResponse.setStatus(200);
		ajaxResponse.setMsg("添加成功!");
		return ajaxResponse;
	}

	@GetMapping("/getCourses")
	public List<Course> getCourses() {
		return courseService.findAllCourses();
	}

}
