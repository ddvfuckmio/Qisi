package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.*;
import qisi.bean.jms.CodeMessage;
import qisi.bean.json.CodeJudge;
import qisi.bean.user.User;
import qisi.utils.CodeMessageConverter;
import qisi.utils.Jms;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.service.UserService;
import qisi.utils.Mock;
import qisi.utils.Utils;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */

@RestController
public class MockController {
	protected static final Logger Logger = LoggerFactory.getLogger(MockController.class);
	private final String commitName = "commit";
	private final String receiveName = "QISI.commit";

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

	@GetMapping("/mockUsers")
	public String mockUsers() {
		List<User> users = Mock.mockUsers();
		userService.mockUsers(users);
		return "done";
	}

	@ResponseBody
	@PostMapping("/mockCommit")
	public CodeJudge mockJms(@RequestBody Code code) {

		Destination destination = new ActiveMQQueue(receiveName);
		CodeMessage codeMessage = new CodeMessage();

		code.setCodeId(Utils.getUUID());
		code.setCreatedAt(new Date());

		codeMessage.setCode("测试代码....");
		codeMessage.setMaxTime(1000);
		codeMessage.setMaxMemory(1024 * 2);
		codeMessage.setCodeId(code.getCodeId());
		codeMessage.setFirstCode("code1...");
		codeMessage.setSecondCode("code2...");
		codeMessage.setTotalCases(3);
		codeMessage.setType("java");

		List<String> inputs = new ArrayList<>(codeMessage.getTotalCases());
		List<String> outputs = new ArrayList<>(codeMessage.getTotalCases());
		for (int i = 0; i < 3; i++) {
			inputs.add(i + "");
			outputs.add(i + "");
		}

		codeMessage.setInputs(inputs);
		codeMessage.setOutputs(outputs);

//		System.out.println(code);
//		System.out.println(code.getCodeId());
		System.out.println(codeMessage);
		MessageConverter messageConverter = new CodeMessageConverter();
		producerService.sendStreamMessage(destination, codeMessage, messageConverter);
//		Map resultMap = Jms.consumer(receiveName, code.getCodeId());
//
//		Boolean pass = (Boolean) resultMap.get("pass");

//		System.out.println("评测完成-->结果..." + pass);

		CodeJudge codeJudge = new CodeJudge();
//		if (pass) {
//			code.setPass(true);
//			codeJudge.setPass(true);
//			codeJudge.setReason("代码通过了所有的测试用例!");
//		} else {
//			codeJudge.setPass(false);
//			codeJudge.setReason("代码不通过,请检查代码是否符合要求!");
//		}
//		courseService.saveCode(code);
		return codeJudge;
	}

	@PostMapping("/mock/judge")
	public String mockJudge(@RequestParam String codeId, @RequestParam Boolean pass) throws JMSException {
		System.out.println(codeId + pass);
		Jms.produce(receiveName, codeId, pass);
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

	@GetMapping("/setSession")
	public String setSession(HttpSession session) {
		session.setAttribute("user", "session");
		return "session存取完毕!";
	}

	@GetMapping("/getSession")
	public String getSession(HttpSession session) {
		String msg = (String) session.getAttribute("username");
		if (msg == null) {
			return "无数据";
		}
		return msg;
	}

	@GetMapping("/mockProgress")
	public String mockProgress() {
		Progress progress = new Progress();

		progress.setCourseId("codeId...");
		progress.setLessonId("lessonId...");
		progress.setTaskId("taskId...");
		progress.setUsername("username...");
		progress.setCreatedAt(new Date());

		courseService.saveProgress(progress);
		return "done...";
	}


}
