package qisi.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.Topic;

/**
 * @author : ddv
 * @date : 2018/10/31 下午3:22
 */
@Configuration
@EnableJms
public class Jms {
	@Value("spring-jms")
	public String Topic = "";

	@Bean
	public Topic topic1() {
		return new ActiveMQTopic(Topic);
	}

	/**
	 * topic模式的ListenerContainer
	 *
	 * @return
	 */
	@Bean
	public JmsListenerContainerFactory<?> topicListenerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		ActiveMQConnectionFactory connectionFactor = new ActiveMQConnectionFactory();
		factory.setPubSubDomain(true);
		factory.setConnectionFactory(connectionFactor);
		return factory;
	}

	/**
	 * queue模式的ListenerContainer
	 *
	 * @return
	 */
	@Bean
	public JmsListenerContainerFactory<?> queueListenerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		ActiveMQConnectionFactory connectionFactor = new ActiveMQConnectionFactory();
		factory.setPubSubDomain(false);
		factory.setConnectionFactory(connectionFactor);
		return factory;
	}
}

