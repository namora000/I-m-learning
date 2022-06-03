package jdev.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PointTest {

    private String lat = "38.760888";
    private String lon = "44.670992";
    private String alt = "97.0";
    private String speed = "40";
    private String time = Long.toString(System.currentTimeMillis());
    private String json = "{\"latitude\":\""+lat+"\",\"longitude\":\""+lon+"\",\"altitude\":\""+alt+"\",\"speed\":\""+speed+"\",\"time\":\""+time+"\"}";

    @Test
    public void encodeDto() throws Exception {
        Point point = new Point();
        point.setLatitude(lat);
        point.setLongitude(lon);
        point.setAltitude(alt);
        point.setSpeed(speed);
        point.setTime(time);
        assertTrue(point.toJson().contains(lat));
        assertTrue(point.toJson().contains(lon));
        assertTrue(point.toJson().contains(alt));
        assertTrue(point.toJson().contains(speed));
        assertTrue(point.toJson().contains(time));
        assertEquals(json, point.toJson());
        assertEquals(json, point.toString());
        System.out.println(point.toJson());
        System.out.println(point.toString());
    }
    @Test
    public void decodeDto() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Point dto = mapper.readValue(json, Point.class);
    }

}