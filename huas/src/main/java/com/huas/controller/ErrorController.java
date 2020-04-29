package com.huas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huas.exception.ErrorReturnPageException;

@Controller
@RequestMapping("/error")
public class ErrorController {

	
	
	@RequestMapping(value = "/500")
	public String error500(){
		
		
		
		
		return "500";
		
	}
	
	@RequestMapping(value = "/404")
	public String error404(){
		
		
		
		
		return "404";
		
	}
	
	
}
