package cn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Profile({"product"})
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView exceptionHandler(Exception e){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("exception", e);
		return new ModelAndView("/admin/error/exception",map);
	}
}
