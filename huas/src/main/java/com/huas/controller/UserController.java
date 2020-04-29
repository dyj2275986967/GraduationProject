package com.huas.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huas.entities.User;
import com.huas.service.UserService;
import com.huas.utils.ConstantUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	
	// 用户进入主页面
	@RequestMapping(value = "/login")
	public String loginIn() {
     //进入登录页面移除验证码
		ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.EMAIL_YZM);
		ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.NOTE_YZM); 
		return "login";
	}


	// 用户进入主页面
	@RequestMapping(value = "/createPwd")
	public String createPwd() {
     //进入登录页面移除验证码
		
		return "forget";
	}

	
	
	// 用户异步登录
	@RequestMapping(value = "/api/login")
	@ResponseBody
	public Object doLogin(String loginName,String password,HttpServletRequest request,HttpServletResponse response) {

		
		User user = new User();
		user.setLoginName(loginName);
		user.setPwd(password);
		String message=service.login(user,request,response);

		JSONObject json = new JSONObject();
		json.put("message", message);
	
		

		return json;

	}
	
	
	//发邮件
	@RequestMapping(value = "/api/sendEmail")
	@ResponseBody
	public String doLoginSendEmail(String email) {

		
	    service.sendEmail(email);
		return "发送成功，请注意查收";

	}
	//发短信
		@RequestMapping(value = "/api/sendPhone")
		@ResponseBody
		public String doLoginSendPhone(String phone) {

			
		    service.sendPhone(phone);
			return "发送成功，请注意查收";

		}
	
	
	//销毁验证码
	@RequestMapping(value = "/api/destroy")
	@ResponseBody
	public String doLoginDeleteSend(String check) {

		
	    service.delectSession(check);
		return "销毁成功";

	}
	
	//邮件或者短信登陆
	@RequestMapping(value = "/api/yzLogin")
	@ResponseBody
	public String doLoginDeleteSend(String check,String emailOrPhone,String yzm,HttpServletResponse response) {
		
		return service.loginByPhoneOrEmail(check, emailOrPhone, yzm, response);

	}
	
	//邮件或者短信登陆
	@RequestMapping(value = "/api/createPwd")
	@ResponseBody
	public String  createByPhoneOrEmail(String check, String emailOrPhone, String yzm, String pwd,
			HttpServletResponse response) {
		
		return service.createByPhoneOrEmail(check, emailOrPhone, yzm, pwd, response);
		 
		
		

	}
	
	
	

	/**
	 * 退出
	 */
	@RequestMapping(value="/loginOut",produces= {"application/text;charset=utf-8"})
	public String loginOut(HttpServletRequest  request, HttpServletResponse response) {
		    
		service.logout(request, response);
		  //退出重定向返回到首页
			return "redirect:/user/login";
		}

}
