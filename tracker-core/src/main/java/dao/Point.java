package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.*;

@Entity
@Table(name="points")
public class Point {

    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "altitude")
    private String altitude;
    @Column(name = "speed")
    private String speed;
    @Column(name = "time")
    private String time;
    @Column(name = "car")
    private String car;

    public void setId(int id) {
        this.id = id;
    }
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
    public void setCar (String car) {
        this.car = car;
    }

    public int getId() {
        return id;
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
    public String getCar() { return car; }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "Point{ id="+id+
                ", latitude="+latitude+
                ", longitude="+longitude+
                ", altitude="+altitude+
                ", speed="+speed+
                ", time="+time+
                ", car="+car+" }";
    }
}
