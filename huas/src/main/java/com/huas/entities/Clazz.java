package com.huas.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级表
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
public class Clazz {
	// 班级id
	private Integer classId;
	// 班级姓名
	private String className;
	// 院系:该班级是属于哪个院系

	private Major major;
	// 学生年级
	private GradeClass gradeClass;
	
	//学生信息
	private List<Student> stus;


}
