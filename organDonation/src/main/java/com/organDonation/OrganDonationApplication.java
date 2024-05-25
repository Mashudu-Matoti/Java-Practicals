package com.organDonation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OrganDonationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganDonationApplication.class, args);
		
	}

}
