package app.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JmsClientImpl implements JmsClient {

	@Autowired
	private JmsConsumer jmsConsumer;

	@Autowired
	private JmsProducer jmsProducer;

	@Override
	public void send(String message) {
		jmsProducer.send(message);
	}

	@Override
	public String receive() {
		return jmsConsumer.receive();
	}

}