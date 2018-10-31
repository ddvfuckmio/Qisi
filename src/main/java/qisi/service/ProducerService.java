package qisi.service;

import org.apache.activemq.command.ActiveMQTempTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @date : 2018/10/31 上午11:34
 */

@Service("producer")
public class ProducerService {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	public void sendMessage(Destination destination, Map map) {
		jmsMessagingTemplate.convertAndSend(destination, map);
	}


}
