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

    private static final Logger log = LoggerFactory.getLogger(SendingService.class);

    @Value("${server.ip}")
    private String ip;
    @Value("${server.port}")
    private String port;
    private Socket socket;
    private ObjectOutputStream out;

    private RestTemplateBuilder builder = new RestTemplateBuilder();

    @Autowired
    private StorageInterface storageInterface;
    @Autowired
    RestTemplate restTemplate;


    @Scheduled(fixedDelay = 2000)
    public void sendMessageToServer () throws InterruptedException, JsonProcessingException {

        transmitter(storageInterface.getCoordinates().take());

    }

    @RequestMapping(method = RequestMethod.POST)
    private void transmitter(GpsPoint coordinates) throws JsonProcessingException {
        String url = "http://"+ip+":"+port+"/coordinates";
        String answer = builder.build().postForObject(url, coordinates, String.class);
        log.info(answer);
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

