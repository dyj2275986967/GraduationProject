package com.huas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ErrorInterceptor  implements HandlerInterceptor {
	
	
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		
        if(response.getStatus()==500){
            modelAndView.setViewName("/errorpage/500");
        }else if(response.getStatus()==404){
        	
        	System.out.println("asdasdasdasdasdsda=======================================================");
            modelAndView.setViewName("/errorpage/404");
        }
    }
	
	
	

}
