package qisi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import qisi.bean.json.ApiResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : ddv
 * @date : 2018/10/24 下午3:41
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = AdminAuthorityException.class)
	public ApiResult AdminAuthorityExceptionHandler() {
		return ApiResult.FAILED("违法的管理员操作!");
	}

	@ResponseBody
	@ExceptionHandler(value = com.alibaba.fastjson.JSONException.class)
	public ApiResult JsonExceptionHandler() {
		return ApiResult.ERROR();
	}

	@ResponseBody
	@ExceptionHandler(value = org.springframework.web.bind.MissingServletRequestParameterException.class)
	public ApiResult ParameterExceptionHandler() {
		return ApiResult.FAILED("请求参数格式有误!");
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public String globalExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		return "服务器繁忙,请稍后再试!";
	}

}
