package qisi.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import qisi.utils.ListenConsumer;
import qisi.utils.Utils;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 课程相关API
 * ResponseBody 此注解标志RestAPI, 并且返回数据JSON格式
 *
 * @author : ddv
 * @date : 2018/10/29 下午1:25
 */

@Controller
public class CourseController {
	private static final String COMMIT_QUEUE = "commit";
	private static final String RECEIVE_QUEUE = "receive";
	private static final int MAX_WAIT = 60;
	private static final int POOL_SIZE = 1;

	private static final String COURSES_HTML = "courses.html";
	private static final String CHAPTER_HTML = "chapters.html";
	private static final String LESSONS_HTML = "lessons.html";
	private static final String TASKS_HTML = "tasks.html";
	private static final String TASK_HTML = "task.html";

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	private CourseService courseService;

	@Autowired
	private ProducerService producerService;

	/**
	 * 课程列表
	 *
	 * @param request 数据回显
	 * @return courses列表
	 */
	@GetMapping("/courses")
	public String findAllCourses(HttpServletRequest request) {
		CoursesQuery coursesQuery = new CoursesQuery();
		coursesQuery.setCourses(courseService.findAllPublishedCourses());
		coursesQuery.setTotal(coursesQuery.getCourses().size());
		request.setAttribute("courses", coursesQuery.getCourses());
		return COURSES_HTML;
	}

	/**
	 * 按课程ID获取该课程下的所有目录
	 *
	 * @param courseId courseId
	 * @param request  数据回显
	 * @return chapters列表
	 */
	@GetMapping("/course/{courseId}/chapters")
	public String findChaptersByCourseId(@PathVariable String courseId, HttpServletRequest request) {
		List<Chapter> chapters = courseService.findChaptersByCourseId(courseId);
		request.setAttribute("chapters", chapters);
		return CHAPTER_HTML;
	}

	/**
	 * 对应chapter下的lessons
	 *
	 * @param chapterId chapterId
	 * @param request   数据回显
	 * @return 训练页面
	 */
	@GetMapping("/chapter/{chapterId}/lessons")
	public String findLessonsByChapterId(@PathVariable("chapterId") String chapterId, HttpServletRequest request) {
		List<Lesson> lessons = courseService.findLessonsByChapterId(chapterId);
		request.setAttribute("lessons", lessons);
		return LESSONS_HTML;
	}

	/**
	 * 查询lesson对应的所有task
	 *
	 * @param lessonId lessonId
	 * @param request  数据回显
	 * @return tasks列表
	 */
	@GetMapping("/lesson/{lessonId}/tasks")
	public String findTasksByLessonId(@PathVariable String lessonId, HttpServletRequest request) {
		List<Task> tasks = courseService.findTasksByLessonId(lessonId);
		request.setAttribute("tasks", tasks);
		return TASKS_HTML;
	}

	/**
	 * 获取对应Task
	 *
	 * @param taskId  taskId
	 * @param request 数据回显
	 * @return task页面
	 */
	@GetMapping("/task/{taskId}")
	public String findTaskByTaskId(@PathVariable("taskId") String taskId, HttpServletRequest request) {
		request.setAttribute("task", courseService.findTaskByTaskId(taskId));
		return TASK_HTML;
	}

	/**
	 * 根据courseName查询courses
	 *
	 * @param courseName courseName
	 * @return courses
	 */
	@ResponseBody
	@GetMapping("/course")
	public List<Course> findCourseByName(@RequestParam("courseName") String courseName) {
		return courseService.findCourseByName(courseName);
	}

	/**
	 * 根据courseId查询course
	 *
	 * @param courseId courseId
	 * @return course
	 */
	@ResponseBody
	@GetMapping("/course/{courseId}")
	public Course findCourseByCourseId(@PathVariable("courseId") String courseId) {
		return courseService.findCourseByCourseId(courseId);
	}

	/**
	 * 提交代码信息到消息队列,串行拉取评测结果
	 * 最大等待60s 否则直接返回代码未通过评测
	 * 通过 存储提交记录,并更新用户课程进度
	 * 未通过 只存储提交记录
	 * 拉取需要观察GC状态
	 *
	 * @param code    code
	 * @param session session
	 * @return CodeJudge结果
	 */
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

		code.setCodeId(Utils.getUUID());
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
			logger.info("评测系统繁忙关闭", e.getStackTrace());
			return codeJudge;
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
			codeJudge.setMsg("代码未通过,请检查代码是否符合要求!");
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

