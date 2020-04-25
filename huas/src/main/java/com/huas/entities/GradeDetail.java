package com.huas.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装各院系成绩合格人数的实体类
 * @author 500R5H
 *
 */

//自动加有参构造器
@AllArgsConstructor
//自动加无参构造器
@NoArgsConstructor
//自动加Get,Set方法
@Data
public class GradeDetail {

	 private Integer value;
	 private String  name;
	 private Integer pass;
	 private Integer notpass;
	 
	 
	
	
}
