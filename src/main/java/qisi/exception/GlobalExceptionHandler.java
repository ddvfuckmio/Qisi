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
	@ExceptionHandler(value = com.alibaba.fastjson.JSONException.class)
	public ApiResult JsonExceptionHandler(){
		return ApiResult.ILLEGAL();
	}

	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	public String globalExceptionHandler(HttpServletRequest request,Exception exception) throws Exception{
		return "服务器繁忙,请稍后再试!";
	}

}
