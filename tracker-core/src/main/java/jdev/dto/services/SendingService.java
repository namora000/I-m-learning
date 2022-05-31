package jdev.dto.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private String ip = "127.0.0.1";
    private String port = "8080";
    private Socket socket;
    private ObjectOutputStream out;
    private RestTemplate restTemplate;
    private RestTemplateBuilder builder = new RestTemplateBuilder();
    public SendingService (@Autowired RestTemplate restTemplate,String ip,String port) {
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
    public String transmitter(GpsPoint coordinates) throws JsonProcessingException {
        String url = "http://"+ip+":"+port+"/coordinates";
        String answer = builder.build().postForObject(url, coordinates, String.class);
        log.info(answer);
        return answer;
    }

    /*
    //старый тестовый метод передачи строки (подготовленной вручную) на тестовый сокет-сервер
    private void transmitter(String coordinates) {
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(coordinates);
            out.flush();
            socket.close();
        } catch (UnknownHostException e) {
            log.info("UnknownHostException: " + e);
        } catch (IOException e) {
            log.info("IOException: " + e);
        }
    }
    */
}

