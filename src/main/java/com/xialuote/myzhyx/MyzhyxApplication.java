package com.xialuote.myzhyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xialuote.myzhyx.mapper")
public class MyzhyxApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyzhyxApplication.class, args);
	}
	
}
