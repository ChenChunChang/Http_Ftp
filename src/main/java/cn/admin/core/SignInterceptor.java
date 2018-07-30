package cn.admin.core;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.util.Tools;

public class SignInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod hm=(HandlerMethod)handler;
			Class<?> clazz=hm.getBeanType();
	        Method m=hm.getMethod();
	        if (clazz!=null && m != null ){
	        	if (m.isAnnotationPresent(IgnoreSign.class)) {
					return true;
				}
	        	if (m.isAnnotationPresent(Sign.class) || clazz.isAnnotationPresent(Sign.class)) {
	        		if (request.getParameter("sign")==null) {
	        			return false;
	        		}
	        		TreeMap<String, String> map=new TreeMap<String,String>();
	        		Enumeration<String> names=request.getParameterNames();
	        		String sign="";
	        		while (names.hasMoreElements()) {
	        			String name=(String)names.nextElement();
	        			if (name.compareTo("sign")==0) {
	        				sign=request.getParameter("sign");
	        				continue;
	        			}
	        			map.put(name, request.getParameter(name));
	        		}
	        		
	        		String signData="";
	        		Iterator<String> it = map.keySet().iterator();  
	        		while (it.hasNext()) {
	        			signData+=it.next()+map.get(it.next());
	        		}
	        		
	        		if (Tools.md5(signData)!=sign) {
	        			return false;
	        		}
				}
	        }
		}

		return true;
	}
}
