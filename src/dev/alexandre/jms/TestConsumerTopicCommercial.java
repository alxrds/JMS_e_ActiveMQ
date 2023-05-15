package dev.alexandre.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import dev.alexandre.model.Order;


public class TestConsumerTopicCommercial {

	public static void main(String[] args) throws Exception{
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
		
		Connection connection = factory.createConnection();
		connection.setClientID("comercial");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topic, "assinatura");
		
		consumer.setMessageListener(new MessageListener(){
			@Override
			public void onMessage(Message message) {
				
				 ObjectMessage objectMessage  = (ObjectMessage)message;
				 
		        try{
		        	Order pedido = (Order) objectMessage.getObject();
		            System.out.println(pedido.getCodigo());
		        } catch(JMSException e){
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
