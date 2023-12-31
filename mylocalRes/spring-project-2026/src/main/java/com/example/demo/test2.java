package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import Personnel_Attend.testServer;

@ComponentScan("Personnel_Attend")
@RestController
public class test2 {
	@Autowired
	private final testServer test;
	 
	public test2(testServer test) {
		this.test=test;
	}
	@CrossOrigin
	@GetMapping("test2/test")
	public String test() {
	    String x=test.get();
		return x;

	}
}
