package qisi.bean.course;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/10/29 下午3:00
 */

@Entity(name = "course_chapter_lesson_tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String lessonId;
	private String taskId;
	private int taskIndex;
	private int maxTime;
	private int maxMemory;
	@Lob
	@Column(columnDefinition = "text")
	private String introduction;
	@Lob
	@Column(columnDefinition = "text")
	private String firstCode;
	@Lob
	@Column(columnDefinition = "text")
	private String secondCode;
	private Date createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(int maxMemory) {
		this.maxMemory = maxMemory;
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", lessonId='" + lessonId + '\'' +
				", taskId='" + taskId + '\'' +
				", taskIndex=" + taskIndex +
				", maxTime=" + maxTime +
				", maxMemory=" + maxMemory +
				", introduction='" + introduction + '\'' +
				", firstCode='" + firstCode + '\'' +
				", secondCode='" + secondCode + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}

