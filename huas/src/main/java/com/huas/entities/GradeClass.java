package com.huas.entities;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 年级表
 * @author 500R5H
 *
 */
//自动加有参构造器
@AllArgsConstructor
//自动加无参构造器
@NoArgsConstructor
//自动加Get,Set方法
@Data
public class GradeClass implements Serializable{
	//年级id
	private Integer gradeClassId;
	//年级名
	private String gradeClassName;
	
	private  List<Clazz> clazzs;
 	
	

}
