package qisi.utils;

import org.apache.activemq.ActiveMQConnectionFactory;

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

public class JmsUtil {

	private static Session getTopicSession() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.start();
		return connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	}

	public static Map<String, String> consumer(String queueName, String codeId) {
		Session session = null;
		Map<String, String> map = new HashMap<>(16);
		try {
			session = getTopicSession();
			Queue queue = session.createQueue(queueName);
			MessageConsumer messageConsumer = session.createConsumer(queue);

			while (true) {
				MapMessage mapMessage = (MapMessage) messageConsumer.receive();
				if (mapMessage != null) {
					if (codeId.equals(mapMessage.getString("codeId"))) {
						map.put("codeId", mapMessage.getString("codeId"));
						map.put("code", mapMessage.getString("code"));
						map.put("exerciseId", mapMessage.getString("exerciseId"));
						return map;
					}
				} else {
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void produce(String queueName) throws JMSException {
		Session session = getTopicSession();
		Queue queue = session.createQueue(queueName);
		MessageProducer messageProducer = session.createProducer(queue);

		for (int i = 0; i < 10; i++) {
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setObject("code", i);
			messageProducer.send(mapMessage);
		}
		// 提交操作
		session.commit();
	}

	public static void main(String[] args) throws JMSException, InterruptedException {
		produce("commit");
//		consumer("jms");
	}
}
