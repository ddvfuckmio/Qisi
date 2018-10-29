package qisi.bean.course;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:48
 */

@Entity(name = "course_lessons")
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String lessonId;
	private String courseId;
	private String lessonName;
	private int lessonIndex;
	private String comment;
	private String originCode;
	private int totalExercises;
	private Date createdAt;

	public Lesson() {
	}

	public Lesson(String lessonId, String courseId, String lessonName, int lessonIndex, String text, String comment, String originCode, int totalExercises, Date createdAt) {
		this.lessonId = lessonId;
		this.courseId = courseId;
		this.lessonName = lessonName;
		this.lessonIndex = lessonIndex;
		this.comment = comment;
		this.originCode = originCode;
		this.totalExercises = totalExercises;
		this.createdAt = createdAt;
	}

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

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public int getLessonIndex() {
		return lessonIndex;
	}

	public void setLessonIndex(int lessonIndex) {
		this.lessonIndex = lessonIndex;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public int getTotalExercises() {
		return totalExercises;
	}

	public void setTotalExercises(int totalExercises) {
		this.totalExercises = totalExercises;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
