package qisi.bean.query;

import qisi.bean.work.WorkerDayOff;

import java.util.List;

/**
 * @author : ddv
 * @date : 2019/1/30 上午12:43
 */

public class WorkerDayOffPageQuery {
	private int total;
	private List<WorkerDayOff> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<WorkerDayOff> getRows() {
		return rows;
	}

	public void setRows(List<WorkerDayOff> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "WorkerDayOffPageQuery{" +
				"total=" + total +
				", rows=" + rows +
				'}';
	}
}
