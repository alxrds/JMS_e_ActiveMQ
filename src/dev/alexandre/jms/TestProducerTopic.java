package dev.alexandre.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import dev.alexandre.model.Order;
import dev.alexandre.model.OrderFactory;


public class TestProducerTopic {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topic = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topic);
		
		Order order = new OrderFactory().geraPedidoComValores();
		
		Message message = session.createObjectMessage(order);
		message.setBooleanProperty("ebook", false);
		producer.send(message);
	
		session.close();
		connection.close();
		context.close();

	}

}
