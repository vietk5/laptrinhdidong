package com.example.category_crud_api;

import com.example.category_crud_api.service.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CategoryCrudApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryCrudApiApplication.class, args);
	}
    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return (args) -> storageService.init();
    }

}
