package com.huas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.serviceImpl.ManagerUserMsgServiceImpl;
import com.huas.utils.ConstantUtils;


/**
 * 个人信息 管理控制层
 * @author 500R5H
 *
 */
@Controller
@RequestMapping("/user/manager")
public class ManagerUserMsgController {
	@Autowired
	private ManagerUserMsgServiceImpl service;
	
	// 用户进入主页面
	@RequestMapping(value = "/index")
	public String loginIn() {

		
		return "myMsgT";
	}

	 // 展示所有学院信息
		@RequestMapping(value = "/depMsg")
		@ResponseBody
		public String dep(){

			List<UniversityDep> lists=	service.findDepMsg();
			 Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			    
		        
		      Map <String ,Object> map=new HashMap<>();
		        
		       map.put("msg", lists);
		   	String json= gson.toJson(map);
			
			return json;
		}
	

		 // 展示所有学院信息
			@RequestMapping(value = "/update")
			@ResponseBody
			public String update(String sex,String name, String dep, String signature, Integer userId ){
            
				service.updateMsg(sex,name, dep, signature, userId);
				
			String strs=System.getProperty("user.dir");
				System.out.println("======================");
				
				System.out.println("======================");
				System.out.println("======================");
				System.out.println(strs);
				System.out.println("======================");
				System.out.println("======================");
				System.out.println("======================");
				//获取user
		      User u=	(User)ConstantUtils.getRequest().getSession().getAttribute("session_user");		
				u.setSex(sex);
                u.setDep(Integer.valueOf(dep));				
				u.setSignature(signature);
				u.setName(name);
				
			  //移除session
				ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.SESSION_USER);
				//重新添加
				ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);
				
				return "添加成功";
			}
		
	
	
	

}
