package com.labsandware.quickpoll;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.labsandware.quickpoll")
public class QuickPollApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickPollApplication.class, args);
	}

}
