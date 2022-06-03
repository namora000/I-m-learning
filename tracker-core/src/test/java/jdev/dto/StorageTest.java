package jdev.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdev.dto.services.StorageService;
import dao.Point;
import org.junit.Test;


import java.util.concurrent.BlockingDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageTest {

    private double lat = 38.760888;
    private double lon = 44.670992;
    private double alt = 97.0;
    private int speed = 40;
    private long time = System.currentTimeMillis();

    private StorageService storageService = new StorageService();

    @Test
    public void testSetAndGetCoordinates () throws InterruptedException, JsonProcessingException {
        // передаём координаты несколько раз, чтоб хватило на все тесты с методом .take() из очереди.
        storageService.setCoordinates(lat, lon, alt, speed, time);
        storageService.setCoordinates(lat, lon, alt, speed, time);
        System.out.println("Переданы тестовые координаты: "+lat+", "+lon+", "+alt+", "+speed+", "+time+".");
        assertTrue(storageService.getCoordinates() instanceof BlockingDeque);
        BlockingDeque deque = storageService.getCoordinates();
        if(deque.take() instanceof Point);
        Point point = (Point)deque.take();
        System.out.println("\"toJson\" : "+point.toJson());
        System.out.println("\"toString\" : "+point.toString());
    }



}
