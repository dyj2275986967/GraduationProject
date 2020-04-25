package com.huas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.huas.mapper")
public class HunasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HunasApplication.class, args);
	}

}
