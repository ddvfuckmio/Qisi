package qisi.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author : ddv
 * @date : 2018/11/2 上午9:53
 */

public class Main {
	private static volatile int i = 0;

	public static void main(String[] args) throws InterruptedException {
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
				.build();
		ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 10,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


		singleThreadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
		singleThreadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
		singleThreadPool.shutdown();


	}
}
