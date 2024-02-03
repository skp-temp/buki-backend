package com.example.skptemp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SkpTempApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkpTempApplication.class, args);
    }

}
