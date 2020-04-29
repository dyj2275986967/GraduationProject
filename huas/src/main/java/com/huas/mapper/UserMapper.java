package com.huas.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.huas.entities.User;

@Mapper
public interface UserMapper {

	
	// 用户账号密码登陆校验
    @Select("SELECT * FROM user WHERE login_name = #{loginName}")
	  User findById(User user);
    //用户 邮件登陆
    @Select("SELECT * FROM USER WHERE email=#{email}")
	  User findByEmail(@Param("email")String email);
    //用户 短信登陆
    @Select("SELECT * FROM USER WHERE phone=#{phone}")
	  User findByPhone(@Param("phone")String phone);
    //用户通过邮箱或者手机号修改密码 
    @Update("UPDATE USER SET pwd=#{pwd} WHERE email=#{email}")
      void createPwdByEmail(@Param("email")String email,@Param("pwd")String pwd);
    @Update("UPDATE USER SET pwd=#{pwd} WHERE phone=#{phone}")
    void createPwdByPhone(@Param("phone")String email,@Param("pwd")String pwd);
  
}
