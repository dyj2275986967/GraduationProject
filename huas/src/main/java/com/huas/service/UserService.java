package com.huas.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huas.entities.User;

public interface UserService {
	
	//用户登陆
	String login(User user,HttpServletRequest request,HttpServletResponse response);
    //用户退出
	void logout(HttpServletRequest request,HttpServletResponse response);
    //用户发邮件发送验证码
    void sendEmail(String email);
    //60s后清除session 
    void delectSession(String check);
    //用户短信，邮箱登陆
    String loginByPhoneOrEmail(String check, String emailOrPhone,String yzm, HttpServletResponse response);
    //用户发短信发验证码
    void sendPhone(String phoneNumber);
    //用户修改密码
    String createByPhoneOrEmail(String check, String emailOrPhone,String yzm, String pwd,HttpServletResponse response);
}
