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
import com.huas.utils.MD5Utils;

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

}
