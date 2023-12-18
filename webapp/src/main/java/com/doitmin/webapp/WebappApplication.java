package com.doitmin.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={"com.doitmin.webapp"})
public class WebappApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WebappApplication.class);
	}
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(WebappApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

}
