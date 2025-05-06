package com.decajon.decajon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class DeCajonApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DeCajonApplication.class, args);
	}
}
