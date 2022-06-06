package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class PointList {

    private ArrayList<Point> points = new ArrayList<>();

    public void addPoint(Point point) {
        points.add(point);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        int i = 0;
        String json = "{\"points\":[";
        for(Point p : points) {
            try {
                json = json+p.toJson();
                if(i<points.size()) {
                    json = json+", ";
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        json = json+"]}";
        return json;
    }

}
