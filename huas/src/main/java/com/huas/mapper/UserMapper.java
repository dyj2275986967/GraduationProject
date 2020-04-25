package com.huas.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.huas.entities.User;

@Mapper
public interface UserMapper {

	
	// 用户登陆校验
  @Select("SELECT * FROM user WHERE login_name = #{loginName}")
	  User findById(User user);
  
     
  
  
}
