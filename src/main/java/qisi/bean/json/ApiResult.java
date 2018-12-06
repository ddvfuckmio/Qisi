package qisi.bean.json;

/**
 * @author : ddv
 * @date : 2018/11/12 下午2:29
 */

public class ApiResult {
	private int status;
	private String msg;

	public ApiResult() {
	}

	public ApiResult(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

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


	public static ApiResult SUCCESS() {
		return new ApiResult(200, "成功!");
	}

	public static ApiResult FAILED(String msg) {
		return new ApiResult(401, msg);
	}

	public static ApiResult ILLEGAL() {
		return new ApiResult(401, "需要登录的API!");
	}

	public static ApiResult ERROR() {
		return new ApiResult(400, "请求数据格式有误!");
	}

}
