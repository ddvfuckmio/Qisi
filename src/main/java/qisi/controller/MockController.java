package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import qisi.bean.course.Course;
import qisi.bean.course.Lesson;
import qisi.bean.user.User;
import qisi.utils.JmsUtil;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.service.UserService;
import qisi.utils.MockUtil;
import qisi.utils.Utils;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */

@RestController
public class MockController {

	private final String commitName = "commit";
	private final String receiveName = "receive";

	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProducerService producerService;

	@GetMapping("/mockCourses")
	public String mockCourses() {
		List<Course> courses = MockUtil.mockCourses();
		courseService.saveCourses(courses);
		return "done";
	}

	@GetMapping("/mockLessons")
	public String mockLessons() {
		List<Lesson> lessons = MockUtil.mockLessons();
		courseService.saveLessons(lessons);
		return "done";
	}

	@GetMapping("/mockUsers")
	public String mockUsers() {
		List<User> users = MockUtil.mockUsers();
		userService.mockUsers(users);
		return "done";
	}

	@PostMapping("/jms/send")
	public String mockJms() {
		Destination destination = new ActiveMQQueue(commitName);
		Destination destination1 = new ActiveMQTopic(receiveName);

		HashMap<String, String> map = new HashMap<>(16);
		String codeId = Utils.getUUID();
		map.put("codeId", codeId);
		map.put("code", "system.out.print()");
		map.put("exerciseId", "1");
		producerService.sendMessage(destination, map);
		Map map1 = JmsUtil.consumer(commitName, "1");
		return map1.toString();
	}
}
