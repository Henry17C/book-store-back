package com.example.book_store_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BookStoreBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreBackApplication.class, args);
	}

}
