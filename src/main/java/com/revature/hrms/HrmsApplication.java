package com.revature.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
public class HrmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(HrmsApplication.class, args);
  }
}
