package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.*;
import qisi.bean.jms.CodeMessage;
import qisi.bean.json.CodeJudge;
import qisi.bean.query.CoursesQuery;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.utils.CodeMessageConverter;
import qisi.utils.Jms;
import qisi.utils.Utils;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:25
 */

@Controller
public class CourseController {
	private final String COMMIT_QUEUE = "commit";
	private final String RECEIVE_QUEUE = "receive";
	private final String CHAPTER_HTML = "chapters";
	private final int MAX_WAIT = 10;
	private final int POOL_SIZE = 1;

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
	public CodeJudge commitCode(@RequestBody Code code, HttpSession session) {

		String username = (String) session.getAttribute("username");
		boolean pass = false;
		CodeJudge codeJudge = new CodeJudge();
		Destination destination = new ActiveMQQueue(COMMIT_QUEUE);
		CodeMessage codeMessage = new CodeMessage();
		ExecutorService executor = new ScheduledThreadPoolExecutor(POOL_SIZE,
				new BasicThreadFactory.Builder().namingPattern("ddv").daemon(true).build());

		code.setCodeId("6a9827d044504c5faf00103a2f0c1d7c");
		code.setCreatedAt(new Date());
		code.setUsername(username);

		Task task = courseService.findTaskByTaskId(code.getTaskId());
		Lesson lesson = courseService.findLessonByTaskId(code.getTaskId());
		Chapter chapter = courseService.findChapterByLessonId(lesson.getLessonId());
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

		producerService.sendStreamMessage(destination, codeMessage, new CodeMessageConverter());
		Future<Boolean> future = executor.submit(new ListenConsumer(RECEIVE_QUEUE, code.getCodeId()));

		try {
			if (future.get(MAX_WAIT, TimeUnit.SECONDS)) {
				pass = true;
			}
		} catch (TimeoutException e) {
			codeJudge.setMsg("评测系统忙,请稍后提交!");
			return codeJudge;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!executor.isShutdown()) {
			executor.shutdown();
		}

		if (pass) {
			code.setPass(true);
			codeJudge.setPass(true);
			codeJudge.setMsg("代码成功通过了所有的测试用例!");
		} else {
			code.setPass(false);
			codeJudge.setPass(false);
			codeJudge.setMsg("评测未通过!");
			codeJudge.setReason("代码不通过,请检查代码是否符合要求!");
		}
		courseService.saveCode(code);
		Course course = courseService.findCourseByTaskId(code.getTaskId());
		Progress progress = courseService.findProgressByUsernameAndCourseId(username, course.getCourseId());
		if (pass) {
			if (progress == null) {
				progress = new Progress();
				progress.setProgressId(Utils.getUUID());
				progress.setCourseId(course.getCourseId());
				progress.setChapterId(chapter.getChapterId());
				progress.setLessonId(lesson.getLessonId());
				progress.setTaskId(task.getTaskId());
				progress.setCreatedAt(new Date());
				progress.setUsername(username);
				courseService.saveProgress(progress);
			} else {
				progress.setChapterId(chapter.getChapterId());
				progress.setLessonId(lesson.getLessonId());
				progress.setTaskId(task.getTaskId());
				progress.setUsername(username);
				courseService.updateProgress(progress);
			}
		}
		return codeJudge;
	}

}

class ListenConsumer implements Callable<Boolean> {

	private String receiveName;
	private String codeId;

	public ListenConsumer(String receiveName, String codeId) {
		this.receiveName = receiveName;
		this.codeId = codeId;
	}

	@Override
	public Boolean call() {
		return Jms.consumer(receiveName, codeId);
	}
}
