package whr.main;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Thread Specific Storage ģʽ (�߳����д洢)
 * ǿ�����������
 * 
 * @author superCynthia
 *
 */
public class ThreadSpecificSecureRandom {

	// ����ģʽ��ֻ��һ��Ψһʵ��
	public static final ThreadSpecificSecureRandom INSTANCE = new ThreadSpecificSecureRandom();

	// SECURE_RANDOM:ģʽ��ɫΪThreadSpecificStorage.TSObjectProxy
	// SecureRandom��ģʽ��ɫΪThreadSpecificStorage.TSObject
	private static final ThreadLocal<SecureRandom> SECURE_RANDOM = new ThreadLocal<SecureRandom>() {

		@Override
		protected SecureRandom initialValue() {
			SecureRandom srnd;
			try {
				// ǿ�����������������
				srnd = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				srnd = new SecureRandom();
			}
			return srnd;
		}
	};

	// ˽�й�����
	private ThreadSpecificSecureRandom() {
	}

	public int nextInt(int upperBound) {
		SecureRandom secureRnd = SECURE_RANDOM.get();
		return secureRnd.nextInt(upperBound);
	}

	public void setSeed(long seed) {
		SecureRandom secureRnd = SECURE_RANDOM.get();
		secureRnd.setSeed(seed);
	}
}
