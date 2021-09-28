package com.tataelxsi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@CrossOrigin("http://localhost:4200/")
//@EnableEurekaClient
//@EnableDiscoveryClient
public class SkillCenterAppApplication {
	private static final Logger logger = LoggerFactory.getLogger(SkillCenterAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SkillCenterAppApplication.class, args);
		logger.info("SkillCenter Application started ....");
		
	}

}
