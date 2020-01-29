package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.entity.Indicator;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long> {	

}
