package qisi.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	protected static final Logger logger = LoggerFactory.getLogger(Jms.class);

	private static Session getTopicSession() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		((ActiveMQConnectionFactory) connectionFactory).setUserName("admin");
		((ActiveMQConnectionFactory) connectionFactory).setPassword("admin");
		((ActiveMQConnectionFactory) connectionFactory).setBrokerURL("tcp://127.0.0.1:61616");

		Connection connection = connectionFactory.createConnection();
		connection.start();
		return connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	}

	public static void checkSendJms(String queueName) {

		Session session;
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
				codeMessage.setType(streamMessage.readString());
				codeMessage.setTotalCases(streamMessage.readInt());

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


	}

	public static void produce(String queueName, String codeId, boolean pass) throws JMSException {
		Session session = getTopicSession();
		Queue queue = session.createQueue(queueName);
		MessageProducer messageProducer = session.createProducer(queue);
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("codeId", codeId);
		mapMessage.setBoolean("pass", pass);
		messageProducer.send(mapMessage);
		session.commit();
		messageProducer.close();
		session.close();
		return;
	}

	public static boolean consumer(String queueName, String codeId) {
		Session session;
		Queue queue;
		try {
			session = getTopicSession();
			queue = session.createQueue(queueName);
			MessageConsumer messageConsumer = session.createConsumer(queue);
			logger.debug("等待评测系统评测!-->");
			while (true) {
				MapMessage map = (MapMessage) messageConsumer.receive();
				if (map.getString("codeId").equals(codeId)) {
					session.close();
					logger.debug("评测结果匹配!");
					return map.getBoolean("pass");
				} else {
					logger.debug("评测结果不匹配!");
				}
			}
		} catch (Exception e) {
			logger.debug("评测出错!-->:", e.getStackTrace());
		}

		return false;
	}

	public static void main(String[] args) throws JMSException, InterruptedException {
//		checkSendJms("commit");
//		produce("receive", "6a9827d044504c5faf00103a2f0c1d7c", true);
		boolean sign = consumer("receive", "12121");
		System.out.println(sign);
	}
}
