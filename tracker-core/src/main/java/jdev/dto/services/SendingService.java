package jdev.dto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SendingService {

    private static final Logger log = LoggerFactory.getLogger(SendingService.class);
    @PostConstruct
    public void info () throws InterruptedException{
        log.info("testSending");
    }
}
