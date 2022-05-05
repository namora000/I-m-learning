package jdev.dto;


import jdev.dto.services.GPSService;
import jdev.dto.services.SendingService;
import jdev.dto.services.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("classpath:/properties")
public class TrackerCoreContext {

    @Bean
    public GPSService gpsService() { return new GPSService();}

    @Bean
    public SendingService sendingService() {return new SendingService();}

    @Bean
    public StorageService storageService() {return new StorageService();}
}
