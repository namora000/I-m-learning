package jdev.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import controllers.ReceiverController;
import dao.Point;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    public void receiverControllerTest() throws Exception {
        Point point = new Point();
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
