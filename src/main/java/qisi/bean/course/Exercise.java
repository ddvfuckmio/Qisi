package qisi.bean.course;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/10/29 下午3:00
 */

@Entity(name = "course_lesson_exercises")
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String courseId;
	private String lessonId;
	private String exerciseId;
	private int exerciseIndex;
	@Lob
	@Column(columnDefinition = "text")
	private String paper;
	@Lob
	@Column(columnDefinition = "text")
	private String firstCode;
	@Lob
	@Column(columnDefinition = "text")
	private String secondCode;
	private Date createdAt;

	public Exercise() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getExerciseIndex() {
		return exerciseIndex;
	}

	public void setExerciseIndex(int exerciseIndex) {
		this.exerciseIndex = exerciseIndex;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
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
}
