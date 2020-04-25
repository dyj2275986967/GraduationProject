package com.huas.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程表
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
public class Course implements Serializable {
	// 课程id
	private Integer courseId;
	// 课程名
	private String courseName;
	// 院系名: 该课程是属于哪个院系
	private Integer majorId;
	
}
