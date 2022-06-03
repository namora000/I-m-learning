package dao.repo;

import dao.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointsRepository extends CrudRepository <Point, Integer> {
}
