package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.*;
import qisi.bean.jms.CodeMessage;
import qisi.bean.json.CodeJudge;
import qisi.bean.query.CourseChaptersQuery;
import qisi.bean.query.CoursesQuery;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.utils.CodeMessageConverter;
import qisi.utils.Utils;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:25
 */

@Controller
public class CourseController {
	private final String commitName = "commit";
	private final String receiveName = "QISI.commit";
	private static final String CHAPTER_HTML = "chapters";

	@Autowired
	private CourseService courseService;

	@Autowired
	private ProducerService producerService;

	/**
	 * 查询所有课程
	 */
	@GetMapping("/courses")
	public String findAllCourses(HttpServletRequest request) {
		CoursesQuery coursesQuery = new CoursesQuery();
		coursesQuery.setCourses(courseService.findAllCourses());
		coursesQuery.setTotal(coursesQuery.getCourses().size());
		request.setAttribute("courses", coursesQuery.getCourses());
		return "courses";
	}

	/**
	 * courseName
	 * 按课程名获取课程信息
	 */
	@ResponseBody
	@GetMapping("/course")
	public Course findCourseByName(@RequestParam("courseName") String courseName) {
		return courseService.findCourseByName(courseName);
	}

	/**
	 * courseId
	 * 按课程ID获取课程信息
	 */
	@ResponseBody
	@GetMapping("/course/{courseId}")
	public Course findCourseByCourseId(@PathVariable("courseId") String courseId) {
		return courseService.findCourseByCourseId(courseId);
	}

	/**
	 * courseId
	 * 按课程ID获取该课程下的所有目录
	 */
	@GetMapping("/course/{courseId}/chapters")
	public String findChaptersByCourseId(@PathVariable String courseId, HttpServletRequest request) {
		List<Chapter> chapters = courseService.findChaptersByCourseId(courseId);
		request.setAttribute("chapters", chapters);
		return CHAPTER_HTML;
	}


	@GetMapping("/chapter/{chapterId}/lessons")
	public String countByCourseId(@PathVariable("chapterId") String chapterId, HttpServletRequest request) {
		List<Lesson> lessons = courseService.findLessonsByChapterId(chapterId);
		request.setAttribute("lessons", lessons);
		return "lessons";
	}

	/**
	 * lessonId
	 * 查询某节课对应的所有task
	 */
	@GetMapping("/lesson/{lessonId}/tasks")
	public String findExercisesByLessonId(@PathVariable String lessonId, HttpServletRequest request) {
		List<Task> tasks = courseService.findTasksByLessonId(lessonId);
		request.setAttribute("tasks", tasks);
		return "tasks";
	}

	/**
	 * 根据训练ID获取训练信息
	 */
	@GetMapping("/task/{taskId}")
	public String findExerciseByExercise(@PathVariable("taskId") String taskId, HttpServletRequest request) {
		request.setAttribute("task", courseService.findTaskByTaskId(taskId));
		return "task";
	}


	@ResponseBody
	@PostMapping("/code/commit")
	public CodeJudge mockJms(@RequestBody Code code) {
		System.out.println(code);

//		Destination destination = new ActiveMQQueue(receiveName);
//		CodeMessage codeMessage = new CodeMessage();
//
//		code.setCodeId(Utils.getUUID());
//		code.setCreatedAt(new Date());
//
//
//		List<String> inputs = new ArrayList<>(codeMessage.getTotalCases());
//		List<String> outputs = new ArrayList<>(codeMessage.getTotalCases());
//		for (int i = 0; i < 3; i++) {
//			inputs.add(i + "");
//			outputs.add(i + "");
//		}
//
//		codeMessage.setInputs(inputs);
//		codeMessage.setOutputs(outputs);
//
////		System.out.println(code);
////		System.out.println(code.getCodeId());
//		System.out.println(codeMessage);
//		MessageConverter messageConverter = new CodeMessageConverter();
//		producerService.sendStreamMessage(destination, codeMessage, messageConverter);
////		Map resultMap = Jms.consumer(receiveName, code.getCodeId());
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
		codeJudge.setMsg("测试未通过!");
		return codeJudge;
	}


}
