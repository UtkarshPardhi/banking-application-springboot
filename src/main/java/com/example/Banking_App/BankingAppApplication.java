package com.example.Banking_App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class BankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String hash = encoder.encode("admin123");
//        System.out.println("HASH: " + hash);

            }
        }



