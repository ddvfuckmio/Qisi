package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import qisi.bean.jms.CodeMessage;
import qisi.utils.CodeMessageConverter;

import javax.jms.*;
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

	public void sendStreamMessage(Destination destination, CodeMessage codeMessage, MessageConverter codeMessageConverter) {
		jmsMessagingTemplate.setJmsMessageConverter(codeMessageConverter);
		jmsMessagingTemplate.convertAndSend(destination, codeMessage);
	}

}
