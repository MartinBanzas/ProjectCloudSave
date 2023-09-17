package com.luv2code.springboot.thymeleafdemo;

import com.luv2code.springboot.thymeleafdemo.service.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class ThymeleafFileUploadApplication implements CommandLineRunner {

	@Resource
	FileStorageService storageService;
	

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafFileUploadApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
//    storageService.deleteAll();
		storageService.init();
	}
}