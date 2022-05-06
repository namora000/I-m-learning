package jdev.dto.services;


import de.micromata.opengis.kml.v_2_2_0.*;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPSService {
    private List<Coordinate> coordinates = new ArrayList<>();

    @PostConstruct
    public void kmlRead() {
        File path = new File("tracker-core/src/main/resources/gpsTrack.kml");
        Kml kml = Kml.unmarshal(path);
        Feature feature = kml.getFeature();
        parseFeature(feature);
    }
    private void parseFeature(Feature feature) {
        if(feature != null) {
            if(feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();
                for(Feature documentFeature : featureList) {
                    if(documentFeature instanceof Placemark) {
                        Placemark placemark = (Placemark) documentFeature;
                        Geometry geometry = placemark.getGeometry();
                        parseGeometry(geometry);
                    }
                }
            }else { System.out.println("1");}
        } else { System.out.println("2");}
    }
    private void parseGeometry(Geometry geometry) {
        if(geometry != null) {
            if(geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary boundary = polygon.getOuterBoundaryIs();
                if(boundary != null) {
                    LinearRing linearRing = boundary.getLinearRing();
                    if(linearRing != null) {
                        coordinates = linearRing.getCoordinates();
                    }
                }
            } else if (geometry instanceof LineString) {
                LineString lineString = (LineString) geometry;
                coordinates = lineString.getCoordinates();

            } else if (geometry instanceof Point) {
                Point point = (Point) geometry;
                coordinates = point.getCoordinates();

            } else if (geometry instanceof MultiGeometry) {
                List<Geometry> geometries = ((MultiGeometry) geometry).getGeometry();
                for (Geometry g : geometries) {
                    if(g instanceof Polygon) {
                        Polygon polygon = (Polygon) g;
                        Boundary boundary = polygon.getOuterBoundaryIs();
                        if(boundary != null) {
                            LinearRing linearRing = boundary.getLinearRing();
                            if(linearRing != null) {
                                coordinates.addAll(linearRing.getCoordinates());
                            }
                        }
                    } else if (geometry instanceof LineString) {
                        LineString lineString = (LineString) geometry;
                        coordinates = lineString.getCoordinates();

                    } else if (geometry instanceof Point) {
                        Point point = (Point) geometry;
                        coordinates = point.getCoordinates();

                    }
                }

            }
        }
        for(Coordinate c : coordinates) {
            parseCoordinate(c);
        }
    }
    private void parseCoordinate(Coordinate coordinate) {
        if(coordinate != null) {
            System.out.println("Longitude: " +  coordinate.getLongitude());
            System.out.println("Latitude : " +  coordinate.getLatitude());
            System.out.println("Altitude : " +  coordinate.getAltitude());
            System.out.println("");
        }
    }


}
