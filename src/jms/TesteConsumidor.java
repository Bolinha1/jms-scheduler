package jms;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidor {

	@SuppressWarnings("resource")
	public static void main(String[] args)  throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		Message message = consumer.receive();
		
		consumer.setMessageListener((MessageListener) new MessageListener() {
			@Override
			public void onMessage(Message message)
			{
				TextMessage textMessage  = (TextMessage)message;
			    try {
					System.out.println(textMessage.getText());
					System.out.println(LocalTime.now());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		new Scanner(System.in).nextLine();
		session.close();
		connection.close();
		context.close();

	}

}
