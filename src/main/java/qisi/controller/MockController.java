package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.Chapter;
import qisi.bean.course.Code;
import qisi.bean.course.Course;
import qisi.bean.json.CodeJudge;
import qisi.bean.user.User;
import qisi.utils.Jms;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.service.UserService;
import qisi.utils.Mock;
import qisi.utils.Utils;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */

@RestController
public class MockController {
	protected static final Logger Logger = LoggerFactory.getLogger(MockController.class);
	private final String commitName = "commit";
	private final String receiveName = "receive";

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

	@GetMapping("/mockUsers")
	public String mockUsers() {
		List<User> users = Mock.mockUsers();
		userService.mockUsers(users);
		return "done";
	}

	@ResponseBody
	@PostMapping("/mock/commit")
	public CodeJudge mockJms(@RequestBody Code code) {
		Destination destination = new ActiveMQQueue(commitName);
		System.out.println(code);
		code.setCodeId(Utils.getUUID());
		code.setCreatedAt(new Date());
		HashMap<String, String> map = new HashMap<>(16);
		System.out.println(code.getCodeId());
		map.put("codeId", code.getCodeId());
		map.put("code", code.getCode());
		map.put("exerciseId", code.getTaskId());
		producerService.sendMessage(destination, map);
		Map resultMap = Jms.consumer(receiveName, code.getCodeId());
		System.out.println("评测完成...");
		Boolean pass = (Boolean) resultMap.get("pass");
		System.out.println("结果..." + pass);
		CodeJudge codeJudge = new CodeJudge();
		if (pass) {
			code.setPass(true);
			codeJudge.setPass(true);
			codeJudge.setReason("代码通过了所有的测试用例!");
		} else {
			codeJudge.setPass(false);
			codeJudge.setReason("代码不通过,请检查代码是否符合要求!");
		}
		courseService.saveCode(code);
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
		stringRedisTemplate.opsForValue().set("user", "张杰", 60*5, TimeUnit.SECONDS);
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
		return session.getAttribute("user").toString();
	}
}
