package com.jeam.springbatch.springbatchfirstproject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.jeam.springbatch.springbatchfirstproject.config",
        "com.jeam.springbatch.springbatchfirstproject.service",
        "com.jeam.springbatch.springbatchfirstproject.listener",
        "com.jeam.springbatch.springbatchfirstproject.reader",
        "com.jeam.springbatch.springbatchfirstproject.processor",
        "com.jeam.springbatch.springbatchfirstproject.writer"})
public class SpringBatchFirstProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchFirstProjectApplication.class, args);
    }

}