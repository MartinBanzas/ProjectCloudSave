package com.martin.springboot.cloudsave;

import com.martin.springboot.cloudsave.service.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



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