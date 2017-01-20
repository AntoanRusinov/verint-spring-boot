package app.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${jms.queue.destination}")
	private String destinationQueue;

	public void send(String message) {
		jmsTemplate.convertAndSend(destinationQueue, message);
	}

}