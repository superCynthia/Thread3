package whr.main;

public class Test {

	public static void main(String[] args) {
		SmsVerficationCodeSender client = new SmsVerficationCodeSender();
		client.sendVerificationSms("18912345678");
		client.sendVerificationSms("18712345679");
		client.sendVerificationSms("18612345676");
		
		//��Ϊ�ػ��̣߳���̨�̣߳�mainִ�����jvm��������Ҫ��������̨����Ļ���Ҫ����main������ʱ��
//		try {
			//�����㹻ʱ��ʹ�̳߳��е�����ִ����
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		client.stopExecutorService();
	}

}
