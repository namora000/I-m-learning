package jdev.dto;

import dao.Point;
import jdev.dto.services.GPSService;
import jdev.dto.services.SendingService;
import jdev.dto.services.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
@EnableJpaRepositories("dao")
@EntityScan(basePackageClasses = Point.class)
@SpringBootApplication
@PropertySource("classpath:/application.properties")
public class TrackerCoreContext{

    @Value("${server.ip}")
    private String ip;
    @Value("${server.port}")
    private String port;
    private RestTemplate restTemplate;

    @Bean
    public GPSService gpsService() { return new GPSService();}
    @Bean
    public StorageService storageService() {return new StorageService();}
    @Bean
    public SendingService sendingService() {return new SendingService(restTemplate, ip, port);}
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {return builder.build();}


    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(20);
        return scheduler;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
