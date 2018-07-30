package cn.admin.core;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserSecurityInterceptor implements HandlerInterceptor {

	

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String userName =  (String) request.getSession().getAttribute("loginUser");
		if (userName == null ) {
			//判断是否是ajax请求
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				response.getWriter().write("{'result':false,'msg':'请登录后操作'}");
				return false;
			}
			
			String uri = request.getRequestURI();
			String query=request.getQueryString();
			if (!StringUtils.isEmpty(query)) {
				query =new String(query.getBytes("ISO-8859-1"),"UTF-8");
			}
			
			if (!StringUtils.isEmpty(query)) {
				uri+="?"+query;
			}
			
			request.getRequestDispatcher("/login?redirectUrl="+URLEncoder.encode(uri,"UTF-8"))
					.forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}