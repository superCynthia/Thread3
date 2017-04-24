package whr.main;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread Pool 模式 线程池应用 task：自动生成随机数短信验证码
 * 
 * @author superCynthia
 *
 */
public class SmsVerficationCodeSender {

	/*
	 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long
	 * keepAliveTime, TimeUnit milliseconds,BlockingQueue runnableTaskQueue,
	 * ThreadFactory threadFactory, RejectedExecutionHandler handler);
	 * 
	 * corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即
	 *                         使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。
	 * maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。
	 * keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率。
	 * milliseconds:线程活动保持时间的单位
	 * runnableTaskQueue（任务队列）：用于保存等待执行的任务的阻塞队列。
	 *                 [ArrayBlockingQueue\LinkedBlockingQueue\SynchronousQueue\PriorityBlockingQueue]
	 * ThreadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
	 * handler（饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。
	 */
	private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1,
			Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "VerfCodeSender");
					// 设置为后台线程。主线程结束后，用户线程将会继续运行，
					// 如果没有用户线程，都是后台进程的话，那么后台线程自杀，jvm结束
					// t.setDaemon(true);
					return t;
				}
			}, new ThreadPoolExecutor.DiscardPolicy());// 饱和策略：不处理，丢弃掉

	/**
	 * 生成并下发验证码短信到指定的手机号码
	 * 
	 * @param msisdn
	 *            短信接收方号码
	 */
	public void sendVerificationSms(final String msisdn) {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				// 生成强随机数验证码
				int verificationCode = ThreadSpecificSecureRandom.INSTANCE.nextInt(999999);
				// 将verificationCode转换为"000000"格式，不足6为高位自动补0
				DecimalFormat df = new DecimalFormat("000000");
				String txtVerCode = df.format(verificationCode);

				// 发送验证码短信
				sendSms(msisdn, txtVerCode);
			}
		};
		EXECUTOR.submit(task);
	}

	private void sendSms(String msisdn, String verificationCode) {
		System.out.println("Sending verification code " + verificationCode + " to " + msisdn);
		// 省略其他代码
	}

	public void stopExecutorService() {
		EXECUTOR.shutdown();
	}
}
