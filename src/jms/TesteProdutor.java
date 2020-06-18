package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.activemq.ScheduledMessage;

public class TesteProdutor {

	@SuppressWarnings("resource")
	public static void main(String[] args)  throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);

		producer.setDeliveryMode(2);
		for (int i = 10; i <= 100; i += 10) {
			Message message = session.createTextMessage("<pedido><id>"+i+"</id></pedido>");
			long time = i * 1000;
			message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
			
			producer.send(message);
		}
		
		new Scanner(System.in).nextLine();
		session.close();
		connection.close();
		context.close();

	}

}
