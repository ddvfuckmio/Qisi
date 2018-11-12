package qisi.bean.json;

/**
 * @author : ddv
 * @date : 2018/11/12 下午2:29
 */

public class LoginResponse {
	private int status;
	private String msg;
	private String username;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"status=" + status +
				", msg='" + msg + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}
