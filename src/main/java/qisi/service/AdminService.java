package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import qisi.bean.admin.AdminUser;
import qisi.bean.json.ApiResult;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerCheck;
import qisi.bean.work.WorkerPayRoll;
import qisi.dao.AdminUserRepository;
import qisi.dao.WorkerCheckRepository;
import qisi.dao.WorkerRepository;
import qisi.dao.admin.WorkerPayRollRepository;
import qisi.dao.worker.WorkerDayOffRepository;
import qisi.utils.TimeUtil;
import qisi.utils.Utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : ddv
 * @date : 2018/12/6 下午3:32
 */
@Service
public class AdminService {

	@Autowired
	private AdminUserRepository adminUserRepository;

	@Autowired
	private WorkerPayRollRepository workerPayRollRepository;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private WorkerCheckRepository workerCheckRepository;

	@Autowired
	private WorkerDayOffRepository workerDayOffRepository;

	public ApiResult login(AdminUser formUser, HttpSession session) {

		if (Utils.checkAdminUser(formUser)) {
			return ApiResult.LOGIN_ERROR();
		}

		AdminUser adminUser = adminUserRepository.findAdminUserByUsername(formUser.getUsername());

		if (!(adminUser != null && adminUser.getPassword().equals(Utils.encode(formUser.getPassword())))) {
			return ApiResult.LOGIN_ERROR();
		}

		session.setAttribute("username", formUser.getUsername());
		return ApiResult.SUCCESS();
	}

	public boolean checkAdminUser(String username) {
		return adminUserRepository.findAdminUserByUsername(username) != null;
	}

	public List<WorkerPayRoll> getWorkerPayRollByParams(WorkerPayRoll workerPayRoll, Pageable pageable) {

		return Utils.PageToList(workerPayRollRepository.findAll(workerPayRollSpecification(workerPayRoll), pageable));
	}

	public int getWorkerPayRollByParamsCount(final WorkerPayRoll workerPayRoll) {
		return (int) workerPayRollRepository.count(workerPayRollSpecification(workerPayRoll));
	}

	private Specification<WorkerPayRoll> workerPayRollSpecification(final WorkerPayRoll workerPayRoll) {
		return new Specification<WorkerPayRoll>() {

			List<Predicate> predicates = new ArrayList<>();

			@Override
			public Predicate toPredicate(Root<WorkerPayRoll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				if (workerPayRoll.getPayrollDate() != null) {
					predicates.add(criteriaBuilder.equal(
							root.get("payrollDate").as(Date.class), workerPayRoll.getPayrollDate()));
				}

				if (workerPayRoll.getDepartment() != null) {
					predicates.add(criteriaBuilder.equal(
							root.get("department").as(String.class), workerPayRoll.getDepartment()));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	/**
	 * 生成新的绩效记录
	 *
	 * @param payrollDate
	 */
	public void triggerWorkerPayRollCount(Date payrollDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(payrollDate.getTime());

		List<Date> dates = TimeUtil.getDayByMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
		TimeUtil.filterWeekend(dates);

		// 应当出勤天数
		int workDayCount = dates.size();

		List<Worker> workers = new ArrayList<>();
		workers.addAll(workerRepository.findAll());

		workers.forEach(worker -> {
			List<WorkerCheck> checks = new ArrayList<>();
			WorkerPayRoll payRoll = new WorkerPayRoll();
			dates.forEach(date -> {
				WorkerCheck check = workerCheckRepository.findWorkerCheckByUsernameAndDate(worker.getUsername(), date);
				checks.add(check);
			});

			// 迟到次数
			long delayCount = checks.stream().filter(check -> {
				Date signIn = check.getSignIn();
				if (signIn != null && signIn.getHours() > 8) {
					return true;
				}
				return false;
			}).count();

			// 早退次数
			long earlyLeave = checks.stream().filter(check -> {
				Date signOut = check.getSignOut();
				if (signOut != null || signOut.getHours() < 18) {
					return true;
				}
				return false;
			}).count();

			//  统计下班无打卡的旷工
			long breakCount = checks.stream().filter(check -> {
				if (check.getSignIn() != null || check.getSignOut() == null) {
					return true;
				}
				return false;
			}).count();

			// 无打卡的记录数
			int breaks = workDayCount - checks.size();

			payRoll.setDepartment(worker.getDepartment());
			payRoll.setAbsentCount(breaks);
			payRoll.setCreatedAt(new Date());
			payRoll.setDelayCount((int) delayCount);
			payRoll.setEarlyCount((int) earlyLeave);
			payRoll.setUsername(worker.getUsername());
		});
	}

	public boolean checkRecords(Date payrollDate) {
		WorkerPayRoll param = new WorkerPayRoll();
		param.setPayrollDate(payrollDate);
		return workerPayRollRepository.count(workerPayRollSpecification(param)) > 0;

	}
}
