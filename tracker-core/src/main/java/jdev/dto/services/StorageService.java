package jdev.dto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class StorageService implements StorageInterface{

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    private BlockingDeque<String> queue = new LinkedBlockingDeque<>(600);


    @Override
    public void setCoordinates(double latitude, double longitude, double altitude, int speed, long time) throws InterruptedException {
        String coordinate = "{" +
                                "\"coordinates\": {" +
                                    "\"lat\":" + "\"" + latitude + "\", " +
                                    "\"lon\":" + "\"" + longitude + "\", " +
                                    "\"alt\":" + "\"" + altitude + "\", " +
                                    "\"speed\":" + "\"" + speed + "\", " +
                                    "\"time\":" + "\"" + time + "\"" +
                                "}" +
                            "}";
        setQueue(coordinate);
    }

    @Override
    //@Scheduled (fixedDelay = 10000)
    public String getCoordinates() throws InterruptedException {
        log.info("Выгрузка координат из очереди");
        return queue.take();
    }
    private void setQueue(String coordinates) throws InterruptedException {
        queue.put(coordinates);
        log.info("Получаем координаты.");
    }
}

interface StorageInterface {
    public void setCoordinates(double latitude, double longitude, double altitude, int speed, long time) throws InterruptedException;
    public String getCoordinates() throws InterruptedException;
}
