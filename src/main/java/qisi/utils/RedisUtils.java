package qisi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author : ddv
 * @date : 2019/2/5 下午5:17
 */

@Component
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public boolean expire(String key, long time, TimeUnit timeUnit) {

		if (key == null || time < 0 || timeUnit == null) {
			return false;
		}

		try {
			redisTemplate.expire(key, time, timeUnit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}



}
