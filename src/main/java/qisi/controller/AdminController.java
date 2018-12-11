package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.admin.AdminUser;
import qisi.bean.course.*;
import qisi.bean.jms.CodeMessage;
import qisi.bean.json.ApiResult;
import qisi.bean.json.CodeJudge;
import qisi.bean.user.MockUser;
import qisi.bean.user.User;
import qisi.exception.AdminAuthorityException;
import qisi.service.AdminService;
import qisi.utils.*;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.service.UserService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.*;

/**
 * 管理员及测试API
 *
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */


@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final String COMMIT_QUEUE = "commit";
	private static final String RECEIVE_QUEUE = "receive";
	private static final int MAX_WAIT = 60;
	private static final int POOL_SIZE = 1;

	protected static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private static final String LOGIN_HTML = "admin_login.html";
	private static final String TEST_HTML = "admin_test.html";
	private static final String USERNAME = "username";

	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private HttpSession session;

	@PostMapping("/login")
	public String adminLogin(AdminUser adminUser, HttpServletRequest request) {
		request.setAttribute("user", adminUser);
		if (adminUser == null || adminUser.getUsername() == null || adminUser.getPassword() == null) {
			request.setAttribute("msg", "请正确填写用户信息!");
			return LOGIN_HTML;
		}

		AdminUser user = adminService.findAdminUserByUsername(adminUser.getUsername());
		if (user == null || !(adminUser.getPassword().equals(user.getPassword()))) {
			request.setAttribute("msg", "登录信息有误!");
			return LOGIN_HTML;
		}
		session.setAttribute("username", adminUser.getUsername());
		request.setAttribute("msg", "登陆成功!");
		return LOGIN_HTML;
	}

	@ResponseBody
	@GetMapping("/mockCourses")
	public String mockCourses() {
		List<Course> courses = Mock.mockCourses();
		courseService.saveCourses(courses);
		return "done";
	}

	@ResponseBody
	@GetMapping("/mockChapters")
	public String mockChapters() {
		List<Chapter> chapters = Mock.mockChapters();
		courseService.saveChapters(chapters);
		return "done";
	}

	@ResponseBody
	@GetMapping("/mockLessons")
	public String mockLessons() {
		List<Lesson> lessons = Mock.mockLessons();
		courseService.saveLessons(lessons);
		return "done";
	}

	@ResponseBody
	@GetMapping("/mockTasks")
	public String mockTasks() {
		List<Task> tasks = Mock.mockTasks();
		courseService.saveTasks(tasks);
		return "done";
	}

	@ResponseBody
	@GetMapping("/mockCases")
	public String mockCases() {
		List<Case> cases = Mock.mockCases();
		courseService.saveCases(cases);
		return "done";
	}

	@ResponseBody
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

	@ResponseBody
	@GetMapping("/mockUsers")
	public String mockUsers(@RequestParam("start") int start) {
		List<User> users = Mock.mockUsers(start);
		System.out.println("mock 完毕...");
		userService.mockUsers(users);
		return "done";
	}

	@ResponseBody
	@GetMapping("/mockJudge")
	public String mockJudge(@RequestParam String codeId, @RequestParam Boolean pass) {
		System.out.println(codeId + pass);
		try {
			Jms.produce(RECEIVE_QUEUE, codeId, pass);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "judge job done...";
	}

	@ResponseBody
	@GetMapping("/getSession")
	public String getSession() {
		return (String) session.getAttribute("username");

	}

	@ResponseBody
	@GetMapping("/removeSession")
	public String removeSession() {
		session.removeAttribute("username");
		return "done";
	}

	/**
	 * 获取所有用户信息
	 *
	 * @return 用户列表
	 */
	@ResponseBody
	@GetMapping("/users")
	public List<User> getUsers() {
		List<User> users = userService.getUsers();
		for (int i = 0; i < users.size(); i++) {
			users.get(i).setPassword(null);
		}
		return users;
	}

	/**
	 * 根据用户名查询用户
	 *
	 * @param username 用户名
	 * @return 用户信息
	 */
	@ResponseBody
	@GetMapping(value = "/user/{username}")
	public MockUser findUserByUserName(@PathVariable("username") String username) throws AdminAuthorityException {
		User user = userService.findUserByUsername(username);
		return user == null ? null : Dozer.getBean(user, MockUser.class);
	}

	/**
	 * 添加课程
	 *
	 * @param courses course列表
	 * @return ApiResult
	 */
	@ResponseBody
	@PostMapping("/addCourses")
	public ApiResult addCourses(@RequestBody List<Course> courses) throws AdminAuthorityException {
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
	@ResponseBody
	@PostMapping("/addChapters")
	public ApiResult addChapters(@RequestBody List<Chapter> chapters) throws AdminAuthorityException {

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
	@ResponseBody
	@PostMapping("/addLessons")
	public ApiResult addLessons(@RequestBody List<Lesson> lessons) throws AdminAuthorityException {

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
	@ResponseBody
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
	@ResponseBody
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

	@ResponseBody
	@GetMapping("/getCourses")
	public List<Course> getCourses() {
		return courseService.findAllCourses();
	}

	@ResponseBody
	@GetMapping("/getChapters")
	public List<Chapter> getChapters() {
		return courseService.findAllChapters();
	}

	@ResponseBody
	@GetMapping("/getLessons")
	public List<Lesson> getLessons() {
		return courseService.findAllLessons();
	}

	@ResponseBody
	@GetMapping("/getTasks")
	public List<Task> getTasks() {
		return courseService.findAllTasks();
	}

	@ResponseBody
	@GetMapping("/getCases")
	public List<Case> getCases() {
		return courseService.findAllCases();
	}


	@GetMapping("/task/{taskId}")
	public String task(HttpServletRequest request, @PathVariable String taskId) {
		Task task = courseService.findTaskByTaskId(taskId);
		request.setAttribute("task", task);
		return "admin_test";
	}

	@ResponseBody
	@PostMapping("/commit")
	public CodeJudge Commit(@RequestBody Code code, HttpServletRequest request) {
		String username = (String) session.getAttribute("username");
		boolean pass = false;
		CodeJudge codeJudge = new CodeJudge();
		Destination destination = new ActiveMQQueue(COMMIT_QUEUE);
		CodeMessage codeMessage = new CodeMessage();
		ExecutorService executor = new ScheduledThreadPoolExecutor(POOL_SIZE,
				new BasicThreadFactory.Builder().namingPattern("ddv").daemon(true).build());

		code.setCodeId(Utils.getUUID());
		code.setCreatedAt(new Date());
		code.setUsername(username);

		Task task = courseService.findTaskByTaskId(code.getTaskId());
		List<Case> cases = courseService.findCasesByTaskId(code.getTaskId());

		List<String> inputs = new ArrayList<>(cases.size());
		List<String> outputs = new ArrayList<>(cases.size());
		for (int i = 0; i < cases.size(); i++) {
			inputs.add(cases.get(i).getInput());
			outputs.add(cases.get(i).getOutput());
		}

		codeMessage.setCodeId(code.getCodeId());
		codeMessage.setTotalCases(cases.size());
		codeMessage.setMaxTime(task.getMaxTime());
		codeMessage.setMaxMemory(task.getMaxMemory());
		codeMessage.setFirstCode(task.getFirstCode());
		codeMessage.setSecondCode(task.getSecondCode());
		codeMessage.setCode(code.getCode());
		codeMessage.setInputs(inputs);
		codeMessage.setOutputs(outputs);
		codeMessage.setType(courseService.findCourseByTaskId(code.getTaskId()).getType());

		logger.info(code.getCodeId());

		producerService.sendStreamMessage(destination, codeMessage, new CodeMessageConverter());
		Future<Boolean> future = executor.submit(new ListenConsumer(RECEIVE_QUEUE, code.getCodeId()));

		try {
			if (future.get(MAX_WAIT, TimeUnit.SECONDS)) {
				pass = true;
			}
		} catch (TimeoutException e) {
			codeJudge.setMsg("评测系统忙,请稍后提交!");
			if (!executor.isShutdown()) {
				executor.shutdown();
			}
			future.cancel(Boolean.TRUE);
			return codeJudge;
		} catch (Exception e) {
			return codeJudge;
		}

		if (!executor.isShutdown()) {
			executor.shutdown();
		}

		if (pass) {
			codeJudge.setPass(true);
			codeJudge.setMsg("代码成功通过了所有的测试用例!");
		} else {
			codeJudge.setPass(false);
			codeJudge.setMsg("代码未通过,请检查代码是否符合要求!");
		}

		return codeJudge;
	}

}
