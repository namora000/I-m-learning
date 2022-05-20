package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.TrackInterpreter;


public class ReceiverController {
    @Autowired
    private TrackInterpreter trackInterpreter;
    private static final Logger log = LoggerFactory.getLogger(ReceiverController.class);

    @RequestMapping(value = "/coordinates", method = RequestMethod.POST)
    public Response setCoordinates(@RequestParam(value = "track") String track) {
        Response response;
        if(track.contains("lat") && track.contains("lon") && track.contains("alt") && track.contains("time") && track.contains("speed")) {
            response = new Response("ok",true);
        } else {
            response = new Response("fail",false);
        }

        log.info(track);

        trackInterpreter.setGPSTrack(track);

        return response;

    }

}
