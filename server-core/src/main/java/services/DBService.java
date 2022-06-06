package services;

import controllers.ReceiverController;
import dao.Point;
import dao.Request;
import dao.User;
import dao.repo.PointsRepository;
import dao.repo.RequestRepository;
import dao.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService implements CommandLineRunner  {

    private List<Point> points;
    private List<User> users;
    private List<Request> requests;
    private static final Logger log = LoggerFactory.getLogger(ReceiverController.class);

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PointsRepository pointsRepository;

    public Point setPoint(Point point) {
        return pointsRepository.save(point);
    }
    public User setUser(User user) {
        return userRepository.save(user);
    }
    public Request setRequest(Request request) {
        return requestRepository.save(request);
    }


    public List<Point> getPoints() {
        return points;
    }
    public List<User> getUsers() {
        return users;
    }
    public List<Request> getRequests() {
        return requests;
    }

    public void updPoint(int id, String lat, String lon, String alt) {
        points = (List<Point>) pointsRepository.findAll();
        Point p = points.get(id-1);
        p.setLatitude(lat);
        p.setLongitude(lon);
        p.setAltitude(alt);
        pointsRepository.save(p);
    }
    public void updUser(int id, String role, String name, String pass) {
        users = (List<User>) userRepository.findAll();
        User u = users.get(id-1);
        u.setRole(role);
        u.setUsername(name);
        u.setPassword(pass);
        userRepository.save(u);
    }


    @Override
    public void run(String... args) {
        points = (List<Point>) pointsRepository.findAll();
        users = (List<User>) userRepository.findAll();

        if(points.size() == 0) {
            log.info("No records in points");
        } else {
            points.stream().forEach(point -> log.info(point.toString()));
        }
        if(users.size() == 0) {
            log.info("No records in users");
        } else {
            users.stream().forEach(user -> log.info(user.toString()));
        }

    }
}
