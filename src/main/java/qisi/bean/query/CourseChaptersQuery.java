package qisi.bean.query;

import qisi.bean.course.Chapter;
import qisi.bean.course.Course;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/11/6 下午1:12
 */

public class CourseChaptersQuery {
	private Course course;
	private List<Chapter> chapters;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	@Override
	public String toString() {
		return "CourseChaptersQuery{" +
				"course=" + course +
				", chapters=" + chapters +
				'}';
	}
}
