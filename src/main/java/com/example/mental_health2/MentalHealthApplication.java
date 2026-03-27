package com.example.mental_health2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MentalHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentalHealthApplication.class, args);
    }
}

// http://localhost:8080/
// http://localhost:8081/
// docker run -p 8081:8080 mental-health-app
// docker build -t mental-health-app .