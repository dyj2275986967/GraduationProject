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


import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	
	// 用户进入主页面
	@RequestMapping(value = "/login")
	public String loginIn() {

		
		return "login";
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
