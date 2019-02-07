package qisi.bean.admin;

import java.util.Date;

/**
 * @author : ddv
 * @date : 2019/2/5 下午6:48
 */

public class News {
	private int id;
	private String msg;
	private Date createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "News{" +
				"id=" + id +
				", msg='" + msg + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
