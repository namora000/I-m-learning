package jdev.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.Point;
import jdev.dto.services.SendingService;
import jdev.dto.services.StorageInterface;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class IntegrationTestSending {

    private String time = Long.toString(System.currentTimeMillis());
    private String ip = "127.0.0.1";
    private String port = "8080";
    private String json = "{\"message\":\"ok\",\"result\":true}";

    @Mock
    RestTemplate restTemplate;

    @Mock
    StorageInterface service;

    @InjectMocks
    SendingService sendingService;

    @Test
    public void sendingServiceTest() throws JsonProcessingException {

        Point point = new Point();
        point.setLatitude("38.760888");
        point.setLongitude("44.670992");
        point.setAltitude("97.0");
        point.setSpeed("40");
        point.setTime(time);

        SendingService service = new SendingService(new RestTemplate(), ip, port);
        String answer = service.transmitter(point);
        System.out.println(answer);
        assertTrue(answer.contains("message"));
        assertTrue(answer.contains("result"));
        assertEquals(json, answer);

    }

}
