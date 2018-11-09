package qisi.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import qisi.bean.jms.CodeMessage;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

		((ActiveMQConnectionFactory) connectionFactory).setUserName("qisi");
		((ActiveMQConnectionFactory) connectionFactory).setPassword("fablab151");
		((ActiveMQConnectionFactory) connectionFactory).setBrokerURL("tcp://192.168.50.2:61616");

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
				CodeMessage codeMessage = new CodeMessage();
				StreamMessage streamMessage = (StreamMessage) messageConsumer.receive();

				codeMessage.setCodeId(streamMessage.readString());
				codeMessage.setCode(streamMessage.readString());
				codeMessage.setFirstCode(streamMessage.readString());
				codeMessage.setSecondCode(streamMessage.readString());
				codeMessage.setMaxTime(streamMessage.readInt());
				codeMessage.setMaxMemory(streamMessage.readInt());
				codeMessage.setTotalCases(streamMessage.readInt());
				codeMessage.setType(streamMessage.readString());

				List<String> inputs = new ArrayList<>(codeMessage.getTotalCases());
				List<String> outputs = new ArrayList<>(codeMessage.getTotalCases());

				for (int i = 0; i < codeMessage.getTotalCases(); i++) {
					inputs.add(streamMessage.readString());
					outputs.add(streamMessage.readString());
				}

				codeMessage.setInputs(inputs);
				codeMessage.setOutputs(outputs);

				System.out.println(codeMessage);
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
		for (; ; ) {
			consumer("QISI.commit", "e089d6b3c9ce45bdbe161daeceead668");
		}
	}
}
