package com.example.schedulemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ScheduleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleManagementApplication.class, args);
        System.out.println("hello spring");
    }

}
