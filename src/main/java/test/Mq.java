package test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;

/**
 * @author : ddv
 * @date : 2018/10/27 下午10:58
 * port : 8161
 * username : admin
 * password : admin
 */

public class Mq {
	public static void main(String[] args) throws JMSException, InterruptedException {
//		produce("msg");

		consumer("msg");

		return;

	}

	private static Session getSession() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.start();
		return connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	}

	private static void consumer(String queueName) throws JMSException {
		int index = 0;
		Session session = getSession();
		Queue queue = session.createQueue(queueName);
		MessageConsumer messageConsumer = session.createConsumer(queue);
		while (true) {
			MapMessage mapMessage = (MapMessage) messageConsumer.receive();

			if (mapMessage != null) {
				String value = (String) mapMessage.getObject(String.valueOf(index));
				System.out.println(value);
				index++;
			} else {
				break;
			}
		}
		return;
	}

	private static void produce(String queueName) throws JMSException, InterruptedException {
		Session session = getSession();
		Queue queue = session.createQueue(queueName);
		MessageProducer messageProducer = session.createProducer(queue);

		for (int i = 0; i < 10; i++) {
			Thread.sleep(1000);
			System.out.println(i);
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setObject(String.valueOf(i), String.valueOf(i));
			messageProducer.send(mapMessage);
		}
		// 提交操作
		session.commit();
	}
}
