package tests.active.mq;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.Application;
import app.messaging.JmsClient;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = { Application.class })
public class ActiveMqProduceAndConsume {

	@Autowired
	private JmsClient jmsClient;

	private BrokerService broker;

	private String message = "myMessage";

	@Before
	public void setup() throws Exception {

		broker = new BrokerService();
		broker.addConnector("tcp://localhost:61616");
		broker.setPersistent(false);

		ManagementContext managementContext = new ManagementContext();
		managementContext.setCreateConnector(true);

		broker.setManagementContext(managementContext);
		broker.start();

		Assert.assertTrue(broker.isStarted());
	}

	@Test
	public void sendTestMessage() throws InterruptedException {
		this.jmsClient.send(message);
		assertThat(this.jmsClient.receive().contains(message)).isTrue();
	}

	@Test
	public void sendAndReceiveMessages() {
		long start = System.currentTimeMillis();
		for (int i = 0; i <= 100; i++) {
			this.jmsClient.send(message);
			String receivedMessage = this.jmsClient.receive();
			Assert.assertTrue(receivedMessage.equals(message));
		}
		// around 17.5 seconds
		System.err.println("Finished sending! Completed in: " + (System.currentTimeMillis() - start) / 1000.0);
	}

	@After
	public void shutdown() throws Exception {
		broker.stop();
		Assert.assertTrue(broker.isStopped());
	}

}