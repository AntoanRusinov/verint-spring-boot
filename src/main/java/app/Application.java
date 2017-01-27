package app;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJms
@ComponentScan
@EnableSwagger2
@SpringBootApplication
// @EnableAutoConfiguration
public class Application {

	public static void main(String[] args) throws IOException {

		// run dockerized activeMQ instance to handle messages
//		Runtime.getRuntime().exec("docker run -p 61616:61616 -p 8161:8161 -t webcenter/activemq");

		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket swaggerSpringMVCPlugin() {
		return new Docket(DocumentationType.SWAGGER_2);
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		JettyEmbeddedServletContainerFactory jetty = new JettyEmbeddedServletContainerFactory();
		jetty.setPort(9000);
		jetty.setSessionTimeout(10, TimeUnit.MINUTES);
		jetty.setPersistSession(true);
		return jetty;
	}

}