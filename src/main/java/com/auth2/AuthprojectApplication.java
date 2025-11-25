package com.auth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//git config --global --add safe.directory E:/springsecutity/authproject
@SpringBootApplication
@EnableDiscoveryClient
public class AuthprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthprojectApplication.class, args);
	}

}
