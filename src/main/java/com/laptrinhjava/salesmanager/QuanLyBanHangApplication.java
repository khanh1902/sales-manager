package com.laptrinhjava.salesmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
// exclude = {SecurityAutoConfiguration.class }
public class QuanLyBanHangApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuanLyBanHangApplication.class, args);
	}
}
