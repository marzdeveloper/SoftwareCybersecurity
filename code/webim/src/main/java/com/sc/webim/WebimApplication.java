package com.sc.webim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class WebimApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebimApplication.class, args);
	}

}
