package com.huas.serviceImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huas.entities.UniversityDep;
import com.huas.entities.User;
import com.huas.mapper.ManagerUserMsgMapper;
import com.huas.service.ManagerUserMsgService;
import com.huas.utils.ConstantUtils;
import com.huas.utils.CookieUtils;
import com.huas.utils.MD5Utils;

@Service
public class ManagerUserMsgServiceImpl implements ManagerUserMsgService {
	
	@Autowired
	private ManagerUserMsgMapper mapper;
	
	
	 public List<UniversityDep> findDepMsg() {
		 return  mapper.findDepMsg();
		
	}


	@Override
	public void updateMsg(String sex,String name, String dep, String signature, Integer userId) {
		mapper.updateMsg(sex,name, dep, signature, userId);
		
	}


	@Override
	public void updateMsgTx(String src, Integer id) {
		mapper.updateMsgTx(src, id);
	}
   
	 /**
	  *  邮箱手机号绑定操作
	  */
	@Override
	public String insertPhoneOrEmail(String emailOrPhone, String check,String yzm) {
		 //获取Session里的user   
		  User u= (User)ConstantUtils.getRequest().getSession().getAttribute("session_user");		
		
		
		
		 String checkYzm="";
		
	     //前端带过来1 就是邮箱 校验
	     if("1".equals(check)){
	    	 checkYzm=(String)ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.EMAIL_YZM);
 	 
 
	 		boolean checkYzmFlag=checkYzm!=null&&!"".equals(checkYzm);
	 		
	 		
	    	  if(!checkYzmFlag){
	    		  
	    		  return"尚未点击发送按钮，或者验证码已经失效";
	    		  
	    	  }else if(!checkYzm.equals(yzm)){
	    		  
	    		  return"验证码输入错误，请仔细检查";
	    	
	    	  //校验成功 插入邮箱
	    	  }else{
	    	  
	    		  mapper.insertEmail(emailOrPhone,u.getUserId());
	  		//存 邮箱
	    		  u.setEmail(emailOrPhone);
	    		   
	    		//移除session
				ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.SESSION_USER);
	    	   //重新添加
				//重新添加
				ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);
	    	  }
       
	     //发短信校验
	        }else {		
	        	
	       	 checkYzm=(String)ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.NOTE_YZM);
	    
	    	   
	    
	 		boolean checkYzmFlag=checkYzm!=null&&!"".equals(checkYzm);
	 		
	 		
	    	  if(!checkYzmFlag){
	    		  
	    		  return"验证码已失效，请重新发送";
	    		  
	    	  }else if(!checkYzm.equals(yzm)){
	    		  
	    		  return"验证码输入错误，请仔细检查";
	    	 
	    	  //校验成功 插入手机号
	    	  }else{
	           
	    		   mapper.insertPhone(emailOrPhone, u.getUserId());
	    		 //存 手机号
		    		  u.setPhone(emailOrPhone);
		    		   
		    		//移除session
					ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.SESSION_USER);
					//重新添加
					ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);
	    		   
	    	  }
	        	
	        	
	        	
		    	 
	    	 
	     }
	     return "登陆成功";
		
		
		
		
	}

	
	
	
	
}
