package whr.main;

public class Test {

	public static void main(String[] args) {
		SmsVerficationCodeSender client = new SmsVerficationCodeSender();
		client.sendVerificationSms("18912345678");
		client.sendVerificationSms("18712345679");
		client.sendVerificationSms("18612345676");
		
		//若为守护线程（后台线程，main执行完后jvm结束），要看到控制台结果的话需要增加main的运行时间
//		try {
			//留出足够时间使线程池中的任务执行完
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		client.stopExecutorService();
	}

}
