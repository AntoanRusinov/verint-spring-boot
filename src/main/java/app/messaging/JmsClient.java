package app.messaging;

public interface JmsClient {

	public void send(String msg);

	public String receive();

}