package qisi.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

/**
 * @author : ddv
 * @date : 2018/11/12 上午9:41
 */

public class Dozer {

	/**
	 * 基于反射机制实现的Bean对象属性的拷贝
	 */
	public static <T, S> S getBean(T t, Class<S> s) {
		S rs = null;
		try {
			rs = s.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Mapper mapper = DozerBeanMapperBuilder.buildDefault();
		rs = mapper.map(t, s);

		return rs;
	}

}
