package com.huas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 * @author 500R5H
 *
 */

//自动加有参构造器
@AllArgsConstructor
//自动加无参构造器
@NoArgsConstructor
//自动加Get,Set方法
@Data

public class User {
	//用户id
    private Integer userId;
    //用户姓名
    private String name;
    //用户登录名
    private String sex;
	private String loginName;
	//用户密码
	private String pwd;
	//用户手机号
	private String phone;
	//用户邮箱
	private String email;
	//用户头像
	private String img;
	//用户个性签名
	private String signature;
	//用户创建账号的时间
	private java.util.Date createTime;
	//用户级别 0表示是超级管理员 1 表示是一级管理员 2表示是超级管理员
	private Integer level;
	//用户的管理者 :该用户是谁创建的
	private Integer manageId;
	private Integer dep;
	

}
