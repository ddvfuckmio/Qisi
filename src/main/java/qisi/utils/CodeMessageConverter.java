package qisi.utils;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.support.GenericMessage;
import qisi.bean.jms.CodeMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;
import java.util.List;

/**
 * @author : ddv
 * @date : 2018/11/7 上午11:48
 */

public class CodeMessageConverter implements MessageConverter {
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		StreamMessage streamMessage = session.createStreamMessage();
		GenericMessage genericMessage = (GenericMessage) object;
		CodeMessage codeMessage = (CodeMessage) genericMessage.getPayload();
		int count = codeMessage.getTotalCases();

		streamMessage.writeString(codeMessage.getCodeId());
		streamMessage.writeString(codeMessage.getCode());
		streamMessage.writeString(codeMessage.getFirstCode());
		streamMessage.writeString(codeMessage.getSecondCode());
		streamMessage.writeInt(codeMessage.getMaxTime());
		streamMessage.writeInt(codeMessage.getMaxMemory());
		streamMessage.writeInt(codeMessage.getTotalCases());

		List<String> inputs = codeMessage.getInputs();
		List<String> outputs = codeMessage.getOutputs();

		for (int i = 0; i < count; i++) {
			streamMessage.writeString(inputs.get(i));
			streamMessage.writeString(outputs.get(i));
		}
		return streamMessage;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		return message;
	}

}
