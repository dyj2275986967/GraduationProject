package com.huas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成绩表
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
public class Grade {
	// 成绩
	private Integer result;

   
	// 学生:哪个学生是这个成绩
	private String courseName;
  
	 private Integer courseId;
	// 学生学号

	// 学生姓名
	private String stuName;

	//学生性别
	private String sex;

	 
	private String stuId;

	// 学生学院id
	//************************************************
	// 班级id
	private Integer classId;
	// 班级姓名
	private String className;

	// 学院id
	private Integer depId;
	// 学院名字
	private String depName;
	
	// 院系名
	private Integer majorId;
	// 院系名
	private String majorName;
	//年级id
	private Integer gradeClassId;
	//年级名
	private String gradeClassName;
	
	
	
}
