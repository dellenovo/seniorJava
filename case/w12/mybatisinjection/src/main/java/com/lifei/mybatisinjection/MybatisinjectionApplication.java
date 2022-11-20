package com.lifei.mybatisinjection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lifei.mybatisinjection")
public class MybatisinjectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisinjectionApplication.class, args);
	}

}
