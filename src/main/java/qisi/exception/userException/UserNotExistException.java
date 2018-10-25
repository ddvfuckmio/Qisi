package qisi.exception.userException;

/**
 * @author : ddv
 * @date : 2018/10/24 下午3:35
 */

public class UserNotExistException extends RuntimeException {

	private String account;

	public UserNotExistException(String account) {
		super("当前账户名不存在!");
		this.account = account;
	}


}
