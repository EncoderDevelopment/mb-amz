package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.entity.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

}
