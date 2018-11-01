package qisi.bean.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/11/1 下午4:30
 */

@Entity(name = "course_chapters")
public class Chapter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String courseId;
	private String ChapterId;
	private String ChapterName;
	private int ChapterIndex;
	private Date createdAt;

	public Chapter() {
	}

	public Chapter(String chapterId, String chapterName, int chapterIndex, Date createdAt) {
		ChapterId = chapterId;
		ChapterName = chapterName;
		ChapterIndex = chapterIndex;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChapterId() {
		return ChapterId;
	}

	public void setChapterId(String chapterId) {
		ChapterId = chapterId;
	}

	public String getChapterName() {
		return ChapterName;
	}

	public void setChapterName(String chapterName) {
		ChapterName = chapterName;
	}

	public int getChapterIndex() {
		return ChapterIndex;
	}

	public void setChapterIndex(int chapterIndex) {
		ChapterIndex = chapterIndex;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "Chapter{" +
				"id=" + id +
				", courseId='" + courseId + '\'' +
				", ChapterId='" + ChapterId + '\'' +
				", ChapterName='" + ChapterName + '\'' +
				", ChapterIndex=" + ChapterIndex +
				", createdAt=" + createdAt +
				'}';
	}
}
