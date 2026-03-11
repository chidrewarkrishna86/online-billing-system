package com.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.billing"})
public class BillingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingSystemApplication.class, args);
        System.out.println("===============================================" );
        System.out.println("   Online Billing System Started Successfully!" );
        System.out.println("   Access the application at: http://localhost:8080/billing" );
        System.out.println("===============================================" );
    }

}