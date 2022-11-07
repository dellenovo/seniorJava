package com.lifei.hac2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lifei.hac2.mapper")
public class Hac2Application {

	public static void main(String[] args) {
		SpringApplication.run(Hac2Application.class, args);
	}

}
