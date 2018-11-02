package qisi.bean.json;

import qisi.bean.course.Course;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/11/2 上午11:38
 */

public class CoursesQuery {
	private int total;
	private List<Course> courses;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "CoursesQuery{" +
				"total=" + total +
				", courses=" + courses +
				'}';
	}
}
