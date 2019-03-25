package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import qisi.bean.admin.AdminUser;
import qisi.bean.json.ApiResult;
import qisi.bean.work.WorkerPayRoll;
import qisi.dao.AdminUserRepository;
import qisi.dao.admin.WorkerPayRollRepository;
import qisi.utils.Utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

}
