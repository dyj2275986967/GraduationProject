package com.huas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huas.entities.User;
import com.huas.utils.ConstantUtils;


@Component
public class AdminsterLevelInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		
    
		User user=(User)request.getSession().getAttribute(ConstantUtils.SESSION_USER);
		
		//如果管理员的级别不是二级就放行
		if (user.getLevel()!=2) {
			//放行
			return true;
		}
		
	//回到个人页面
	 request.getRequestDispatcher("/user/manager/index").forward(request, response);

		return false;
	
	}
	
	
}
