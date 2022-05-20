package services;

import controllers.ReceiverController;
import org.apache.coyote.http11.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackInterpreter {
    private static final Logger log = LoggerFactory.getLogger(TrackInterpreter.class);

    private List<Double> longitude = new ArrayList<>();
    private List<Double> latitude = new ArrayList<>();
    private List<Double> altitude = new ArrayList<>();
    private List<Integer> speed = new ArrayList<>();
    private List<Long> time = new ArrayList<>();



    public void setGPSTrack(String track) {
        String zero = track.replace('"', ' ');
        String first = zero.replace('{', ' ');
        String second = first.replace('}', ' ');
        String third = second.replaceAll("coordinates", "");
        String fourth = third.replaceAll(" ", "");
        String [] fifth = fourth.split(",");
        for(String s : fifth) {
            if(s.contains("lat")) {
                latitude.add(Double.valueOf(s.replaceAll("lat:", "")));
            }
            if(s.contains("Lon")) {
                longitude.add(Double.valueOf(s.replaceAll("lat:", "")));
            }
            if(s.contains("alt")) {
                altitude.add(Double.valueOf(s.replaceAll("lat:", "")));
            }
            if(s.contains("speed")) {
                speed.add(Integer.valueOf(s.replaceAll("lat:", "")));
            }
            if(s.contains("time")) {
                time.add(Long.valueOf(s.replaceAll("lat:", "")));
            }
        }
        logger(longitude.size() - 1);
    }
    private void logger (int i) {
        log.info("--------------------");
        log.info("longitude / широта :" + longitude.get(i));
        log.info("latitude / долгота :" + latitude.get(i));
        log.info("altitude / высота :" + altitude.get(i));
        log.info("speed / скорость :" + speed.get(i));
        Date date = new Date(time.get(i));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a");
        log.info("time / дата :" + sdf);
        log.info("====================");
    }
}
