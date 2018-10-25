package qisi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import qisi.exception.userException.UserNotExistException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : ddv
 * @date : 2018/10/24 下午3:41
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


	@ExceptionHandler(value=UserNotExistException.class)
	public String userNotExistException(HttpServletRequest request,Exception exception) throws Exception{
		return "当前用户不存在!";
	}

	@ExceptionHandler(value=Exception.class)
	public String globalExceptionHandler(HttpServletRequest request,Exception exception) throws Exception{
		return "服务器繁忙,请稍后再试!";
	}
}
