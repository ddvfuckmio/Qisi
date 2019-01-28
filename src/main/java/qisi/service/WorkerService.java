package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.json.ApiResult;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerCheck;
import qisi.dao.WorkerCheckRepository;
import qisi.dao.WorkerRepository;
import qisi.utils.Utils;

import java.util.Date;

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
					return ApiResult.FAILED("您已经打卡了,请勿重复打卡!");
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
					return ApiResult.FAILED("您已经打卡了,请勿重复打卡!");

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
}
