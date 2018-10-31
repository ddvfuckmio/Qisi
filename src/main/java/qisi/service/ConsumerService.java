//package qisi.service;
//
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * @author : ddv
// * @date : 2018/10/31 下午2:31
// */
//
//@Component
//public class ConsumerService {
//
//	@JmsListener(destination = "spring-jms",containerFactory = "topicListenerFactory")
//	public void receiveTopic(Map map){
//		System.out.println("Topic Consumer1:"+map.get("code"));
//	}
//}
