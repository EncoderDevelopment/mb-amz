package api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.entity.User;
import api.repository.UserRepository;
import api.response.ResponseError;
import api.service.UserService;

@RestController
@RequestMapping("/register")
public class RegisterControl {

	 @Autowired
	 UserRepository userRepository;
	 
	 @Autowired
	 UserService userService;

	 @PostMapping(value = "/user")
	 public ResponseEntity<List<ResponseError>> register(@RequestBody User user) {		 
	     return userService.validateNewUser(user);
	 }
}
