package api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.entity.User;
import api.response.ResponseError;
import api.response.ResponseUser;
import api.service.UserService;

@RestController
@RequestMapping("/api")
public class UserControl {

	 @Autowired
	 UserService userService;
	
	 //sample: http://localhost:8080/api/login?email=46ba9d9e87eafb1cb338498c35b0e925&password=passwordEncoder.encode("3d3be52677dec456a1272f4c1c4b8cf7")
	@PostMapping(value = "/login", params = { "nick", "password" })
	public ResponseEntity<ResponseUser> login(@RequestParam("nick") String nick,
			@RequestParam("password") String password) {
		return userService.loginValidate(nick, password);
	}
	
	 @PostMapping(value = "/register")
	 public ResponseEntity<List<ResponseError>> register(@RequestBody User user) {		 
	     return userService.save(user);    
	 }

	 @PostMapping(value = "/code")
	 public ResponseEntity<ResponseUser> code(@RequestBody String code) {		 
	     return userService.codeValidated(code);    
	 }
}
