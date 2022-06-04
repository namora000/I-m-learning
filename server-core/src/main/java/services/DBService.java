package services;

import controllers.ReceiverController;
import dao.Point;
import dao.User;
import dao.repo.PointsRepository;
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
    private static final Logger log = LoggerFactory.getLogger(ReceiverController.class);


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

    public List<Point> getPoints() {
        return points;
    }
    public List<User> getUsers() {
        return users;
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
