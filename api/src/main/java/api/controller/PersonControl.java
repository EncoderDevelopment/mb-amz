package api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.entity.Person;
import api.repository.PersonRepository;

@RestController
@RequestMapping("/api")
public class PersonControl {

	 @Autowired
	 PersonRepository personRepository;
	 
	 @GetMapping("/getAllPersons")
	 public List<Person> getAllPersons() {
	     return personRepository.findAll();
	 }
	 
}
