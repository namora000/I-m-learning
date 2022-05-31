package jdev.dto.services;

import de.micromata.opengis.kml.v_2_2_0.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
public class GPSService {
    private static final Logger log = LoggerFactory.getLogger(GPSService.class);
    private List<Coordinate> coordinates;
    private int i = 0;
    private double lat = 0.0;
    private double lon = 0.0;
    private boolean test;

    private File path;

    public GPSService () {
        test = false;
        this.path = new File("tracker-core/src/main/resources/gpsTrack.kml");
        coordinates = new ArrayList<>();
        kmlRead();
        log.info("Конструктор отработал: " + coordinates.size());
    }
    public GPSService (File path) {
        test = true;
        this.path = path;
        coordinates = new ArrayList<>();
        kmlRead();
        log.info("Конструктор отработал: " + coordinates.size());
    }

    @Autowired
    private StorageInterface storageInterface;

    @Scheduled (cron = "${cycle}")
    public void clockCycle() throws InterruptedException {
        if(i == -1) {
            log.info("Вы достигли конца маршрута, можно остановить выполнение программы.");
        } else {
            //получаем широту, долготу, азимут из массива координат
            double latGPS = coordinates.get(i).getLatitude();
            double lonGPS = coordinates.get(i).getLongitude();
            double alt = coordinates.get(i).getAltitude();
            int speed = 0;

            //получаем мгновенную скорость автомобиля в зависимости от изменения координат
            if (lon == 0.0) { //здесь производится первое присвоение реальных координат для lon и lat
                lat = latGPS;
                lon = lonGPS;
                speed = (int)getRandomCurrentSpeed(35, 42);
            } else { // здесь проверяем, движется автомобиль или стоит на местве (а вдруг пробка)
                if(lat - latGPS == 0 && lon - lonGPS == 0) {
                    lat = latGPS;
                    lon = lonGPS;
                    speed = (int)getRandomCurrentSpeed(0, 3); // если не меняются координаты, значит скорость маленькая
                } else {
                    lat = latGPS;
                    lon = lonGPS;
                    speed = (int)getRandomCurrentSpeed(35, 42);
                }
            }

            //получаем текущее время
            long time = System.currentTimeMillis();
            if(test) {
                log.info("Test string: " + lon + ", " + lat + ", " + alt + ", " + speed + ", " + time);
            } else  {
                storageInterface.setCoordinates(lon, lat, alt, speed, time);
            }
            increment_i();
        }
    }
    //имитируем изменение скорости автомобиля при поездке
    private double getRandomCurrentSpeed(double min, double max) {
        double s = (int)(Math.random()*((max-min)+1))+min;
        return s;
    }

    //имитация передвижения
    private void increment_i() {
        if(i < coordinates.size()) {
            i++;
        } else {
            i = -1;
        }
    }

    // Метод выполняет подключение KML трека и последующий запуск методов, которые парсят данный KML
    private void kmlRead() {
        Kml kml = Kml.unmarshal(path);
        Feature feature = kml.getFeature();
        parseFeature(feature);
    }
    // Метод определяет возможность работы библиотеки JAK с данным KML
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
                    } else { log.info("Функция не является меткой геопозиции");}
                }
            } else { log.info("feature не является документом библиотеки JAK");}
        } else { log.info("feature = null!!");}
    }
    /* Метод выполняет проверку того, в каком формате содержатся метки геопозиции.
       1. Метки могут быть записаны одной строкой.
       2. Метка может быть одной точкой
       3. метка может быть полигоном (не разобрался как это, возможно имеется ввиду целый квадрат между определёнными
       широтами и долготоами.)
       4. Метки могут быть массивом строк маршрутов, массивом туристических точек, массивами квадратов (полигонов)

       Вот с этой солянкой предстоит разобраться данному методу и определившись с форматом данных вытянуть координаты в
       заранее подготовленный ArrayList
     */
    private void parseGeometry(Geometry geometry) {
        if(geometry != null) {
            if(geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary boundary = polygon.getOuterBoundaryIs();
                if(boundary != null) {
                    LinearRing linearRing = boundary.getLinearRing();
                    if(linearRing != null) {
                        coordinates = linearRing.getCoordinates();
                    } else { log.info("Квадрат не имеет задыннх границ");}
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
                            } else { log.info("Границы квадрата не линеаризованы");}
                        } else { log.info("Квадрат в массиве полигонов не имеет задыннх границ");}
                    } else if (geometry instanceof LineString) {
                        LineString lineString = (LineString) geometry;
                        coordinates = lineString.getCoordinates();

                    } else if (geometry instanceof Point) {
                        Point point = (Point) geometry;
                        coordinates = point.getCoordinates();

                    }
                }
            }
        } else { log.info("Не возможно определить формат хранения координат");}
    }
}
