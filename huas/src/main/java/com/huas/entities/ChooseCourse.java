package com.huas.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 选课表
 * @author 500R5H
 *
 */
//自动加有参构造器
@AllArgsConstructor
// 自动加无参构造器
@NoArgsConstructor
// 自动加Get,Set方法
@Data
public class ChooseCourse implements Serializable {

	// 课程id
	private Integer courseId;
	private String courseName;

}
