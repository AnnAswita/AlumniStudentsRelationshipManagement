package com.alumni.mentorshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MentorshipServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentorshipServiceApplication.class, args);
    }

}
