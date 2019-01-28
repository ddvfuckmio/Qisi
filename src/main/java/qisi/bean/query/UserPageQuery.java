package qisi.bean.query;

import qisi.bean.user.User;

import java.util.List;

/**
 * @author : ddv
 * @date : 2019/1/22 上午11:15
 */

public class UserPageQuery {
	private int total;
	private List<User> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<User> getRows() {
		return rows;
	}

	public void setRows(List<User> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "UserPageQuery{" +
				"total=" + total +
				", rows=" + rows +
				'}';
	}
}
