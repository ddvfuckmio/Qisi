package qisi.bean.json;

/**
 * @author : ddv
 * @date : 2018/11/1 上午11:10
 */

public class CodeJudge {

	private boolean pass;
	private String reason;
	private String msg;

	public CodeJudge() {
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
