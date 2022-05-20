package jdev.dto;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String... args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(TrackerCoreContext.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {return builder.build();}
}