package com.huas.serviceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huas.entities.User;
import com.huas.mapper.UserMapper;
import com.huas.service.UserService;
import com.huas.utils.ConstantUtils;
import com.huas.utils.CookieUtils;
import com.huas.utils.EmailUtil;
import com.huas.utils.MD5Utils;
import com.huas.utils.PhoneUtil;
import com.huas.utils.yzmUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;

	@Override
	public String login(User user, HttpServletRequest request, HttpServletResponse response) {
		// 判断账号是否正确
		User u = mapper.findById(user);

		if (u == null) {
			return "1";
		} else {
			if (!u.getPwd().equals(user.getPwd())) {
				return "2";
			}

			// 登录成功，将用户的信息存放在session中
			ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);

			// 进行加密
			String secret = MD5Utils.convertMD5(u.getUserId() + "_" + u.getPwd());
			// addCookie(cookie名字,存活时间,cookie的值,request,response)

			CookieUtils.addCookie(ConstantUtils.LOGIN_COOKIE, 7*24*60*60, secret, ConstantUtils.getRequest(),
					response);

			
			
		}

		return null;
	}

	/**
	 * 用户退出
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().removeAttribute(ConstantUtils.SESSION_USER);
		
		
		
       CookieUtils.removeCookie(ConstantUtils.LOGIN_COOKIE, request, response);
		
	}
    /**
     * 用户发邮件发送验证码
     */
	@Override
	public void sendEmail(String email) {
		//生成验证码
		Integer yzm=yzmUtils.getlinkNo();
		//发邮件
		EmailUtil.sendEmain(email, String.valueOf(yzm));
		//存session
		ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.EMAIL_YZM, String.valueOf(yzm));
	}
    /**
     * 60s后清除session 前端带过来1 就是邮箱 ，2就是手机 
     */
	@Override
	public void delectSession(String check) {
     //前端带过来1 就是邮箱
     if("1".equals(check)){
    	 //移除邮箱session
    	ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.EMAIL_YZM);
        }else {
    	 //移除短信session
        ConstantUtils.getRequest().getSession().removeAttribute(ConstantUtils.NOTE_YZM); 
    	 
     }
		
		
		
		
	}
    /**
     * 用户短信或者邮件登陆
     */
	@Override
	public String loginByPhoneOrEmail(String check, String emailOrPhone,String yzm, HttpServletResponse response) {
 
		
		 String checkYzm="";
		 User u=null;
	     //前端带过来1 就是邮箱 校验
	     if("1".equals(check)){
	    	 checkYzm=(String)ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.EMAIL_YZM);
  	 
	    	 u= mapper.findByEmail(emailOrPhone);
	    	   
	    
	 		boolean checkYzmFlag=checkYzm!=null&&!"".equals(checkYzm);
	 		
	 		
	    	  if(!checkYzmFlag){
	    		  
	    		  return"尚未点击发送按钮，或者验证码已经失效";
	    		  
	    	  }else if(!checkYzm.equals(yzm)){
	    		  
	    		  return"验证码输入错误，请仔细检查";
	    	  }else if(u == null){
	    		  
	    			return "该用户不存在";
	    	  //登陆成功
	    	  }else{
	    		// 登录成功，将用户的信息存放在session中
	  			ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);

	  			// 进行加密
	  			String secret = MD5Utils.convertMD5(u.getUserId() + "_" + u.getPwd());
	  			// addCookie(cookie名字,存活时间,cookie的值,request,response)

	  			CookieUtils.addCookie(ConstantUtils.LOGIN_COOKIE, 7*24*60*60, secret, ConstantUtils.getRequest(),
	  					response);   
	  			
	    	  }
        
	     //发短信校验
	        }else {		
	        	
	       	 checkYzm=(String)ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.NOTE_YZM);
	      	 
	    	 u= mapper.findByPhone(emailOrPhone);
	    	   
	    
	 		boolean checkYzmFlag=checkYzm!=null&&!"".equals(checkYzm);
	 		
	 		
	    	  if(!checkYzmFlag){
	    		  
	    		  return"尚未点击发送按钮，或者验证码已经失效";
	    		  
	    	  }else if(!checkYzm.equals(yzm)){
	    		  
	    		  return"验证码输入错误，请仔细检查";
	    	  }else if(u == null){
	    		  
	    			return "该用户不存在";
	    	  //登陆成功
	    	  }else{
	    		// 登录成功，将用户的信息存放在session中
	  			ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.SESSION_USER, u);

	  			// 进行加密
	  			String secret = MD5Utils.convertMD5(u.getUserId() + "_" + u.getPwd());
	  			// addCookie(cookie名字,存活时间,cookie的值,request,response)

	  			CookieUtils.addCookie(ConstantUtils.LOGIN_COOKIE, 7*24*60*60, secret, ConstantUtils.getRequest(),
	  					response);   
	  			
	    	  }
	        	
	        	
	        	
		    	 
	    	 
	     }
	     return "登陆成功";
			
		
	}
    /**
     * 发短信
     */
	@Override
	public void sendPhone(String phoneNumber) {
		
		//生成验证码
				Integer yzm=yzmUtils.getlinkNo();
				//发邮件
			    PhoneUtil.sendPhoneMsg(String.valueOf(yzm), phoneNumber);
				//存session
				ConstantUtils.getRequest().getSession().setAttribute(ConstantUtils.NOTE_YZM, String.valueOf(yzm));
		
		
		
		
	}
 /**
  * 修改密码
  */
	@Override
	public String createByPhoneOrEmail(String check, String emailOrPhone, String yzm, String pwd,
			HttpServletResponse response) {
             
		
				
		User u=null;
		
		
		 String checkYzm="";
		
	     //前端带过来1 就是邮箱 校验
	     if("1".equals(check)){
	    	 checkYzm=(String)ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.EMAIL_YZM);
	 
	    	 u= mapper.findByEmail(emailOrPhone);
	 		boolean checkYzmFlag=checkYzm!=null&&!"".equals(checkYzm);
	 		
	 		
	    	  if(!checkYzmFlag){
	    		  
	    		  return"尚未点击发送按钮，或者验证码已经失效";
	    		  
	    	  }else if(!checkYzm.equals(yzm)){
	    		  
	    		  return"验证码输入错误，请仔细检查";
	    	
	    	 
	    	  } else if(u == null){
	    		  
	    			return "该用户不存在,请仔细检查您输入的邮箱";
	    			 //校验成功 修改密码
	    	  }else{
	    		  //MD5加密
	    		// String md5Pwd= MD5Utils.string2MD5(pwd);
	    		  
	    		 mapper.createPwdByEmail(emailOrPhone, pwd);
	    		 
	    		 //发邮件
	    		 EmailUtil.sendEmainByForget(u.getLoginName(),emailOrPhone, "用户", pwd);
	    	  
	    	
	    	  }
     
	     //发短信校验
	        }else {		
	        	
	       	 checkYzm=(String)ConstantUtils.getRequest().getSession().getAttribute(ConstantUtils.NOTE_YZM);
	    
	       	 u= mapper.findByPhone(emailOrPhone);
	    
	 		boolean checkYzmFlag=checkYzm!=null&&!"".equals(checkYzm);
	 		
	 		
	    	  if(!checkYzmFlag){
	    		  
	    		  return"尚未点击发送按钮，或者验证码已经失效";
	    		  
	    	  }else if(!checkYzm.equals(yzm)){
	    		  
	    		  return"验证码输入错误，请仔细检查";
	    	 
	    		
	    	   } else if(u == null){
	    		  
	    			return "该用户不存在,请仔细检查您输入的手机号";
	    			 //校验成功 修改密码
	    	  }else{
	           
	    		  // MD5加密
		    		// String md5Pwd= MD5Utils.string2MD5(pwd);
		    		  
		    		 mapper.createPwdByPhone(emailOrPhone, pwd);
		    		 
		    		 //发 短信
		    	//	 EmailUtil.sendEmainByForget(emailOrPhone, "用户", pwd);
		    		 PhoneUtil.sendPhoneMsgBycreatePwd(u.getLoginName(), pwd, emailOrPhone);
		    		 
	    	  }
	        	
	        	
	        	
		    	 
	    	 
	     }
	     return "修改成功";
	
	     
	}
	

}
