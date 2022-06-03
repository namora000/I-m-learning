package jdev.dto.services;

import dao.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class StorageService implements StorageInterface{

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    private BlockingDeque<Point> queue = new LinkedBlockingDeque<>(600);


    @Override
    public void setCoordinates(double latitude, double longitude, double altitude, int speed, long time) throws InterruptedException {

        Point point = new Point();
        point.setLongitude(Double.toString(longitude));
        point.setLatitude(Double.toString(latitude));
        point.setAltitude(Double.toString(altitude));
        point.setSpeed(Integer.toString(speed));
        point.setTime(Long.toString(time));

        setQueue(point);

        /* Старый (ручной) метод формирования JSON строки координат
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
        */
    }

    @Override
    //@Scheduled (fixedDelay = 10000)
    public BlockingDeque<Point> getCoordinates() throws InterruptedException {
        return queue;
    }
    private void setQueue(Point coordinates) throws InterruptedException {
        queue.put(coordinates);
    }
}

