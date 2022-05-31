package jdev.dto;
import jdev.dto.services.GPSService;
import java.io.File;


public class GPSServiceTest{
    public static void main (String [] args) throws InterruptedException {
        File test = new File("tracker-core/src/test/resources/gpsTrack.kml");

        if(test.exists()) {
            System.out.println("Файл существует");
            GPSService gpsService = new GPSService(test);
            while (true) {
                gpsService.clockCycle();
                Thread.sleep(1000);
            }
        } else {
            System.out.println("Файл не существует");

        }
    }
}