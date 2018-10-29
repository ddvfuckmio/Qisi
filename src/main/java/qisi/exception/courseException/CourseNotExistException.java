package qisi.exception.courseException;

/**
 * @author : ddv
 * @date : 2018/10/29 下午4:21
 */

public class CourseNotExistException extends RuntimeException {

	private String courserName;

	public CourseNotExistException(String courserName) {
		super("课程名不存在!");
		this.courserName = courserName;
	}
}
