package com.alumni.apiaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAggregatorApplication.class, args);
    }

}
