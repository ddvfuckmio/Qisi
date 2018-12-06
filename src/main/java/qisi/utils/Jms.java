package qisi.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import qisi.bean.jms.CodeMessage;
import qisi.controller.CourseController;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/27 下午10:58
 * port : 8161
 * username : admin
 * password : admin
 */

public class Jms {
	private static final String MESSAGE_PRE = "--->";

	private static final String DEVELOP_URL = "tcp://127.0.0.1:61616";
	private static final String DEVELOP_USERNAME = "admin";
	private static final String DEVELOP_PASSWORD = "admin";

	private static final String PRODUCTION_URL = "tcp://192.168.50.2:61616";
	private static final String PRODUCTION_USERNAME = "qisi";
	private static final String PRODUCTION_PASSWORD = "fablab151";

	private static Session getTopicSession() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		((ActiveMQConnectionFactory) connectionFactory).setUserName(DEVELOP_USERNAME);
		((ActiveMQConnectionFactory) connectionFactory).setPassword(DEVELOP_PASSWORD);
		((ActiveMQConnectionFactory) connectionFactory).setBrokerURL(DEVELOP_URL);

		Connection connection = connectionFactory.createConnection();
		connection.start();
		return connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	}

	/**
	 * 检查发送的commitCode消息
	 *
	 * @param queueName 队列名
	 */
	public static void checkSendJms(String queueName) {

		Session session;
		try {
			session = getTopicSession();
			Queue queue = session.createQueue(queueName);
			MessageConsumer messageConsumer = session.createConsumer(queue);

			while (true) {
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
	}

	/**
	 * 拉取评测结果
	 *
	 * @param queueName 队列名
	 * @param codeId    codeId
	 * @return 是否通过
	 */
	public static boolean consumer(String queueName, String codeId) {
		Session session = null;
		Queue queue = null;

		try {
			session = getTopicSession();
			queue = session.createQueue(queueName);
			MessageConsumer messageConsumer = session.createConsumer(queue);
			while (true) {
				MapMessage map = (MapMessage) messageConsumer.receive();
				if (map.getString("codeId").equals(codeId)) {
					session.close();
					System.out.println("代码评测完成!");
					return map.getBoolean("pass");
				} else {
					System.out.println(("评测结果不匹配!"));
				}
			}
		} catch (JMSException e) {
			System.out.println("评测超时返回");
			return false;
		}


	}

	public static void main(String[] args) throws JMSException, InterruptedException {
//		checkSendJms("commit");
		produce("receive", "a32bcab2a5394766828d888fdbb5e012", true);
//		boolean sign = consumer("receive", "a32bcab2a5394766828d888fdbb5e012");
//		System.out.println(sign);
	}
}
