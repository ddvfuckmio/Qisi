package qisi.bean.json;

/**
 * @author : ddv
 * @date : 2018/11/1 上午11:10
 */

public class CodeJudge {

	private boolean pass;
	private String reason;

	public CodeJudge() {
	}

	public CodeJudge(boolean pass, String reason) {
		this.pass = pass;
		this.reason = reason;
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

	@Override
	public String toString() {
		return "CodeJudge{" +
				"pass=" + pass +
				", reason='" + reason + '\'' +
				'}';
	}
}
