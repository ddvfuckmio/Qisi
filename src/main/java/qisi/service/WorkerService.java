package qisi.service;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import qisi.bean.json.ApiResult;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerCheck;
import qisi.bean.work.WorkerDayOff;
import qisi.bean.work.WorkerUpdatePassword;
import qisi.dao.WorkerCheckRepository;
import qisi.dao.WorkerRepository;
import qisi.dao.worker.WorkerDayOffRepository;
import qisi.utils.Utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author : ddv
 * @date : 2019/1/24 下午2:35
 */

@Service
public class WorkerService {

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private WorkerCheckRepository workerCheckRepository;

	@Autowired
	private WorkerDayOffRepository workerDayOffRepository;

	/**
	 * 根据用户名查找员工
	 *
	 * @param username
	 * @return
	 */
	public Worker findWorkerByUsername(String username) {
		return workerRepository.findWorkerByUsername(username);
	}

	/**
	 * @param username
	 * @param type     1表示上班 2表示下班
	 * @return
	 */
	public ApiResult workerSign(String username, int type) {
		WorkerCheck workerCheck = workerCheckRepository.
				findWorkerCheckByUsernameAndDate(username, Utils.getFormatDate());
		switch (type) {
			case 1: {
				if (workerCheck != null) {
					SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
					System.out.println(formatter.format(workerCheck.getCheckDay()));
					return ApiResult.FAILED("您已经进行过上班打卡了!");
				}
				workerCheck = new WorkerCheck();
				workerCheck.setSignIn(new Date());
				workerCheck.setUsername(username);
				workerCheck.setCheckDay(Utils.getFormatDate());
				workerCheckRepository.save(workerCheck);
				return ApiResult.SUCCESS();
			}
			case 2: {
				if (workerCheck == null) {
					return ApiResult.FAILED("请先进行上班打卡哦!");
				}

				if (workerCheck.getSignOut() != null) {
					return ApiResult.FAILED("您已经进行过下班打卡了!");

				} else {
					workerCheckRepository
							.updateWorkerCheckSignOut(new Date(), workerCheck.getUsername()
									, workerCheck.getCheckDay());
				}
				return ApiResult.SUCCESS();
			}
			default:
				return ApiResult.FAILED("请求参数有误!");
		}

	}

	/**
	 * 获取用户请假表记录数
	 *
	 * @return
	 */
	public int getWorkerDayOffCount(String username) {
		return workerDayOffRepository.getWorkerDayOffsCount(username);

	}

	public List<WorkerDayOff> findWorkerDayOffsByPage(String username, int start, int rows) {
		return workerDayOffRepository.findWorkerDayOffsByPage(username, Utils.getFormatDate(), start, rows);

	}

	public ApiResult cancelDayOff(String username, int id) {

		WorkerDayOff workerDayOff = workerDayOffRepository.findWorkerDayOffById(id);

		if (workerDayOff == null || !workerDayOff.getUsername().equals(username)) {
			return ApiResult.ERROR();
		}

		if (workerDayOff.getStartDate().getTime() <= new Date().getTime()) {
			return ApiResult.FAILED("不能取消正在进行中的假期!");
		}

		workerDayOffRepository.deleteWorkerDayOff(id);

		return ApiResult.SUCCESS();
	}

	public ApiResult addDayOff(WorkerDayOff workerDayOff) {
		if (checkDayOff(workerDayOff)) {
			workerDayOff.setCreatedAt(Utils.getFormatDate());
			workerDayOff.setState(1);
			workerDayOffRepository.save(workerDayOff);
			return ApiResult.SUCCESS();
		}
		return ApiResult.FAILED("当前时间段已经有假期申请记录!");
	}

	public boolean checkDayOff(WorkerDayOff workerDayOff) {
		List<WorkerDayOff> workerDayOffs = workerDayOffRepository.
				findWorkerDayOffsByParams(workerDayOff.getUsername(), workerDayOff.getStartDate(), workerDayOff.getEndDate());
		return workerDayOffs.size() == 0;
	}

	public ApiResult updatePassword(WorkerUpdatePassword workerUpdatePassword) {
		if (!Utils.checkWorkerUpdatePassword(workerUpdatePassword)) {
			return ApiResult.FAILED("请求参数有误!");
		}

		if (workerUpdatePassword.getPassword().equals(workerUpdatePassword.getNewPassword())) {
			return ApiResult.FAILED("原密码不能与新密码相同!");
		}

		Worker worker = workerRepository.findWorkerByUsername(workerUpdatePassword.getUsername());

		if (!(worker.getPassword().equals(Utils.encode(workerUpdatePassword.getPassword())))) {
			return ApiResult.FAILED("用户原密码不正确!");
		}

		workerRepository.updatePassword(workerUpdatePassword.getUsername(), Utils.encode(workerUpdatePassword.getNewPassword()));

		return ApiResult.SUCCESS();
	}

	public Page<Worker> findWorkerByPageAndParams(final Worker worker, Pageable pageable) {

		return workerRepository.findAll(new Specification<Worker>() {

			List<Predicate> predicates = new ArrayList<Predicate>();

			@Override
			public Predicate toPredicate(Root<Worker> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				if (Utils.checkStringIsNotBlank(worker.getUsername())) {
					predicates.add(criteriaBuilder.equal(root.get("username").as(String.class), worker.getUsername()));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}
}
