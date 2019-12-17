package com.touraj.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotrestserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotrestserviceApplication.class, args);

		System.out.println("REST Service Started ... ");
	}
}