package qisi.utils;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import qisi.bean.admin.AdminUser;
import qisi.bean.admin.News;
import qisi.bean.json.ApiResult;
import qisi.bean.json.CodeJudge;
import qisi.bean.user.User;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerUpdatePassword;

import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:14
 */

public class Utils {
	private static final String SALT = "ddv";
	private static final String SPACE = " ";
	private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private static final String REGEX_PHONE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	public static String encode(String password) {
		password = password + SALT;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static boolean checkPhone(String phone) {
		if (phone == null) {
			return false;
		}
		return Pattern.matches(REGEX_PHONE, phone);
	}

	public static boolean checkEmail(String email) {
		if (email == null) {
			return false;
		}
		return Pattern.matches(REGEX_EMAIL, email);
	}

	public static boolean fieldValue(String value) {
		if (value == null || "".equals(value) || value.contains(SPACE)) {
			return false;
		}
		return true;
	}

	public static boolean checkFormUser(User user, ApiResult response) {
		response.setStatus(400);
		if (user == null) {
			response.setMsg("用户信息有误!");
			return false;
		}
		if (!Utils.fieldValue(user.getUsername())) {
			response.setMsg("用户名格式有误!");
			return false;
		}

		if (!Utils.fieldValue(user.getPassword())) {
			response.setMsg("用户密码格式有误!");
			return false;
		}

		if (!Utils.fieldValue(user.getSex())) {
			response.setMsg("性别格式有误!");
			return false;
		}

		if (!Utils.fieldValue(user.getAge())) {
			response.setMsg("年龄格式有误!");
			return false;
		}

		if (!Utils.fieldValue(user.getJob())) {
			response.setMsg("职业格式有误!");
			return false;
		}

		if (!Utils.checkPhone(user.getPhone())) {

			response.setMsg("无效的电话号码!");
			return false;
		}

		if (!Utils.checkEmail(user.getEmail())) {
			response.setMsg("无效的电子邮箱!");
			return false;
		}
		response.setStatus(200);
		return true;
	}

	public static CodeJudge getCodeJudge(CodeJudge codeJudge, ExecutorService executor, Future<Boolean> future, int MAX_WAIT) {
		boolean pass = false;
		try {
			if (future.get(MAX_WAIT, TimeUnit.SECONDS)) {
				pass = true;
			}
		} catch (TimeoutException e) {
			codeJudge.setMsg("评测系统忙,请稍后提交!");
			future.cancel(Boolean.TRUE);
			return codeJudge;
		} catch (Exception e) {
			return codeJudge;
		} finally {
			if (!executor.isShutdown()) {
				executor.shutdown();
			}
		}

		if (!executor.isShutdown()) {
			executor.shutdown();
		}

		if (pass) {
			codeJudge.setPass(true);
			codeJudge.setMsg("代码成功通过了所有的测试用例!");
		} else {
			codeJudge.setPass(false);
			codeJudge.setMsg("代码未通过,请检查代码是否符合要求!");
		}

		return codeJudge;
	}

	public static boolean checkWorker(Worker formWorker) {
		return !(formWorker == null || formWorker.getUsername() == null || formWorker.getPassword() == null);
	}

	public static boolean checkAdminUser(AdminUser adminUser) {
		return (adminUser == null || adminUser.getUsername() == null || adminUser.getPassword() == null);
	}

	public static Date getFormatDate() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return new Date(year - 1900, month, day);
	}

	public static int getDays(Date start, Date end) {
		if (start.getTime() > end.getTime()) {
			return 0;
		}
		return (int) ((end.getTime() - start.getTime()) / (1000 * 3600 * 24));
	}

	public static List<News> mockNews() {
		List<News> newsList = new ArrayList<>();
		String[] msgs = {"所有员工,新年快乐!🐖阖家团结,幸福美满!",
				"紧急通知,所有技术部门的年底总结需要在2.19日之前上交.",
				"国家税务总局关于修改〈税务部门规章制定实施办法〉的决定》，已经2018年12月29日国家税务总局2018年度第3次局务会议审议通过，现予公布，自2019年3月1日起施行。",
				"10月受邀出席2018世界知识论坛，加强国际合作与交流，深化出海战略",
				"投资少儿绘本平台咔哒故事和儿童编程教育平台妙小程，进军互联网少儿教育领域"
		};

		for (int i = 0; i < 5; i++) {
			News news = new News();
			news.setId(i);
			news.setMsg(msgs[i]);
			news.setCreatedAt(new Date());
			newsList.add(news);
		}

		return newsList;
	}

	public static boolean checkWorkerUpdatePassword(WorkerUpdatePassword workerUpdatePassword) {
		return !(workerUpdatePassword == null || workerUpdatePassword.getPassword() == null || workerUpdatePassword.getNewPassword() == null);
	}

	public static void main(String[] args) {
		System.out.println(encode("123"));
	}


}
