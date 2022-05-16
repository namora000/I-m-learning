package jdev.dto;


import jdev.dto.services.GPSService;
import jdev.dto.services.SendingService;
import jdev.dto.services.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@PropertySource("classpath:/properties")
public class TrackerCoreContext {

    @Bean
    public GPSService gpsService() { return new GPSService();}
    @Bean
    public StorageService storageService() {return new StorageService();}
    @Bean
    public SendingService sendingService() {return new SendingService();}



    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(20);
        return scheduler;
    }

}
