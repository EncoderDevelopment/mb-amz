package api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.entity.Point;
import api.repository.PointRepository;

@RestController
@RequestMapping("/api")
public class PointControl {

	 @Autowired
	 PointRepository pointRepository;
	 
	 @GetMapping("/getAllPoints")
	 public List<Point> getAllPoints() {
	     return pointRepository.findAll();
	 }
	 
}
