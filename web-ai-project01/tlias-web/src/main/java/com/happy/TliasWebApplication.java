package com.happy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan
@SpringBootApplication
public class TliasWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TliasWebApplication.class, args);
    }

}
