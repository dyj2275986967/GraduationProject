package com.huas.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自动加有参构造器
@AllArgsConstructor
//自动加无参构造器
@NoArgsConstructor
//自动加Get,Set方法
@Data
public class JsonData  implements Serializable {

	private Integer code; // 状态码 0 表示成功，1表示处理中，-1表示失败
	private Object data; // 数据
	private String msg;// 描述
	
	
	
	
}
