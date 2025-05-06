package com.ssafy.ssafyhome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.ssafy.ssafyhome.model.dao")
public class SsafyhomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsafyhomeApplication.class, args);
	}

}
