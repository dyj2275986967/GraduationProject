package com.huas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Component
	public class ErrorInterceptor implements ErrorPageRegistrar {

		@Override
		public void registerErrorPages(ErrorPageRegistry registry) {
			// TODO Auto-generated method stub
			  ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
		
			  registry.addErrorPages(e404);
		
		}
	

		}

