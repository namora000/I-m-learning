package jdev.dto.services;

import dao.Point;

import java.util.concurrent.BlockingDeque;

public interface StorageInterface {
    public void setCoordinates(double latitude, double longitude, double altitude, int speed, long time) throws InterruptedException;
    public BlockingDeque<Point> getCoordinates() throws InterruptedException;
}
