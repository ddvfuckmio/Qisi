package qisi.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @date : 2018/10/27 下午10:58
 * port : 8161
 * username : admin
 * password : admin
 */

public class Jms {

	private static Session getTopicSession() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.start();
		return connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	}

	public static Map<String, Object> consumer(String queueName, String codeId) {

		Session session = null;
		Map<String, Object> map = new HashMap<>(16);
		try {
			session = getTopicSession();
			Queue queue = session.createQueue(queueName);
			MessageConsumer messageConsumer = session.createConsumer(queue);

			while (true) {
				System.out.println("监听开始...");
				MapMessage mapMessage = (MapMessage) messageConsumer.receive();
				if (mapMessage != null) {
					if (codeId.equals(mapMessage.getString("codeId"))) {
						map.put("codeId", mapMessage.getString("codeId"));
						map.put("pass", mapMessage.getBoolean("pass"));
						session.close();
						System.out.println("return...");
						return map;
					} else {
						System.out.println("当前接受的消息不匹配...");
					}
				} else {
					System.out.println("等待评测中...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void produce(String queueName, String codeId, boolean pass) throws JMSException {
		Session session = null;

		session = getTopicSession();
		Queue queue = session.createQueue(queueName);
		MessageProducer messageProducer = session.createProducer(queue);
		System.out.println(pass);
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("codeId", codeId);
		mapMessage.setBoolean("pass", pass);
		messageProducer.send(mapMessage);

		session.commit();


	}

	public static void main(String[] args) throws JMSException, InterruptedException {
//		produce("receive", "e089d6b3c9ce45bdbe161daeceead668", true);
		for(;;) {
			consumer("receive", "e089d6b3c9ce45bdbe161daeceead668");
		}
	}
}
