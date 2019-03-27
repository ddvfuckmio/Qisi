package qisi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import qisi.bean.admin.AdminUser;
import qisi.bean.json.ApiResult;
import qisi.bean.query.WorkerDayOffPageQuery;
import qisi.bean.query.WorkerPageQuery;
import qisi.bean.query.WorkerPayRollPageQuery;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerDayOff;
import qisi.bean.work.WorkerPayRoll;
import qisi.service.AdminService;
import qisi.service.WorkerService;
import qisi.utils.TimeUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 管理员及测试API
 * 添加类API自身主键id,标志id,createdAt自动生成,无需添加
 *
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */


@RestController
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private HttpSession session;

	@Autowired
	private AdminService adminService;

	@Autowired
	private WorkerService workerService;

	@PostMapping("/login")
	public ApiResult login(@RequestBody AdminUser formUser) {
		return adminService.login(formUser, session);
	}

	@GetMapping("/workers")
	@ResponseBody
	public WorkerPageQuery workers(String username, String realName, String sex, String department, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		WorkerPageQuery workerPageQuery = new WorkerPageQuery();

		Worker worker = new Worker();
		worker.setUsername(username);
		worker.setRealName(realName);
		worker.setDepartment(department);
		worker.setSex(sex);

		workerPageQuery.setRows(workerService.findWorkerByPageAndParams(worker, PageRequest.of((page - 1), rows)));
		workerPageQuery.setTotal(workerService.getWorkerByParamsCount(worker));

		return workerPageQuery;

	}

	@GetMapping("/dayOffs")
	public WorkerDayOffPageQuery workerDayOffs(Integer state, Date startDate, Date endDate, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		WorkerDayOffPageQuery workerDayOffPageQuery = new WorkerDayOffPageQuery();

		WorkerDayOff workerDayOff = new WorkerDayOff();

		if (state != null) {
			workerDayOff.setState(state);
		}
		workerDayOff.setStartDate(startDate);
		workerDayOff.setEndDate(endDate);

		List<WorkerDayOff> workerDayOffList = workerService.getWorkerDayOffByParams(workerDayOff, PageRequest.of((page - 1), rows));
		int total = workerService.getWorkerDayOffCountByParams(workerDayOff);

		workerDayOffPageQuery.setTotal(total);
		workerDayOffPageQuery.setRows(workerDayOffList);

		return workerDayOffPageQuery;
	}

	@PostMapping("/operatorDayOff")
	public ApiResult operatorDayOff(@RequestBody  WorkerDayOff workerDayOff) {
		System.out.println(workerDayOff);
		if(workerDayOff==null || workerDayOff.getId()==0 || workerDayOff.getState()==0){
			return ApiResult.FAILED("参数有误!");
		}

		return workerService.updateWorkerDayOffState(workerDayOff);
	}

	@GetMapping("/workerPayRoll")
	public WorkerPayRollPageQuery getWorkerPayRoll(String department, @DateTimeFormat(pattern = "yyyy-MM") Date payRollDate, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		WorkerPayRollPageQuery workerPayRollPageQuery = new WorkerPayRollPageQuery();
		WorkerPayRoll workerPayRoll = new WorkerPayRoll();

		if (payRollDate == null) {
			payRollDate = TimeUtil.getLastMonth();
		}

		if (department != null && !department.equals("")) {
			workerPayRoll.setDepartment(department);
		}
		workerPayRoll.setPayrollDate(payRollDate);

		workerPayRollPageQuery.setRows(adminService.getWorkerPayRollByParams(workerPayRoll, PageRequest.of(page - 1, rows)));
		workerPayRollPageQuery.setTotal(adminService.getWorkerPayRollByParamsCount(workerPayRoll));
		return workerPayRollPageQuery;
	}

}
