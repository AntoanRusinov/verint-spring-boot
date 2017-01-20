package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.messaging.JmsClient;

@RestController
@RequestMapping(value = "/jms", method = RequestMethod.GET)
public class WebController {

	@Autowired
	private JmsClient jsmClient;

	@RequestMapping(value = "/produce")
	public String produce(@RequestParam("message") String message) {
		jsmClient.send(message);
		return "Send message: " + message;
	}

	@RequestMapping(value = "/receive")
	public String receive() {
		return "Received: " + jsmClient.receive();
	}
}