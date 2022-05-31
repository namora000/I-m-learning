package jdev.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.GpsPoint;
import controllers.ReceiverController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;


import static org.junit.Assert.*;

public class IntegrationTestReceiver {
    private String time = Long.toString(System.currentTimeMillis());
    private String json = "{\"message\":\"ok\",\"result\":true}";

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    ReceiverController receiverController;

    @Test
    public void receiverControllerTest() throws JsonProcessingException {
        GpsPoint point = new GpsPoint();
        ReceiverController controller = new ReceiverController(new RestTemplate());
        point.setLatitude("38.760888");
        point.setLongitude("44.670992");
        point.setAltitude("97.0");
        point.setSpeed("40");
        point.setTime(time);

        String result = controller.coordinates(point);
        System.out.println(result);
        assertTrue(result.contains("message"));
        assertTrue(result.contains("result"));
        assertEquals(json, result);

    }

}
