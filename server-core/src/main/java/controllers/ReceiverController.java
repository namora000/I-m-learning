package controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class ReceiverController {
    private static final Logger log = LoggerFactory.getLogger(ReceiverController.class);
    private RestTemplate restTemplate;
    public ReceiverController (@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/coordinates", method = RequestMethod.POST)
    public String coordinates(@RequestBody GpsPoint point) throws JsonProcessingException {

        log.info(point.toString());
        Response response = new Response();
        response.setMessage("ok");
        response.setResult(true);
        return response.toString();
    }

}


