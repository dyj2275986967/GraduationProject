package com.huas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huas.entities.User;
import com.huas.utils.ConstantUtils;


@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		
    
		User user=(User)request.getSession().getAttribute(ConstantUtils.SESSION_USER);
		if (user!=null) {
			//放行
			return true;
		}
		
		//重定向到登录页面
		//request.setAttribute("msg", "*请先登录");
	 request.getRequestDispatcher("/user/login").forward(request, response);

		return false;
	
	}

}
