package com.huas.entities;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 院系表
 * 
 * @author 500R5H
 *
 */
// 自动加有参构造器
@AllArgsConstructor
// 自动加无参构造器
@NoArgsConstructor
// 自动加Get,Set方法
@Data
public class Major implements Serializable {
	// 院系名
	private Integer majorId;
	// 院系名
	private String majorName;
	private Integer depId;
	private UniversityDep university;
	
	// 动态添加按钮 查询院系信息所需要的
	private List<Clazz> clazzs;
    //课程
	private List<Course> courses;
	
	
}
