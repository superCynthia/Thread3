package whr.main;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread Pool ģʽ �̳߳�Ӧ�� task���Զ����������������֤��
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
	 * corePoolSize���̳߳صĻ�����С�������ύһ�������̳߳�ʱ���̳߳ػᴴ��һ���߳���ִ�����񣬼�
	 *                         ʹ�������еĻ����߳��ܹ�ִ��������Ҳ�ᴴ���̣߳��ȵ���Ҫִ�е������������̳߳ػ�����Сʱ�Ͳ��ٴ�����
	 * maximumPoolSize���̳߳�����С�����̳߳�������������߳�����
	 * keepAliveTime���̻߳����ʱ�䣩���̳߳صĹ����߳̿��к󣬱��ִ���ʱ�䡣�����������ܶ࣬����ÿ������ִ�е�ʱ��Ƚ϶̣����Ե������ʱ�䣬����̵߳������ʡ�
	 * milliseconds:�̻߳����ʱ��ĵ�λ
	 * runnableTaskQueue��������У������ڱ���ȴ�ִ�е�������������С�
	 *                 [ArrayBlockingQueue\LinkedBlockingQueue\SynchronousQueue\PriorityBlockingQueue]
	 * ThreadFactory���������ô����̵߳Ĺ���������ͨ���̹߳�����ÿ�������������߳����ø�����������֡�
	 * handler�����Ͳ��ԣ��������к��̳߳ض����ˣ�˵���̳߳ش��ڱ���״̬����ô�����ȡһ�ֲ��Դ����ύ��������
	 */
	private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1,
			Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "VerfCodeSender");
					// ����Ϊ��̨�̡߳����߳̽������û��߳̽���������У�
					// ���û���û��̣߳����Ǻ�̨���̵Ļ�����ô��̨�߳���ɱ��jvm����
					// t.setDaemon(true);
					return t;
				}
			}, new ThreadPoolExecutor.DiscardPolicy());// ���Ͳ��ԣ�������������

	/**
	 * ���ɲ��·���֤����ŵ�ָ�����ֻ�����
	 * 
	 * @param msisdn
	 *            ���Ž��շ�����
	 */
	public void sendVerificationSms(final String msisdn) {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				// ����ǿ�������֤��
				int verificationCode = ThreadSpecificSecureRandom.INSTANCE.nextInt(999999);
				// ��verificationCodeת��Ϊ"000000"��ʽ������6Ϊ��λ�Զ���0
				DecimalFormat df = new DecimalFormat("000000");
				String txtVerCode = df.format(verificationCode);

				// ������֤�����
				sendSms(msisdn, txtVerCode);
			}
		};
		EXECUTOR.submit(task);
	}

	private void sendSms(String msisdn, String verificationCode) {
		System.out.println("Sending verification code " + verificationCode + " to " + msisdn);
		// ʡ����������
	}

	public void stopExecutorService() {
		EXECUTOR.shutdown();
	}
}
