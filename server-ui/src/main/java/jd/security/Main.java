package jd.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("jd.config")

public class Main {

    public static void main (String[] args) throws Throwable {
        SpringApplication.run(Main.class, args);
    }

}
