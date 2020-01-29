package api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/android")
public class Android {
	
	@GetMapping(value = "/access")
	public ResponseEntity<String> access() {
		return new ResponseEntity<String>("Acesso validado",HttpStatus.OK);
	}

}
