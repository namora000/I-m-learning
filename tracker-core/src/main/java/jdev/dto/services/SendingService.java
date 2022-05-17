package jdev.dto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

@Service
public class SendingService {

    private static final Logger log = LoggerFactory.getLogger(SendingService.class);

    @Value("${server.ip}")
    private String ip;
    @Value("${server.port}")
    private String port;
    private Socket socket;
    private ObjectOutputStream out;



    @Autowired
    private StorageInterface storageInterface;
    //@PostConstruct
    @Scheduled(fixedDelay = 500)
    public void sendMessageToServer () throws InterruptedException{
        transmitter(storageInterface.getCoordinates());
    }


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
}
