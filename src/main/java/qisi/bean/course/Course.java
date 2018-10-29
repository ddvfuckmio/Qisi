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
	private int totalLessons;
	private int totalExercises;
	private Date createdAt;

	public Course() {
	}

	public Course(String courseId, String courseName, String introduction, int totalLessons, int totalExercises, Date createdAt) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.introduction = introduction;
		this.totalLessons = totalLessons;
		this.totalExercises = totalExercises;
		this.createdAt = createdAt;
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

	public int getTotalLessons() {
		return totalLessons;
	}

	public void setTotalLessons(int totalLessons) {
		this.totalLessons = totalLessons;
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
