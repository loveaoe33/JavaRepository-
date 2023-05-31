package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
 

@SpringBootApplication
public class SpringProject2026Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringProject2026Application.class, args);
	}
	@Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
	public StandardServletMultipartResolver multipartResolver(MultipartProperties multipartProperties) {
	  StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
	  multipartResolver.setStrictServletCompliance(true);
	  multipartResolver.setResolveLazily(multipartProperties.isResolveLazily());
	  return multipartResolver;
	}
}
