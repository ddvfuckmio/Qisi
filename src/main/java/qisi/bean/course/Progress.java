package qisi.bean.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/11/7 下午4:58
 */
@Entity(name = "user_course_progress")
public class Progress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String progressId;
	private String username;
	private String courseId;
	private String chapterId;
	private String lessonId;
	private String taskId;
	private Date createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getLessonId() {
		return lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getProgressId() {
		return progressId;
	}

	public void setProgressId(String progressId) {
		this.progressId = progressId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	@Override
	public String toString() {
		return "Progress{" +
				"id=" + id +
				", progressId='" + progressId + '\'' +
				", username='" + username + '\'' +
				", courseId='" + courseId + '\'' +
				", chapterId='" + chapterId + '\'' +
				", lessonId='" + lessonId + '\'' +
				", taskId='" + taskId + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
