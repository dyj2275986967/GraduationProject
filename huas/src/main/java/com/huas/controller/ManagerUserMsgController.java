package com.huas.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.serviceImpl.ManagerUserMsgServiceImpl;
import com.huas.utils.ConstantUtils;
import com.huas.utils.PicUtil;


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
		
	
		
			@RequestMapping(value = "/fileUpload" ,method = RequestMethod.POST)
			public String fileUpload(@RequestParam(value = "filename") MultipartFile filename, HttpServletRequest request) {
			       
				try {
					PicUtil.singleFileUpload(filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				  User u=	(User)ConstantUtils.getRequest().getSession().getAttribute("session_user");		
				
			   String imgSrc=	 (String) ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.USER_IMG_UUID);
					//重新设置图片
			   u.setImg(imgSrc);
			   
			   //修改数据库里的头像 src
			   
			   service.updateMsgTx(imgSrc, u.getUserId());
				  //移除session
					ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.SESSION_USER);
					ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.USER_IMG_UUID);
					//重新添加
					ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);
				
				
				
				
				 return "redirect:/user/manager/index";
			}
	
			  //进入绑定操作页面
			@RequestMapping(value = "/bind")
			public String add() {
				
		

				
				 return "bind";
			}
			
			//邮件或者短信绑定
			@RequestMapping(value = "/bind/insert")
			@ResponseBody
			public String doLoginDeleteSend(String check,String emailOrPhone,String yzm,HttpServletResponse response) {
				
				return service.insertPhoneOrEmail(emailOrPhone,  check, yzm);

			}
				
			
			//邮件或者短信绑定
			@RequestMapping(value = "/music")
			@CrossOrigin
			public String music() {
				
				return"music";

			}
				
			
			
			
}
