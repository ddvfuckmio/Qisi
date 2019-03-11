package qisi.bean.query;

import qisi.bean.user.User;
import qisi.bean.work.Worker;

import java.util.List;

/**
 * @author : ddv
 * @date : 2019/1/22 上午11:15
 */

public class WorkerPageQuery {
	private int total;
	private List<Worker> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Worker> getRows() {
		return rows;
	}

	public void setRows(List<Worker> rows) {
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
