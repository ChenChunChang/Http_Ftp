package cn.admin.core;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@SuppressWarnings("unused")
//@Controller
public class ErrorHandler implements ErrorController {
	private static final String ERROR_PATH = "/error";
	
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }     
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    String error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring. 
        // Here we just define response body.
    	
    	response.setStatus(200);
    	Map<?, ?> error=new HashMap<String, Object>();
    	//error.put(key, value)
        return "error";
    }
    
}