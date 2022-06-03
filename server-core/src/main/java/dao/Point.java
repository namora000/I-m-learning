package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.*;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="points")
public class Point {

    @Id
    @GeneratedValue(strategy = AUTO)
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
                ", time="+time+" }";
    }
}
