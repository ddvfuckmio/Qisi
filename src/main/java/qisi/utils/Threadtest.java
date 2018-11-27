package qisi.utils;

/**
 * @author : ddv
 * @date : 2018/11/27 上午10:19
 */

import java.util.concurrent.*;


public class Threadtest {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		ExecutorService exec = Executors.newFixedThreadPool(1);

		Callable<String> call = new Callable<String>() {
			@Override
			public String call() throws Exception {
				//开始执行耗时操作
				Thread.sleep(1000 * 5);
				return "线程执行完成.";
			}
		};

		try {
			Future<String> future = exec.submit(call);
			String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS);
			System.out.println("任务成功返回:" + obj);
		} catch (TimeoutException ex) {
			System.out.println("处理超时啦....");
			ex.printStackTrace();
		} catch (Exception e) {
			System.out.println("处理失败.");
			e.printStackTrace();
		}
		// 关闭线程池
		exec.shutdown();
	}
}
