package com.cosimba.dive_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DiveBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiveBatchApplication.class, args);
    }
}
