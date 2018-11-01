package qisi.bean.course;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:48
 */

@Entity(name = "course_chapter_lessons")
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String chapterId;
	private String lessonId;
	private String lessonName;
	private int lessonIndex;
	private String introduction;
	private String originCode;
	private Date createdAt;

	public Lesson() {

	}

	public Lesson(String chapterId, String lessonId, String lessonName, int lessonIndex, String introduction, String originCode, Date createdAt) {
		this.chapterId = chapterId;
		this.lessonId = lessonId;
		this.lessonName = lessonName;
		this.lessonIndex = lessonIndex;
		this.introduction = introduction;
		this.originCode = originCode;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getLessonId() {
		return lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Lesson{" +
				"id=" + id +
				", chapterId='" + chapterId + '\'' +
				", lessonId='" + lessonId + '\'' +
				", lessonName='" + lessonName + '\'' +
				", lessonIndex=" + lessonIndex +
				", introduction='" + introduction + '\'' +
				", originCode='" + originCode + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
