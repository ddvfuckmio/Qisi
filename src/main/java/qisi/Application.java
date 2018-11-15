package qisi;

/**
 * @author : ddv
 * @date   : 2018/10/24 上午11:47
 */

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class Application {

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 创建fastJson对象
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

		FastJsonConfig config = new FastJsonConfig();
		// 设置是否需要格式化
		config.setSerializerFeatures(SerializerFeature.PrettyFormat);
		converter.setFastJsonConfig(config);
		return new HttpMessageConverters(converter);
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
