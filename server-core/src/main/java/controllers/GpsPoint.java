package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GpsPoint {
    private String longitude;
    private String latitude;
    private String altitude;
    private String speed;
    private String time;

    public void setLongitude (String longitude) {
        this.longitude = longitude;
    }
    public void setLatitude (String latitude) {
        this.latitude = latitude;
    }
    public void setAltitude (String altitude) {
        this.altitude = altitude;
    }
    public void setSpeed (String speed) {
        this.speed = speed;
    }
    public void setTime (String time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getAltitude() {
        return altitude;
    }
    public String getSpeed() {
        return speed;
    }
    public String getTime() {
        return time;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "{" +
                "\"coordinates\": {" +
                "\"lat\":" + "\"" + latitude + "\", " +
                "\"lon\":" + "\"" + longitude + "\", " +
                "\"alt\":" + "\"" + altitude + "\", " +
                "\"speed\":" + "\"" + speed + "\", " +
                "\"time\":" + "\"" + time + "\"" +
                "}" +
                "}";
    }
}
