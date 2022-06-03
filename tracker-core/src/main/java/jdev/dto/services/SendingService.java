package jdev.dto.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Service
public class SendingService {
    @Autowired
    private StorageInterface storageInterface;
    private static final Logger log = LoggerFactory.getLogger(SendingService.class);
    private String ip;
    private String port;
    private Socket socket;
    private ObjectOutputStream out;
    private RestTemplate restTemplate;
    private RestTemplateBuilder builder = new RestTemplateBuilder();
    public SendingService (@Autowired RestTemplate restTemplate, String ip, String port) {
        this.restTemplate = restTemplate;
        this.ip = ip;
        this.port = port;
    }


    @Scheduled(fixedDelay = 2000)
    public void sendMessageToServer () throws InterruptedException, JsonProcessingException {
        System.out.println(storageInterface.getCoordinates().size());
        transmitter(storageInterface.getCoordinates().take());

    }

    @RequestMapping(method = RequestMethod.POST)
    public String transmitter(Point coordinates) throws JsonProcessingException {
        String url = "http://"+ip+":"+port+"/coordinates";
        String answer = builder.build().postForObject(url, coordinates, String.class);
        log.info(answer);
        return answer;
    }
}

