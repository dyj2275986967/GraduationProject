package com.huas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存放学习各科成绩的的实现类
 * 迫不得已 才写 A,B,C,D,E,F,G
 * 还有种解决方案是 把所有的课程信息 列出来 
 * @author 500R5H
 *
 */
//自动加有参构造器
@AllArgsConstructor
//自动加无参构造器
@NoArgsConstructor
//自动加Get,Set方法
@Data
public class GradeDetailWithTable {

	    // 成绩
		private String stuId;
		private String stuName;
		private String className;
		
		//虽然丑了点 但还是 开始吧

		 private Integer a;
		 private Integer b;
		 private Integer c;
		 private Integer d;
		 private Integer e;
		 private Integer f;
		 private Integer j;
		 private Integer h;
		 private Integer i;
		 
		 
		 
		
		
	
}
