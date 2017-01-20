package tests.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.Application;
import app.messaging.JmsClient;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = {
		Application.class/* , WebSecurityConfig.class */ })
public class ActiveMqTest {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Autowired
	private JmsClient jmsClient;

	@Test
	public void sendTestMessage() throws InterruptedException {
		final String testMessage = "Test Message";
		this.jmsClient.send(testMessage);
		Thread.sleep(1000L);
		assertThat(this.jmsClient.receive().contains(testMessage)).isTrue();
	}

}