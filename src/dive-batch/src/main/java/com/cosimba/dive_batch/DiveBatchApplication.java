package com.cosimba.dive_batch;

import com.cosimba.dive.domain.user.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
@EnableJpaAuditing
@EntityScan(basePackages = {"com.cosimba.dive", "com.cosimba.dive_batch.domain"}) // 엔티티 스캔 경로 설정
@EnableJpaRepositories(basePackages = "com.cosimba.dive_batch.repository")
public class DiveBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiveBatchApplication.class, args);
    }
}
