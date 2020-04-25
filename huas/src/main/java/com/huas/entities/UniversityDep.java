package com.huas.entities;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学院实体类
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
public class UniversityDep implements Serializable {
	// 学院id
	private Integer depId;
	// 学院名字
	private String depName;
	
	// 院系:该班级是属于哪个院系

		private List<Major> majors;
	

}
