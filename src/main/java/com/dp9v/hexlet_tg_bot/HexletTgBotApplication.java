package com.dp9v.hexlet_tg_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HexletTgBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexletTgBotApplication.class, args);
	}

}
