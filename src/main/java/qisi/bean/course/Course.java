package qisi.bean.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:19
 */

@Entity(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String courseId;
	private String courseName;
	private String introduction;
	private String type;
	private boolean isPublished;
	private Date createdAt;

	public Course() {
	}

	public Course(String courseId, String courseName, String introduction, int totalLessons, int totalExercises, Date createdAt) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.introduction = introduction;
		this.createdAt = createdAt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean published) {
		isPublished = published;
	}

	@Override
	public String toString() {
		return "Course{" +
				"id=" + id +
				", courseId='" + courseId + '\'' +
				", courseName='" + courseName + '\'' +
				", introduction='" + introduction + '\'' +
				", type='" + type + '\'' +
				", isPublished=" + isPublished +
				", createdAt=" + createdAt +
				'}';
	}
}
