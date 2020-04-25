package com.huas.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//常量类
public class ConstantUtils{

	//配置验证码对应的key
	public final static String VERIFY_CODE = "veryfyCode";
	//配置session中用户信息相应的key
	public static final String SESSION_USER = "session_user";
	//登录页面Cookie名字
	public static final String LOGIN_COOKIE = "loginCookie"; 
	
	
	//获取request
	public static HttpServletRequest getRequest() {

		 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
         return request;
	}
	
	
	
	
	
	
	
}