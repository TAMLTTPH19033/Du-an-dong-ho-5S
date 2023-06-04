package com.dongho5s.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
public class ProjectDatnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectDatnApplication.class, args);
	}

}
