package qisi.bean.query;

import qisi.bean.work.WorkerPayRoll;

import java.util.List;

/**
 * @author : ddv
 * @since : 2019/3/24 下午9:27
 */

public class WorkerPayRollPageQuery {

	private int total;
	private List<WorkerPayRoll> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<WorkerPayRoll> getRows() {
		return rows;
	}

	public void setRows(List<WorkerPayRoll> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "WorkerPayRollPageQuery{" +
				"total=" + total +
				", rows=" + rows +
				'}';
	}
}
