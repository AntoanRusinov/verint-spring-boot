package app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/docker1")
public class DockerController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String welcome() {
		return "You called app 1";
	}

	@RequestMapping(value = "/call-1-to-2", method = RequestMethod.GET)
	public ResponseEntity<?> callTheOtherApp() {

		final String url = "http://localhost:8080/docker2/hello";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);

		System.out.println(result);

		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
}
