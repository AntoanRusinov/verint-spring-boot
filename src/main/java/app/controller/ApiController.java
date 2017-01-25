package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

	@RequestMapping(value = "/ping")
	private String ping() {
		return "Page accessible!";
	}
	
	@RequestMapping(value = "/haha")
	private String smile() {
		return ":D";
	}
	
}