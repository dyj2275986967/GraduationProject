package com.huas.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huas.entities.User;

public interface UserService {
	
	String login(User user,HttpServletRequest request,HttpServletResponse response);
    void logout(HttpServletRequest request,HttpServletResponse response);
}
