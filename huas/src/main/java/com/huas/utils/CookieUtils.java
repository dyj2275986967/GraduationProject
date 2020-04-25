package com.huas.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//操作Cookie的工具类
public class CookieUtils {
	
	
	 //添加Cookie
	 public static  void  addCookie(String cookieName,int age,String value,HttpServletRequest request,HttpServletResponse response) {
		 
		 //1、根据Cookie的名字获取Cookie信息
		Cookie cookie =  getCookieByName(cookieName,request);
		
		if(cookie == null) {
			//new Cookie(cookie名字,值);
			cookie = new Cookie(cookieName, value);
		}
		
		//设置存活时间
		cookie.setMaxAge(age);
		//指定Cookie的作用范围 
		cookie.setPath(request.getContextPath()+"/");
		//更新cookie的value值
		cookie.setValue(value);
	
		
        //将Cookie响应值客户端（浏览器）
		response.addCookie(cookie);
		
	 }

	
	
	
	 //根据Cookie名字获取Cookie
	 public static Cookie getCookieByName(String cookieName, HttpServletRequest request) {
			// TODO Auto-generated method stub
		    //获取客户端所有的cookie信息
		    Cookie[] cookies =  request.getCookies();
		    if(cookies != null) {
		    	for(Cookie cookie : cookies) {
		    		if(cookie.getName().equals(cookieName)) {
		    			return cookie;
		    		}
		    	}
		    }
			return null;
		}




	 //删除Cookie
	public static void removeCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 //1、根据Cookie的名字获取Cookie信息
		Cookie cookie =  getCookieByName(cookieName,request);
		
		if(cookie!=null) {
			cookie.setMaxAge(0);
			cookie.setPath(request.getContextPath()+"/");
			response.addCookie(cookie);
		}
	}
}
