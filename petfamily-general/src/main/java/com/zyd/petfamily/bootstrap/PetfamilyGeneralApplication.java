package com.zyd.petfamily.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zyd.petfamily"})
@MapperScan("com.zyd.petfamily.dao")
public class PetfamilyGeneralApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetfamilyGeneralApplication.class, args);
	}

}
