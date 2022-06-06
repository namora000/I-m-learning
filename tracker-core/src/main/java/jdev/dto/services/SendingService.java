package jdev.dto.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.Point;
import dao.repo.PointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Service
public class SendingService implements CommandLineRunner {
    @Autowired
    private StorageInterface storageInterface;

    @Autowired
    PointsRepository pointsRepository;

    private List <Point> list;
    private static final Logger log = LoggerFactory.getLogger(SendingService.class);
    private String ip;
    private String port;
    private RestTemplate restTemplate;
    private RestTemplateBuilder builder = new RestTemplateBuilder();
    public SendingService (@Autowired RestTemplate restTemplate, String ip, String port) {
        this.restTemplate = restTemplate;
        this.ip = ip;
        this.port = port;
    }


    @Scheduled(fixedDelay = 2000)
    public void sendMessageToServer () throws InterruptedException, JsonProcessingException {
        Point point = storageInterface.getCoordinates().take();
        create(point);
        transmitter(point);
    }

    public void run (String... args) {}

    @RequestMapping(method = RequestMethod.POST)
    public String transmitter(Point coordinates) throws JsonProcessingException {
        String url = "http://"+ip+":"+port+"/coordinates";
        String answer = builder.build().postForObject(url, coordinates, String.class);
        log.info(answer);
        return answer;
    }

    private Point create (Point point) {
        return pointsRepository.save(point);
    }

    private void read () {
        list = (List<Point>) pointsRepository.findAll();

        if (list.size() == 0) {
            log.info("No Records");
        } else {
            list.stream().forEach(point -> log.info(point.toString()));
        }
    }
}

